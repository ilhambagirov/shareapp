package api.mappers.impl;

import api.entities.AppUser;
import api.entities.Post;
import api.mappers.dtos.AppUserDto;
import api.mappers.dtos.FileDto;
import api.mappers.dtos.PostDto;
import api.mappers.interfaces.IPostMapper;
import api.models.posts.SavePostRequest;
import api.services.files.FileService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import api.entities.File;

@Component
public class PostMapper implements IPostMapper {

    private final FileService _fileService;

    public PostMapper(FileService fileService) {
        _fileService = fileService;
    }

    @Override
    public PostDto toDto(Post post) {

        var filesDto = post.getFiles().stream().map(file -> {
            var url = _fileService.getFile(file.getFilename());

            return new FileDto(file.getId(), url);
        }).toList();

        var userDto = new AppUserDto(post.getAppUser().getId(), post.getAppUser().getUsername());

        return new PostDto(post.getId(), post.getContent(), post.getPostedOn(), userDto, filesDto);
    }

    @Override
    public List<PostDto> toDtoList(List<Post> posts) {
        return posts.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Post toEntity(SavePostRequest request, List<File> files, AppUser user) {
        var post = new Post();
        post.setContent(request.content);
        post.setAppUser(user);
        post.setPostedOn(LocalDateTime.now());

        for (File file : files) {
            file.setPost(post);
            post.getFiles().add(file);
        }

        return post;
    }
}
