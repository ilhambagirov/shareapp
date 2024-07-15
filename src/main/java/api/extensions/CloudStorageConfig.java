package api.extensions;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class CloudStorageConfig {
    @Value("${storage.minioUrl}")
    private String minioUrl;
    @Value("${storage.minioUser}")
    private String minioUser;
    @Value("${storage.minioPassword}")
    private String minioPassword;
    @Value("${storage.bucketName}")
    private String bucketName;

    @Bean
    public Storage cloudStorage() {
        return StorageOptions.newBuilder().setProjectId("projectId").build().getService();
    }

    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        var client = new MinioClient.Builder().endpoint(minioUrl).credentials(minioUser, minioPassword).build();

        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()))
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

        return client;
    }
}
