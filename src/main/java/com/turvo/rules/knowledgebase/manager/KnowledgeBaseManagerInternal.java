package com.turvo.rules.knowledgebase.manager;

import java.util.Map;

public interface KnowledgeBaseManagerInternal {
	void executeRules(Object factSet, Map<String, Boolean> agendaGroupsMap,
			Map<String, Object> globalParamsMap);
}
