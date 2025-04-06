package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Farm;
import gdghufs.jandies.entity.FarmType;
import gdghufs.jandies.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FarmDto {
    @Schema(description = "농장 ID", example = "1")
    private Long id;

    @Schema(description = "농장 이름", example = "GDGHUFS 농장")
    private String name;

    @Schema(description = "농장 타입", example = "GITHUB, INSTAGRAM, NAVERBLOG ...")
    private FarmType type;

    @Schema(description = "농장 위치", example = "GDGHUFS")
    private Location location;

    @Schema(description = "농장 이미지 경로", example = "/images/farm.png")
    private String imagePath;

    @Schema(description = "농장 유저", example = "")
    private List<UserSimpleDto> users;


    public static FarmDto fromEntity(Farm farm) {
        return new FarmDto(
                farm.getId(),
                farm.getName(),
                farm.getType(),
                farm.getLocation(),
                farm.getImagePath(),
                farm.getUsers().stream()
                        .map(UserSimpleDto::fromEntity) // DTO 변환
                        .toList()
        );
    }
}
