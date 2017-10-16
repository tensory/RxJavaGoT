package net.tensory.rxjavatalk.models;

class Value<T> {
    private T value;

    Value(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
