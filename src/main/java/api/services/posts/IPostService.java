package api.services.posts;

import api.entities.Post;
import api.mappers.dtos.PostDto;
import api.models.posts.SavePostRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IPostService {
    CompletableFuture<Void> save(SavePostRequest request) throws ExecutionException, InterruptedException;
    CompletableFuture<List<PostDto>> getAll();
}
