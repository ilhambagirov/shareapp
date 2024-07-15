package api.mappers.dtos;

import java.io.Serializable;

public record FileDto(Long id, String url) implements Serializable {
}