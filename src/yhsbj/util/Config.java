package yhsbj.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
	static Properties configures = new Properties();
	static {
		try (var input = Files.newInputStream(Paths.get("config.properties"))) {
			configures.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return configures.getProperty(key);
	}
}
