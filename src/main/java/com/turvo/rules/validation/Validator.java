package com.turvo.rules.validation;

import com.turvo.rules.validation.input.FactsRecord;

public interface Validator {
	void validateFacts(FactsRecord factRecord);
}
