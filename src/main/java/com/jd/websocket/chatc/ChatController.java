package com.jd.websocket.chatc;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("null")
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Constructor injection
    public ChatController(ChatMessageRepository chatMessageRepository, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Save the message to MongoDB
        saveChatMessageToMongoDB(chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        // Save join message to MongoDB
        saveChatMessageToMongoDB(ChatMessage.builder()
                .type(MessageType.JOIN)
                .sender(chatMessage.getSender())
                .build());

        return chatMessage;
    }

    private void saveChatMessageToMongoDB(ChatMessage chatMessage) {
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .type(chatMessage.getType())
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .timestamp(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessageEntity);
    }

    @MessageMapping("/chat.fetchStoredMessages")
    public void fetchStoredMessages(SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            // Fetch stored messages from MongoDB
            List<ChatMessageEntity> storedMessages = chatMessageRepository.findByTypeOrderByTimestampAsc(MessageType.CHAT);
            storedMessages.forEach(storedMessage -> {
                ChatMessage message = ChatMessage.builder()
                        .type(storedMessage.getType())
                        .content(storedMessage.getContent())
                        .sender(storedMessage.getSender())
                        .build();
                messagingTemplate.convertAndSendToUser(username, "/topic/public", message);
            });
        }
    }
}
