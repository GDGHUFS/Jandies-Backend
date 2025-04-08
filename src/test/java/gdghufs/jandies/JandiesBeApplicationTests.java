package gdghufs.jandies;

import gdghufs.jandies.repository.FortuneRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JandiesBeApplicationTests {

    private final FortuneRepository fortuneRepository;

    JandiesBeApplicationTests(FortuneRepository fortuneRepository) {
        this.fortuneRepository = fortuneRepository;
    }

    @Test
    void contextLoads() {
        System.out.println(fortuneRepository.findTodayFortune(0));
    }

}
