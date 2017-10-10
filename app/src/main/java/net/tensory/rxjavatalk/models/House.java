package net.tensory.rxjavatalk.models;

public enum House {
    STARK("Stark"),
    TARGARYEN("Targaryen"),
    LANNISTER("Lannister"),
    NIGHT_KING("Night King");

    private String houseName;

    House(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseName() {
        return houseName;
    }
}
