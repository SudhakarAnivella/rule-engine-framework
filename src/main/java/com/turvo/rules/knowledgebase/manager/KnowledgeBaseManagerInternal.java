package com.turvo.rules.knowledgebase.manager;

import java.util.List;
import java.util.Map;

public interface KnowledgeBaseManagerInternal extends KnowledgebaseManager {
	void executeRules(Object factSet, List<String> agendaGroups,
			Map<String, Object> globalParamsMap, String context,
			String customerId);
}
