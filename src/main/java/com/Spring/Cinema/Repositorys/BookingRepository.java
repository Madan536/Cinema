package com.Spring.Cinema.Repositorys;

import com.Spring.Cinema.Models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Movies,Integer> {
    @Query(value = "select * from movies where movie_name=:MovieName",nativeQuery = true)
    public Movies FindOne(String MovieName);

    @Query(value = "select * from movies where movie_name like '%' :Search '%'",nativeQuery = true)
    public List<Movies> Search(String Search);
}
