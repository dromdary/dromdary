/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.jpa.generator.helper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
	private static final String JPA_PROPERTIES_PATH = "../dromdary.jpa.generator/src/main/resources/jpa.properties";
	private static final String PROPERTY_PERSISTENCE_XML_PATH = "persistence.xml.path";
	private static final String PROPERTY_PERSISTENCE_XML_PROPERTIES = "persistence.xml.properties";
	private static final String PROPERTY_TABLE_SCHEMA = "table.schema";
	private static final String PROPERTY_TABLE_NAME_PREFIX = "table.name.prefix";
	private static final String PROPERTY_DAO_PACKAGE_NAME = "dao.package.name";
	private static final String GEN_DEF_INHERITANCE_AND_DISCRIMINATOR_COLUMN_ANNOTAION = "gen.def.inheritance.and.discriminator.column.annotation";
	private static final String GEN_DEF_ORPHAN_REMOVAL_TAGGED_VALUE_FOR_RELATIONS = "gen.def.orphan.removal.taggedValue.for.relations";
	private static final String GEN_DEF_TABLE_SCHEMA_TAGGED_VALUE = "gen.def.table.schema.taggedValue";
	
	public static String getPersistenceXmlPath() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_PERSISTENCE_XML_PATH);
	}
	
	public static String getPersistenceProperties() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_PERSISTENCE_XML_PROPERTIES);
	}
	
	public static String getTableSchema() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_TABLE_SCHEMA);
	}
	
	public static String getTableNamePrefix() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_TABLE_NAME_PREFIX);
	}
	
	public static String getDaoPackageName() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_DAO_PACKAGE_NAME);
	}
	
	public static String genDefInheritanceAndDiscriminatorColumnAnnotation() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(GEN_DEF_INHERITANCE_AND_DISCRIMINATOR_COLUMN_ANNOTAION);
	}
	
	public static String genDefOrphanRemovalTaggedValueForRelations() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(GEN_DEF_ORPHAN_REMOVAL_TAGGED_VALUE_FOR_RELATIONS);
	}
	
	public static String genDefTableSchemaTaggedValue() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(JPA_PROPERTIES_PATH));
		return properties.getProperty(GEN_DEF_TABLE_SCHEMA_TAGGED_VALUE);
	}
}
