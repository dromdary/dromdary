import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CommonPropertiesHelper {
	public static String getJpaFlag() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/resources/common.properties"));
		return properties.getProperty("generate.jpa");
	}
	public static String getBvaFlag() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/resources/common.properties"));
		return properties.getProperty("generate.bva");
	}
}
