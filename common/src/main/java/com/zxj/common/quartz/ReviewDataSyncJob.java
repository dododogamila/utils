package cn.zxj.utils.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@Slf4j
public class ReviewDataSyncJob implements Job {

    private static volatile int state = 0;

    public static final int STATE_NOTSTART = 0;

    public static final int STATE_EXECUTING = 1;

    public static final int STATE_COMPLETED = 2;


    public static void setState(int state){
        ReviewDataSyncJob.state = state;
    }

    public static int getState(){
        return  ReviewDataSyncJob.state ;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("dataSync start");
        dataSync();
        log.info("dataSync end");
    }

    private void dataSync(){

    }
}
