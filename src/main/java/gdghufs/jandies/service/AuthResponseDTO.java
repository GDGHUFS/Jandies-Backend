package gdghufs.jandies.service;

import gdghufs.jandies.entity.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String githubAccessToken;

    @Builder
    public AuthResponseDTO(Auth entity) {
        this.tokenType = entity.getTokenType();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
        this.githubAccessToken = entity.getGithubAccessToken();
    }

    public static AuthResponseDTO fromEntity(Auth entity) {
        return AuthResponseDTO.builder()
                .entity(entity)
                .build();
    }

}