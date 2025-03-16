package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.utils.Constants;

/** Создает SQL - запросы */
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String allFieldsString = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(Constants.DELIMITER));

        return String.format(Constants.SELECT_ALL_EXP, allFieldsString, entityClassMetaData.getName())
                .toUpperCase();
    }

    @Override
    public String getSelectByIdSql() {
        String idField = entityClassMetaData.getIdField().getName();
        String selectAllSql = getSelectAllSql();

        StringBuilder stringBuilder = new StringBuilder(selectAllSql)
                .append(String.format(Constants.WHERE_CONDITION, idField))
                .append(Constants.QUESTION_MARK);

        return stringBuilder.toString().toUpperCase();
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String fieldsWithoutIdString =
                fieldsWithoutId.stream().map(Field::getName).collect(Collectors.joining(Constants.DELIMITER));
        String questions = fieldsWithoutId.stream()
                .map(field -> Constants.QUESTION_MARK)
                .collect(Collectors.joining(Constants.DELIMITER));

        return String.format(Constants.INSERT_EXP, entityClassMetaData.getName(), fieldsWithoutIdString, questions)
                .toUpperCase();
    }

    @Override
    public String getUpdateSql() {
        String idField = entityClassMetaData.getIdField().getName();
        String parameters = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName().concat(Constants.EQUALS_QUESTION_MARK))
                .collect(Collectors.joining(Constants.DELIMITER));

        StringBuilder stringBuilder = new StringBuilder()
                .append(String.format(Constants.UPDATE_EXP, entityClassMetaData.getName(), parameters))
                .append(String.format(Constants.WHERE_CONDITION, idField))
                .append(Constants.QUESTION_MARK);

        return stringBuilder.toString().toUpperCase();
    }
}
