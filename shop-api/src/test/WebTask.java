import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebTask {
@Scheduled(cron = "0/5****?")
protected void taskJob(){
        System.out.println("hauha");
    }
}
