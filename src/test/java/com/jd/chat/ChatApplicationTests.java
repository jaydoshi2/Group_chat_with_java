package com.jd.chat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.jd.websocket.ChatApplication;
// import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ContextConfiguration(classes = ChatApplication.class)
class ChatApplicationTests {

    @Test
    void contextLoads() {
    }
} 