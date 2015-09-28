package com.turvo.rules.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManager;
import com.turvo.rules.knowledgebase.manager.KnowledgebaseManagerImpl;
import com.turvo.rules.validation.Validator;
import com.turvo.rules.validation.ValidatorImpl;
import com.turvo.rules.validation.input.FactsRecord;

public class ValidateTest {

	@Test
	public void testValidate() {
		DatastoreConfig dsConfig = new DatastoreConfig(
				"jdbc:mysql://localhost/test", "org.gjt.mm.mysql.Driver",
				"root", "turvo");
		KnowledgebaseManager km = new KnowledgebaseManagerImpl(dsConfig);
		FactsRecord fr = new FactsRecord();

		Map<String, Object> topMap = new HashMap<String, Object>();
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("note", "");
		topMap.put("status", statusMap);
		fr.setFactSet(topMap);

		List<String> errors = new ArrayList<String>();
		Map<String, Object> globalParams = new HashMap<String, Object>();
		globalParams.put("errors", errors);
		fr.setGlobalParamsMap(globalParams);

		Validator v = new ValidatorImpl(km);
		v.validateFacts(fr);

		for (String error : errors) {
			System.out.println("Error : " + error);
		}
	}
}
