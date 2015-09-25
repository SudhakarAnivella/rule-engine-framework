package com.turvo.rules.model;

import java.util.Arrays;

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
	@Column(name = "rule_id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int ruleId;

	@Column(name = "rule_name", unique = true)
	private String ruleName;

	@Column(name = "rule_blob")
	private byte[] ruleBlob;

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

	public byte[] getRuleBlob() {
		return ruleBlob;
	}

	public void setRuleBlob(byte[] ruleBlob) {
		this.ruleBlob = ruleBlob;
	}

	@Override
	public String toString() {
		return "Rule [ruleId=" + ruleId + ", ruleName=" + ruleName
				+ ", ruleBlob=" + Arrays.toString(ruleBlob) + "]";
	}

}