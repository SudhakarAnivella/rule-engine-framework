package com.turvo.rules.base;

import java.util.Properties;

import com.turvo.rules.base.config.DatastoreConfig;

public class RDBPropertiesProvider {
	private Properties persitentProperties = new Properties();

	public Properties getProperties() {
		return persitentProperties;
	}

	public RDBPropertiesProvider buildDataStoreProperties(
			DatastoreConfig dataStoreConfig) {
		persitentProperties.setProperty("javax.persistence.jdbc.driver",
				dataStoreConfig.getDataStoreDriverClass());
		persitentProperties.setProperty("javax.persistence.jdbc.url",
				dataStoreConfig.getDbUrl());
		persitentProperties.setProperty("javax.persistence.jdbc.user",
				dataStoreConfig.getUsername());
		persitentProperties.setProperty("javax.persistence.jdbc.password",
				dataStoreConfig.getPassword());
		return this;
	}

	public RDBPropertiesProvider buildMiscProperties(Properties properties) {
		for (Object key : properties.keySet()) {
			persitentProperties.setProperty((String) key,
					(String) properties.get(key));
		}

		return this;
	}
}
