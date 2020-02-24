package cn.zxj.utils.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class ReviewDataSyncJobListener implements JobListener {
    @Override
    public String getName() {
        return "reviewDataSyncJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("jobListener:job to be executed");
        ReviewDataSyncJob.setState(ReviewDataSyncJob.STATE_EXECUTING);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("jobListener:job was executed");
        ReviewDataSyncJob.setState(ReviewDataSyncJob.STATE_COMPLETED);
    }
}
