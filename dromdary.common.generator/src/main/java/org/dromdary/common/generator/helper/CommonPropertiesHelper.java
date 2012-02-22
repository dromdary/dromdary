package org.dromdary.common.generator.helper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CommonPropertiesHelper {
	
	public static String COMMON_PROPERTIES_PATH = "src/main/resources/common.properties";
	public static String PROPERTY_GENERATE_JPA = "generate.jpa";
	public static String PROPERTY_GENERATE_BVA = "generate.bva";
	
	public static String getJpaFlag() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(COMMON_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_GENERATE_JPA);
	}
	public static String getBvaFlag() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(COMMON_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_GENERATE_BVA);
	}
	

	public static String PROPERTY_IMPL_GENERALIZATION = "impl.generalization";
	public static String implHierarchy() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(COMMON_PROPERTIES_PATH));
		return properties.getProperty(PROPERTY_IMPL_GENERALIZATION);
	}
}
