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
    public void SaveAlJandiLongerThanOneDay() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
                    saveAllJandiByUser(user);
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
                        "from", LocalDateTime.now().minusDays(1).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
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


    public void saveAllJandiByUser(User user) {
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

        // 날짜 범위 설정
        LocalDateTime from = user.getGithubCreatedAt();
        LocalDateTime to = LocalDateTime.now();

        ArrayList<Map<String, String>> dateRanges = new ArrayList<>();
        LocalDateTime fromDateTime = user.getGithubCreatedAt();
        while (fromDateTime.isBefore(to)) {
            LocalDateTime toDateTime = fromDateTime.plusYears(1);
            if (toDateTime.isAfter(to)) {
                toDateTime = to;
            }
            dateRanges.add(Map.of(
                    "from", fromDateTime.toString(),
                    "to", toDateTime.toString()
            ));
            fromDateTime = toDateTime;
        }
        //여러 요청 합치기
        List<ContributionResponse> contributionResponses = new ArrayList<>();
        List<ContributionResponse.ContributionDay> allContributionDays = new ArrayList<>();
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Content-Type", "application/json");

        for (Map<String, String> dateRange : dateRanges) {
            System.out.println(dateRange);
            System.out.println(dateRange.get("from"));
            System.out.println(dateRange.get("to"));
            // 요청 바디 생성
            Map<String, Object> requestBody = Map.of(
                    "query", query,
                    "variables", Map.of(
                            "userName", user.getLogin(),
                            "from", dateRange.get("from"),
                            "to", dateRange.get("to")
                    )
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 요청 전송
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println(response.getBody());

            try {
                ContributionResponse contributionResponse = objectMapper.readValue(response.getBody(), ContributionResponse.class);

                // 각 응답에서 contributionDays 리스트를 가져와서 합침
                for (ContributionResponse.Week week : contributionResponse.getData().getUser().getContributionsCollection().getContributionCalendar().getWeeks()) {
                    allContributionDays.addAll(week.getContributionDays());

                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to parse GraphQL response", e);
            }

        }

        List<Jandi> jandis = new ArrayList<>();

        for ( ContributionResponse.ContributionDay day : allContributionDays) {
            Jandi jandi = Jandi.builder()
                    .id(new JandiId(user.getId(), LocalDate.parse(day.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                    .count((long) day.getContributionCount())
                    .isActive(day.getContributionCount() != 0)
                    .build();

            System.out.println(jandi.toString());

            jandis.add(jandi);
        }

        jandiRepository.saveAll(jandis);

    }


}
