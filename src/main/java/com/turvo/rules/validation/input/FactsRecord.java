package com.turvo.rules.validation.input;

import java.util.List;
import java.util.Map;

public class FactsRecord {
	private Object factSet;

	private List<String> agendaGroups;

	private String context;

	private String customerId;

	private Map<String, Object> globalParamsMap;

	public FactsRecord(Object factSet, List<String> agendaGroups, String context, String customerId,
			Map<String, Object> globalParamsMap) {
		super();
		this.factSet = factSet;
		this.agendaGroups = agendaGroups;
		this.context = context;
		this.customerId = customerId;
		this.globalParamsMap = globalParamsMap;
	}

	public FactsRecord(Object factSet, List<String> agendaGroups, Map<String, Object> globalParamsMap) {
		super();
		this.factSet = factSet;
		this.agendaGroups = agendaGroups;
		this.globalParamsMap = globalParamsMap;
	}

	public Object getFactSet() {
		return factSet;
	}

	public Map<String, Object> getGlobalParamsMap() {
		return globalParamsMap;
	}

	public List<String> getAgendaGroups() {
		return agendaGroups;
	}

	public String getContext() {
		return context;
	}

	public String getCustomerId() {
		return customerId;
	}
}
