package com.example.backend_prj.repository;

import com.example.backend_prj.entity.SavedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedMovieRepository extends JpaRepository<SavedMovie, Long> {
    List<SavedMovie> findAllByFilmMovieId(int id);

    List<SavedMovie> findAllByUserUserId(int id);

}
