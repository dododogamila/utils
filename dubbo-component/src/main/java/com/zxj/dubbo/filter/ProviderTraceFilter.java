package com.zxj.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.zxj.dubbo.entity.AttachmentContent;
import com.zxj.dubbo.entity.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import static com.zxj.dubbo.entity.Const.ATTACHMENT_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

@Slf4j
@Activate(group = {PROVIDER})
public class ProviderTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("ProviderTraceFilter come in");
        try{
            String contextStr = RpcContext.getContext().getAttachment(ATTACHMENT_KEY);
            AttachmentContent attachmentContent = JSON.parseObject(contextStr,AttachmentContent.class);
            if (attachmentContent!=null){
                TraceContext.setAttachment(attachmentContent);
                MDC.put("traceId",attachmentContent.getTraceId());
            }
            return invoker.invoke(invocation);
        }finally {
            MDC.remove("traceId");
        }
    }
}
