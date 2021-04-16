package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.UserRequest;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 3; Limite = 9
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "tb_user_group",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "group_id", nullable = false)
    )
    // Carga: +1 (Group)
    private Set<Group> groups = new HashSet<>();

    @Deprecated
    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Carga: +1 (UserRequest)
    public void setPropertiesToUpdate(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
    }

    public boolean passwordEqualsTo(String currentPassword) {
        return this.password.equals(currentPassword);
    }

    public boolean hasThisGroupById(Long groupId) {
        // Carga: +1 (função como argumento)
        return groups.stream().filter(group -> group.getId().equals(groupId)).findAny().isPresent();
    }

    public void associateGroup(Group group) {
        groups.add(group);
    }

    public void dissociateGroup(Group group) {
        groups.remove(group);
    }

}
