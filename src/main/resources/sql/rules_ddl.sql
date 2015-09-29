CREATE TABLE rules
(
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(255) UNIQUE NOT NULL,
rule_text BLOB NOT NULL,
active BIT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT uc_rule_name UNIQUE (id, name)
);


CREATE TABLE rules_meta_info
(
id int NOT NULL AUTO_INCREMENT,
customer_id VARCHAR(128) NOT NULL,
context VARCHAR(55),
rule_group_name VARCHAR(128) UNIQUE NOT NULL,
active BIT NOT NULL,
PRIMARY KEY (id)
)