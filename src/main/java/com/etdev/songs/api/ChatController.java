package com.etdev.songs.api;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    private final ChatClient  chatClient;
    private final RagService ragService;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Value("classpath:/prompts/default-pirate.st")
    private Resource defaultPirate;

    @Value("classpath:/docs/wikipedia-curling-shortened.md")
    private Resource docsToStuffResource;

    @Value("classpath:/prompts/qa-prompt.st")
    private Resource qaPromptResource;

    @Autowired
    public ChatController(@Qualifier("openAiChatClient") ChatClient chatClient, RagService ragService) {
        this.ragService = ragService;
        this.chatClient = chatClient;
    }


    @GetMapping("/ai/generate")
    public Completion generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return new Completion(chatClient.call(message));
    }

    @GetMapping("/ai/output")
    public ActorsFilms output(@RequestParam(value = "actor", defaultValue = "Jeff Bridges") String actor,
                              @RequestParam(value = "limit", defaultValue = "5") String limit) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class); // can break easily

        String format = outputParser.getFormat();
        String userMessage = """
                Generate the filmography for the actor {actor}.
                {format}, limit by {limit}. Do not include backticks or word "json". 
                """;
        PromptTemplate promptTemplate = new PromptTemplate(userMessage,
                Map.of("actor", actor,
                        "limit", limit,
                        "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();
        ActorsFilms actorsFilms = outputParser.parse(generation.getOutput().getContent());
        return actorsFilms;
    }

    @GetMapping("/ai/prompt")
    public Generation completion(@RequestParam(value = "adjective", defaultValue = "funny") String adjective,
                                 @RequestParam(value = "topic", defaultValue = "cows") String topic) {

        PromptTemplate promptTemplate = new PromptTemplate(jokeResource);

        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));
        System.out.println("Prompt"+ prompt);
        return chatClient.call(prompt).getResult();
    }

    @GetMapping("ai/roles")
    public Generation roles(
            @RequestParam(value = "message", defaultValue = """
                            Tell me about three famous pirates from Golden Age of
                            Piracy and what the did. Write at least a sentence for each pirate.
                            """ ) String message,
            @RequestParam(value = "name", defaultValue = "Hook") String name,
            @RequestParam(value = "voice", defaultValue = "pirate") String voice ) {

        var userMessage = new UserMessage(message);
        var systemPromptTemplate = new SystemPromptTemplate(systemResource);
        var systemMessage = systemPromptTemplate.createMessage(
                Map.of(
                        "name", name,
                        "voice", voice));
        var prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatClient.call(prompt).getResult();
    }

    @GetMapping("/ai/stuff")
    public Completion stuffing(@RequestParam( value = "message", defaultValue = "Which athletes won the mixed doubles gold medal in curling at the 2022 Winter Olympics?") String message,
                                 @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {
        PromptTemplate promptTemplate = new PromptTemplate(qaPromptResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        if (stuffit) {
            map.put("context", docsToStuffResource);
        } else {
            map.put("context", "");
        }
        Prompt prompt = promptTemplate.create(map);
        var aiResponse = chatClient.call(prompt);
        return new Completion(aiResponse.getResult().getOutput().getContent());
    }

    @GetMapping("/ai/rag")
    public Generation rag(@RequestParam(defaultValue = "What bike is good for city commuting?") String message) {
        return ragService.retrieve(message);
    }
}
