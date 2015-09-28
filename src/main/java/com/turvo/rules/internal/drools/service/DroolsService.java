package com.turvo.rules.internal.drools.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.turvo.rules.misc.ErrorConstants;

public class DroolsService implements CorePlatformService {
	private final Logger LOGGER = LoggerFactory.getLogger(DroolsService.class);

	private final static String KIE_FILE_SYSTEM_BASE_DIR = "/kie/data/";

	private final static String KIE_FILE_NAME_EXT = ".drl";

	private final static String DEFAULT_AGENDA_GROUP = null;

	private static KieServices services = KieServices.Factory.get();

	private KieFileSystem kieFileSystem = services.newKieFileSystem();

	private KieBase kieBase;

	private KieSessionConfiguration kieSessionConfiguration = services
			.newKieSessionConfiguration();

	private String getSourcePath(String memoryFileName) {
		return new StringBuilder().append(KIE_FILE_SYSTEM_BASE_DIR)
				.append(memoryFileName).append(KIE_FILE_NAME_EXT).toString();
	}

	private boolean isKnowledgeAvailable() {
		return kieBase != null;
	}

	private void bindGlobals(KieSession kieSession,
			Map<String, Object> globalParamsMap) {
		if (MapUtils.isNotEmpty(globalParamsMap)) {
			for (Map.Entry<String, Object> globalEntry : globalParamsMap
					.entrySet()) {
				kieSession.setGlobal(globalEntry.getKey(),
						globalEntry.getValue());
			}
		}
	}

	private void populateKnowledgeBaseProperties(
			KieBaseConfiguration kieConfiguration,
			Properties knowledgeBaseProperties) {
		if (knowledgeBaseProperties != null
				&& !knowledgeBaseProperties.isEmpty()) {
			Set<Object> keys = knowledgeBaseProperties.keySet();
			for (Object key : keys) {
				String value = knowledgeBaseProperties
						.getProperty((String) key);
				kieConfiguration.setProperty((String) key, value);
			}
		}
	}

	public void addknowledge(String memoryFileName, byte[] ruleContent) {
		Resource byteArrayResource = ResourceFactory
				.newByteArrayResource(ruleContent);
		byteArrayResource.setSourcePath(getSourcePath(memoryFileName));
		kieFileSystem.write(byteArrayResource);
	}

	public void buildKnowledgeBase(Properties knowledgeBaseProperties) {
		KieBuilder kieBuilder = services.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();
		if (kieBuilder.getResults().hasMessages(Level.ERROR)) {
			LOGGER.error(
					"Knowldgebase has errors, cannot proceed further..!!!");
			LOGGER.error(kieBuilder.getResults().toString());
			throw new RuntimeException("Unable to build knowledgebase");
		}

		ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();
		KieContainer kieContainer = services.newKieContainer(releaseId);
		KieBaseConfiguration kieConfiguration = services
				.newKieBaseConfiguration();
		populateKnowledgeBaseProperties(kieConfiguration,
				knowledgeBaseProperties);
		kieBase = kieContainer.newKieBase(kieConfiguration);
	}

	private int fireAgendaGroup(Object factSet, String agendaGroup,
			KieSession kieSession) {
		if (StringUtils.isNotBlank(agendaGroup)) {
			kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
		}
		kieSession.insert(factSet);
		return kieSession.fireAllRules();
	}

	public void runRulesOnSatefullSession(Object factSet,
			List<String> agendaGroups, Map<String, Object> globalParamsMap) {
		Preconditions.checkNotNull(factSet,
				ErrorConstants.NULL_FACT_ERROR_MESSAGE);
		if (!isKnowledgeAvailable()) {
			throw new RuntimeException(
					"Please ensure for knowledge availablity!!!");
		}
		KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration,
				null);
		try {
			bindGlobals(kieSession, globalParamsMap);

			if (CollectionUtils.isEmpty(agendaGroups)) {
				fireAgendaGroup(factSet, DEFAULT_AGENDA_GROUP, kieSession);
			}
			else {
				for (String agendaGroup : agendaGroups) {
					fireAgendaGroup(factSet, agendaGroup, kieSession);
				}
			}
		}
		finally {
			kieSession.dispose();
		}
	}
}