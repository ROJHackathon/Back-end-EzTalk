package com.roj.eztalk.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.roj.eztalk.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    // @Query("SELECT m FROM Material m where m.material_id = :id")
    // public Optional<Material> findByMaterialId(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.name = :name ")
    public Optional<User> findByName(@Param("name") String name);
}