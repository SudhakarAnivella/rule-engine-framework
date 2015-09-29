package com.turvo.rules.execution.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Preconditions;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManager;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.misc.RuleConstants;
import com.turvo.rules.platform.service.CorePlatformService;
import com.turvo.rules.validation.input.FactsRecord;

public class DroolsFactsExecutionService implements FactsExecutionService<FactsRecord> {

	private CorePlatformService droolsService;
	private KnowledgebaseManager kbaseManager;

	public DroolsFactsExecutionService(KnowledgebaseManager kbaseManager, CorePlatformService platformService) {
		Preconditions.checkNotNull(kbaseManager, ErrorConstants.NULL_KNOWLEDGE_BASE_MANAGER_MESSAGE);
		Preconditions.checkNotNull(platformService, ErrorConstants.NULL_PLATFORM_SERVICE_MESSAGE);
		this.droolsService = platformService;
		this.kbaseManager = kbaseManager;
	}

	public void executeFacts(FactsRecord factObject) {
		Preconditions.checkNotNull(factObject, ErrorConstants.NULL_FACT_ERROR_MESSAGE);

		List<String> agendaGroups = factObject.getAgendaGroups();
		List<String> allAgendaGroups = null;

		if (CollectionUtils.isEmpty(agendaGroups)) {
			allAgendaGroups = new ArrayList<String>();
		} else {
			allAgendaGroups = new ArrayList<String>(agendaGroups);
		}

		Iterator<String> customAgendaGroupsIterator = kbaseManager
				.getAllActiveAgendaGroupsByCustIdAndContext(factObject.getCustomerId(), factObject.getContext());
		while (customAgendaGroupsIterator.hasNext()) {
			allAgendaGroups.add(customAgendaGroupsIterator.next());
		}

		allAgendaGroups.add(RuleConstants.DEFAULT_AGENDA_GROUP);
		droolsService.runRulesOnSatefullSession(factObject.getFactSet(), allAgendaGroups,
				factObject.getGlobalParamsMap());
	}

}
