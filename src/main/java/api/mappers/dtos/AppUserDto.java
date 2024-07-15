package api.mappers.dtos;

import java.io.Serializable;

public record AppUserDto(Long id, String username) implements Serializable {
}