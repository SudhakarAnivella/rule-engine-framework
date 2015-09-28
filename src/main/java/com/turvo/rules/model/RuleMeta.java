package com.turvo.rules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "rules_meta_info")
public class RuleMeta {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int Id;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "context")
	private String context;

	@Column(name = "rule_group_name", unique = true)
	private String groupName;

	@Column(name = "active")
	private boolean active;
	
	public RuleMeta() {}

	public RuleMeta(String customerId, String context, String groupName,
			boolean active) {
		super();
		this.customerId = customerId;
		this.context = context;
		this.groupName = groupName;
		this.active = active;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
