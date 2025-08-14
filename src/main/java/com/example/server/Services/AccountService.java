package com.example.server.Services;
import com.example.server.Models.Account;
import com.example.server.Repositories.AccountRepo;
import com.example.server.config.PasswordEncoderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;
@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo repo;

    Account user;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
         user=repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
    public boolean registerUser(Account user){
        if(repo.existsByEmail(user.getEmail())){
            return false;
        }
        user.setId(null);
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()));
        repo.save(user);
        return true;
    }
}
