package cn.zxj.utils.dynamicDatasource;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 以单例模式保存当前系统是否支持主从切换，如果支持，保存支持切换的dao的map
 */
public class DataSourceSwitchConfig {

    private static DataSourceSwitchConfig _instance = new DataSourceSwitchConfig();

    private ConcurrentHashMap<String,Object> switchDaoMap = new ConcurrentHashMap<String, Object>();

    private volatile boolean isDBSwitchSupported = false;// 如果默认为true，则在容器启动过程中会有dao从从库读取

    private int currentSlave=-1;
    private int currentMaster=-1;

    private DataSourceSwitchConfig(){

    }

    public static DataSourceSwitchConfig getInstance(){
        return _instance;
    }

    /**
     * 轮询master datasource
     * @param size
     * @return
     */
    public int getMaster(int size) {
        synchronized (this) {
            ++currentMaster;
            if(currentMaster>=size) {
                currentMaster=0;
            }
            return currentMaster;
        }
    }

    /**
     * 轮询slave datasource
     * @param size
     * @return
     */
    public int getSlave(int size) {
        synchronized (this) {
            ++currentSlave;
            if(currentSlave>=size) {
                currentSlave = 0;
            }
            return currentSlave;
        }
    }

    public boolean isSupportDataSourceSwitch(String classname){
        if(!isDBSwitchSupported)
            return false;
        /*else
            return true;*/
        return switchDaoMap.containsKey(classname);  // todo 暂时不判断annotation
    }

    public synchronized void addDBSwitchSupportClass(String name, Object bean){
        if(switchDaoMap == null) switchDaoMap = new ConcurrentHashMap<String, Object>();
        switchDaoMap.put(name,bean);
    }

    public boolean isDBSwitchSupported() {
        return isDBSwitchSupported;
    }

    public void setDBSwitchSupported(boolean DBSwitchSupported) {
        isDBSwitchSupported = DBSwitchSupported;
    }
}
