package gdghufs.jandies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FarmType {
    지역모임("지역모임"),
    동아리("동아리");

    private String value;
}
