package com.turvo.rules.base;

import java.util.Iterator;

import com.turvo.rules.model.Rule;

public interface RuleBase {
	Rule persistRule(Rule rule);

	void persistRuleBatch(Iterator<Rule> ruleIterator);

	Rule updateRule(Rule rule);

	Iterator<Rule> getAllActiveRules();
}
