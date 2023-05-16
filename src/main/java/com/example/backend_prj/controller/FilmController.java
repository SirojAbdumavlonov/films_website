package com.example.backend_prj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FilmController {

    @GetMapping("/")
    public String mainPage()
    {
        return "mainPage";
    }
    @GetMapping("/add_film")
    public String addFilmPage(){
        return "add_film";
    }




}
