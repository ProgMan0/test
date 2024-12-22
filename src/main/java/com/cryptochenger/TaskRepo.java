package com.cryptochenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("select p from Task p where p.title = :title")
    Optional<Task> findByTitle(String title);
}
