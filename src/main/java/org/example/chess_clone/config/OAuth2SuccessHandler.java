package org.example.chess_clone.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.chess_clone.model.User;
import org.example.chess_clone.model.UserPrincipal;
import org.example.chess_clone.service.OAuthUserService;
import org.example.chess_clone.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final OAuthUserService oAuthUserService;

    public OAuth2SuccessHandler(JwtUtil jwtUtil,
                                UserDetailsService userDetailsService,
                                OAuthUserService oAuthUserService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.oAuthUserService = oAuthUserService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = oAuthUserService.findOrCreateOAuthUser(email);

        UserPrincipal principal = new UserPrincipal(user);

        String token = jwtUtil.generateToken(principal);

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.sendRedirect("/oauth-success.html");

    }
}
