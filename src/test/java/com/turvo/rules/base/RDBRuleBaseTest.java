package com.turvo.rules.base;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.model.Rule;

public class RDBRuleBaseTest {

	private RuleBase testRuleBase;

	private Rule TEST_RULE;

	private String TEST_RULE_NAME = "TEST_RULE";

	private byte[] TEST_RULE_BLOB = { 12, 13, 14, 15 };

	@Before
	public void setup() {
		setUpDBConfig();
		setUpTestData();
	}

	private void setUpDBConfig() {
		String dbUrl = "jdbc:hsqldb:mem:test";
		String driverClass = "org.hsqldb.jdbcDriver";
		String username = "sa";
		String password = "";

		DatastoreConfig config = new DatastoreConfig(dbUrl, driverClass,
				username, password);
		Properties miscProperties = new Properties();
		miscProperties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.HSQLDialect");
		miscProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		testRuleBase = new RDBRuleBase(config, miscProperties);
	}

	private void setUpTestData() {
		TEST_RULE = new Rule();
		TEST_RULE.setRuleName(TEST_RULE_NAME);
		TEST_RULE.setRuleBlob(TEST_RULE_BLOB);
	}

	@Test
	@Ignore
	public void testPersistRule() {
		Rule rule = testRuleBase.persistRule(TEST_RULE);
		assertNotNull("rule must not be null", rule);
		assertTrue("rule_name must match",
				rule.getRuleName().equals(TEST_RULE_NAME));

		assertTrue("rule_blob must match",
				rule.getRuleBlob().equals(TEST_RULE_BLOB));
	}

	@Test
	@Ignore
	public void testUpdateRule() {
		Rule origianalRule = testRuleBase.persistRule(TEST_RULE);
		origianalRule.setRuleName("UPDATED_RULE_NAME");
		Rule updatedRule = testRuleBase.updateRule(origianalRule);

		assertNotNull("rule must not be null", updatedRule);
		assertTrue("rule_name must match",
				updatedRule.getRuleName().equals("UPDATED_RULE_NAME"));
	}

	@Test
	public void testGetAllRules() {
		Rule rule1 = new Rule();
		rule1.setRuleName("r1");
		rule1.setRuleBlob(TEST_RULE_BLOB);
		
		Rule rule2 = new Rule();
		rule2.setRuleName("r2");
		rule2.setRuleBlob(TEST_RULE_BLOB);
		
		rule1 = testRuleBase.persistRule(rule1);
		rule2 = testRuleBase.persistRule(rule2);

		Iterator<Rule> ruleIteror = testRuleBase.getAllActiveRules();
		assertNotNull("ruleIterator must not be null", ruleIteror);

		assertTrue("Rule match error", ruleIteror.next().equals(rule1));
		assertTrue("Rule match error", ruleIteror.next().equals(rule2));
	}
}
