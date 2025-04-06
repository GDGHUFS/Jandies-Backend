package gdghufs.jandies.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    // callback url
    @GetMapping("/callback")
    @ResponseBody
    public String callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        return code;
    }
}
