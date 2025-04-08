package gdghufs.jandies.controller;

import gdghufs.jandies.dto.JandiDto;
import gdghufs.jandies.dto.UserDto;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.User;
import gdghufs.jandies.repository.JandiRepository;
import gdghufs.jandies.scheduler.job.JandiJob;
import gdghufs.jandies.service.AuthResponseDTO;
import gdghufs.jandies.service.JandiService;
import gdghufs.jandies.service.Oauth2Service;
import gdghufs.jandies.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JandiController {

    private final JandiService jandiService;
    private final UserService userService;
    private final JandiJob jandiJob;

    @Operation(summary = "👑테스트 - 내 잔디 가져오기", tags = {"Jandi"})
    @GetMapping("/Jandi/me")
    public List<JandiDto> GetJandi(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userService.findById(userId);

        List<Jandi> jandiList = jandiService.findAllById_UserId(user.getId());

        return jandiList.stream()
                .map(JandiDto::fromEntity)
                .toList();
    }

    @Operation(summary = "👑테스트 - 전체유저 잔디 등록", tags = {"Jandi"})
    @GetMapping("/Jandi/all")
    public String  GetAllJandi() {
        jandiJob.SaveAllJandi();

        return "redirect:/Jandi.es";
    }


}