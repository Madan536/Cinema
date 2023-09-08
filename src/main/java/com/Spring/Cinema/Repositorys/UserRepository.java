package com.Spring.Cinema.Repositorys;

import com.Spring.Cinema.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    @Query(value = "select * from users where user_name=:UserName",nativeQuery = true)
    public Users FindOneUser(String UserName);
    @Modifying
    @Query(value = "update users set password=:password where user_name=:UserName",nativeQuery = true)
    public void ForgetPassword(String UserName,String password);

    @Modifying
    @Query(value = "update users set user_name=:newusername , user_image=:userimage where id=:userid",nativeQuery = true)
    public void UserProfileEdit(String newusername,String userimage,int userid);

    @Query(value = "select * from users where user_name=:UserName",nativeQuery = true)
    public List<Users> ExitUserName(String UserName);

    @Query(value = "select * from users where email=:email",nativeQuery = true)
    public List<Users> ExitEmail(String email);
}
