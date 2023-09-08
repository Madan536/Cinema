package com.Spring.Cinema.Controller;

import com.Spring.Cinema.Models.Movies;
import com.Spring.Cinema.Models.Users;
import com.Spring.Cinema.Repositorys.UserRepository;
import com.Spring.Cinema.Services.MoviesModelServices;
import com.Spring.Cinema.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
     private MoviesModelServices moviesModelServices;
     @Autowired
    HomeController(MoviesModelServices moviesModelServices){
        this.moviesModelServices=moviesModelServices;
    }
    @GetMapping("/")
    public String HomePage(Model model){
        List<Movies> Movies=moviesModelServices.MovieList();
        model.addAttribute("Movies",Movies);
        return "Home-page";
    }

    @GetMapping("/error")
    public String error(){
         return "errorPage";
    }

    @GetMapping("/User-Register")
    public String UserRegister(Model model){
        model.addAttribute("Users",new Users());
        return "User-RegisterPage";
    }
    @PostMapping("/Register-Save")
    public String RegisterSave(@RequestParam("UserImage") MultipartFile multipartFile,
                               @RequestParam("username") String username,@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session, @RequestParam("conpassword") String conpassword){
        String userimage="/Images/UsersProfile/"+multipartFile.getOriginalFilename().toString();
       System.out.println("Users..."+userRepository.ExitUserName(username).size() +" "+ userRepository.ExitEmail(email).size());
        if(userRepository.ExitUserName(username).size() !=1){
            if (userRepository.ExitEmail(email).size()!=1) {
                if(userService.CheckPassword(password,conpassword)) {
                    Users users=new Users(username,email,passwordEncoder.encode(password),userimage);
                    userRepository.save(users);
                    session.setAttribute("msg",true);
                    return "redirect:/User-Register";
                }else {
                    session.setAttribute("msg",false);
                    return "redirect:/User-Register";
                }
            }else {
                session.setAttribute("msg","ErrorEmail");
                return "redirect:/User-Register";
            }
        }else {
            session.setAttribute("msg","ErrorUserName");
            return "redirect:/User-Register";
        }
    }
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request){
         model.addAttribute("users",new Users());
         Cookie[] cookies=request.getCookies();
         for(Cookie c:cookies){
             model.addAttribute("usernames",c);
         }
         return "Login-Page";
    }
    @GetMapping("/forget")
    public String ForgetPasswordPage(){
         return "User-forget-password";
    }
    @PostMapping("/forget-password-process")
    public String ForgetPasswordProcess(@RequestParam("username") String username,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("conpassword") String conpassword,
                                        Model model){

                 if(userRepository.ExitUserName(username).size() ==1){
                     if(userRepository.ExitEmail(email).size() ==1){
                         if(userService.CheckPassword(password,conpassword)){
                             userService.ForgetPassword(username,passwordEncoder.encode(password));
                             model.addAttribute("msg",true);
                             return "User-forget-password";
                         }else {
                             model.addAttribute("msg", "Rpassword");
                             return "User-forget-password";
                         }
                    }else {
                         model.addAttribute("msg", "email");
                         return "User-forget-password";
                     }
                 }else{
                     model.addAttribute("msg", false);
                     return "User-forget-password";
                 }
    }
    @GetMapping("/Home-search")
    public String Search(@RequestParam("search") String search,
                         Model model){
        List<Movies> moviesearch=moviesModelServices.Search(search);
        model.addAttribute("Movies",moviesearch);
        System.out.println("..."+moviesearch);
        return "/Home-Page";
    }

    @GetMapping("/contact")
    public String Contact(Model model){
         return "Contact";
    }

}
