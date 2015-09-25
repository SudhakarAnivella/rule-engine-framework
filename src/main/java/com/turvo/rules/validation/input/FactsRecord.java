package com.turvo.rules.validation.input;

import java.util.Map;

public class FactsRecord {
	private Object factSet;

	private Map<String, Boolean> agendaGroups;

	private Map<String, Object> globalParamsMap;

	public Object getFactSet() {
		return factSet;
	}

	public void setFactSet(Object factSet) {
		this.factSet = factSet;
	}

	public Map<String, Boolean> getAgendaGroups() {
		return agendaGroups;
	}

	public void setAgendaGroups(Map<String, Boolean> agendaGroups) {
		this.agendaGroups = agendaGroups;
	}

	public Map<String, Object> getGlobalParamsMap() {
		return globalParamsMap;
	}

	public void setGlobalParamsMap(Map<String, Object> globalParamsMap) {
		this.globalParamsMap = globalParamsMap;
	}
}
