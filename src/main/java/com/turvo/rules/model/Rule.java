package com.turvo.rules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "rules")
public class Rule {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int ruleId;

	@Column(name = "name", unique = true)
	private String ruleName;

	@Column(name = "rule_text")
	private byte[] ruleText;

	@Column(name = "active")
	private boolean active;

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public byte[] getRuleText() {
		return ruleText;
	}

	public void setRuleText(byte[] ruleText) {
		this.ruleText = ruleText;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}