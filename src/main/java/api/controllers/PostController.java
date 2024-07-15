package api.controllers;

import api.entities.Post;
import api.mappers.dtos.PostDto;
import api.models.posts.SavePostRequest;
import api.services.posts.IPostService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class PostController {

    private final IPostService _postService;

    public PostController(IPostService postService) {
        _postService = postService;
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<Void>> save(HttpServletRequest request, @RequestParam("content") String content, @RequestParam("taggedUsernames") List<String> taggedUsernames, @RequestParam("files") List<MultipartFile> files) throws ExecutionException, InterruptedException {
        return _postService.save(new SavePostRequest(content, files, taggedUsernames)).thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/post")
    public CompletableFuture<ResponseEntity<List<PostDto>>> save(){
        return _postService.getAll().thenApply(ResponseEntity::ok);
    }

}
