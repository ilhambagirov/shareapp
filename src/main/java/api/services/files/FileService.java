package api.services.files;

import api.entities.File;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class FileService implements IFileService {

    private final Storage storage;
    private final MinioClient _minioClient;

    public FileService(Storage storage,
                       MinioClient minioClient) {
        this.storage = storage;
        this._minioClient = minioClient;
    }

    @Value("${storage.bucketName}") // Use @Value for environment variables
    private String bucketName;

    @Override
    public String uploadByteArray(MultipartFile file) {
        if (file == null)
            return "";

        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new AssertionError();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        byte[] content = null;
        try {
            content = file.getBytes();

            try (WriteChannel writer = storage.writer(blobInfo)) {
                writer.write(ByteBuffer.wrap(content));
                storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public CompletableFuture<File> buildFile(MultipartFile file) {
        try {
            String token = uploadByteArrayMinio(file);

            return CompletableFuture.completedFuture(new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), token));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<File> buildFile(List<MultipartFile> files) {
        try {
            List<CompletableFuture<File>> futures = files.stream()
                    .map(this::buildFile)
                    .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFile(String name) {
        try {
            return _minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(name)
                            .expiry(1, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String uploadByteArrayMinio(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(fileBytes);

            var response = _minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .object(file.getOriginalFilename())
                    .stream(inputStream, fileBytes.length, -1)
                    .build());

            inputStream.close();

            System.out.println(response.etag());
            return response.etag();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
