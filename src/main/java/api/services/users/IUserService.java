package api.services.users;

import api.entities.AppUser;
import api.models.users.LoginResponse;
import api.models.users.SaveUserRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IUserService {

    CompletableFuture<Void> addUser(SaveUserRequest request);
    CompletableFuture<LoginResponse> login(SaveUserRequest request);
    CompletableFuture<List<AppUser>> getAll() throws InterruptedException, ExecutionException;
}
