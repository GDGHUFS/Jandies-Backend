package gdghufs.jandies.controller;

import gdghufs.jandies.dto.UserDto;
import gdghufs.jandies.repository.UserRepository;
import gdghufs.jandies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public UserDto user(Authentication authentication) {

        return userService.findById(Long.parseLong(authentication.getName()));
    }

    @GetMapping("/user/count")
    public Long count() {
        return userService.count();
    }

}
