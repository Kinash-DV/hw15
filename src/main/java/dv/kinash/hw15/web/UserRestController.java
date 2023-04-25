package dv.kinash.hw15.web;

import dv.kinash.hw15.dto.UserDTO;
import dv.kinash.hw15.dto.UserNewDTO;
import dv.kinash.hw15.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewAdmin(@Valid @RequestBody UserNewDTO newUser){
        userService.addUser(newUser, true);
    }
    @PostMapping("/reader")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewReader(@Valid @RequestBody UserNewDTO newUser){
        userService.addUser(newUser, false);
    }

    @GetMapping("/list")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }


}
