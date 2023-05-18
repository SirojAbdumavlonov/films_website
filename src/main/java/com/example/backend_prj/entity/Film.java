package com.example.backend_prj.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private int movieId;
    private String film_name;
    private String film_info;
    @Column(name = "releasedate")
    private int release;
    private float film_grade;
    private String film_link;
    private String nameOfImage;

    private int likes = 0;
    private int dislikes = 0;
}
