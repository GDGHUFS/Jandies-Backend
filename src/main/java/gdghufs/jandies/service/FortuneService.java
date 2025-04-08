package gdghufs.jandies.service;

import gdghufs.jandies.dto.FortuneDto;
import gdghufs.jandies.entity.Fortune;
import gdghufs.jandies.repository.FortuneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FortuneService {
    private final FortuneRepository fortuneRepository;

    public FortuneDto getTodayForturn(int offset) {
        return FortuneDto.toFarmDto(fortuneRepository.findTodayFortune(offset));
    }

}