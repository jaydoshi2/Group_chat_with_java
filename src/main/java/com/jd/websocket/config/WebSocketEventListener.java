package com.jd.websocket.config;

import com.jd.websocket.chatc.ChatMessage;
import com.jd.websocket.chatc.ChatMessageEntity;
import com.jd.websocket.chatc.ChatMessageRepository;
import com.jd.websocket.chatc.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor

public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    

    @SuppressWarnings("null")
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User disconnected: {}", username);

            ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                    .type(MessageType.LEAVE)
                    .content(username + " left!")
                    .sender(username)
                    .timestamp(LocalDateTime.now())
                    .build();

            chatMessageRepository.save(chatMessageEntity);

            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
