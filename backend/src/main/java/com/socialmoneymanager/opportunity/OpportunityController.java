package com.socialmoneymanager.opportunity;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/opportunities")
public class OpportunityController {
    private static final List<Opportunity> OPPORTUNITIES = List.of(
        new Opportunity("AI short-form content service", "Service", "Create short videos for local businesses using AI-assisted scripting and editing.", 4, 2500, 4, 88),
        new Opportunity("Niche digital template store", "Digital Product", "Sell reusable templates for a focused audience through a digital marketplace.", 3, 1000, 5, 84),
        new Opportunity("Affiliate content channel", "Affiliate", "Build useful niche content and earn commissions from relevant products or services.", 3, 1500, 4, 81),
        new Opportunity("Social media management service", "Service", "Manage content calendars, publishing and analytics for small businesses.", 4, 5000, 5, 79),
        new Opportunity("Educational micro-course", "Education", "Package a practical skill into a short paid course supported by social content.", 4, 3000, 4, 76)
    );

    @GetMapping
    public List<Opportunity> search(@RequestParam(defaultValue = "") String q) {
        String query = q.trim().toLowerCase();
        return OPPORTUNITIES.stream()
            .filter(o -> query.isBlank() || o.title().toLowerCase().contains(query) || o.category().toLowerCase().contains(query) || o.description().toLowerCase().contains(query))
            .toList();
    }

    public record Opportunity(String title, String category, String description, int difficulty, int startupCostInr, int scalability, int score) {}
}
