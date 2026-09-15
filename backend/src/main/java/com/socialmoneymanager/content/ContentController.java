package com.socialmoneymanager.content;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {
    @PostMapping("/ideas")
    public Map<String, Object> ideas(@RequestBody ContentRequest request) {
        String topic = request.topic() == null || request.topic().isBlank() ? "online income" : request.topic().trim();
        List<String> ideas = List.of(
            "3 mistakes people make with " + topic,
            "A beginner's 30-day plan for " + topic,
            "5 tools that can save time on " + topic,
            "How to turn " + topic + " into a repeatable content series",
            "Myth vs reality: what actually works with " + topic
        );
        return Map.of("topic", topic, "ideas", ideas);
    }

    public record ContentRequest(String topic) {}
}
