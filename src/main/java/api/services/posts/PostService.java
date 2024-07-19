package api.services.posts;

import api.mappers.dtos.PostDto;
import api.mappers.interfaces.IPostMapper;
import api.models.posts.SavePostRequest;
import api.repositories.PostRepository;
import api.services.BaseManager;
import api.services.files.IFileService;
import api.services.msgQueue.IMsgQueue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.*;

@Service
public class PostService extends BaseManager implements IPostService {

    private final PostRepository _postRepository;
    private final IFileService _fileService;
    private final IPostMapper _postMapper;
    private final IMsgQueue _msgQueue;
    private final Executor _asyncExecutor;

    public PostService(PostRepository postRepository,
                       IFileService fileService,
                       IPostMapper postMapper,
                       IMsgQueue msgQueue) {
        super();
        this._postRepository = postRepository;
        _fileService = fileService;
        _postMapper = postMapper;
        _msgQueue = msgQueue;
    }

    @Async("asyncExecutor")
    public CompletableFuture<Void> save(SavePostRequest request) {
        try {
            var files = _fileService.buildFile(request.files);
            _postRepository.save(_postMapper.toEntity(request, files,  getUser()));

            _msgQueue.SetTaggerEvent(request.taggedUsernames);

            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<PostDto>> getAll() {
        try {
            var posts = _postRepository.findAllByUserId(getUser().getId());
            return CompletableFuture.completedFuture(_postMapper.toDtoList(posts));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public record TagModel(String tagger, List<String> mails) implements Serializable {
    }
}
