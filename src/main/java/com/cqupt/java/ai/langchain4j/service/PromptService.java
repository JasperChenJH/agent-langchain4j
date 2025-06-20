package com.cqupt.java.ai.langchain4j.service;

/**
 * 动态生成提示词实现个性化
 */
public interface PromptService {
    /**
     * 生成提示词
     *
     * @param userMessage 用户输入
     * @return 提示词
     */
    String generatePrompt(String userMessage);
}
