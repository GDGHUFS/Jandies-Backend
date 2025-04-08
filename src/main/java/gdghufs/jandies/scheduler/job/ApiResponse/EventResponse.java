package gdghufs.jandies.scheduler.job.ApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;

    @Data
    public static class actor{
        @JsonProperty("id")
        private String id;
        @JsonProperty("login")
        private String login;
        @JsonProperty("display_login")
        private String display_login;
        @JsonProperty("gravatar_id")
        private String gravatar_id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("avatar_url")
        private String avatar_url;
    }

    @Data
    public static class repo{
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("url")
        private String url;
    }

    @Data
    public static class payload{
        @JsonProperty("push_id")
        private String push_id;
        @JsonProperty("size")
        private String size;
        @JsonProperty("distinct_size")
        private String distinct_size;
        @JsonProperty("ref")
        private String ref;
        @JsonProperty("head")
        private String head;
        @JsonProperty("before")
        private String before;

        @Data
        public static class commits{
            @JsonProperty("sha")
            private String sha;
            @Data
            public static class author{
                @JsonProperty("email")
                private String email;
                @JsonProperty("name")
                private String name;
            }
            @JsonProperty("message")
            private String message;
            @JsonProperty("distinct")
            private String distinct;
            @JsonProperty("url")
            private String url;
        }
    }

    @Data
    public static class created_at {
        @JsonProperty("created_at")
        private String created_at;
    }
}
