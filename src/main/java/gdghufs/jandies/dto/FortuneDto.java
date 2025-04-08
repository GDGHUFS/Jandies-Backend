package gdghufs.jandies.dto;

import gdghufs.jandies.entity.Fortune;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FortuneDto {
    LocalDate date;
    String fortune;

    public FortuneDto(Fortune fortune) {
        this.date = LocalDate.now();
        this.fortune = fortune.getFortune();
    }

    public static FortuneDto toFarmDto(Fortune fortune) {
        return new FortuneDto(
                LocalDate.now(),
                fortune.getFortune()
        );
    }
}
