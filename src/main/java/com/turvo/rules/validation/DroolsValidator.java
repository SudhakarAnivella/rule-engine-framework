package com.turvo.rules.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.turvo.rules.engine.RuleEngine;
import com.turvo.rules.execution.service.FactsExecutionService;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.validation.input.FactsRecord;

public class DroolsValidator implements Validator {
	private final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

	@SuppressWarnings("unused")
	private RuleEngine ruleEngine;
	@SuppressWarnings("rawtypes")
	private FactsExecutionService factsExecutionService;

	public DroolsValidator(RuleEngine ruleEngine) {
		Preconditions.checkNotNull(ruleEngine, ErrorConstants.NULL_RULE_ENGINE_MESSAGE);
		Preconditions.checkArgument(ruleEngine.isUp(), ErrorConstants.RULE_ENGINE_DOWN_MESSAGE);
		this.ruleEngine = ruleEngine;
		this.factsExecutionService = ruleEngine.getExecutionService();
		LOGGER.info("Validator initalized sucessfully...!!! ");
	}

	@SuppressWarnings("unused")
	private DroolsValidator() {
	};

	@SuppressWarnings("unchecked")
	public void validateFacts(FactsRecord factRecord) {
		Preconditions.checkNotNull(factRecord, ErrorConstants.NULL_FACT_RECORD_MESSAGE);
		factsExecutionService.executeFacts(factRecord);
	}
}
