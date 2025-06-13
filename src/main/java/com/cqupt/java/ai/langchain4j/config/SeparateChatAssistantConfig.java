package com.cqupt.java.ai.langchain4j.config;

import com.cqupt.java.ai.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeparateChatAssistantConfig {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return memoryId ->
                MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .chatMemoryStore(mongoChatMemoryStore)
                .maxMessages(20).build();
    }
}
