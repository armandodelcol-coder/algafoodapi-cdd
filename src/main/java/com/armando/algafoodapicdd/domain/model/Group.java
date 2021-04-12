package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 60)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "tb_group_permission",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    // Carga: +1 (Permission)
    private Set<Permission> permissions = new HashSet<>();

    @Deprecated
    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void associatePermission(Permission permission) {
        this.permissions.add(permission);
    }

    public void dissociatePermission(Permission permission) {
        this.permissions.remove(permission);
    }

}
