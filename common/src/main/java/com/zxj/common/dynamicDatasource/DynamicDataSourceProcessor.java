package cn.zxj.utils.dynamicDatasource;
import cn.zxj.utils.dynamicDatasource.annotation.DBSwitchSupport;
import org.aspectj.lang.ProceedingJoinPoint;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  动态切换数据源
 */
public class DynamicDataSourceProcessor implements BeanPostProcessor {
	/**
	 * 在执行service方法之前根据method方法名匹配相应的主从库 aspectj joinpoint
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object determineDataSource(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		String className = pjp.getSignature().getDeclaringType().getCanonicalName();
		DBContextHolder.DataSourceType old = DBContextHolder.getDBType();
		if(DataSourceSwitchConfig.getInstance().isSupportDataSourceSwitch(className)){// 当前dao支持主从切换
			boolean isWrite = false;
			for (String mappedName : this.wirteMethodList) {// 循环检验当前方法是否写方法
				if (this.isMatch(methodName, mappedName)) {
					isWrite = true;
					break;
				}
			}
			if (isWrite) {// 写方法设置为主库
				logger.debug(methodName + " USE master-datasource.");
				DBContextHolder.setDBType(DBContextHolder.DataSourceType.master);
			} else {// 非写方法设置为从库
				logger.debug(methodName	+ " USE slave-datasource.");
				DBContextHolder.setDBType(DBContextHolder.DataSourceType.slave);
			}
		}
		try {
			return pjp.proceed();
		} finally {
			DBContextHolder.setDBType(old);
		}
	}

	/**
	 * spring初始化bean之后执行
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof MapperFactoryBean){
			Annotation[] annotationArray= ((MapperFactoryBean) bean).getMapperInterface().getAnnotations();
			for(Annotation annotation:annotationArray){
				if(annotation.annotationType() == DBSwitchSupport.class){
					String className = ((MapperFactoryBean) bean).getMapperInterface().getCanonicalName();
					DataSourceSwitchConfig.getInstance().addDBSwitchSupportClass(className,bean);
				}
			}
		}

		if (!(bean instanceof NameMatchTransactionAttributeSource)) {
			return bean;
		}

		try {
			NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource) bean;
			Field nameMapField = ReflectionUtils.findField(
					NameMatchTransactionAttributeSource.class, "nameMap");
			nameMapField.setAccessible(true);
			Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField
					.get(transactionAttributeSource);

			for (Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
				RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute) entry
						.getValue();
				String methodName = entry.getKey();
				// 写操作
				if (!attr.isReadOnly()) {// 非只读方法添加到写方法列表
					this.wirteMethodList.add(methodName);
				}
			}

		} catch (Exception e) {
			throw new ReadWriteDataSourceTransactionException(
					"process read/write transaction error", e);
		}

		return bean;
	}

	/**
	 * spring初始化bean之前执行
	 */
	//@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	protected boolean isMatch(String methodName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, methodName);
	}

	private class ReadWriteDataSourceTransactionException extends
            NestedRuntimeException {
		public ReadWriteDataSourceTransactionException(String message,
				Throwable cause) {
			super(message, cause);
		}
	}

	private static final Logger logger = LoggerFactory
			.getLogger(DynamicDataSourceProcessor.class);

	private List<String> wirteMethodList = new ArrayList<String>();
}
