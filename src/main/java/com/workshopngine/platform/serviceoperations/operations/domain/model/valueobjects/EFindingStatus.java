package com.workshopngine.platform.serviceoperations.operations.domain.model.valueobjects;

public enum EFindingStatus {
    OPEN,
    RESOLVED,
    NEEDS_REVIEW;

    public static EFindingStatus fromString(String status) {
        for (EFindingStatus findingStatus : EFindingStatus.values()) {
            if (findingStatus.name().equalsIgnoreCase(status)) {
                return findingStatus;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
