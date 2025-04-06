package gdghufs.jandies.service;

import gdghufs.jandies.entity.Auth;
import gdghufs.jandies.entity.Role;
import gdghufs.jandies.jwt.JwtProvider;
import gdghufs.jandies.repository.AuthRepository;
import gdghufs.jandies.repository.UserRepository;
import gdghufs.jandies.service.callbackDto.CallbackResponse;
import gdghufs.jandies.service.callbackDto.GithubDetailDto;
import gdghufs.jandies.service.callbackDto.GithubTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import gdghufs.jandies.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthRepository authRepository;
    RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    String clientId;
    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    String clientSecret;

    public AuthResponseDTO getAccessToken(String code) {
        String getTokenUrl = "https://github.com/login/oauth/access_token?client_id="+clientId+"&client_secret="+clientSecret+"&code="+code;
        System.out.println(getTokenUrl);
        GithubTokenDto githubToken = restTemplate.postForObject(getTokenUrl, null, GithubTokenDto.class);

        if(githubToken == null || githubToken.getAccess_token() == null){
            System.out.println("토큰이 null입니다.");
            return null;
        }

        String userUrl = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubToken.getAccess_token());
        HttpEntity<Void> request = new HttpEntity<>(headers);

        GithubDetailDto githubDetail = restTemplate.exchange(userUrl, HttpMethod.GET, request, GithubDetailDto.class).getBody();


        if(githubDetail == null || githubDetail.getLogin() == null){
            return null;
        }

        CallbackResponse callbackResponse = new CallbackResponse();
        callbackResponse.setTokenInfo(githubToken);
        callbackResponse.setGithubInfo(githubDetail);

        Optional<User> userEntity = userRepository.findBygithubId(githubDetail.getId());
        User user;
        Auth auth;

        System.out.println("userEntity = " + userEntity);

        if (userEntity.isEmpty()) {
            System.out.println(githubDetail.getLogin() + "회원가입");
            user = userRepository.save(User.builder()
                    .username(githubDetail.getLogin())
                    .role(Role.ROLE_USER)
                    .userCreatedAt(LocalDateTime.now())
                    .githubCreatedAt(callbackResponse.getGithubInfo().getCreated_at())
                    .githubId(githubDetail.getId())
                    .login(githubDetail.getLogin())
                    .email(githubDetail.getEmail())
                    .avatarUrl(githubDetail.getAvatar_url())
                    .build());

            auth = authRepository.save(Auth.builder()
                    .user(user)
                    .tokenType("Bearer")
                    .accessToken(jwtProvider.createToken(user.getId(), user.getRole(), "access"))
                    .refreshToken(jwtProvider.createToken(user.getId(), user.getRole(), "refresh"))
                    .githubAccessToken(githubToken.getAccess_token())
                    .build());
        } else {
            user = userEntity.get();
            System.out.println(githubDetail.getLogin() + "로그인");

            auth = authRepository.existsByUser(user) ? authRepository.findByUser(user).get() : Auth.builder().user(user).build();
            auth.updateAccessToken(this.jwtProvider.createToken(user.getId(), user.getRole(), "access"));
            auth.updateRefreshToken(this.jwtProvider.createToken(user.getId(), user.getRole(), "refresh"));
            auth.updateGithubAccessToken(githubToken.getAccess_token());

            user.setAvatarUrl(githubDetail.getAvatar_url());
            user.setEmail(githubDetail.getEmail());
            userRepository.save(user);

        }
        // :todo 깃허브 데이터 연동
        return AuthResponseDTO.fromEntity(auth);
    }

}