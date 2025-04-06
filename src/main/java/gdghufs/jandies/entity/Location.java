package gdghufs.jandies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum Location {
    서울("서울"),
    경기("경기"),
    인천("인천"),
    대전("대전"),
    대구("대구"),
    부산("부산"),
    광주("광주"),
    울산("울산"),
    세종("세종"),
    강원("강원"),
    충북("충북"),
    충남("충남"),
    전북("전북"),
    전남("전남"),
    경북("경북"),
    경남("경남"),
    제주("제주");

    private String location;

}
