package com.roj.eztalk.dao;

import org.springframework.data.repository.CrudRepository;

import com.roj.eztalk.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
    // @Query("SELECT m FROM Material m where m.material_id = :id")
    // public Optional<Material> findByMaterialId(@Param("id") Long id);

    // @Query("SELECT u FROM User u WHERE u.name = :name ")
    // public Optional<User> findByName(@Param("name") String name);
}