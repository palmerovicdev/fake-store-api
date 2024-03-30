package com.suehay.fsastorageservice.model.enums;

import java.util.List;

public enum Role {
    ADMIN(List.of(Permisions.DELETE, Permisions.DOWNLOAD, Permisions.READ, Permisions.UPLOAD, Permisions.UPDATE)),
    USER(List.of(Permisions.DOWNLOAD, Permisions.READ, Permisions.UPLOAD, Permisions.UPDATE)),
    GUEST(List.of(Permisions.READ));

    final List<Permisions> permissions;

    Role(List<Permisions> permissions) {
        this.permissions = permissions;
    }

    public List<Permisions> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(Permisions permission) {
        return permissions.contains(permission);
    }

    Role getRole(String role) {
        return Role.valueOf(role);
    }
}