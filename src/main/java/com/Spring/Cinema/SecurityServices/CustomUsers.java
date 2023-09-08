package com.Spring.Cinema.SecurityServices;

import com.Spring.Cinema.Models.Users;
import com.Spring.Cinema.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUsers implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=userRepository.FindOneUser(username);
        if(users==null){
            throw new UsernameNotFoundException(username +"Not Found");
        }
        UserDetails us=User.withUsername(users.getUserName()).password(users.getPassword())
                .roles(users.getRoles()).build();
        return us;
    }
}
