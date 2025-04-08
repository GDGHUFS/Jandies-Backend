package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Farm;
import gdghufs.jandies.entity.FarmType;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class JandiDto {

    Long userId;
    LocalDate date;
    Long count;
    Boolean isActive;

//    fromEntity
    public static JandiDto fromEntity(Jandi jandi) {
        return new JandiDto(
                jandi.getId().getUserId(),
                jandi.getId().getDate(),
                jandi.getCount(),
                jandi.getIsActive()
        );
    }

    // toEntity
    public gdghufs.jandies.entity.Jandi toEntity() {
        return gdghufs.jandies.entity.Jandi.builder()
                .id(gdghufs.jandies.entity.JandiId.builder()
                        .userId(userId)
                        .date(date)
                        .build())
                .count(count)
                .isActive(isActive)
                .build();
    }

}
