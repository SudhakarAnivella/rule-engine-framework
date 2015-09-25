package com.turvo.rules.knowledgebase.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.turvo.rules.base.RDBRuleBase;
import com.turvo.rules.base.RuleBase;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.internal.drools.service.CorePlatformService;
import com.turvo.rules.internal.drools.service.DroolsService;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.model.Rule;

public class KnowledgebaseManagerImpl
		implements KnowledgeBaseManagerInternal, KnowledgebaseManager {
	private RuleBase ruleBase;

	private CorePlatformService platformService;

	private Properties DEFAULT_KIE_PROPERTIES = null;

	private final String RULE_FILE_NAME_BASE = "rule_";

	private boolean isKnowledgeBaseReady = Boolean.FALSE;

	public KnowledgebaseManagerImpl(DatastoreConfig dataSourceConfig) {
		this.ruleBase = new RDBRuleBase(dataSourceConfig);
		this.platformService = new DroolsService();
		buildKnowledgeBase();
		isKnowledgeBaseReady = Boolean.TRUE;
	}

	private String buildRuleFileName(int ruleId) {
		return new StringBuilder(RULE_FILE_NAME_BASE).append(ruleId).toString();
	}

	private List<String> getAgendaGroups(Map<String, Boolean> agendaGroupsMap) {
		List<String> agendaGroups = new ArrayList<String>();
		if (MapUtils.isNotEmpty(agendaGroupsMap)) {
			for (Map.Entry<String, Boolean> agendaGroupEntry : agendaGroupsMap
					.entrySet()) {
				String agendaGroupKey = agendaGroupEntry.getKey();
				if (StringUtils.isNotBlank(agendaGroupEntry.getKey())) {
					agendaGroups.add(agendaGroupKey);
				}
			}
		}
		return agendaGroups;
	}

	private synchronized void buildKnowledgeBase() {
		Preconditions.checkNotNull(ruleBase,
				ErrorConstants.NULL_RULE_BASE_MESSAGE);

		Preconditions.checkNotNull(platformService,
				ErrorConstants.NO_CORE_SERVICE_MESSAGE);

		Iterator<Rule> rulesIterator = ruleBase.getAllActiveRules();
		while (rulesIterator.hasNext()) {
			Rule rule = rulesIterator.next();
			platformService.addknowledge(buildRuleFileName(rule.getRuleId()),
					rule.getRuleBlob());
		}
		platformService.buildKnowledgeBase(DEFAULT_KIE_PROPERTIES);
	}

	// TODO Need to give more thought
	public void rebuildKnowledgeBase() {
		isKnowledgeBaseReady = Boolean.FALSE;
		buildKnowledgeBase();
		isKnowledgeBaseReady = Boolean.TRUE;
	}

	public void executeRules(Object factSet,
			Map<String, Boolean> agendaGroupsMap,
			Map<String, Object> globalParamsMap) {
		Preconditions.checkNotNull(factSet,
				ErrorConstants.NULL_FACT_ERROR_MESSAGE);

		Preconditions.checkArgument(isKnowledgeBaseReady,
				ErrorConstants.KNOWLEDGE_BASE_NOT_READY_MESSAGE);

		List<String> agendaGroups = getAgendaGroups(agendaGroupsMap);
		platformService.runRulesOnSatefullSession(factSet, agendaGroups,
				globalParamsMap);
	}
}
