package id.careerfund.api.services;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.requests.NewTokenRequest;
import id.careerfund.api.domains.models.responses.TokenResponse;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

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
    public Exception authUser(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public String refreshToken(String email) {
        Boolean userState = verifyUser(email);
        if (userState) {
            User user = userRepo.findByEmail(email);
            assert user != null;
            return refreshTokenService.createRefreshToken(user.getId()).getToken();
        } else
            return null;
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
        return new TokenResponse(newToken, refreshToken, RoleMapper.rolesToERoles(token.getUser().getRoles()));
    }

    @Override
    public TokenResponse getToken(User user) {
        String jwtToken = jwtService.generateToken(user.getEmail());
        String refreshToken = refreshToken(user.getEmail());

        return new TokenResponse(jwtToken, refreshToken, RoleMapper.rolesToERoles(user.getRoles()));
    }
}
