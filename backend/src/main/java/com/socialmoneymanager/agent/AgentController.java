package com.socialmoneymanager.agent;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {
    @PostMapping("/plan")
    public Map<String, Object> plan(@RequestBody PlanRequest request) {
        String goal = request.goal() == null || request.goal().isBlank() ? "make money with social media" : request.goal().trim();
        return Map.of(
            "goal", goal,
            "plan", List.of(
                "Research opportunities matching your goal",
                "Score opportunities by cost, difficulty and scalability",
                "Generate a content series around the strongest opportunity",
                "Run a privacy check before any publication",
                "Require user approval before external publishing or account actions"
            ),
            "tools", List.of("opportunity-search", "content-ideas", "privacy-check"),
            "requiresApproval", true
        );
    }
    public record PlanRequest(String goal) {}
}
