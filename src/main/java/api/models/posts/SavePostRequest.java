package api.models.posts;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SavePostRequest {
    public String content;
    public List<MultipartFile> files;
    public List<String> taggedUsernames;

    public SavePostRequest(String content, List<MultipartFile> files,List<String> taggedUsernames) {
        this.files = files;
        this.content = content;
        this.taggedUsernames = taggedUsernames;
    }
}
