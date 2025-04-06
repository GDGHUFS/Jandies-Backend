package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Role;
import gdghufs.jandies.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserSimpleDto {
    private Long id;
    private Long githubId;
    private String name;
    private String login;
    private String bio;
    private String email;
    private String avatarUrl;
    private Role role;
    private LocalDateTime userCreatedAt;
    private LocalDateTime githubCreatedAt;
    private List<LinkTreeDto> linkTree;


    public static UserSimpleDto fromEntity(User user) {
        List<LinkTreeDto> linkTreeDtos = user.getLinkTree().stream()
                .map(LinkTreeDto::fromEntity)
                .collect(Collectors.toList());


        return new UserSimpleDto(
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
                linkTreeDtos
        );
    }
}