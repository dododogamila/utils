package com.zxj.dubbo.configuration;

import com.zxj.dubbo.component.EmptyServiceCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomDubboConfig {

    @Autowired
    private EmptyServiceCleaner emptyServiceCleaner;

//    @EventListener(HeartbeatEvent.class)
    public void onHeartbeatEvent(HeartbeatEvent event) {
        emptyServiceCleaner.doClean();
    }
}
