package com.Spring.Cinema.Repositorys;

import com.Spring.Cinema.Models.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking,Integer> {
    @Query(value = "select * from user_tickets where user_name=:username",nativeQuery = true)
    public List<TicketBooking> findByTicket(String username);

    @Query(value = "select * from user_tickets where time=:time and date=:date and movie_name=:moviename",nativeQuery = true)
    public List<TicketBooking> seatCheck(String time,String date,String moviename);
}
