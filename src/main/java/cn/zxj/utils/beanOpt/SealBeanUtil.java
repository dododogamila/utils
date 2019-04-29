package cn.zxj.utils.beanOpt;


import java.lang.reflect.Field;
import java.util.*;

public class SealBeanUtil {

	/**
	 * 按照目标类名创建对象，并拷贝原对象属性
	 * 如果原对象为空，则将目标对象也置为空
	 * @param source 原对象
	 * @param targetClass 目标类名
	 */
	public static <T1 ,T2> T2 createCopy(T1 source,  Class<T2> targetClass){
		
		if(source==null){
			return null;
		}else{
			T2 target;
			try {
				target = targetClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
			BeanUtils.copyVpsProperties(source, target);
			return target;
		}
	}

	/**
	 * 按照目标类名创建List，并拷贝原List中对象属性
	 * 如果原List为空，则返回为空
	 * @param sourceList 原对象List
	 * @param targetClass 目标类名
	 */
	public static <T1 ,T2> List<T2> createCopyList(List<T1> sourceList, Class<T2> targetClass){
		if(sourceList==null){
			return null;
		}else if(sourceList.size()==0){
			return new ArrayList<T2>();
		}else{
			List<T2> targetList=new ArrayList<T2>();
			for(Object source:sourceList){
				T2 target = (T2)createCopy(source,targetClass);
				targetList.add(target);
			}
			return targetList;
		}
	}
	
	public static <T1 ,T2> T2 createCopy(T1 source,  Class<T2> targetClass,String... ignorProperties){
		
		if(source==null){
			return null;
		}else{
			T2 target;
			try {
				target = targetClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
			BeanUtils.copyVpsProperties(source, target,ignorProperties);
			return target;
		}
		
	}
	
	/**
	 * 把对象放进map中的list
	 * @param map
	 * @param obj
	 * @param key
	 */
	public static <KEY,T> void putToMap(Map<KEY, List<T>> map, T obj,KEY key){
		if(map == null){
			map = new HashMap<KEY, List<T>>();
		}
		List<T> list = map.get(key);
		if(null == list){
			list = new ArrayList<T>();
		}
		list.add(obj);
		map.put(key, list);
	} 
	
	/**
	 * 集合转成Map，使用属性作为key
	 * @param data
	 * @param field
	 * @param key
	 * @return
	 */
	public static <KEY,T> Map<KEY, List<T>> listToListMap(Collection<T> data,String field, Class<KEY> key){
		Map<KEY, List<T>> map = new HashMap<KEY, List<T>>();
		try {
			for (T t : data) {
				Field field2 = t.getClass().getDeclaredField(field);
				field2.setAccessible(true);
				Object object = field2.get(t);
				
				KEY o = key.cast(object);
				putToMap(map, t, o);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 集合转成Map，使用属性作为key
	 * @param data
	 * @param field
	 * @param key
	 * @return
	 */
	public static <KEY,T> Map<KEY, T> listToMap(Collection<T> data,String field, Class<KEY> key){
		Map<KEY, T> map = new HashMap<KEY, T>();
		if(null==data){
			return map;
		}
		try {
			for (T t : data) {
				Field field2 = t.getClass().getDeclaredField(field);
				field2.setAccessible(true);
				Object object = field2.get(t);
				
				KEY o = key.cast(object);
				
				map.put(o, t);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return map;
	} 
	
	/**
	 * 集合转成Map，使用属性作为key
	 * @param data
	 * @param field
	 * @param ignorCase
	 * @return
	 */
	public static <T> Map<String, T> listToMap(Collection<T> data,String field, boolean ignorCase){
		Map<String, T> map = new HashMap<String, T>();
		if(null==data){
			return map;
		}
		try {
			for (T t : data) {
				Field field2 = t.getClass().getDeclaredField(field);
				field2.setAccessible(true);
				Object object = field2.get(t);
				
				String o = object.toString();
				if(ignorCase){
					map.put(o.toLowerCase(), t);
				}else{
					map.put(o, t);
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return map;
	} 
	
	/**
	 * 集合转成Map，使用多个属性及分隔符作为key
	 * @param data
	 * @param separate
	 * @param fields
	 * @return
	 */
	public static <T> Map<String, T> listToMap(Collection<T> data,String separate,String... fields){
		return listToMap(data, false, separate, fields);
	} 
	
	/**
	 * 集合转成Map，使用多个属性及分隔符作为key
	 * @param data
	 * @param separate
	 * @param fields
	 * @return
	 */
	public static <T> Map<String, T> listToMap(Collection<T> data,boolean ignorCase,String separate,String... fields){
		Map<String, T> map = new HashMap<String, T>();
		if(null==data){
			return map;
		}
		try {
			for (T t : data) {
				StringBuffer key = new StringBuffer();
				for (String field : fields) {
					Field field2 = t.getClass().getDeclaredField(field);
					field2.setAccessible(true);
					if(key.length() != 0 && null != separate){
						key.append(separate);
					}
					if (ignorCase) {
						key.append((String.valueOf(field2.get(t))).toLowerCase());
					}else {
						key.append((String.valueOf(field2.get(t))));
						
					}
				}
				
				map.put(key.toString(), t);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param data
	 * @param field
	 * @param valueField
	 * @param ignorCase
	 * @return
	 */
	public static <T,V> Map<String, V> listToMap(Collection<T> data,String field,String valueField, boolean ignorCase){
		Map<String, V> map = new HashMap<String, V>();
		if(null==data){
			return map;
		}
		try {
			for (T t : data) {
				//key
				Field field2 = t.getClass().getDeclaredField(field);
				field2.setAccessible(true);
				Object object = field2.get(t);
				
				//value
				Field field1 = t.getClass().getDeclaredField(valueField);
				field1.setAccessible(true);
				Object object1 = field1.get(t);
				
				String o = object.toString();
//				String o1 = object1.toString();
				
				if(ignorCase){
					map.put(o.toLowerCase(), (V)object1);
				}else{
					map.put(o, (V)object1);
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassCastException e) {
			e.printStackTrace();
		}
		
		return map;
	} 
}
