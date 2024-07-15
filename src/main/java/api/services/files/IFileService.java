package api.services.files;

import api.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IFileService {
    String uploadByteArray(MultipartFile file);
    List<File> buildFile(List<MultipartFile> files);
    CompletableFuture<File> buildFile(MultipartFile file);
}
