package dv.kinash.hw15.service;

import dv.kinash.hw15.dto.BookInfoWithReaderDTO;
import dv.kinash.hw15.dto.UserDTO;
import dv.kinash.hw15.dto.UserNewDTO;
import dv.kinash.hw15.exception.UserAlreadyExistsException;
import dv.kinash.hw15.exception.UserNotFoundException;
import dv.kinash.hw15.repository.UserRepository;
import dv.kinash.hw15.repository.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        checkAdmin();
    }

    @Override
    public void addUser(UserNewDTO newUser, Boolean isAdmin) {
        if (repository.findByEmail(newUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(
                    String.format("User with e-mail <%s> already exists", newUser.getEmail()));
        }
        final User user = mapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setIsAdmin(isAdmin);
        repository.save(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        return mapper.map(repository.findAll(),
                new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                String.format("User with e-mail <%s> not found", email)));
    }

    @Override
    public void checkAdmin() {
        if (repository.findByEmail("admin@book.com").isPresent())
            return;
        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@book.com");
        admin.setPassword(passwordEncoder.encode("Admin"));
        admin.setIsAdmin(true);
        repository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = getUserByEmail(userEmail);

        String userRole = user.getIsAdmin() ? "admin" : "reader";
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userRole)));
    }
}
