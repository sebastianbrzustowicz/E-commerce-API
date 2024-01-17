package com.sebastianbrzustowicz.shopapi.service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.List;

public class ChatGPTHelper {
    OpenAiService service;

    public ChatGPTHelper() {
        service = new OpenAiService("YOUR-API-KEY-HERE", Duration.ofSeconds(30));
    }

    public String getGPTAdvice(String question) {
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo")
                .build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();

        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
