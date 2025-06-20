package com.cqupt.java.ai.langchain4j.controller;

import com.cqupt.java.ai.langchain4j.assistant.XiaoZhiAgent;
import com.cqupt.java.ai.langchain4j.bean.ChatForm;
import com.cqupt.java.ai.langchain4j.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SuppressWarnings("all")
@Slf4j
@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaoZhiController {
    @Autowired
    XiaoZhiAgent xiaoZhiAgent;
    @Resource
    PromptService promptService;
    @Operation(summary = "对话")
    //produces = "text/stream;charset=utf-8
    //声明返回的是流式文本（SSE 格式），客户端可以逐步接收数据，而非一次性等待全部响应
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    //Flux<String>
    //表示一个异步的字符序列流，支持背压（Backpressure），避免内存溢出。
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        //生成提示 用户个性提示词
        String prompt = promptService.generatePrompt(chatForm.getMessage());
        log.info("用户输入：{}", prompt);
        return xiaoZhiAgent.chat(chatForm.getMemoryId(), prompt)
                .doOnNext(message -> log.info("原始消息: {}", message))
                .concatMap(text -> Flux.fromStream(
                        text.codePoints()
                                //正确处理代理对（Surrogate Pairs），如 Emoji（👍）或生僻字
                                .mapToObj(cp -> new String(Character.toChars(cp)))
                ))
                //每个字符间隔 150ms 发送，实现“逐字打印”的交互效果。
                .delayElements(Duration.ofMillis(150))
                .doOnComplete(() -> log.info("消息流处理完毕"));
    }
}
