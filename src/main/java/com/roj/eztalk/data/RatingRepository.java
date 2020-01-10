package com.roj.eztalk.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.material.id = :mid")
    public List<Rating> findByMaterialIdAndUserId(@Param("mid") Long mid);
}