package gdghufs.jandies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    // GetValue
    // "USER", "ADMIN"
    private final String value;

}