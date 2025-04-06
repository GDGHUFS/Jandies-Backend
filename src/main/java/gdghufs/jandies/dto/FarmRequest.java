package gdghufs.jandies.dto;

import gdghufs.jandies.entity.FarmType;
import gdghufs.jandies.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "농장 데이터 전송 객체")
public class FarmRequest {
    @Schema(description = "농장 이름", example = "한국외대 GDG on Campus")
    private String name;
    @Schema(description = "농장 타입", example = "동아리, 지역모임")
    private FarmType type;
    @Schema(description = "농장 지역", example = "서울")
    private Location location;
    @Schema(description = "농장 이미지", example = "농장 이미지 (MultipartFile 형식으로 전송) -> form-data iamge")
    private MultipartFile image;
}