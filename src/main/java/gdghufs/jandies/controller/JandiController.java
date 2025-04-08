package gdghufs.jandies.controller;

import gdghufs.jandies.dto.JandiDto;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.User;
import gdghufs.jandies.scheduler.job.JandiJob;
import gdghufs.jandies.service.JandiService;
import gdghufs.jandies.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Operation(summary = "👑테스트 특정유저 잔디 가져오기 - 유저아이디 필요", tags = {"Jandi"})
    @GetMapping("/Jandi/user")
    public List<JandiDto> GetJandiByUserId(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다. userId = " + userId);
        }

        List<Jandi> jandiList = jandiService.findAllById_UserId(userId);

        return jandiList.stream()
                .map(JandiDto::fromEntity)
                .toList();
    }

    @Operation(summary = "👑테스트 - 오늘일자 전체유저 잔디 등록", tags = {"Jandi"})
    @PostMapping("/Jandi/save/today")
    public void GetAllJandi() {
        jandiJob.SaveAllJandi();
    }

    @Operation(summary = "👑테스트 - 전체일자 잔디 등록", tags = {"Jandi"})
    @PostMapping("/Jandi/save/all")
    public void GetAllJandiAll() {
        jandiJob.SaveAlJandiLongerThanOneDay();
    }

}