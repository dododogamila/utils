package cn.zxj.utils.beanOpt;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils {  
  
	public static void copyVpsProperties(Object source, Object target) {
		copyVpsProperties(source, target, null);
	}

	public static void copyVpsProperties(Object source, Object target,String[] ignoreProperties) throws BeansException {
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays
				.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null
					&& (ignoreProperties == null || (!ignoreList
							.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method tarReadMethod = targetPd.getReadMethod();
							Method writeMethod = targetPd.getWriteMethod();
							Class<?> returnType = tarReadMethod.getReturnType();
							if(value instanceof String && !returnType.isInstance(value)){
								//如果数据源是字符串类型，并且target不是字符串
								continue;
							}else if (!isWrapClass(returnType) && !returnType.isInstance(value)) {
								Object newInstance = returnType.newInstance();
								copyVpsProperties(value, newInstance);
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, newInstance);
							}else if(value instanceof Collection){
								Field sourceField = source.getClass().getDeclaredField(sourcePd.getName());
								ParameterizedType sourcelistGenericType = (ParameterizedType) sourceField.getGenericType();
								Type[] sourcelistGenericTypeActualTypeArguments = sourcelistGenericType.getActualTypeArguments();

								Field declaredField = target.getClass().getDeclaredField(targetPd.getName());
								ParameterizedType listGenericType = (ParameterizedType) declaredField.getGenericType();
								Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
								if(isWrapClass(Class.forName(sourcelistGenericTypeActualTypeArguments[0].getTypeName()))
										|| sourcelistGenericTypeActualTypeArguments[0].getTypeName().equals(listActualTypeArguments[0].getTypeName())){
									if (!Modifier.isPublic(writeMethod
											.getDeclaringClass().getModifiers())) {
										writeMethod.setAccessible(true);
									}
									writeMethod.invoke(target, value);
									continue;
								}
								Collection copyList = createCopyList((Collection) value, Class.forName(listActualTypeArguments[0].getTypeName()));
								writeMethod.invoke(target, copyList);

							}
							else{
								
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}

						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}  
	

    public static boolean isWrapClass(Class<?> clz) { 
        try { 
           return clz.isPrimitive() || (((Class<?>) clz.getField("TYPE").get(null)).isPrimitive())  ;
        } catch (Exception e) { 
            return false; 
        } 
    }

	public static <T1 ,T2> Collection<T2> createCopyList(Collection<T1> sourceList, Class<T2> targetClass){
		try {
			if(sourceList==null){
				return null;
			}else if(sourceList.size()==0){
				return sourceList.getClass().newInstance();
			}else{
				Collection targetList=sourceList.getClass().newInstance();
				for(Object source:sourceList){
					T2 target = targetClass.newInstance();
					copyVpsProperties(source,target);
					targetList.add(target);
				}
				return targetList;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


}