package gdghufs.jandies.scheduler.job.ApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributionResponse {

    @JsonProperty("data")
    private Data data;

    @lombok.Data
    public static class Data {
        @JsonProperty("user")
        private User user;

        // Getter and Setter
    }
    @lombok.Data
    public static class User {
        @JsonProperty("contributionsCollection")
        private ContributionsCollection contributionsCollection;

        // Getter and Setter
    }
    @lombok.Data
    public static class ContributionsCollection {
        @JsonProperty("contributionCalendar")
        private ContributionCalendar contributionCalendar;

        // Getter and Setter
    }
    @lombok.Data
    public static class ContributionCalendar {
        @JsonProperty("totalContributions")
        private int totalContributions;

        @JsonProperty("weeks")
        private List<Week> weeks;

        // Getter and Setter
    }
    @lombok.Data
    public static class Week {
        @JsonProperty("contributionDays")
        private List<ContributionDay> contributionDays;

        // Getter and Setter
    }
    @lombok.Data
    public static class ContributionDay {
        @JsonProperty("contributionCount")
        private int contributionCount;
        @JsonProperty("contributionLevel")
        private String contributionLevel;
        @JsonProperty("color")
        private String color;
        @JsonProperty("date")
        private String date;

        // Getter and Setter
    }

    // Getter and Setter
}