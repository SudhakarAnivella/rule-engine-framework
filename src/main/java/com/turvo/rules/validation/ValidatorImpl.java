package com.turvo.rules.validation;

import com.google.common.base.Preconditions;
import com.turvo.rules.knowledgebase.manager.KnowledgeBaseManagerInternal;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManager;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.validation.input.FactsRecord;

public class ValidatorImpl implements Validator {

	private KnowledgeBaseManagerInternal kBaseManager;

	public ValidatorImpl(KnowledgebaseManager kBaseManager) {
		this.kBaseManager = (KnowledgeBaseManagerInternal) kBaseManager;
	}

	@SuppressWarnings("unused")
	private ValidatorImpl() {
	};

	public void validateFacts(FactsRecord factRecord) {
		Preconditions.checkNotNull(factRecord,
				ErrorConstants.NULL_FACT_RECORD_MESSAGE);
		kBaseManager.executeRules(factRecord.getFactSet(),
				factRecord.getAgendaGroups(), factRecord.getGlobalParamsMap());
	}
}
