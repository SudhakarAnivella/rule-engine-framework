package com.turvo.rules.knowledgebase.manager;

import java.util.List;
import java.util.Map;

public interface KnowledgeBaseManagerInternal extends KnowledgebaseManager {
	/**
	 * executes facts on knowledge-base.
	 * 
	 * @param factSet Subject to be validated/executed against rules..
	 * @param agendaGroups Logical grouping of rules is done using
	 * agenda-groups...
	 * @param globalParamsMap Global params required for execution of rules.
	 * @param context indicates the context of the validation/execution of
	 * rules.
	 * @param customerId this is used when we have special-rules executed for
	 * specific customers...
	 */
	void executeRules(Object factSet, List<String> agendaGroups,
			Map<String, Object> globalParamsMap, String context,
			String customerId);
}
