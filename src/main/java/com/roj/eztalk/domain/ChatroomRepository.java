package com.roj.eztalk.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatroomRepository extends CrudRepository<Chatroom, Long> {
    // @Query("SELECT m FROM Material m where m.material_id = :id")
    // public Optional<Material> findByMaterialId(@Param("id") Long id);

    @Query("SELECT c FROM Chatroom c WHERE c.name = :name ")
    public Optional<Chatroom> findByName(@Param("name") String name);

    @Query("SELECT c FROM Chatroom c")
    public List<Chatroom> findAll();
}