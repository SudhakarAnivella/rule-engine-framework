package com.turvo.rules.engine;

import java.util.Iterator;
import java.util.Properties;

import com.google.common.base.Preconditions;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.execution.service.DroolsFactsExecutionService;
import com.turvo.rules.execution.service.FactsExecutionService;
import com.turvo.rules.knowledgebase.manager.DroolsKnowledgebaseManager;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManager;
import com.turvo.rules.knowledgebase.model.KnowledgeContent;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.platform.service.CorePlatformService;
import com.turvo.rules.platform.service.DroolsService;

public class DroolsRuleEngine implements RuleEngine {

	private KnowledgebaseManager kbaseManager;
	private DroolsFactsExecutionService factsExecutionservice;
	private CorePlatformService platformService;
	private boolean engineUp = Boolean.FALSE;
	private Properties DEFAULT_KIE_PROPERTIES = new Properties();

	public DroolsRuleEngine(DatastoreConfig datastoreConfig) {
		Preconditions.checkNotNull(datastoreConfig, ErrorConstants.NULL_DATA_STORE_CONFIG_ERROR_MESSAGE);
		this.kbaseManager = new DroolsKnowledgebaseManager(datastoreConfig);
		this.platformService = new DroolsService();
		this.factsExecutionservice = new DroolsFactsExecutionService(kbaseManager, platformService);
	}

	@SuppressWarnings("unused")
	private DroolsRuleEngine() {
	}

	public void gearUp() {
		Iterator<KnowledgeContent> knowledgeContentIterator = kbaseManager.getAllActiveKnowledge();
		while (knowledgeContentIterator.hasNext()) {
			KnowledgeContent knowledgeContent = knowledgeContentIterator.next();
			platformService.addknowledge(knowledgeContent.getContentId(), knowledgeContent.getContent());
		}
		platformService.initPlatform(DEFAULT_KIE_PROPERTIES);
		this.engineUp = Boolean.TRUE;
	}

	public void tearDown() {
		this.platformService.shutdownPlatform();
		this.engineUp = Boolean.FALSE;
	}

	@SuppressWarnings("rawtypes")
	public FactsExecutionService getExecutionService() {
		return this.factsExecutionservice;
	}

	public boolean isUp() {
		return engineUp;
	}
}
