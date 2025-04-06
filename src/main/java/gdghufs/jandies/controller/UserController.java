package gdghufs.jandies.controller;

import gdghufs.jandies.dto.LinkTreeDto;
import gdghufs.jandies.dto.UserDto;
import gdghufs.jandies.repository.UserRepository;
import gdghufs.jandies.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "✅전체 접근, 접근 유저 ID 필요", tags = {"User"})
    @ApiResponse(responseCode = "200", description = "정상적으로 사용자 정보를 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDto> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation( summary = "내 정보 조회", description = "❌토큰 필요", tags = {"Me"})
    @ApiResponse(responseCode = "200", description = "유저조회성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    @GetMapping("/profile/me")
    public ResponseEntity<UserDto> getMe(Authentication authentication) {
        return ResponseEntity.ok(userService.findById(Long.parseLong(authentication.getName())));
    }

    @Operation( summary = "내 정보 수정", description = "❌토큰과 UserDto 필요", tags = {"Me"})
    @ApiResponse(responseCode = "200", description = "정상적으로 사용자 정보를 수정", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    @PutMapping("/profile/me")
    public UserDto putMe(Authentication authentication, @RequestBody UserDto userDto) {
        return userService.updateUser(Long.valueOf(authentication.getName()), userDto);
    }

    @Operation( summary = "유저 수 조회", description = "✅전체 접근", tags = {"User"})
    @ApiResponse(responseCode = "200", description = "정상적으로 사용자 정보를 수정", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @GetMapping("/profile/count")
    public Long count() {
        return userService.count();
    }

    @Operation(summary = "링크트리 저장", description = "❌토큰과 LinkTreeDto 필요", tags = {"LinkTree"})
    @ApiResponse(responseCode = "200", description = "정상적으로 링크트리 저장", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PostMapping("/profile/linktree")
    public Boolean saveLinkTree(Authentication authentication, @RequestBody LinkTreeDto linkTreeDto) {
        return userService.saveLinkTree(Long.valueOf(authentication.getName()), linkTreeDto);
    }

    @Operation(summary = "링크트리 삭제", description = "❌토큰과 링크트리 ID 필요", tags = {"LinkTree"})
    @ApiResponse(responseCode = "200", description = "정상적으로 링크트리 삭제", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping("/profile/linktree/{linkTreeId}")
    public Boolean deleteLinkTree(Authentication authentication, @PathVariable Long linkTreeId) {
        return userService.deleteLinkTree(Long.valueOf(authentication.getName()), linkTreeId);
    }

    @Operation(summary = "링크트리 수정", description = "❌토큰과 LinkTreeDto 필요", tags = {"LinkTree"})
    @ApiResponse(responseCode = "200", description = "정상적으로 링크트리 수정", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PutMapping("/profile/linktree/{linkTreeId}")
    public Boolean updateLinkTree(Authentication authentication, @PathVariable Long linkTreeId, @RequestBody LinkTreeDto linkTreeDto) {
        return userService.updateLinkTree(Long.valueOf(authentication.getName()), linkTreeId, linkTreeDto);
    }

}
