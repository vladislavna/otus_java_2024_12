package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
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
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
                    List<T> entities = new ArrayList<T>();
                    try {
                        while (resultSet.next()) {
                            entities.add(createEntity(resultSet));
                        }
                        return entities;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getInsertSql(), getFieldsWithoutId(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getUpdateSql(), List.of(getFieldsWithoutId(object), getId(object)));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createEntity(ResultSet resultSet) {
        try {
            List<Object> initArgs = new ArrayList<>();
            for (Field field : entityClassMetaData.getAllFields()) {
                initArgs.add(resultSet.getObject(field.getName()));
            }
            return entityClassMetaData.getConstructor().newInstance(initArgs.toArray());
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldsWithoutId(T object) {
        try {
            List<Object> fieldsWithoutId = new ArrayList<>();
            for (var field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                fieldsWithoutId.add(field.get(object));
            }
            return fieldsWithoutId;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private Object getId(T object) {
        try {
            var fieldId = entityClassMetaData.getIdField();
            fieldId.setAccessible(true);
            return fieldId.get(object);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
