package api.services.msgQueue;

import java.util.List;

public interface IMsgQueue {

    void SetTaggerEvent(List<String> taggedUsernames);
}
