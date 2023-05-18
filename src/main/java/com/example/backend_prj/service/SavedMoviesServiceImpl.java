package com.example.backend_prj.service;

import com.example.backend_prj.entity.Film;
import com.example.backend_prj.entity.SavedMovie;
import com.example.backend_prj.entity.User;
import com.example.backend_prj.repository.FilmRepository;
import com.example.backend_prj.repository.SavedMovieRepository;
import com.example.backend_prj.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedMoviesServiceImpl implements SavedMoviesService{
    @Autowired
    SavedMovieRepository savedMovieRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    UserRepository userRepository;

    public List<SavedMovie> savedMoviesOfUser(int id){
        return savedMovieRepository.findAllByUserUserId(id);
    }
    public List<Film> allMoviesOfOneUser(int id){
        List<SavedMovie> savedMovies = savedMoviesOfUser(id);
        List<Integer> ids = new ArrayList<>();
        List<Film> films = new ArrayList<>();
        for (SavedMovie movie: savedMovies){
            ids.add(movie.getFilm().getMovieId());
        }
        for (Integer idd: ids){
            films.add(filmRepository.findByMovieId(idd));
        }
        return films;
    }
    public SavedMovie findingMovieOfUser(int userId, int movieId){
        return savedMovieRepository.findByUserUserIdAndFilmMovieId(userId, movieId);
    }
    public void saveMovieToCart(int userId, int movieId){
        if (savedMovieRepository.findByUserUserIdAndFilmMovieId(userId, movieId) == null){
            savedMovieRepository.save(saveThisMovie(userId,movieId));
        }
    }

    public void removeFromCart(int userID, int movieId){
        savedMovieRepository.deleteByFilmMovieIdAndUserUserId(movieId, userID);
    }
    public SavedMovie saveThisMovie(int userId, int movieId){
        Film film = filmRepository.findByMovieId(movieId);
        User user = userRepository.findByUserId(userId);
       return SavedMovie.builder()
                        .film(film)
                        .user(user)
                        .build();

    }



}
