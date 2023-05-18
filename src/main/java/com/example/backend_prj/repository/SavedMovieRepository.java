package com.example.backend_prj.repository;

import com.example.backend_prj.entity.SavedMovie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedMovieRepository extends JpaRepository<SavedMovie, Long> {
    List<SavedMovie> findAllByUserUserId(int id);

    SavedMovie findByUserUserIdAndFilmMovieId(int userId, int movieId);

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(
            value = "delete from SavedMovie s where s.film.movieId = ?1 and s.user.userId = ?2"
    )
    void deleteByFilmMovieIdAndUserUserId(int movieId, int userId);


}
