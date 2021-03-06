package com.turvo.rules.base;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.misc.RuleConstants;
import com.turvo.rules.misc.SqlConstants;
import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;

public class RDBRuleBase implements RuleBase {
	private final Logger LOGGER = LoggerFactory.getLogger(RDBRuleBase.class);

	private EntityManager entityManager;

	private RDBPropertiesProvider propertiesProvider;

	public RDBRuleBase(DatastoreConfig dataStoreConfig) {
		propertiesProvider = new RDBPropertiesProvider();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(RuleConstants.RULES_PERSISTENT_UNIT,
				propertiesProvider.buildDataStoreProperties(dataStoreConfig).getProperties());
		entityManager = emf.createEntityManager();
		LOGGER.info("RDB RuleBase has been initiated sucessfully...!!!");
	}

	public RDBRuleBase(DatastoreConfig dataStoreConfig, Properties properties) {
		propertiesProvider = new RDBPropertiesProvider();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(RuleConstants.RULES_PERSISTENT_UNIT,
				propertiesProvider.buildDataStoreProperties(dataStoreConfig).buildMiscProperties(properties)
						.getProperties());
		entityManager = emf.createEntityManager();
		LOGGER.info("RDB RuleBase has been initiated sucessfully...!!!");
	}

	private void rollBackTransactionIfOpen(EntityTransaction transaction) {
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}

	public RDBRuleBase(EntityManager entityManager) {
		if (entityManager != null) {
			this.entityManager = entityManager;
		}
	}

	public Rule persistRule(Rule rule) {
		Preconditions.checkNotNull(rule, ErrorConstants.NULL_RULE_ERROR_MESSAGE);

		EntityTransaction rulePersistTransaction = entityManager.getTransaction();
		rulePersistTransaction.begin();
		try {
			entityManager.persist(rule);
			rulePersistTransaction.commit();
		} finally {
			rollBackTransactionIfOpen(rulePersistTransaction);
		}
		return rule;
	}

	public Rule updateRule(Rule rule) {
		Preconditions.checkNotNull(rule, ErrorConstants.NULL_RULE_ERROR_MESSAGE);
		EntityTransaction ruleUpdateTransaction = entityManager.getTransaction();
		ruleUpdateTransaction.begin();

		try {
			rule = entityManager.merge(rule);
		} finally {
			rollBackTransactionIfOpen(ruleUpdateTransaction);
		}

		return rule;
	}

	public void persistRuleBatch(Iterator<Rule> ruleIterator) {
		Preconditions.checkNotNull(ruleIterator, ErrorConstants.NULL_ITERATOR_ERROR_MESSAGE);

		EntityTransaction rulesPersistTransaction = entityManager.getTransaction();
		rulesPersistTransaction.begin();
		try {
			while (ruleIterator.hasNext()) {
				Rule rule = ruleIterator.next();
				if (rule != null) {
					entityManager.persist(rule);
				}
			}
			rulesPersistTransaction.commit();
		} finally {
			rollBackTransactionIfOpen(rulesPersistTransaction);
		}
	}

	public RuleMeta persistRuleMeta(RuleMeta ruleMeta) {
		Preconditions.checkNotNull(ruleMeta, ErrorConstants.NULL_RULE_META_ERROR_MESSAGE);

		EntityTransaction ruleMetaPersistTransaction = entityManager.getTransaction();
		ruleMetaPersistTransaction.begin();
		try {
			entityManager.persist(ruleMeta);
			ruleMetaPersistTransaction.commit();
		} finally {
			rollBackTransactionIfOpen(ruleMetaPersistTransaction);
		}
		return ruleMeta;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Rule> fetchAllActiveRules() {
		List<Rule> rules = (List<Rule>) entityManager.createQuery(SqlConstants.GET_ALL_ACTIVE_RULES_SQL)
				.getResultList();

		return rules.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByContextAndCustomerId(String context, String customerId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(context), ErrorConstants.NULL_CONTEXT_MESSAGE);
		Preconditions.checkArgument(StringUtils.isNotBlank(customerId), ErrorConstants.NULL_CUSTOMER_ID_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(SqlConstants.GET_ALL_ACTIVE_META_INFO_FILTER_BY_CUSTID_CONTEXT_SQL)
				.setParameter(SqlConstants.SQL_CONTEXT_PLACE_HOLDER, context)
				.setParameter(SqlConstants.SQL_CUST_ID_PLACE_HOLDER, customerId).getResultList();
		return ruleMetaData.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByContext(String context) {
		Preconditions.checkArgument(StringUtils.isNotBlank(context), ErrorConstants.NULL_CONTEXT_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(SqlConstants.GET_ALL_ACTIVE_META_INFO_FILTER_BY_CONTEXT_SQL)
				.setParameter(SqlConstants.SQL_CONTEXT_PLACE_HOLDER, context).getResultList();
		return ruleMetaData.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> fetchAllActiveRuleMetaFilterByCustomerId(String customerId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(customerId), ErrorConstants.NULL_CUSTOMER_ID_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(SqlConstants.GET_ALL_ACTIVE_META_INFO_FILTER_BY_CUSTID_SQL)
				.setParameter(SqlConstants.SQL_CUST_ID_PLACE_HOLDER, customerId).getResultList();
		return ruleMetaData.iterator();
	}
}
