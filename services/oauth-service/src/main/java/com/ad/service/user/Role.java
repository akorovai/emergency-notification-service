package com.ad.service.user;

public enum Role {
    USER("ROLE_USER");

    Role(String roleUser) {}

    public String getRoleName() {
        return name();
    }
}
