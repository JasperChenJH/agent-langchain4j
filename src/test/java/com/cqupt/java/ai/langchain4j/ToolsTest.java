package com.cqupt.java.ai.langchain4j;

import com.cqupt.java.ai.langchain4j.assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ToolsTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testCalculatorTools() {
        String string = separateChatAssistant.chat(3, "1234567890的平方根是多少?,3+3=?");
        System.out.println(string);
    }
    @Autowired
    private ApplicationContext context;

    @Test
    void checkChatModelBean() {
        // 检查是否存在名为 qwenChatModel 的 ChatModel
        QwenChatModel model = context.getBean("qwenChatModel", QwenChatModel.class);
        System.out.println(model.getClass()); // 应输出 QwenChatModel 或类似
    }
}
