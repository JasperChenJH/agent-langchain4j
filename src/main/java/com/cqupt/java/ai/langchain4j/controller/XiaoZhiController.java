package com.cqupt.java.ai.langchain4j.controller;

import com.cqupt.java.ai.langchain4j.assistant.XiaoZhiAgent;
import com.cqupt.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Slf4j
@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaoZhiController {

    @Autowired
    XiaoZhiAgent xiaoZhiAgent;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat",produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return xiaoZhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());

    }
}
