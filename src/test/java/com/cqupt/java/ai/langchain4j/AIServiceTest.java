package com.cqupt.java.ai.langchain4j;

import com.cqupt.java.ai.langchain4j.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTest {
    @Autowired
    private QwenChatModel qwenChatModel;
    @Autowired
    private Assistant assistant;

    @Test
    public void testChat() {
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String answer = assistant.chat("99*99=");
        System.out.println(answer);
    }

    @Test
    public void testChat2() {
        String answer = assistant.chat("99*99=");
        System.out.println(answer);
    }
}
