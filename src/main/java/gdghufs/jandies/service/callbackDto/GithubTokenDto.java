package gdghufs.jandies.service.callbackDto;

import lombok.Data;

@Data
public class GithubTokenDto {
    private String access_token;
    private String token_type;
    private String scope;
    private String error;
    private String error_description;
    private String error_uri;
}