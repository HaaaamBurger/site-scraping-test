package org.main.sitescrapingtest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JobsScraperUtils {

    private final ObjectMapper objectMapper;

    private static final String TECH_STARS_URI = "https://jobs.techstars.com";

    public String getUrlWithEncodedFunction(String jobFunction) {
        String jobFunctionsJson = generateJobFunctionsJson(jobFunction);
        String encodedJobFunction = Base64.getEncoder().encodeToString(jobFunctionsJson.getBytes(StandardCharsets.UTF_8));
        return TECH_STARS_URI + "/jobs?filter=" + encodedJobFunction;

    }

    public JsonNode extractJobsFromHtml(String html) throws JsonProcessingException {
        String jsonData = extractJsonFromScript(html);
        JsonNode root = objectMapper.readTree(jsonData);

        return root.path("props")
                .path("pageProps")
                .path("initialState")
                .path("jobs")
                .path("found");
    }

    private String generateJobFunctionsJson(String jobFunction) {
        return "{\"job_functions\":[\"" + jobFunction + "\"]}";
    }

    private String extractJsonFromScript(String scriptHtml) {
        Pattern pattern = Pattern.compile("\\{.*\"jobDiscardIds\".*\\}");
        Matcher matcher = pattern.matcher(scriptHtml);
        if (matcher.find()) {
            return matcher.group(0);
        }
        throw new RuntimeException("JSON script not found");
    }

}
