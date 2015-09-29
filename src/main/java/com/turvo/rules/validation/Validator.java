package com.turvo.rules.validation;

import com.turvo.rules.validation.input.FactsRecord;

/**
 * @author krishna
 *
 *         Interface for validating facts on given knowledge-base
 * 
 *         <% RuleEngine re = new DroolsRuleEngine(dataSourceConfig) Validator v
 *         = new ValidatorImpl(re); v.validate(factRecord); %>
 * 
 *         We do not need to create Validator every-time we need to validate..
 *         It is recommended to build once and use multiple times.
 *
 */
public interface Validator {
	/**
	 * Validates provided factRecord on the available knowledgebase
	 * 
	 * @param factRecord
	 *            input record comprising of facts, and other parameters needed
	 *            for validation.
	 * 
	 *            Note: We do not provide any return type here... We expect the
	 *            users to communicate the execution result back using global
	 *            values binded in the rules.
	 */
	void validateFacts(FactsRecord factRecord);
}
