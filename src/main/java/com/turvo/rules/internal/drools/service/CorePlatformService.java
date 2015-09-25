package com.turvo.rules.internal.drools.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface CorePlatformService {
	void addknowledge(String memoryFilePath, byte[] ruleContent);

	void buildKnowledgeBase(Properties knowledgeBaseProperties);

	void runRulesOnSatefullSession(Object factSet, List<String> agendaGroups,
			Map<String, Object> globalParamsMap);
}
