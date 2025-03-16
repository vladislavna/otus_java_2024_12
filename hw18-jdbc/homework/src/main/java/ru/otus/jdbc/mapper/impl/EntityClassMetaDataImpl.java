package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.otus.crm.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

/** "Разбирает" объект на составные части */
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
            var types =
                    Arrays.stream(clazz.getDeclaredFields()).map(Field::getType).toList();
            return clazz.getDeclaredConstructor(types.toArray(Class[]::new));
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
}
