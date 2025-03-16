package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.otus.crm.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.model.MetaDataInfo;

/** "Разбирает" объект на составные части */
@SuppressWarnings("java:S112")
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
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
        List<Field> allFields = getAllFields();
        return allFields.stream()
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
        List<Field> allFields = getAllFields();
        return allFields.stream()
                .filter(field -> Objects.isNull(field.getAnnotation(Id.class)))
                .toList();
    }

    @Override
    public MetaDataInfo<T> getMetaDataInfo() {
        return MetaDataInfo.<T>builder()
                .constructor(getConstructor())
                .name(getName())
                .idField(getIdField())
                .allFields(getAllFields())
                .fieldsWithoutId(getFieldsWithoutId())
                .build();
    }
}
