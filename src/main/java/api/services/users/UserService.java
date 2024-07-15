package api.services.users;

import api.entities.AppUser;
import api.models.users.LoginResponse;
import api.models.users.SaveUserRequest;
import api.repositories.UserRepository;
import api.services.jwt.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
public class UserService implements IUserService {

    private final UserRepository _userRepository;
    private final Executor asyncExecutor;
    private final PasswordEncoder _passwordEncoder;
    private final AuthenticationManager _authenticationManager;
    private final IJwtService _jwtService;

    @Autowired
    public UserService(UserRepository userRepository,
                       @Qualifier("asyncExecutor") Executor asyncExecutor,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       IJwtService jwtService) {
        this._userRepository = userRepository;
        this.asyncExecutor = asyncExecutor;
        this._passwordEncoder = passwordEncoder;
        this._authenticationManager = authenticationManager;
        _jwtService = jwtService;
    }

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> addUser(SaveUserRequest request) {
        AppUser user = new AppUser();
        user.setUsername(request.username);
        user.setPassword(_passwordEncoder.encode(request.password));

        return CompletableFuture.runAsync(() -> {
            _userRepository.save(user);
        }, asyncExecutor);
    }

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<LoginResponse> login(SaveUserRequest request) {
        _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username,
                        request.password
                )
        );

        var user = _userRepository.findByUsername(request.username)
                .orElseThrow();

        var claims = new HashMap<String, Object>();

        String jwtToken = _jwtService.generateToken(claims, user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(_jwtService.getExpirationTime());

        return CompletableFuture.completedFuture(loginResponse);
    }

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<List<AppUser>> getAll() throws InterruptedException, ExecutionException {

        System.out.println("Before " + Thread.currentThread().threadId());
        var a1 = _jwtService.test().thenApply((a) -> {
            System.out.println("THEN " + a);
            return 2;
        });
        System.out.println("After " + Thread.currentThread().threadId());
        System.out.println("did not wait");
        return CompletableFuture.completedFuture(_userRepository.findAll());
    }
}
