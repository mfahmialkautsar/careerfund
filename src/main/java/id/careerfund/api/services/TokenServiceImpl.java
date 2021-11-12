package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.converters.ModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService{
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;

    @Override
    public Boolean verifyUser(String email) {
        return !ObjectUtils.isEmpty(userRepo.findByEmail(email));
    }

    @Override
    public Boolean authUser(String email, String password) {
        Boolean user = verifyUser(email);

        try {
            if (user) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                return true;
            } else return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<String> roles(String email) throws Exception  {
        Boolean userState = verifyUser(email);

        try {
            if (userState) {
                User user = userRepo.findByEmail(email);

                assert user != null;
                Collection<Role> roles = user.getRoles();
                return ModelConverter.toStringRoleList(Objects.requireNonNull(roles));
            } else return null;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public String refreshToken(String email) {
        Boolean userState = verifyUser(email);
        if (userState) {
            User user = userRepo.findByEmail(email);
            assert user != null;
            return refreshTokenService.createRefreshToken(user.getId()).getToken();
        } else return null;
    }
}
