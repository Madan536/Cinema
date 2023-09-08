package com.Spring.Cinema.Controller;

import com.Spring.Cinema.Models.Movies;
import com.Spring.Cinema.Models.Users;
import com.Spring.Cinema.Repositorys.BookingRepository;
import com.Spring.Cinema.Repositorys.UserRepository;
import com.Spring.Cinema.Services.MoviesModelServices;
import com.Spring.Cinema.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/Add")
public class SaveController {
    @Autowired
    private MoviesModelServices moviesModelServices;

    @Autowired
    private UserRepository userRepository;
     private BookingRepository bookingRepository;
    SaveController(BookingRepository bookingRepository){
        this.bookingRepository=bookingRepository;
    }
    @GetMapping("/Movie")
    public String inputMovies(){
        return "Add-Movies";
    }
    @PostMapping("/Add-Movie")
    public String AddMovie(@RequestParam("file")MultipartFile multipartFile,
                           @RequestParam("moviename") String moviename,
                           @RequestParam("date") String date,
                           @RequestParam("detractor") String detractor,
                           @RequestParam("actorname") String ActorName,
                           Model model) throws IOException {
        System.out.println("File Name : "+"Images/"+multipartFile.getOriginalFilename());
        if (multipartFile.isEmpty() || moviename.isEmpty() || date.isEmpty() || detractor.isEmpty() || ActorName.isEmpty()) {
            model.addAttribute("msg",false);
            return "Add-Movies";
        }else {
            Movies movies = new Movies(moviename,
                    "Images/" + multipartFile.getOriginalFilename(),
                    date, detractor, ActorName);
            System.out.println(movies);
            bookingRepository.save(movies);
            model.addAttribute("msg",true);
            return "Add-Movies";
        }
    }


}
