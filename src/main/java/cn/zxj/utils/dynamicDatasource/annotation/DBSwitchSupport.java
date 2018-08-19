package cn.zxj.utils.dynamicDatasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识支持主从切换的dao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBSwitchSupport {

    // 切换到的数据源
    public String dataSource() default "";


}
