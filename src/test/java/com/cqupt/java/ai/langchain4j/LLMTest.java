package com.cqupt.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class LLMTest {
    /**
     * gpt-4o-mini语言模型接入测试
     */
    @Test
    public void testGPTDemo() {
//初始化模型
        OpenAiChatModel model =
                OpenAiChatModel.builder().
                apiKey("demo").
                modelName("gpt-4o-mini").
                build();
//LangChain4j提供的代理服务器，该代理服务器会将演示密钥替换成真实密钥， 再将请求发给OpenAI API
//.baseUrl("http://langchain4j.dev/demo/openai/v1") //设置模型api地址（如果apiKey = "demo"，则可省略baseUrl的配置）
        //设置模型apiKey
        //设置模型名称
        //向模型提问
        String answer = model.chat("你好");
        //输出结果
        System.out.println(answer);
    }
    @Autowired
    OpenAiChatModel openAiChatModel;
    @Test
    public void testSpringBoot(){
        //提问
        String answer = openAiChatModel.chat("hello");
        System.out.println(answer);
    }

    @Autowired
    private OllamaChatModel ollamaChatModel;
    @Test
    public void testOllamaChatModel(){
        String answer = ollamaChatModel.chat("99*99=?");
        System.out.println(answer);
    }
    //通译千问
    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testQwenChatModel(){
        //提问
        String answer = qwenChatModel.chat("hello");
        System.out.println(answer);
    }
    //通译万象
//    @Test
//    public void testDashScopeWanx(){
//            QwenStreamingChatModel qwenStreamingChatModel = QwenStreamingChatModel.builder()
//                .modelName("wanx2.1-t2i-turbo")
//                .apiKey("sk-9018992edfc9400cb4e7c68f8612b549")
//                .build();
//        Flux<String> response = qwenStreamingChatModel.chat("ww");
//        System.out.println(response.content().url());
//    }
}