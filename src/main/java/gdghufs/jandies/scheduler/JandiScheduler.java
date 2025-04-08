package gdghufs.jandies.scheduler;

import gdghufs.jandies.scheduler.job.JandiJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JandiScheduler {

    private final JandiJob jandiJob;

     @Scheduled(cron = "0 0 */1 * * *")
     public void schedule() {
         jandiJob.SaveAllJandi();
     }

}
