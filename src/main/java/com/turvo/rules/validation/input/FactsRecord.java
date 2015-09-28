package com.turvo.rules.validation.input;

import java.util.List;
import java.util.Map;

public class FactsRecord {
	private Object factSet;

	private List<String> agendaGroups;

	private String context;

	private String customerId;

	private Map<String, Object> globalParamsMap;

	public Object getFactSet() {
		return factSet;
	}

	public void setFactSet(Object factSet) {
		this.factSet = factSet;
	}

	public Map<String, Object> getGlobalParamsMap() {
		return globalParamsMap;
	}

	public void setGlobalParamsMap(Map<String, Object> globalParamsMap) {
		this.globalParamsMap = globalParamsMap;
	}

	public List<String> getAgendaGroups() {
		return agendaGroups;
	}

	public void setAgendaGroups(List<String> agendaGroups) {
		this.agendaGroups = agendaGroups;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
