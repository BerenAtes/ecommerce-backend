package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
