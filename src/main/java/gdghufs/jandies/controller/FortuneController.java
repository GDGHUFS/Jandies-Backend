package gdghufs.jandies.controller;

import gdghufs.jandies.dto.FortuneDto;
import gdghufs.jandies.entity.Fortune;
import gdghufs.jandies.service.FortuneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class FortuneController {

    private final FortuneService fortuneService;

    @Operation(summary = "오늘의 운세 - ✅전체 접근", tags = {"Fortune"})
    @GetMapping("/Fortune")
    public FortuneDto  getToday() {
        final LocalDate now = LocalDate.now();
        Fortune fortune = fortuneService.getTodayForturn(now.getDayOfYear()-1);
        return FortuneDto.toFarmDto(fortune);
    }

}
