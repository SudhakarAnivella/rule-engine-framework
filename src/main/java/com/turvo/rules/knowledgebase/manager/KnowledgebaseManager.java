package com.turvo.rules.knowledgebase.manager;

/**
 * 
 * @author krishna
 *
 * Class responsible for managing Knowledge base.. i.e it
 * builds/manages/rebuilds if instructed..
 * 
 * Please make sure we have only one active instance of knowledge-base manager.
 * This serves as input to Validator/Executor
 *
 */
public interface KnowledgebaseManager {
	/**
	 * Rebuilds the Knowledge-base from data-base... Use with JMX extension to
	 * accommodate changes made to rules DB.
	 * 
	 * Please note that we get exception if we try to execute rules while we
	 * rebuild knowledge-base
	 */
	void rebuildKnowledgeBase();

	/**
	 * Helper function to indicate if the knowledge-base is ready for execution
	 * 
	 * @return true if knowledge-base is ready. false else case
	 */
	boolean isKnowldgeBaseReady();
}
