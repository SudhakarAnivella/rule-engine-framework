package com.turvo.rules.knowledgebase.manager;

import java.util.Iterator;

import com.turvo.rules.knowledgebase.model.KnowledgeContent;

/**
 * 
 * @author krishna
 *
 *         Class responsible for managing Knowledge base.. i.e it
 * 
 *         Please make sure we have only one active instance of knowledge-base
 *         manager.
 */
public interface KnowledgebaseManager {
	/**
	 * Gets all Active knowledge from the underlying data-store
	 * 
	 * @return Iterator to the knowledge content fetched from data-store.
	 */
	Iterator<KnowledgeContent> getAllActiveKnowledge();

	/**
	 * Gets All active Agenda-groups for given customerId and/or context. if
	 * either customerId or context is null, the other param is used for fetch.
	 * 
	 * @param customerId
	 *            id of the customer
	 * @param context
	 *            the context in which the rules are executed.
	 * @return
	 */
	Iterator<String> getAllActiveAgendaGroupsByCustIdAndContext(String customerId, String context);
}
