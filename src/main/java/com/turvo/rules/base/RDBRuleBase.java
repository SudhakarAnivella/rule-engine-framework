package com.turvo.rules.base;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.misc.RuleConstants;
import com.turvo.rules.model.Rule;
import com.turvo.rules.model.RuleMeta;

public class RDBRuleBase implements RuleBase {
	private EntityManager entityManager;

	private RDBPropertiesProvider propertiesProvider;

	public RDBRuleBase(DatastoreConfig dataStoreConfig) {
		propertiesProvider = new RDBPropertiesProvider();
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(RuleConstants.RULES_PERSISTENT_UNIT,
						propertiesProvider
								.buildDataStoreProperties(dataStoreConfig)
								.getProperties());
		entityManager = emf.createEntityManager();
	}

	public RDBRuleBase(DatastoreConfig dataStoreConfig, Properties properties) {
		propertiesProvider = new RDBPropertiesProvider();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				RuleConstants.RULES_PERSISTENT_UNIT,
				propertiesProvider.buildDataStoreProperties(dataStoreConfig)
						.buildMiscProperties(properties).getProperties());
		entityManager = emf.createEntityManager();
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
		Preconditions.checkNotNull(rule,
				ErrorConstants.NULL_RULE_ERROR_MESSAGE);

		EntityTransaction rulePersistTransaction = entityManager
				.getTransaction();
		rulePersistTransaction.begin();
		try {
			entityManager.persist(rule);
			rulePersistTransaction.commit();
		}
		finally {
			rollBackTransactionIfOpen(rulePersistTransaction);
		}
		return rule;
	}

	public Rule updateRule(Rule rule) {
		Preconditions.checkNotNull(rule,
				ErrorConstants.NULL_RULE_ERROR_MESSAGE);
		EntityTransaction ruleUpdateTransaction = entityManager
				.getTransaction();
		ruleUpdateTransaction.begin();

		try {
			rule = entityManager.merge(rule);
		}
		finally {
			rollBackTransactionIfOpen(ruleUpdateTransaction);
		}

		return rule;
	}

	public void persistRuleBatch(Iterator<Rule> ruleIterator) {
		Preconditions.checkNotNull(ruleIterator,
				ErrorConstants.NULL_ITERATOR_ERROR_MESSAGE);

		EntityTransaction rulesPersistTransaction = entityManager
				.getTransaction();
		rulesPersistTransaction.begin();
		try {
			while (ruleIterator.hasNext()) {
				Rule rule = ruleIterator.next();
				if (rule != null) {
					entityManager.persist(rule);
				}
			}
			rulesPersistTransaction.commit();
		}
		finally {
			rollBackTransactionIfOpen(rulesPersistTransaction);
		}
	}

	public RuleMeta persistRuleMeta(RuleMeta ruleMeta) {
		Preconditions.checkNotNull(ruleMeta,
				ErrorConstants.NULL_RULE_META_ERROR_MESSAGE);

		EntityTransaction ruleMetaPersistTransaction = entityManager
				.getTransaction();
		ruleMetaPersistTransaction.begin();
		try {
			entityManager.persist(ruleMeta);
			ruleMetaPersistTransaction.commit();
		}
		finally {
			rollBackTransactionIfOpen(ruleMetaPersistTransaction);
		}
		return ruleMeta;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Rule> getAllActiveRules() {
		List<Rule> rules = (List<Rule>) entityManager
				.createQuery("SELECT r FROM com.turvo.rules.model.Rule r")
				.getResultList();

		return rules.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> getAllActiveRuleMetaFilterByContextAndCustomerId(
			String context, String customerId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(context),
				ErrorConstants.NULL_CONTEXT_MESSAGE);
		Preconditions.checkArgument(StringUtils.isNotBlank(customerId),
				ErrorConstants.NULL_CUSTOMER_ID_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(
						"SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.context = :context AND rm.customerId = :customerId")
				.setParameter("context", context)
				.setParameter("customerId", customerId).getResultList();
		return ruleMetaData.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> getAllActiveRuleMetaFilterByContext(
			String context) {
		Preconditions.checkArgument(StringUtils.isNotBlank(context),
				ErrorConstants.NULL_CONTEXT_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(
						"SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.context = :context")
				.setParameter("context", context).getResultList();
		return ruleMetaData.iterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<RuleMeta> getAllActiveRuleMetaFilterByCustomerId(
			String customerId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(customerId),
				ErrorConstants.NULL_CUSTOMER_ID_MESSAGE);
		List<RuleMeta> ruleMetaData = (List<RuleMeta>) entityManager
				.createQuery(
						"SELECT rm FROM com.turvo.rules.model.RuleMeta rm WHERE rm.active = true AND rm.customerId = :customerId")
				.setParameter("customerId", customerId).getResultList();
		return ruleMetaData.iterator();
	}
}
