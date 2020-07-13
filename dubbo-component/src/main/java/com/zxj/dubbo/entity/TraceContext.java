package com.zxj.dubbo.entity;


public class TraceContext {

    private static final ThreadLocal<AttachmentContent> ATTACHMENT = new InheritableThreadLocal<>();

    public static void setAttachment(AttachmentContent attachment){
        ATTACHMENT.set(attachment);
    }

    public static AttachmentContent getAttachment(){
        return ATTACHMENT.get();
    }

    public static void clear() {
        ATTACHMENT.remove();
    }
}
