package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.utils.Constants;

/** Создает SQL - запросы */
@SuppressWarnings("java:S1068")
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    private String selectAllSql;
    private String selectByIdSql;
    private String insertSql;
    private String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.selectAllSql = getSelectAllSql();
        this.selectByIdSql = getSelectByIdSql();
        this.insertSql = getInsertSql();
        this.updateSql = getUpdateSql();
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName()).intern();
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                        "select * from %s where %s = ?",
                        entityClassMetaData.getName(),
                        entityClassMetaData.getIdField().getName())
                .intern();
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String fieldsWithoutIdString =
                fieldsWithoutId.stream().map(Field::getName).collect(Collectors.joining(Constants.DELIMITER));
        String questions = fieldsWithoutId.stream()
                .map(field -> Constants.QUESTION_MARK)
                .collect(Collectors.joining(Constants.DELIMITER));

        return String.format(
                        "insert into %s ( %s ) values ( %s )",
                        entityClassMetaData.getName(), fieldsWithoutIdString, questions)
                .intern();
    }

    @Override
    public String getUpdateSql() {
        String idField = entityClassMetaData.getIdField().getName();
        String parameters = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName().concat(Constants.EQUALS_QUESTION_MARK))
                .collect(Collectors.joining(Constants.DELIMITER));

        return String.format("update %s set %s where %s = ?", entityClassMetaData.getName(), parameters, idField)
                .intern();
    }
}
