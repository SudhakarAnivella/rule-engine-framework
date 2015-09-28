package com.turvo.rules.knowledgebase.manager;

import java.util.Map;

public interface KnowledgeBaseManagerInternal extends KnowledgebaseManager {
	void executeRules(Object factSet, Map<String, Boolean> agendaGroupsMap,
			Map<String, Object> globalParamsMap);
}
