package com.cqupt.java.ai.langchain4j.assistant;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;
// 由于properties中声明了多个 chatModel 所以这里需要显示声明 wiringMode = AiServiceWiringMode.EXPLICIT , chatModel = "qwenChatModel"
@AiService(
            wiringMode = EXPLICIT,
            chatModel = "qwenChatModel"
          )
public interface Assistant {
    String chat(String userMassage);
}
