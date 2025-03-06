package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum ECostStatus {
    ESTIMATED,
    FINAL;

    public static ECostStatus fromString(String status) {
        for (ECostStatus e : ECostStatus.values()) {
            if (e.name().equalsIgnoreCase(status)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid ECostStatus value: " + status);
    }
}
