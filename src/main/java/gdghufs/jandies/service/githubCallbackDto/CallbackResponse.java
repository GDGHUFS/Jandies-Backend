package gdghufs.jandies.service.githubCallbackDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CallbackResponse {
    private GithubTokenDto tokenInfo;
    private GithubDetailDto githubInfo;
}