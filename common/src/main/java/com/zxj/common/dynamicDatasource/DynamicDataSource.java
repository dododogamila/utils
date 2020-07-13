package cn.zxj.utils.dynamicDatasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义动态数据源
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

	private List<Object> masterKeys = new ArrayList<>();
	private List<Object> slaveKeys = new ArrayList<>();

	@Autowired
//	private RedisManage redisManage;

	@Override
	public Connection getConnection() throws SQLException {
		Connection rtn = super.getConnection();
		this.changeCharset(rtn);
		return rtn;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		Connection rtn = super.getConnection(username, password);
		this.changeCharset(rtn);
		return rtn;
	}

	/**
	 * 在子类中分别保存主、从库KEY列表，方便计算大小以及不定数量切换主从
	 * @param targetDataSources
	 */
	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);

		for(Object key: targetDataSources.keySet()) {
			if(key.toString().startsWith(DBContextHolder.DataSourceType.master.toString())) {
				masterKeys.add(key);
			}
			if(key.toString().startsWith(DBContextHolder.DataSourceType.slave.toString())) {
				slaveKeys.add(key);
			}
		}
	}

	/**
	 * 根据holder中保存的当前线程对应数据库类型，返回对应datasource的key
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		DBContextHolder.DataSourceType dbType = DBContextHolder.getDBType();
		Object key;
		switch (dbType) {
			case master:
				key = masterKeys.get(DataSourceSwitchConfig.getInstance().getMaster(masterKeys.size()));
				logger.debug("use master db,key:"+key);
				break;
			case slave:
				key = slaveKeys.get(DataSourceSwitchConfig.getInstance().getSlave(slaveKeys.size()));
				logger.debug("use slave db,key:"+key);
				break;
			default:
				key = masterKeys.get(DataSourceSwitchConfig.getInstance().getMaster(masterKeys.size()));
				logger.warn("can't get current db type, default to use master");
				break;
		}
//		redisManage.increValue("db_counter_"+key.toString());
		return key;
	}

	private void changeCharset(Connection conn) throws SQLException {
		conn.createStatement().execute("SET NAMES 'utf8mb4'");
	}

}
