package com.zxj.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.zxj.dubbo.entity.AttachmentContent;
import com.zxj.dubbo.entity.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import static com.zxj.dubbo.entity.Const.ATTACHMENT_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

@Slf4j
@Activate(group = {CONSUMER})
public class ConsumerTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("ConsumerTraceFilter come in");
        String contextStr = RpcContext.getContext().getAttachment(ATTACHMENT_KEY);
        if (StringUtils.isBlank(contextStr)){
            AttachmentContent attachmentContent = TraceContext.getAttachment();
            contextStr = JSON.toJSONString(attachmentContent);
        }

        RpcContext.getContext().setAttachment(ATTACHMENT_KEY,contextStr);
        return invoker.invoke(invocation);
    }
}
