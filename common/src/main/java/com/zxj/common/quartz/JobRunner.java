package cn.zxj.utils.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//@Component
@Slf4j
public class JobRunner implements CommandLineRunner {

    @Autowired
    private QuartzJobManager quartzJobManager;

    @Override
    public void run(String... args) throws Exception {
        log.info("jobRunner start");
        quartzJobManager.startJob();
    }
}
