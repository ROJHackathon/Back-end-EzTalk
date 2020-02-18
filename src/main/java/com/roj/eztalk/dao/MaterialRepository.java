package com.roj.eztalk.dao;

import org.springframework.data.repository.CrudRepository;

import com.roj.eztalk.domain.Material;

public interface MaterialRepository extends CrudRepository<Material, Long> {
    
}