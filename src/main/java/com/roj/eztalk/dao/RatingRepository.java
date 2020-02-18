package com.roj.eztalk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.roj.eztalk.domain.Rating;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.material.id = :mid")
    public List<Rating> findByMaterialIdAndUserId(@Param("mid") Long mid);
}