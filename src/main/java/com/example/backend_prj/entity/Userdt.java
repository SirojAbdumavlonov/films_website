package com.example.backend_prj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Userdt {


    private boolean liked = false;
    private boolean disliked = false;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch =  FetchType.LAZY
    )
    @JoinColumn(
            name = "movie_id",
            referencedColumnName = "movieId"
    )
    private Film film;
    @OneToOne(
        cascade = CascadeType.ALL,
        fetch =  FetchType.LAZY
    )
    @Id
    @JoinColumn(
        name = "userId",
            referencedColumnName = "userId"
    )
    private User user;


}
