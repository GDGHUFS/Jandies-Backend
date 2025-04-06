package gdghufs.jandies.jwt;

import gdghufs.jandies.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.access-token-expiration}")
    private long ACCESS_EXPIRE_TIME;
    @Value("${jwt.jwt.refresh-token-expiration-time}")
    private long REFRESH_EXPIRE_TIME;
    @Value("${jwt.secret}")
    private String salt;

    private Key secretKey;

    private final JpaUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(final Long account, final Role roles, final String type) {

        final Claims claims = Jwts.claims().setSubject(String.valueOf(account));
        claims.put("roles", roles);
        final Date now = new Date();

        if (type.equals("access")) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + ACCESS_EXPIRE_TIME))
                    .signWith(this.secretKey, SignatureAlgorithm.HS256)
                    .compact();
        } else if (type.equals("refresh")) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + REFRESH_EXPIRE_TIME))
                    .signWith(this.secretKey, SignatureAlgorithm.HS256)
                    .compact();
        } else {
            throw new IllegalArgumentException("Invalid token type");
        }
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(final String token) {
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 account 획득
    public String getAccount(final String token) {
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(final HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }

            final Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (final Exception exception) {
            return false;
        }
    }
}
