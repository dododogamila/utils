package com.zxj.dubbo.component;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmptyServiceCleaner {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    // org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient
    @Autowired
    private DiscoveryClient discoveryClient;

    public void doClean() {
        try {
            List<String> services = discoveryClient.getServices();
            for (String service : services) {
                if (discoveryClient.getInstances(service).size() == 0) {
                    nacosDiscoveryProperties.namingMaintainServiceInstance().deleteService(service);
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

}
