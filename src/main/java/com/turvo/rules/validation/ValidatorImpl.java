package com.turvo.rules.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.turvo.rules.knowledgebase.manager.KnowledgeBaseManagerInternal;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManager;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.validation.input.FactsRecord;

public class ValidatorImpl implements Validator {
	private final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

	private KnowledgeBaseManagerInternal kBaseManager;

	public ValidatorImpl(KnowledgebaseManager kBaseManager) {
		Preconditions.checkNotNull(kBaseManager,
				ErrorConstants.NULL_KNOWLEDGE_BASE_MESSAGE);
		Preconditions.checkArgument(kBaseManager.isKnowldgeBaseReady(),
				ErrorConstants.KNOWLEDGE_BASE_NOT_READY_MESSAGE);
		this.kBaseManager = (KnowledgeBaseManagerInternal) kBaseManager;
		LOGGER.info("Validator initalized sucessfully...!!! ");
	}

	@SuppressWarnings("unused")
	private ValidatorImpl() {
	};

	public void validateFacts(FactsRecord factRecord) {
		Preconditions.checkNotNull(factRecord,
				ErrorConstants.NULL_FACT_RECORD_MESSAGE);
		kBaseManager.executeRules(factRecord.getFactSet(),
				factRecord.getAgendaGroups(), factRecord.getGlobalParamsMap(),
				factRecord.getContext(), factRecord.getCustomerId());
	}
}
