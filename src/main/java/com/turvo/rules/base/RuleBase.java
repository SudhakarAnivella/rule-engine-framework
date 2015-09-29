package com.turvo.rules.base;

import java.util.Iterator;

import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;

public interface RuleBase {
	Rule persistRule(Rule rule);

	RuleMeta persistRuleMeta(RuleMeta ruleMeta);

	void persistRuleBatch(Iterator<Rule> ruleIterator);

	Rule updateRule(Rule rule);

	Iterator<Rule> fetchAllActiveRules();

	Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByContextAndCustomerId(
			String context, String customerId);

	Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByContext(String context);

	Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByCustomerId(
			String customerId);
}
