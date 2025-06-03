package com.fashion.Shop_BE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
public class Role {
    @Id
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_description")
    private String roleDescription;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="role_permissions",
            joinColumns=@JoinColumn(name="role_name"),
            inverseJoinColumns = @JoinColumn(name="permission_name")
    )
    private Set<Permission> permissions;
    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
}
