package api.services.msgQueue;

import api.entities.AppUser;
import api.repositories.UserRepository;
import api.services.BaseManager;
import api.services.posts.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MsgQueue extends BaseManager implements IMsgQueue {

    private final Connection _connection;
    private final UserRepository _userRepository;

    public MsgQueue(Connection connection, UserRepository userRepository) {
        _connection = connection;
        _userRepository = userRepository;
    }

    @Override
    public void SetTaggerEvent(List<String> taggedUsernames) {
        try {
            var mails = _userRepository.findByUsernames(taggedUsernames).stream().map(AppUser::getEmail).toList();

            Channel channel = _connection.createChannel();

            ObjectMapper mapper = new ObjectMapper();
            byte[] messageBytes = mapper.writeValueAsBytes(new PostService.TagModel(getUser().getUsername(), mails));

            channel.basicPublish("", "mail", null, messageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
