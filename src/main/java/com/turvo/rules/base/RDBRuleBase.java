package com.turvo.rules.base;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.google.common.base.Preconditions;
import com.turvo.rules.base.config.DatastoreConfig;
import com.turvo.rules.misc.ErrorConstants;
import com.turvo.rules.misc.RuleConstants;
import com.turvo.rules.model.Rule;

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

	@SuppressWarnings("unchecked")
	public Iterator<Rule> getAllActiveRules() {
		List<Rule> rules = (List<Rule>) entityManager
				.createQuery("SELECT r FROM com.turvo.rules.model.Rule r")
				.getResultList();

		return rules.iterator();
	}
}
