package ru.otus.model;

import java.util.List;

public class ObjectForMessage implements Copyable<ObjectForMessage> {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage copy() {
        ObjectForMessage copy = new ObjectForMessage();
        if (data != null) {
            copy.setData(List.copyOf(data));
        }
        return copy;
    }
}
