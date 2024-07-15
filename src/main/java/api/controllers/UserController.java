package api.controllers;

import api.entities.AppUser;
import api.models.users.LoginResponse;
import api.models.users.SaveUserRequest;
import api.services.users.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<Void>> save(@RequestBody SaveUserRequest user) {
        return userService.addUser(user).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/user")
    public CompletableFuture<ResponseEntity<List<AppUser>>> getAll() throws InterruptedException, ExecutionException {
        System.out.println("Before Ctrl" + Thread.currentThread().threadId());

        var a = userService.getAll().thenApply(ResponseEntity::ok);

        System.out.println("After Ctrl" + Thread.currentThread().threadId());
        return a;
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<LoginResponse>> login(@RequestBody SaveUserRequest user, HttpServletRequest request) {
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        return userService.login(user).thenApply((response) -> {
//            response.setCsrfToken(csrfToken);
//            return ResponseEntity.ok(response);
//        });
        return userService.login(user).thenApply(ResponseEntity::ok);
    }
}
