package com.hua.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;

public class PropertiesUtils {

	private static Properties properties=new Properties();
	/**
	 * 加载配置文件，返回Datasource数据源
	 */
	static {
		try {
			InputStream inputstream = PropertiesUtils.class.getClassLoader().getResourceAsStream("db.properties");
			properties.load(inputstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static DataSource getDataSource()
	{
		try {
			return BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
