package com.cqupt.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
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
    @Test
    public void testDashScopeWanx(){
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-turbo")
                .apiKey("sk-9018992edfc9400cb4e7c68f8612b549")
                .build();
        Response<Image> response = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的\n" +
                "古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她\n" +
                "有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的\n" +
                "连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植\n" +
                "物丛生，营造出神秘而梦幻的氛围");
        System.out.println(response.content().url());
    }
}