package com.cqupt.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;
// chatMemoryProvider实现聊天会话隔离
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools"
)

public interface SeparateChatAssistant {
    //SystemMessage 预输入提示词
    // 一旦系统提示词更换会导致失忆 {{current_date}}日期
//    @SystemMessage("你现在是我的,请用东北话回答.今天是{{current_date}}")
    @SystemMessage(fromResource = "my-prompt-template.txt" ) //从资源模板中获取提示词
    String chat(@MemoryId int memoryId, @UserMessage String userMassage);

    //如果有两个或两个以上的参数，我们必须要用 @V ，在 SeparateChatAssistant 中定义方法 chat2
   // UserMessage每一次用户发消息都会带上 你是我的好朋友，请用粤语回答问题这句话
    @UserMessage("你是我的好朋友，请用粤语回答问题。{{message}}")
    String chat2(@MemoryId int memoryId, @V("message") String userMessage);

    @SystemMessage(fromResource = "my-prompt-template3.txt")
    String chat3(
            @MemoryId int memoryId,
            @UserMessage String userMessage,
            @V("username") String username,
            @V("age") int age
    );
}
