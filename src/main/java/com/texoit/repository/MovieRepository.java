package com.texoit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.texoit.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
