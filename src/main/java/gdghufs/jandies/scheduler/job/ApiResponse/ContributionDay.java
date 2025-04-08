package gdghufs.jandies.scheduler.job.ApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContributionDay {
    @JsonProperty("contributionCount")
    private int contributionCount;
    @JsonProperty("contributionLevel")
    private String contributionLevel;
    @JsonProperty("color")
    private String color;
    @JsonProperty("date")
    private String date;
}