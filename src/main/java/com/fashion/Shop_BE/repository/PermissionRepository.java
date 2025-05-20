package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    public Permission findByPermissionName(String name);


}
