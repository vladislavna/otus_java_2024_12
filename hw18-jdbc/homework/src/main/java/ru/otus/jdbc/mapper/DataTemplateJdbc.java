package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exception.DataTemplateJdbcException;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings({"java:S1068", "java:S3011"})
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return createEntity(resultSet);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateJdbcException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
                    List<T> entities = new ArrayList<>();
                    try {
                        while (resultSet.next()) {
                            entities.add(createEntity(resultSet));
                        }
                        return entities;
                    } catch (SQLException e) {
                        throw new DataTemplateJdbcException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            List<Object> listObjects = listFieldsToListObjects(object);
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), listObjects);
        } catch (Exception e) {
            throw new DataTemplateJdbcException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            List<Object> listObjects = listFieldsToListObjects(object);
            dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getUpdateSql(),
                    List.of(listObjects, entityClassMetaData.getIdField()));
        } catch (Exception e) {
            throw new DataTemplateJdbcException(e);
        }
    }

    private T createEntity(ResultSet resultSet) {
        try {
            Constructor<T> constructor = entityClassMetaData.getConstructor();
            T object = constructor.newInstance();
            List<Field> allFields = entityClassMetaData.getAllFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
            return object;
        } catch (Exception e) {
            throw new DataTemplateJdbcException(e);
        }
    }

    private List<Object> listFieldsToListObjects(T object) {
        try {
            List<Object> listObjects = new ArrayList<>();
            List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
            for (Field field : fieldsWithoutId) {
                field.setAccessible(true);
                listObjects.add(field.get(object));
            }
            return listObjects;
        } catch (Exception e) {
            throw new DataTemplateJdbcException(e);
        }
    }
}
