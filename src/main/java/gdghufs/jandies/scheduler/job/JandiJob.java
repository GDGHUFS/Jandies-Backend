package gdghufs.jandies.scheduler.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.JandiId;
import gdghufs.jandies.entity.User;
import gdghufs.jandies.repository.AuthRepository;
import gdghufs.jandies.repository.JandiRepository;
import gdghufs.jandies.repository.UserRepository;
import gdghufs.jandies.scheduler.job.ApiResponse.ContributionResponse;
import gdghufs.jandies.scheduler.job.ApiResponse.ContributionResponse.ContributionDay;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JandiJob {

    private final JandiRepository jandiRepository;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void SaveAllJandi() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            saveJandiByUser(user);
        }
    }

    public void saveJandiByUser(User user) {
        String githubToken = authRepository.findByUser(user).orElseThrow(
                () -> new RuntimeException("Github token not found")
        ).getGithubAccessToken();

        String url = "https://api.github.com/graphql";

        String query = """
            query ($userName: String!, $from: DateTime!, $to: DateTime!) {
                user(login: $userName) {
                    contributionsCollection(from: $from, to: $to) {
                        contributionCalendar {
                            totalContributions
                            weeks {
                                contributionDays {
                                    contributionCount
                                    contributionLevel
                                    color
                                    date
                                }
                            }
                        }
                    }
                }
            }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = Map.of(
                "query", query,
                "variables", Map.of(
                        "userName", user.getLogin(),
                        "from", LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        "to", LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                )
        );

        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println(response.getBody());
            ContributionResponse contributionResponse = objectMapper.readValue(response.getBody(), ContributionResponse.class);

            List<ContributionDay> contributionDays = new ArrayList<>();
            var weeks = contributionResponse.getData().getUser().getContributionsCollection().getContributionCalendar().getWeeks();

            for (var week : weeks) {
                contributionDays.addAll(week.getContributionDays());
            }

            for (ContributionDay day : contributionDays) {
                Jandi jandi = Jandi.builder()
                        .id(new JandiId(user.getId(), LocalDate.parse(day.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        .count((long) day.getContributionCount())
                        .isActive(day.getContributionCount() != 0)
                        .build();

                jandiRepository.save(jandi);
            }

        } catch (Exception e) {
            System.out.println("GraphQL 응답 파싱 실패: " + e.getMessage());
            throw new RuntimeException("Failed to parse GraphQL response", e);
        }
    }
}
