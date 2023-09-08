package com.Spring.Cinema.Controller;

import com.Spring.Cinema.Models.Movies;
import com.Spring.Cinema.Models.TicketBooking;
import com.Spring.Cinema.Models.Users;
import com.Spring.Cinema.Repositorys.TicketBookingRepository;
import com.Spring.Cinema.Repositorys.UserRepository;
import com.Spring.Cinema.Services.MoviesModelServices;
import com.Spring.Cinema.Services.TicketBookingService;
import com.Spring.Cinema.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketBookingRepository ticketBookingRepository;
     @Autowired
     private MoviesModelServices moviesModelServices;
    @GetMapping("/Movie-CinemaScreen")
    public String MovieBooking(@RequestParam("MovieName") String Moviename, Model model,
                               @AuthenticationPrincipal UserDetails userDetails,
                               HttpSession httpSession){
        Movies movies=moviesModelServices.findone(Moviename);
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        model.addAttribute("MovieName",movies.getMovieName());
        model.addAttribute("check",new ArrayList<>());
        model.addAttribute("seatcheck",null);
        model.addAttribute("price","0");
        httpSession.setAttribute("username",userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        model.addAttribute("date","null");
        model.addAttribute("time","null");
        return "Cinemascreen";
    }
    @GetMapping("/User-Pages")
    public String UserPages(@AuthenticationPrincipal UserDetails userDetails, Model model,
                            HttpSession httpSession, HttpServletResponse response){
        List<Movies> Movies=moviesModelServices.MovieList();
        System.out.println("MovieList"+Movies);
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        httpSession.removeAttribute("time");
        httpSession.removeAttribute("date");
        httpSession.setAttribute("username",userDetails.getUsername());
        Cookie cookie=new Cookie("username",userDetails.getUsername());
        response.addCookie(cookie);
        model.addAttribute("Movies",Movies);

        return "/User-Home-Page";
    }
    @GetMapping("/Tickets")
    public String UserTickets(@AuthenticationPrincipal UserDetails userDetails,
                              Model model){
        List<TicketBooking> ticket=ticketBookingRepository.findByTicket(userDetails.getUsername());
        model.addAttribute("TicketList",ticket);
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        return "User-Tickets";
    }
    @GetMapping("/TicketDelete/{id}")
    public String TicketDelete(@PathVariable int id){
        ticketBookingRepository.deleteById(id);
        return "redirect:/User/Tickets";
    }
    @GetMapping("/Profile")
    public String UserDetails(@AuthenticationPrincipal UserDetails userDetails,Model model){
        Users users=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("userimage",users.getUserImage());
        return "User-profile-Page";
    }
    @PostMapping("/User-profile-edit")
    public String EditProfile(@RequestParam("username") String username,
                              @RequestParam("file") MultipartFile multipartFile, Model model,
                              @AuthenticationPrincipal UserDetails userDetails){
        Users users=userRepository.FindOneUser(userDetails.getUsername());
        String file="";
        System.out.println("File"+multipartFile.getOriginalFilename());
        if(multipartFile.isEmpty()){
            file=users.getUserImage();
        }else {
            file="/Images/UsersProfile/"+multipartFile.getOriginalFilename().toString();
        }

        System.out.println("UserName "+username +" "+users);
        userService.userProfileEdit(username,file,users.getId());
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        return "redirect:/User/Profile";
    }

    @GetMapping("/service")
    public String UserService(@AuthenticationPrincipal UserDetails userDetails,Model model){
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        return "User-Service-Page";
    }

    @GetMapping("/User-change-password")
    public  String Userchangepassword(@AuthenticationPrincipal UserDetails userDetails,Model model){
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        model.addAttribute("userimage",user.getUserImage());
        return "User-change-password-page";
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/user-change-password-process")
    public String userforgetpasswordprocess(@RequestParam("oldpassword") String oldpassword,
                                            @RequestParam("newpassword") String newpassword,
                                            @RequestParam("conpassword") String conpassword,
                                            @AuthenticationPrincipal UserDetails userDetails,
                                            Model model){
        Users user=userRepository.FindOneUser(userDetails.getUsername());
        System.out.println("pass :"+passwordEncoder.matches(oldpassword, user.getPassword()));
        System.out.println("pass :"+ user.getPassword());
        System.out.println("pass :"+ user.getPassword());

            if (passwordEncoder.matches(oldpassword, user.getPassword())) {
                if (userService.CheckPassword(newpassword, conpassword)) {
                    userService.ForgetPassword(userDetails.getUsername(), passwordEncoder.encode(newpassword));
                    model.addAttribute("msg", true);
                    return "User-change-password-page";
                } else {
                    model.addAttribute("msg", "Rpassword");
                    return "User-change-password-page";
                }
            } else {
                model.addAttribute("msg", false);
                return "User-change-password-page";
            }

    }
    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/seat-check")
    public String seatCheck(@RequestParam("Date") String date, @RequestParam("Time") String time,
                            @RequestParam("MovieName") String moviename,HttpSession httpSession,Model model,
                            @AuthenticationPrincipal UserDetails userDetails){

           model.addAttribute("MovieName",moviename);
           List<String> check=ticketBookingService.SeatCheck(time,date,moviename);
           Users user=userRepository.FindOneUser(userDetails.getUsername());
           System.out.println("Check : "+check);
           model.addAttribute("userimage",user.getUserImage());
           model.addAttribute("check",check);
           model.addAttribute("price","0");
           model.addAttribute("date",date);
           model.addAttribute("time",time);
           httpSession.setAttribute("time",time);
           httpSession.setAttribute("date",date);
       //    return "redirect:/User/Movie-CinemaScreen?MovieName="+moviename;
        return "Cinemascreen";
    }
    @PostMapping("/Movie-seat-Booking")
    public String SaveMovie(@RequestParam("seat") String MovieTickets[],@RequestParam("MovieName") String MovieName,
                            HttpSession httpSession,
                            @AuthenticationPrincipal UserDetails userDetails) {

        String date=(String) httpSession.getAttribute("date");
        String time=(String)httpSession.getAttribute("time");
        final int Price=120;
        long totalPrice=userService.TicketPrice(MovieTickets,Price);
        String ticket=ticketBookingService.TicketSplits(MovieTickets);
        if(MovieTickets.length !=0) {
            TicketBooking ticketBooking = new TicketBooking(userDetails.getUsername(),MovieName, ticket, totalPrice, date, time);
            ticketBookingRepository.save(ticketBooking);
            return "redirect:/User/Tickets";
        }else {
            return "redirect:/User/seat-check";
        }
    }
    @PostMapping("/Ticket-price-count")
    public String TicketPriceCount(@RequestParam("seat") String MovieTickets[],
                                   Model model){
        final int price=120;
        long totalPrice=userService.TicketPrice(MovieTickets,price);
        model.addAttribute("price",totalPrice);
        System.out.println("Totalprice :"+totalPrice);
        return "Cinemascreen";
    }

    @GetMapping("/Movie-search")
    public String Search(@RequestParam("search") String search,
                         Model model){
        List<Movies> moviesearch=moviesModelServices.Search(search);
        model.addAttribute("Movies",moviesearch);
        System.out.println("..."+moviesearch);
        return "/User-Home-Page";
    }
}
