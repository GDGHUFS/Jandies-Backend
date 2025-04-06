package gdghufs.jandies.service.callbackDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CallbackResponse {
    private GithubTokenDto tokenInfo;
    private GithubDetailDto githubInfo;
}