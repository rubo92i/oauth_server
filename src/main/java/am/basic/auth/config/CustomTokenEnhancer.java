package am.basic.auth.config;


import am.basic.auth.model.Authority;
import am.basic.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User oauthUser = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();


        am.basic.auth.model.User user = userRepository.getByUsername(oauthUser.getUsername());

        if (user != null) {
            additionalInfo.put("userId", user.getId());
            additionalInfo.put("username", user.getUsername());
            additionalInfo.put("name", user.getName());
            additionalInfo.put("surname", user.getSurname());

            HashMap<Integer, String> roles = new HashMap<>();
            for (Authority authority : user.getAuthorities()) {
                roles.put(authority.getId(), authority.getName());
            }
            additionalInfo.put("roles", roles);

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }


        return accessToken;
    }

}
