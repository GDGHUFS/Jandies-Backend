package gdghufs.jandies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum LinkTreeType {
    Github("github"),
    Instagram("instagram"),
    Naverblog("naverblog");

    private final String value;

}