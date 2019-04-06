package dev.oenomel.guide.dblogin.service;

import dev.oenomel.guide.dblogin.config.security.SecurityUser;
import dev.oenomel.guide.dblogin.model.MyUser;
import dev.oenomel.guide.dblogin.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        if(myUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return new SecurityUser(myUser);
    }
}
