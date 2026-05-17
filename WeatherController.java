package com.example.weather_dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherController {

    private final RestClient restClient;

    public WeatherController() {
        // We use RestClient.create() to build it ourselves,
        // bypassing the need for Spring's auto-configured builder!
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weather.gov")
                .defaultHeader("User-Agent", "FCC-Student-App")
                .build();
    }

    /**
     * Fetches current weather observations for Fresno, CA and New York, NY.
     * Returns a combined raw JSON response.
     */
    @GetMapping("/weather")
    public Map<String, Object> getFresnoAndNewYorkWeather() {
        // NWS Station IDs: KFAT (Fresno), KNYC (New York Central Park)
        String fresnoUrl = "/stations/KFAT/observations/latest";
        String nycUrl = "/stations/KNYC/observations/latest";

        // Fetch raw JSON as a Map to preserve the structure
        Object fresnoData = restClient.get()
                .uri(fresnoUrl)
                .retrieve()
                .body(Map.class);

        Object nycData = restClient.get()
                .uri(nycUrl)
                .retrieve()
                .body(Map.class);

        // Combine both into one JSON object
        Map<String, Object> result = new HashMap<>();
        result.put("fresno_ca", fresnoData);
        result.put("new_york_ny", nycData);

        return result;
    }
}