package com.example.backend_prj.service;

import com.example.backend_prj.entity.Film;
import com.example.backend_prj.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService{

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> returnAllMovies() {
        return filmRepository.findAll();
    }
    public Film showFilm(int id){
        return filmRepository.findByMovieId(id);
    }

    public Film saveFilm(float filmGrade, String filmInfo, String filmLink,
                         String filmName, int filmRelease, String originalFilename,
                         int likes, int dislikes) {
        Film film =
                Film.builder()
                        .film_grade(filmGrade)
                        .film_name(filmName)
                        .film_link(filmLink)
                        .release(filmRelease)
                        .film_info(filmInfo)
                        .nameOfImage(originalFilename)
                        .likes(likes)
                        .dislikes(dislikes)
                        .build();
        filmRepository.save(film);
        return film;
    }
    public List<Film> findAllNewFilms(){
        Pageable sortByRelease =
                PageRequest.of(0,
                        9,
                        Sort.by("release").descending());
        return filmRepository.findAll(sortByRelease).getContent();
    }
}
