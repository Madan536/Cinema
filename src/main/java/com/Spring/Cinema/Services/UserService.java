package com.Spring.Cinema.Services;

import com.Spring.Cinema.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Boolean CheckPassword(String password,String conpassword){
        boolean check=false;
        if(password.equals(conpassword)){
            check=true;
        }
        return check;
    }
    public void ForgetPassword(String username,String password){
         userRepository.ForgetPassword(username,password);
    }
    public long TicketPrice(String ticket[],int price){
        long totalPrice=price* ticket.length;
        return totalPrice;
    }

    public void userProfileEdit(String newusername,String userimage,int userid){
        userRepository.UserProfileEdit(newusername,userimage,userid);
    }

}
