insert into person('id', 'name', 'age', 'blood_type') values (1, 'martin', 10, 'A');
-- TODO: fix following bug in this sql file:
-- org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSourceScriptDatabaseInitializer'
-- defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]:
--
-- Invocation of init method failed; nested exception is
-- org.springframework.jdbc.datasource.init.ScriptStatementFailedException:
-- Failed to execute SQL script statement #1 of URL [file:/D:/FAST_JAVA/demo/mycontact/build/resources/test/data.sql]:
-- insert into person('id', 'name', 'age', 'blood_type') values (1, 'martin', 10, 'A');
--
-- nested exception is org.h2.jdbc.JdbcSQLSyntaxErrorException:
-- Table "PERSON" not found (this database is empty); SQL statement:
