package net.tensory.rxjavatalk.models;

public class Value<T> {
    private T value;

    public Value(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
