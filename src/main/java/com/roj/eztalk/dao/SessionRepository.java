package com.roj.eztalk.dao;

import java.util.Optional;
import com.roj.eztalk.domain.Session;
import com.roj.eztalk.domain.User;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends CrudRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE s.user = user")
    public Optional<Session> findByUser(@Param("user") User user);
}