package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.otus.crm.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

/** "Разбирает" объект на составные части */
@SuppressWarnings({"java:S112", "java:S1068"})
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private String name;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.name = getName();
        this.constructor = getConstructor();
        this.idField = getIdField();
        this.allFields = getAllFields();
        this.fieldsWithoutId = getFieldsWithoutId();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Exception getting constructor", e);
        }
    }

    // Поле Id должно определять по наличию аннотации Id
    // Аннотацию @Id надо сделать самостоятельно

    @Override
    public Field getIdField() {
        List<Field> allFieldsList = getAllFields();
        return allFieldsList.stream()
                .filter(field -> Objects.nonNull(field.getAnnotation(Id.class)))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(clazz.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> allFieldsList = getAllFields();
        return allFieldsList.stream()
                .filter(field -> Objects.isNull(field.getAnnotation(Id.class)))
                .toList();
    }
}
