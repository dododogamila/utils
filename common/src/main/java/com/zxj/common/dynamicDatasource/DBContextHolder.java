package cn.zxj.utils.dynamicDatasource;

/**
 * 以线程变量保存本线程当前对应主从模式
 */
public class DBContextHolder {
	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();
	
	public enum DataSourceType {
	        master, slave
	}
	
    public static void setDBType(DataSourceType dbType) {
        contextHolder.set(dbType);   
    }   
  
    public static DataSourceType getDBType() {
        DataSourceType currentType = contextHolder.get();
        if(currentType == null)
            return DataSourceType.master;
        else
            return currentType;
    }   
  
    public static void clearDBType() {   
        contextHolder.remove();   
    } 
}
