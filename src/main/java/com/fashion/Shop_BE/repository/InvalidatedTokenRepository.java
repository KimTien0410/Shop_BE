package com.fashion.Shop_BE.repository;

import com.fashion.Shop_BE.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    public boolean existsById(String id);

}
