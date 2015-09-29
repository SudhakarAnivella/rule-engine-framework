package com.turvo.rules.knowledgebase.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turvo.rules.base.RDBRuleBase;
import com.turvo.rules.base.RuleBase;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.knowledgebase.model.KnowledgeContent;
import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;

public class DroolsKnowledgebaseManager implements KnowledgebaseManager {
	private final Logger LOGGER = LoggerFactory.getLogger(KnowledgebaseManager.class);

	private RuleBase ruleBase;

	public DroolsKnowledgebaseManager(DatastoreConfig dataSourceConfig) {
		this.ruleBase = new RDBRuleBase(dataSourceConfig);
		LOGGER.info("Knowlodge base has been initialized successfully... Ready for war....");
	}

	@SuppressWarnings("unused")
	private DroolsKnowledgebaseManager() {
	}

	public Iterator<KnowledgeContent> getAllActiveKnowledge() {
		List<KnowledgeContent> knowledge = new ArrayList<KnowledgeContent>();
		Iterator<Rule> rulesIterator = ruleBase.fetchAllActiveRules();
		while (rulesIterator.hasNext()) {
			Rule r = rulesIterator.next();
			knowledge.add(new KnowledgeContent(r.getRuleName(), r.getRuleText()));
		}
		return knowledge.iterator();
	}

	public Iterator<String> getAllActiveAgendaGroupsByCustIdAndContext(String customerId, String context) {
		List<String> customAgendaGroups = new ArrayList<String>();
		if (StringUtils.isNotBlank(context) || StringUtils.isNotBlank(customerId)) {
			Iterator<RuleMeta> ruleMetaIterator = null;
			if (StringUtils.isBlank(context)) {
				ruleMetaIterator = ruleBase.fetchAllActiveRuleMetaFilterByCustomerId(customerId);
			} else if (StringUtils.isBlank(customerId)) {
				ruleMetaIterator = ruleBase.fetchAllActiveRuleMetaFilterByContext(context);
			} else {
				ruleMetaIterator = ruleBase.fetchAllActiveRuleMetaFilterByContextAndCustomerId(context, customerId);
			}

			while (ruleMetaIterator.hasNext()) {
				customAgendaGroups.add(ruleMetaIterator.next().getGroupName());
			}
		}
		return customAgendaGroups.iterator();
	}
}
