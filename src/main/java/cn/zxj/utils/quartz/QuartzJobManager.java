package cn.zxj.utils.quartz;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobManager {

    @Autowired
    private Scheduler scheduler;

    private static final String CORN_SCHEDULE ="0 0 12,0 * * ?" ;

    private JobDetail getJobDetail(){
        return  JobBuilder.newJob(ReviewDataSyncJob.class)
                .withIdentity("job","group")
                .build();
    }

    private CronTrigger getTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(CORN_SCHEDULE);
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger","group")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
    private void reviewDataSyncJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = getJobDetail();
        CronTrigger trigger = getTrigger();

        ReviewDataSyncJobListener jobListener = new ReviewDataSyncJobListener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
        scheduler.getListenerManager().addJobListener(jobListener, matcher);

        scheduler.scheduleJob(jobDetail,trigger);
    }

    public int getReviewDataSyncJobState() throws SchedulerException {
        return ReviewDataSyncJob.getState();
    }

    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }else {
            scheduler.pauseJob(jobKey);
        }
    }

    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }else {
            scheduler.resumeJob(jobKey);
        }
    }

    public void startJob() throws SchedulerException {
        reviewDataSyncJob(scheduler);
    }

}
