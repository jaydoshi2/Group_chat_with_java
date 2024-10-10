package com.jd.websocket.chatc;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {
     List<ChatMessageEntity> findByTypeOrderByTimestampAsc(MessageType type);
}
