package com.socialmoneymanager.privacy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/privacy")
public class PrivacyController {
    private static final Pattern EMAIL = Pattern.compile("\\b[\\w.+-]+@[\\w-]+\\.[\\w.-]+\\b");
    private static final Pattern PHONE = Pattern.compile("(?<!\\d)(?:\\+?\\d[\\d .-]{8,13}\\d)(?!\\d)");
    private static final Pattern SECRET = Pattern.compile("(?i)\\b(api[_ -]?key|password|secret|access[_ -]?token)\\s*[:=]");

    @PostMapping("/check")
    public Map<String, Object> check(@RequestBody PrivacyRequest request) {
        String text = request.text() == null ? "" : request.text();
        List<Finding> findings = new ArrayList<>();
        if (EMAIL.matcher(text).find()) findings.add(new Finding("EMAIL", "HIGH", "Possible email address detected."));
        if (PHONE.matcher(text).find()) findings.add(new Finding("PHONE", "HIGH", "Possible phone number detected."));
        if (SECRET.matcher(text).find()) findings.add(new Finding("SECRET", "CRITICAL", "Possible credential/secret marker detected."));
        return Map.of("safeToPublish", findings.isEmpty(), "findings", findings);
    }

    public record PrivacyRequest(String text) {}
    public record Finding(String type, String severity, String message) {}
}
