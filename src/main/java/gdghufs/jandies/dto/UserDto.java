package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Role;
import gdghufs.jandies.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserDto {
    @Schema(description = "유저 ID", example = "1")
    private Long id;
    @Schema(description = "깃허브 ID", example = "53990946")
    private Long githubId;
    @Schema(description = "유저 닉네임", example = "강대현")
    private String name;
    @Schema(description = "깃허브 아이디(닉네임)", example = "daehyuh")
    private String login;
    @Schema(description = "유저 자기소개", example = "자기소개입니다.")
    private String bio;
    @Schema(description = "유저 이메일", example = "daehyuh@hufs.ac.kr")
    private String email;
    @Schema(description = "유저 아바타 URL", example = "https://avatars.githubusercontent.com/u/1?v=4")
    private String avatarUrl;
    @Schema(description = "유저 권한", example = "ROLE_USER")
    private Role role;
    @Schema(description = "유저 생성일", example = "2025-04-06T21:21:47.3964")
    private LocalDateTime userCreatedAt;
    @Schema(description = "깃허브 생성일", example = "2019-08-11T07:14:34")
    private LocalDateTime githubCreatedAt;
    @Schema(description = "유저 링크트리 목록")
    private List<LinkTreeDto> linkTree;
    @Schema(description = "유저 농장 목록")
    private List<FarmDto> farms;


    public static UserDto fromEntity(User user) {
        List<LinkTreeDto> linkTreeDtos = user.getLinkTree().stream()
                .map(LinkTreeDto::fromEntity)
                .collect(Collectors.toList());
        List<FarmDto> farmDtos = user.getFarms().stream()
                .map(FarmDto::fromEntity)
                .collect(Collectors.toList());


        return new UserDto(
                user.getId(),
                user.getGithubId(),
                user.getName(),
                user.getLogin(),
                user.getBio(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getRole(),
                user.getUserCreatedAt(),
                user.getGithubCreatedAt(),
                linkTreeDtos,
                farmDtos
        );
    }
}