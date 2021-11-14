package id.careerfund.api.services;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NewTokenRequest;
import id.careerfund.api.domains.models.SignInRequest;
import id.careerfund.api.domains.models.TokenResponse;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.converters.ModelConverter;
import javassist.NotFoundException;
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
public class TokenServiceImpl implements TokenService {
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Boolean checkObject(Object object) {
        return ObjectUtils.isEmpty(object);
    }

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
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> roles(String email) throws Exception {
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

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) throws Exception {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        if (checkObject(email) || checkObject(password)) {
            throw new Exception("Bad Request");
        }

        if (!authUser(email, password)) {
            throw new NotFoundException("User Not Found");
        }

        List<String> roles = roles(email);
        String jwtToken = jwtService.generateToken(email);
        String refreshToken = refreshToken(email);

        return new TokenResponse(jwtToken, refreshToken, roles);
    }

    @Override
    public TokenResponse getNewToken(NewTokenRequest request) throws Exception {
        String refreshToken = request.getRefreshToken();

        if (checkObject(refreshToken)) {
            throw new Exception("Bad Request");
        }
        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        if (checkObject(token)) {
            throw new Exception("Token Not Found");
        }

        Boolean notExpire = refreshTokenService.verifyExpiration(token);
        if (!notExpire) {
            throw new Exception("Token Not Found");
        }

        String newToken = jwtService.generateToken(token.getUser().getUsername());
        List<String> roles = roles(token.getUser().getEmail());
        return new TokenResponse(newToken, refreshToken, roles);
    }
}
