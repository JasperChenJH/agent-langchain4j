package com.cqupt.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
    wiringMode = EXPLICIT,
    chatModel = "qwenChatModel",
    chatMemoryProvider = "chatMemoryProviderXiaoZhi",
    tools = {
            "calculatorTools",
            "appointmentTools"
    },
    contentRetriever = "contentRetrieverXiaozhiPincone"
)
public interface XiaoZhiAgent {
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
