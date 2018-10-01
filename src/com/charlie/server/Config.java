package com.charlie.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Config {

	protected static String C_configFile = "conf/server.properties";

	Logger logger = Logger.getLogger(Config.class.getName());

	protected int CONNECT_PORT;

	protected String JDBC_MAIN_SERVER;
	protected int JDBC_MAIN_PORT;
	protected String JDBC_MAIN_USER;
	protected String JDBC_MAIN_PASSWORD;
	protected String JDBC_MAIN_DBNAME;
	protected String JDBC_MAIN_DBTYPE;
	protected boolean JDBC_MAIN_UNICODE;
	protected String JDBC_MAIN_ENCODING;

	Config() throws Exception {
		Properties properties = new Properties();

		try (FileInputStream config = new FileInputStream(C_configFile);) {
			PropertyConfigurator.configure("conf/log4j.properties");
			properties.load(config);
			setConfig(properties);
		} catch (Exception ee) {
			logger.error("속성을 불러올 수 없습니다.\n" + ee.getMessage(), ee);
			throw new FileNotFoundException("파일 속성을 찾을 수 없습니다.");
		}
	}

	private void setConfig(Properties properties) {
		this.CONNECT_PORT = Integer.parseInt(properties.getProperty("socket.port").trim());
		this.JDBC_MAIN_SERVER = properties.getProperty("main.jdbc.server").trim();
		this.JDBC_MAIN_PORT = Integer.parseInt(properties.getProperty("main.jdbc.port").trim());
		this.JDBC_MAIN_USER = properties.getProperty("main.jdbc.user").trim();
		this.JDBC_MAIN_PASSWORD = properties.getProperty("main.jdbc.password").trim();
		this.JDBC_MAIN_DBNAME = properties.getProperty("main.jdbc.db").trim();
		this.JDBC_MAIN_DBTYPE = properties.getProperty("main.jdbc.dbtype").trim();
		this.JDBC_MAIN_UNICODE = Boolean.parseBoolean(properties.getProperty("main.jdbc.useUnicode").trim());
		this.JDBC_MAIN_ENCODING = properties.getProperty("characterEncoding").trim();
	}

	public static String getC_configFile() {
		return C_configFile;
	}

	public int getCONNECT_PORT() {
		return CONNECT_PORT;
	}

	public String getJDBC_MAIN_SERVER() {
		return JDBC_MAIN_SERVER;
	}

	public int getJDBC_MAIN_PORT() {
		return JDBC_MAIN_PORT;
	}

	public String getJDBC_MAIN_USER() {
		return JDBC_MAIN_USER;
	}

	public String getJDBC_MAIN_PASSWORD() {
		return JDBC_MAIN_PASSWORD;
	}

	public String getJDBC_MAIN_DBNAME() {
		return JDBC_MAIN_DBNAME;
	}

	public String getJDBC_MAIN_DBTYPE() {
		return JDBC_MAIN_DBTYPE;
	}

	public boolean isJDBC_MAIN_UNICODE() {
		return JDBC_MAIN_UNICODE;
	}

	public String getJDBC_MAIN_ENCODING() {
		return JDBC_MAIN_ENCODING;
	}

}
