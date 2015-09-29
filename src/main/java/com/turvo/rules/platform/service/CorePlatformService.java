package com.turvo.rules.platform.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface CorePlatformService {
	/**
	 * Adds knowledge to the platform.. After addition of all the knowledge one
	 * has to invoke initPlatform().
	 * 
	 * @param contentId rule-content-id (should not be null)
	 * @param ruleContent actual rule-content (should not be null)
	 */
	void addknowledge(String contentId, byte[] ruleContent);

	/**
	 * Initiates platform and other assosiated components.
	 * 
	 * @param additionalProperties
	 *            additional params required if any.
	 */
	void initPlatform(Properties additionalProperties);

	/**
	 * executes rules on statefull session.
	 * 
	 * @param factSet
	 *            subject on whcih rules are executed.
	 * @param agendaGroups
	 *            logical partition of rules are acheived by agenda-groups.
	 * @param globalParamsMap
	 *            carries globalparams used in rules.
	 */
	void runRulesOnSatefullSession(Object factSet, List<String> agendaGroups, Map<String, Object> globalParamsMap);

	/**
	 * shuts down platform.
	 */
	void shutdownPlatform();
}
