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
	public static String getPersistenceXmlPath() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("../dromdary.jpa.generator/src/main/resources/jpa.properties"));
		return properties.getProperty("persistence.xml.path");
	}
	public static String getPersistenceProperties() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("../dromdary.jpa.generator/src/main/resources/jpa.properties"));
		return properties.getProperty("persistence.xml.properties");
	}
	public static String getTableSchema() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("../dromdary.jpa.generator/src/main/resources/jpa.properties"));
		return properties.getProperty("table.schema");
	}
	public static String getDaoPackageName() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("../dromdary.jpa.generator/src/main/resources/jpa.properties"));
		return properties.getProperty("dao.package.name");
	}
}
