package com.example.backend_prj.service;

import com.example.backend_prj.entity.Film;

import java.util.List;

public interface FilmService {

    Film saveFilm(float filmGrade, String filmInfo, String filmLink,
                  String filmName, int filmRelease, String originalFilename,
                  int likes, int dislikes);
    List<Film> returnAllMovies();

}
