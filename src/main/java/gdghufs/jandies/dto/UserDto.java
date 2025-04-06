package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Role;
import gdghufs.jandies.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Long githubId;
    private String username;
    private String login;
    private String email;
    private String avatarUrl;
    private Role role;
    private LocalDateTime userCreatedAt;
    private LocalDateTime githubCreatedAt;

    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getGithubId(),
                user.getUsername(),
                user.getLogin(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getRole(),
                user.getUserCreatedAt(),
                user.getGithubCreatedAt()
        );
    }
}