package com.Spring.Cinema.Services;

import com.Spring.Cinema.Models.Movies;
import com.Spring.Cinema.Repositorys.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesModelServices {
    private BookingRepository bookingRepository;
    @Autowired
    MoviesModelServices(BookingRepository bookingRepository){
        this.bookingRepository=bookingRepository;
    }

    public List<Movies> MovieList() {
        return bookingRepository.findAll();
    }
    public Movies findone(String MovieName){
       return bookingRepository.FindOne(MovieName);
    }

    public List<Movies> Search(String search){
        return bookingRepository.Search(search);
    }
}
