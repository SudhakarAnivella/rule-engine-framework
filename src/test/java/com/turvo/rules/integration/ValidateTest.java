package com.turvo.rules.integration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.turvo.rules.base.RDBRuleBase;
import com.turvo.rules.base.RuleBase;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.engine.DroolsRuleEngine;
import com.turvo.rules.engine.RuleEngine;
import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;
import com.turvo.rules.validation.DroolsValidator;
import com.turvo.rules.validation.Validator;
import com.turvo.rules.validation.input.FactsRecord;

public class ValidateTest {

	static String[] ruleFiles = { "rules/test_rule_1.drl", "rules/test_rule_2.drl", "rules/test_rule_3.drl" };

	private static Validator v = null;

	static {
		String dbUrl = "jdbc:hsqldb:mem:test";
		String driverClass = "org.hsqldb.jdbcDriver";
		String username = "sa";
		String password = "";

		DatastoreConfig config = new DatastoreConfig(dbUrl, driverClass, username, password);
		Properties miscProperties = new Properties();
		miscProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		miscProperties.setProperty("hibernate.hbm2ddl.auto", "create");

		RuleBase testRuleBase = new RDBRuleBase(config, miscProperties);

		List<Rule> rules = new ArrayList<Rule>();
		int i = 1;
		for (String fileName : ruleFiles) {
			Path path = null;
			try {
				path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
			} catch (URISyntaxException e1) {
			}
			Rule r = new Rule();
			r.setActive(true);
			r.setRuleName("rule_" + i);
			try {
				r.setRuleText(Files.readAllBytes(path));
			} catch (IOException e) {
			}
			rules.add(r);
			++i;
			testRuleBase.persistRule(r);
		}
		RuleEngine re = new DroolsRuleEngine(config);
		re.gearUp();
		v = new DroolsValidator(re);

		RuleMeta rm1 = new RuleMeta("test_customer", "t3", "ag3", true);
		testRuleBase.persistRuleMeta(rm1);
		RuleMeta rm2 = new RuleMeta("test_customer", "t2", "ag2", true);
		testRuleBase.persistRuleMeta(rm2);
	}

	@Before
	public void setUp() throws IOException {
	}

	@Test
	public void testValidate() {
		Map<String, Object> topMap = new HashMap<String, Object>();
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("note", "");
		topMap.put("status", statusMap);

		List<String> errors = new ArrayList<String>();
		Map<String, Object> globalParams = new HashMap<String, Object>();
		globalParams.put("errors", errors);

		FactsRecord fr = new FactsRecord(topMap, Arrays.asList("ag2", "ag1"), "t3", "test_customer", globalParams);
		v.validateFacts(fr);
	}
}
