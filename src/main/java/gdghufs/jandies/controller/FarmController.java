package gdghufs.jandies.controller;

import gdghufs.jandies.dto.FarmDto;
import gdghufs.jandies.dto.FarmRequest;
import gdghufs.jandies.entity.Farm;
import gdghufs.jandies.entity.FarmType;
import gdghufs.jandies.entity.Location;
import gdghufs.jandies.service.FarmService;
import gdghufs.jandies.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;
    private final UserService userService;

    @Operation(summary = "농장 생성 - ✅전체 접근 FarmRequest 필요", tags = {"Farm"})
    @PostMapping("/farm")
    public FarmDto createFarm(@ModelAttribute FarmRequest farmRequest) throws IOException {

        return farmService.createFarm(farmRequest);
    }

    @Operation(summary = "농장 정보 조회 - ✅전체 접근, 접근 유저 ID 필요", tags = {"Farm"})
    @GetMapping("/farm/{id}")
    public FarmDto getFarm(@PathVariable Long id) {
        return farmService.findById(id);
    }

    @Operation(summary = "농장 전체 정보 조회 - ✅전체 접근", tags = {"Farm"})
    @GetMapping("/farm/all")
    public List<FarmDto> getFarm() {
        return farmService.findAll();
    }

    @Operation(summary = "농장 수정 - ✅전체 접근 FarmRequest 필요", tags = {"Farm"})
    @PutMapping("/farm/{id}")
    public FarmDto updateFarm(@PathVariable Long id, @ModelAttribute FarmRequest farmRequest) throws IOException {
        return farmService.updateFarm(id, farmRequest);
    }

    @Operation(summary = "농장 타입 리스트 조회 - ✅전체 접근", tags = {"Farm"})
    @GetMapping("/farm/types")
    public FarmType[] getFarmTypes() {
        return farmService.getFarmTypes();
    }

    @Operation(summary = "위치 리스트 조회 - ✅전체 접근", tags = {"Farm"})
    @GetMapping("/farm/locations")
    public Location[] getLocations() {
        return farmService.getLocations();
    }

    @Operation(summary = "농장 삭제 - ✅전체 접근", tags = {"Farm"})
    @DeleteMapping("/farm/{id}")
    public Boolean deleteFarm(@PathVariable Long id) {
        return farmService.deleteFarm(id);
    }

    @Operation(summary = "농장 가입 - ❌토큰과 FarmId 필요", tags = {"Farm"})
    @PostMapping("/farm/join/{id}")
    public Boolean joinFarm(@PathVariable Long id, Authentication authentication) {

        return farmService.joinFarm(id, Long.valueOf(authentication.getName()));
    }

    @Operation(summary = "농장 탈퇴 - ❌토큰과 FarmId 필요", tags = {"Farm"})
    @DeleteMapping("/farm/join/{id}")
    public Boolean leaveFarm(@PathVariable Long id, Authentication authentication) {

        return farmService.leaveFarm(id, Long.valueOf(authentication.getName()));
    }

}

















