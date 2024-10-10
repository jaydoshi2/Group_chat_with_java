package com.jd.websocket.chatc;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "chat_messages")
public class ChatMessageEntity {

    @Id
    private String id;
    private MessageType type;
    private String content;
    private String sender;
    private LocalDateTime timestamp;

    // Constructors, getters, setters
}
