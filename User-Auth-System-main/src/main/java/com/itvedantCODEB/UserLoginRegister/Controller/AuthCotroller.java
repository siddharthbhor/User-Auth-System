package com.itvedantCODEB.UserLoginRegister.Controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itvedantCODEB.UserLoginRegister.Entity.User;
import com.itvedantCODEB.UserLoginRegister.Repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthCotroller {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public AuthCotroller(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
		userRepository.save(user);
		return "User Registered Successfully!";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User loginUser) {
		Optional<User> userOpt = userRepository.findByEmail(loginUser.getEmail());
		if(userOpt.isPresent() && passwordEncoder.matches(loginUser.getPasswordHash(), userOpt.get().getPasswordHash())) {
			return "Login SuccessFully!";
		}else {
			return "Invalid Input";
		}
	}
	

}
