package gdghufs.jandies.controller;

import gdghufs.jandies.service.AuthResponseDTO;
import gdghufs.jandies.service.Oauth2Service;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    final Oauth2Service oauth2Service;
    public AuthController(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=Ov23liMuB1y8uvz4q9Bc";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        AuthResponseDTO authResponseDTO = oauth2Service.getAccessToken(code);

        String accessToken = authResponseDTO.getAccessToken();
        String refreshToken = authResponseDTO.getRefreshToken();

        response.setHeader("Set-Cookie", "accessToken=" + accessToken + "; Path=/; Domain=localhost; HttpOnly; SameSite=None; Secure;");
        response.addHeader("Set-Cookie", "refreshToken=" + refreshToken + "; Path=/; Domain=localhost; HttpOnly; SameSite=None; Secure;");

        return "redirect:http://localhost";
    }

}
