package gdghufs.jandies.controller;

import gdghufs.jandies.service.AuthResponseDTO;
import gdghufs.jandies.service.Oauth2Service;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AuthController {

    final Oauth2Service oauth2Service;

    @Operation(summary = "👑테스트 - 깃허브 리디렉션(로그인/회원가입)", tags = {"Auth"})
    @GetMapping("/login")
    public String login() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=Ov23liMuB1y8uvz4q9Bc";
    }

    @Operation(summary = "👑테스트 - 깃허브 로그인 콜백", tags = {"Auth"})
    @GetMapping("/callback")
    public String callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        AuthResponseDTO authResponseDTO = oauth2Service.getAccessToken(code);

        String accessToken = authResponseDTO.getAccessToken();
        String refreshToken = authResponseDTO.getRefreshToken();

        response.setHeader("Set-Cookie", "accessToken=" + accessToken + "; Path=/; Domain=jandi.es; HttpOnly; SameSite=None; Secure;");
        response.addHeader("Set-Cookie", "refreshToken=" + refreshToken + "; Path=/; Domain=jandi.es; HttpOnly; SameSite=None; Secure;");

        return "redirect:https://jandi.es";
    }

}