package com.turvo.rules.base.config;

public class DatastoreConfig {
	private String dbUrl;

	private String dataStoreDriverClass;

	private String username;

	private String password;

	public DatastoreConfig(String dbUrl, String dataStoreDriverClass,
			String username, String password) {
		super();
		this.dbUrl = dbUrl;
		this.dataStoreDriverClass = dataStoreDriverClass;
		this.username = username;
		this.password = password;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDataStoreDriverClass() {
		return dataStoreDriverClass;
	}

	public void setDataStoreDriverClass(String dataStoreDriverClass) {
		this.dataStoreDriverClass = dataStoreDriverClass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
