package com.farfarcoder.domain.meta;

public enum Profiles {
    LOCAL,
    DEV,
    STG,
    RELEASE;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
