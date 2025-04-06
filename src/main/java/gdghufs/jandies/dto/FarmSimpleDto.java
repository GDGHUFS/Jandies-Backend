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
public class FarmSimpleDto {
    private Long id;

    private String name;

    private FarmType type;

    private Location location;

    private String imagePath;


    
    public static FarmSimpleDto fromEntity(Farm farm) {
        return new FarmSimpleDto(
                farm.getId(),
                farm.getName(),
                farm.getType(),
                farm.getLocation(),
                farm.getImagePath()
        );
    }
}
