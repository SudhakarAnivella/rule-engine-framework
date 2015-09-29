package com.turvo.rules.engine;

import com.turvo.rules.execution.service.FactsExecutionService;

public interface RuleEngine {
	/**
	 * Checks if the engine is Up
	 * 
	 * @return
	 */
	public boolean isUp();

	/**
	 * Brings up the engine to running state. Must be called before doing any
	 * validation.
	 */
	public void gearUp();

	/**
	 * Destroys engine's context.. To get back to running one must call gearUp()
	 * again.
	 */
	public void tearDown();

	/**
	 * This is not for the consumer. Gets underlying execution service.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public FactsExecutionService getExecutionService();
}
