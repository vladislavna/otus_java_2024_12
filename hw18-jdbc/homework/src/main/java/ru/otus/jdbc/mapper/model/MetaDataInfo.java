package ru.otus.jdbc.mapper.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaDataInfo<T> {
    private Constructor<T> constructor;
    private String name;
    private Field idField;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;
}
