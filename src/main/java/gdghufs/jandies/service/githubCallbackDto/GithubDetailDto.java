package gdghufs.jandies.service.githubCallbackDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;



@Data
@RequiredArgsConstructor
public class GithubDetailDto {
    private String login;
    private Long id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private String user_view_type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitter_username;
    private String notification_email;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String private_gists;
    private int total_private_repos;
    private int owned_private_repos;
    private int disk_usage;
    private int collaborators;
    private boolean two_factor_authentication;
    private Plan plan;
}

@Data
class Plan {
    private String name;
    private int space;
    private int collaborators;
    private int private_repos;
}