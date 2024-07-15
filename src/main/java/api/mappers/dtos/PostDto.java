package api.mappers.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record PostDto(Long id, String content, LocalDateTime postedOn, AppUserDto appUser,
                      List<FileDto> files) implements Serializable {
}