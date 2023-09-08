package com.Spring.Cinema.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Movieid;
    private String MovieName;
    private String MovieImage;
    private String ActorName;
    private String Date;
    private String Detractor;

    public Movies(String movieName, String movieImage, String date, String detractor,String ActorName) {
        this.MovieName = movieName;
        this.MovieImage = movieImage;
        this.Date = date;
        this.ActorName=ActorName;
        this.Detractor = detractor;
    }
}
