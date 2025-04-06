package gdghufs.jandies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String tokenType;

    @Column(nullable = true)
    private String accessToken;

    @Column(nullable = true)
    private String refreshToken;

    @Column(nullable = true)
    private String githubAccessToken;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateGithubAccessToken(String githubAccessToken) {this.githubAccessToken = githubAccessToken;}

    @Builder
    public Auth(User user, String tokenType, String accessToken, String refreshToken, String githubAccessToken) {
        this.user = user;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.githubAccessToken = githubAccessToken;
    }

}