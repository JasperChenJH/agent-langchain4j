package com.cqupt.java.ai.langchain4j;

import com.cqupt.java.ai.langchain4j.assistant.MemoryChatAssistant;
import com.cqupt.java.ai.langchain4j.assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {
    @Autowired
    private QwenChatModel qwenChatModel;
    @Autowired
    SeparateChatAssistant separateChatAssistant;

    @Test
    public void testSystemMessage() {
        String anwser1 = separateChatAssistant.chat(3, "我是谁");
        System.out.println(anwser1);
        String anwser2 = separateChatAssistant.chat(3, "我是缓缓");
        System.out.println(anwser2);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testUserMessage() {
        String anwser1 = memoryChatAssistant.chat("我是谁");
        System.out.println(anwser1);
        String anwser3 = memoryChatAssistant.chat("我18了");
        System.out.println(anwser3);
        String anwser2 = memoryChatAssistant.chat("我是缓缓");
        System.out.println(anwser2);
        String anwser4= memoryChatAssistant.chat("我是谁,你呢?");
        System.out.println(anwser4);
    }

    @Test
    public void testV() {
        String answer1 = separateChatAssistant.chat2(10, "我是环环");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat2(10, "我是谁");
        System.out.println(answer2);
    }

    @Test
    public void testUserInfo() {
        //从数据库中获取用户消息
        String userName = "翠花";
        int age = 18;
        String answer = separateChatAssistant.chat3(11, "我是谁，我多大了", userName, age);
        System.out.println(answer);
    }
}
