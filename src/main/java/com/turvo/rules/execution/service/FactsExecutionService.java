package com.turvo.rules.execution.service;

public interface FactsExecutionService<T> {
	/**
	 * Executes facts using underlying service.
	 * @param factObject
	 */
	void executeFacts(T factObject);
}
