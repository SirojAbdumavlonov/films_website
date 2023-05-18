package com.example.backend_prj.repository;

import com.example.backend_prj.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {


    List<Film> findAll();
    Film findByMovieId(int id);
    List<Film> findByOrderByReleaseDesc();

    List<Film> findAllByMovieId(int id);

}
