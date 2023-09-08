package com.Spring.Cinema.Services;

import com.Spring.Cinema.Models.TicketBooking;
import com.Spring.Cinema.Repositorys.TicketBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class TicketBookingService {
    @Autowired
    private TicketBookingRepository ticketBookingRepository;
    public String TicketSplits(String[] tickets){
        StringJoiner stringJoiner=new StringJoiner(",");
        for(String ticket:tickets){
            stringJoiner.add(ticket);
        }
        String ticket=String.valueOf(stringJoiner);
        return ticket;
    }

    public List<String> SeatCheck(String time,String date,String moviename){
        List<TicketBooking> seat=ticketBookingRepository.seatCheck(time,date,moviename);
        System.out.println("seatsize :"+seat.size());
        ArrayList<String> arrayList=new ArrayList();
        if(seat !=null){
            for(TicketBooking ticketBooking:seat){
                arrayList.add(ticketBooking.getTickets());
            }
        }
        return arrayList;
    }
}
