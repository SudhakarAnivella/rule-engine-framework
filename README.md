# rule-engine-framework

Documentation
-------------

This is nothing more than a wrapper on top of drools + persistant
support for rule content.

Prerequisites
-------------

(a) A basic understanding of drools.
(b) A basic understanding of persistent technology(We are using
Relational Database in our case).
(c) Basic understanding of java.


Third party libraries
---------------------

Drools, Hibernate, Apache-lang, Apache-collections, guava from google,
slf4j for logging, hsql and juint, mockito for testing.

Code design
-----------

There are 4 primary components:
(a) RuleBase ... Used for persistence of rules.
(b) KnowledgebaseManager... Manages the knowledge(bunch of rules) of the system.
(c) PlatformService... Responsible for building and execution of rules.
(d) RuleEngine... This holds the context togather.. one can do gearUp
and tearDown opeartions on it.

And on top the these are the 2 client facing components.
(a) Validator.
(b) RulesExecutor.

Both perform same job execpt that they are different semantically.
Both take RuleEngine as base.

It is recomended for users to build RuleEngine once and provide
the same instance to validator and RulesExecutor.

Usage patterns
--------------

Step #1 (Build RuleEngine and bring-it-up)
//dataStoreConfig is the basic properties required to connect to a
//database (dbUrl,  username, password... etc).
RuleEngine re = new DroolsRuleEngine(dataStoreConfig);
re.gearUp();

Step #2
Make RuleEngine a single-ton instance.

Step #3 (Build Valaidator using RuleEngine as base)
Validator v = new ValidatorImpl(re);

Step #4
Make Validator a single-ton instance.

Step #5 (Use validator to validate any given facts)
v.validate(factsRecord).

And that is it... Your rules are executed.

Please note that al though it looks like 5 step process.. step 1..4 are
only done once during the application init.


factsRecord
-------------
This is an input record for sent for validation.

This compraises of 5 dimensions. One needs to understand them carefully
before writing/executing any rules.

FactsSet (mandatory): Object to be validated.

agendaGroups: This is the property of drools. Using agenda groups we can
dynamically group the rules for execution.

context: This refers to the context in which rules should be executed..
ex.. per-insert, Shipment-state-change... etc

customerId: This is used to execute customer-specific rules.. ex:
mega-corp...etc

globalParamsMap: This is used to pass helper-input and output to rules.
Please not that the key value should match the defined name in the
rules.

RulesDatabase
-------------
Before integrating this framework with any application.. please make
sure you run the script placed in resources/sql. By running this you
will create necessary tables for rule persistance.


Dynamically pick rules for execution (advanced)
-----------------------------------
We have provided a support for executing rules dynamically through
agenda-groups.

There are 2 ways in which we can do this

(a) Provide agenda-group directly in the factsrecord.
(b) provide context/customerId in the facts record.

By providing context/customerId pair.. we fetch db for the agenda-groups
defined with this name and execute those groups.

For having this kind of functinality, we need to register the groups
associated with context/customer-id with DB before hand.

Final Notes
-----------
Please define your defualt rules in the agenda group with name
"default-rules"

These will be executed once for every run of validate/executerules.


Please make sure you comment/fix if you find something messy.

Take care and enjoy the ride with rules-engine-framework...!!!
