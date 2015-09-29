package com.turvo.rules.misc;

public class SqlConstants {
	public final static String GET_ALL_ACTIVE_RULES_SQL = "SELECT r FROM com.turvo.rules.model.Rule r WHERE r.active = true";
	public final static String GET_ALL_ACTIVE_META_INFO_FILTER_BY_CUSTID_CONTEXT_SQL = "SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.context = :context AND rm.customerId = :customerId";
	public final static String GET_ALL_ACTIVE_META_INFO_FILTER_BY_CONTEXT_SQL = "SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.context = :context";
	public final static String GET_ALL_ACTIVE_META_INFO_FILTER_BY_CUSTID_SQL = "SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.customerId = :customerId";

	public final static String SQL_CUST_ID_PLACE_HOLDER = "customerId";
	public final static String SQL_CONTEXT_PLACE_HOLDER = "context";
}
