package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SocialAuthController {

    private final UserRepository userRepository;

    public SocialAuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/social-login-success")
    public String handleSocialLogin(OAuth2AuthenticationToken token) {
        OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");
        String provider = token.getAuthorizedClientRegistrationId();
        String providerId = user.getName();

        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProvider(provider);
            newUser.setProviderId(providerId);
            return userRepository.save(newUser);
        });

        return "Social login successful";
    }
}
