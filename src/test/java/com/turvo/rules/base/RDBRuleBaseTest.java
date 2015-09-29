package com.turvo.rules.base;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;

public class RDBRuleBaseTest {

	private RuleBase testRuleBase;

	private Rule TEST_RULE;

	private RuleMeta TEST_RULE_META;

	private String TEST_RULE_NAME = "TEST_RULE";

	private String TEST_CUSTOMER_ID = "test_customer_id";

	private String TEST_CONTEXT = "test_context";

	private String TEST_GROUP_NAME = "test_group_name";

	private byte[] TEST_RULE_TEXT = { 12, 13, 14, 15 };

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

		DatastoreConfig config = new DatastoreConfig(dbUrl, driverClass, username, password);
		Properties miscProperties = new Properties();
		miscProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		miscProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		testRuleBase = new RDBRuleBase(config, miscProperties);
	}

	private void setUpTestData() {
		TEST_RULE = new Rule();
		TEST_RULE.setRuleName(TEST_RULE_NAME);
		TEST_RULE.setRuleText(TEST_RULE_TEXT);

		TEST_RULE_META = new RuleMeta();
		TEST_RULE_META.setGroupName(TEST_GROUP_NAME);
		TEST_RULE_META.setCustomerId(TEST_CUSTOMER_ID);
		TEST_RULE_META.setContext(TEST_CONTEXT);
		TEST_RULE_META.setActive(true);
	}

	@Test
	public void testPersistRule() {
		Rule rule = testRuleBase.persistRule(TEST_RULE);
		assertNotNull("rule must not be null", rule);
		assertTrue("rule_name must match", rule.getRuleName().equals(TEST_RULE_NAME));

		assertTrue("rule_blob must match", rule.getRuleText().equals(TEST_RULE_TEXT));
	}

	@Test
	public void testUpdateRule() {
		Rule origianalRule = testRuleBase.persistRule(TEST_RULE);
		origianalRule.setRuleName("UPDATED_RULE_NAME");
		Rule updatedRule = testRuleBase.updateRule(origianalRule);

		assertNotNull("rule must not be null", updatedRule);
		assertTrue("rule_name must match", updatedRule.getRuleName().equals("UPDATED_RULE_NAME"));
	}

	@Test
	public void testGetAllActiveRules() {
		Rule rule1 = new Rule();
		rule1.setRuleName("r1");
		rule1.setRuleText(TEST_RULE_TEXT);
		rule1.setActive(Boolean.TRUE);

		Rule rule2 = new Rule();
		rule2.setRuleName("r2");
		rule2.setRuleText(TEST_RULE_TEXT);
		rule2.setActive(Boolean.FALSE);

		rule1 = testRuleBase.persistRule(rule1);
		rule2 = testRuleBase.persistRule(rule2);

		Iterator<Rule> ruleIteror = testRuleBase.fetchAllActiveRules();
		assertNotNull("ruleIterator must not be null", ruleIteror);

		assertTrue("One rule must be fetched", ruleIteror.hasNext());
		assertTrue("Rule match error", ruleIteror.next().equals(rule1));
		assertFalse("Only one rule should be fetched", ruleIteror.hasNext());
	}

	@Test
	public void testPersistRuleMeta() {
		RuleMeta ruleMeta = testRuleBase.persistRuleMeta(TEST_RULE_META);

		assertNotNull("ruleMeta must not be null", ruleMeta);
		assertTrue("rule_name must match", ruleMeta.getGroupName().equals(TEST_GROUP_NAME));
		assertTrue("rule_context must match", ruleMeta.getContext().equals(TEST_CONTEXT));
		assertTrue("rule_customerId must match", ruleMeta.getCustomerId().equals(TEST_CUSTOMER_ID));
	}

	@Test
	public void testGetAllActiveRulesByContext() {
		RuleMeta rm1 = new RuleMeta(TEST_CUSTOMER_ID, TEST_CONTEXT, TEST_GROUP_NAME, true);
		RuleMeta rm2 = new RuleMeta(TEST_CUSTOMER_ID, "", TEST_GROUP_NAME + "_new", true);

		RuleMeta dbRm1 = testRuleBase.persistRuleMeta(rm1);
		RuleMeta dbRm2 = testRuleBase.persistRuleMeta(rm2);

		assertNotNull(dbRm1);
		assertNotNull(dbRm2);

		Iterator<RuleMeta> ruleMetaIterator = testRuleBase.fetchAllActiveRuleMetaFilterByContext(TEST_CONTEXT);
		assertNotNull("ruleMetaIterator must not be null", ruleMetaIterator);
		assertTrue("rule-meta must match rm1", ruleMetaIterator.next().getGroupName().equals(TEST_GROUP_NAME));
		assertFalse("only one rule-meta must be returned", ruleMetaIterator.hasNext());
	}

	@Test
	public void testGetAllActiveRulesByCustomerId() {
		RuleMeta rm1 = new RuleMeta(TEST_CUSTOMER_ID, TEST_CONTEXT, TEST_GROUP_NAME, true);
		RuleMeta rm2 = new RuleMeta("", TEST_CONTEXT, TEST_GROUP_NAME + "_new", true);

		RuleMeta dbRm1 = testRuleBase.persistRuleMeta(rm1);
		RuleMeta dbRm2 = testRuleBase.persistRuleMeta(rm2);

		assertNotNull(dbRm1);
		assertNotNull(dbRm2);

		Iterator<RuleMeta> ruleMetaIterator = testRuleBase.fetchAllActiveRuleMetaFilterByCustomerId(TEST_CUSTOMER_ID);
		assertNotNull("ruleMetaIterator must not be null", ruleMetaIterator);
		assertTrue("rule-meta must match rm1", ruleMetaIterator.next().getGroupName().equals(TEST_GROUP_NAME));
		assertFalse("only one rule-meta must be returned", ruleMetaIterator.hasNext());
	}

	@Test
	public void testGetAllActiveRulesByCustomerIdAndContext() {
		RuleMeta rm1 = new RuleMeta(TEST_CUSTOMER_ID, TEST_CONTEXT, TEST_GROUP_NAME, true);
		RuleMeta rm2 = new RuleMeta("", TEST_CONTEXT, TEST_GROUP_NAME + "_new", true);

		RuleMeta dbRm1 = testRuleBase.persistRuleMeta(rm1);
		RuleMeta dbRm2 = testRuleBase.persistRuleMeta(rm2);

		assertNotNull(dbRm1);
		assertNotNull(dbRm2);

		Iterator<RuleMeta> ruleMetaIterator = testRuleBase
				.fetchAllActiveRuleMetaFilterByContextAndCustomerId(TEST_CONTEXT, TEST_CUSTOMER_ID);
		assertNotNull("ruleMetaIterator must not be null", ruleMetaIterator);
		assertTrue("rule-meta must match rm1", ruleMetaIterator.next().getGroupName().equals(TEST_GROUP_NAME));
		assertFalse("only one rule-meta must be returned", ruleMetaIterator.hasNext());
	}
}
