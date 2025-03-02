package ru.otus.model;

public interface Copyable<C extends Copyable<C>> {
    C copy();
}
