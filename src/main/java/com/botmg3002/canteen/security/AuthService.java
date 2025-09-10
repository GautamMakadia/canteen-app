package com.botmg3002.canteen.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.botmg3002.canteen.exception.UserAlreadyExistsException;
import com.botmg3002.canteen.model.Admin;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.User;
import com.botmg3002.canteen.model.UserRole;
import com.botmg3002.canteen.repository.UserRepository;
import com.botmg3002.canteen.schema.auth.LoginRequest;
import com.botmg3002.canteen.schema.auth.LoginResponse;
import com.botmg3002.canteen.schema.auth.SigninResponse;
import com.botmg3002.canteen.schema.auth.SignupRequest;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) throws BadCredentialsException {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername()).get();

            return new LoginResponse(user.getId(), user.getRole().toString());

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "error in login service");
        }
    }

    public SigninResponse signup(SignupRequest signupRequest) throws UserAlreadyExistsException {

        Optional<User> user = userRepository.findByEmail(signupRequest.getEmail());

        if (user.isPresent())
            throw new UserAlreadyExistsException("User with email " + signupRequest.getEmail() + " already exists");

        User newUser = new User(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                UserRole.valueOf(signupRequest.getRole()));

        SigninResponse signinResponse = new SigninResponse();
        signinResponse.setEmail(newUser.getEmail());
        signinResponse.setRole(newUser.getRole());
        
        if (newUser.getRole() == UserRole.ADMIN) {
            Admin admin = new Admin(signupRequest.getName(), signupRequest.getPhone());
            newUser.setAdmin(admin);
            newUser = userRepository.save(newUser);

            signinResponse.setName(newUser.getAdmin().getName());
            signinResponse.setPhone(newUser.getAdmin().getPhone());
        } else {
            Customer customer = new Customer(signupRequest.getName(), signupRequest.getPhone());

            newUser.setCustomer(customer);
            newUser = userRepository.save(newUser);

            signinResponse.setName(newUser.getCustomer().getName());
            signinResponse.setPhone(newUser.getCustomer().getPhone());
        }

        signinResponse.setId(newUser.getId());

        return signinResponse;
    }
}
