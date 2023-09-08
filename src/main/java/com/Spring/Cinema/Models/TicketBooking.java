package com.Spring.Cinema.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "UserTickets")
@Getter
@Setter
@NoArgsConstructor
public class TicketBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String UserName;
    private String MovieName;
    private long price;
    private String Tickets;
    private String Date;
    private String Time;

    public TicketBooking(String userName,String MovieName,String tickets,long price, String date, String time) {
        this.UserName = userName;
        this.Tickets = tickets;
        this.Date = date;
        this.Time = time;
        this.MovieName=MovieName;
        this.price=price;

    }
}
