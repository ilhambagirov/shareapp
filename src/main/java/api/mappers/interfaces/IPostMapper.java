package api.mappers.interfaces;

import api.entities.AppUser;
import api.entities.Post;
import api.mappers.dtos.PostDto;
import api.models.posts.SavePostRequest;
import org.mapstruct.Mapper;

import api.entities.File;
import java.util.List;

@Mapper
public interface IPostMapper {
    PostDto toDto(Post post);
    List<PostDto> toDtoList(List<Post> posts);
    Post toEntity(SavePostRequest request, List<File> files, AppUser user);
}
