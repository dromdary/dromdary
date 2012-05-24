package org.dromdary.jpa.generator.helper;

import org.eclipse.uml2.uml.Property;

public class NameHelper {
	private static final String ABSTRACT_CLASS_NAME_PREFIX = "Abstract";
	private static final String INTERFACE_NAME_PREFIX = "I";
	private static final String TABLE_NAME_PREFIX = "TEST_";
	
	public static String abstractClassName(String name) {
		name = convertToJavaPackageName(name);
		if (!name.contains(ABSTRACT_CLASS_NAME_PREFIX))
		{
			return addPrefixToName(name, ABSTRACT_CLASS_NAME_PREFIX);
		}
		else
		{
			return name;
		}
	}
	
	private static String addPrefixToName(String name, String prefix) {
		String className = name;
		String packageName = "";
		int classNamePosition = name.lastIndexOf(".");
		if (classNamePosition != -1) {
			classNamePosition++;
			className = name.substring(classNamePosition);
			packageName = name.substring(0, classNamePosition);
		}
		return packageName + prefix + className;
	}
	
	public static String interfaceName(String name) {
		return addPrefixToName(name, INTERFACE_NAME_PREFIX);
	}
	
	public static String convertToJavaPackageName(String name) {
		if (name != null && name.contains("::")) {
			if (name.startsWith("Model::")) {
				name = name.replaceFirst("Model::", "");
			}
			name = name.replaceAll("::", ".");
		}
		return name;
	}
	
	public static String generateTableName(String tableName) {
		StringBuffer bf = new StringBuffer(tableName);
		int underScores = 0;
		for (int i = 1; i < tableName.length(); i++) 
		{
			if (tableName.substring(i, i + 1).equals(tableName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf = bf.insert(i, "_");
				underScores++;
			}
			else if (tableName.substring(i, i + 1).equals(tableName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf = bf.insert(i + underScores, "_");
			}
		} 
		return TABLE_NAME_PREFIX + bf.toString().toUpperCase();
	}
	
	public static String generateColumnName(Property property) {
		String tableName = generateTableName(property.getClass_().getName());
		String[] splitString = tableName.split("_");
		
		StringBuffer bf = new StringBuffer();
		if (splitString.length == 2)
		{
			bf.append(splitString[1].substring(0, 3));
		}
		else if (splitString.length == 3)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
		}
		else if (splitString.length == 4)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
			bf.append(splitString[3].substring(0, 1));
		}
		
		bf.append("_");
		
		String propertyName = property.getName();
		int underScores = 0;
		StringBuffer bf2 = new StringBuffer(propertyName);
		for (int i = 1; i < propertyName.length(); i++) 
		{
			if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf2 = bf2.insert(i, "_");
				underScores++;
			}
			else if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf2 = bf2.insert(i + underScores, "_");
			}
		}
		bf.append(bf2);
		
		return bf.toString().toUpperCase();
	}
	
	public static String generateColumnName(String property, String tableName) {
		String[] splitString = tableName.split("_");
		
		StringBuffer bf = new StringBuffer();
		if (splitString.length == 2)
		{
			bf.append(splitString[1].substring(0, 3));
		}
		else if (splitString.length == 3)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
		}
		else if (splitString.length == 4)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
			bf.append(splitString[3].substring(0, 1));
		}
		
		bf.append("_");
		
		String propertyName = property;
		int underScores = 0;
		StringBuffer bf2 = new StringBuffer(propertyName);
		for (int i = 1; i < propertyName.length(); i++) 
		{
			if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf2 = bf2.insert(i, "_");
				underScores++;
			}
			else if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf2 = bf2.insert(i + underScores, "_");
			}
		}
		bf.append(bf2);
		
		return bf.toString().toUpperCase();
	}

	public static String getColumnNamePrefix(String columnName) {
		String[] splitString = columnName.split("_");
		return splitString[0].toUpperCase();
	}
}
