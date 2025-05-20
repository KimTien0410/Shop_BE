package com.fashion.Shop_BE.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="permissions")
public class Permission {
    @Id
    @Column(name="permission_name")
    private String permissionName;
    @Column(name="permission_description")
    private String permissionDescription;
}
