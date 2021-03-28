package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UrlRepository {
	
	private static Properties envURLProperties;


	static {
		
		String directoryPath = "./src/test/resources/env_config/";
		String env; // will be taken at run time so that different env files can be run
		try {
			if (System.getProperty("env") == null) {
				env = "test";
			} else {
				env = System.getProperty("env");
			}
			Properties props = new Properties();
			props.load(new FileInputStream(directoryPath + env + "_env.properties"));
			UrlRepository.setEnvURLProperties(props);
		} catch (IOException ioException) {
			// TODO: handle exception
		  ioException.printStackTrace(); 
		  System.exit(0);
		}

	
	}

	public static Properties getEnvURLProperties() {
		
		return envURLProperties;
	}

	public static void setEnvURLProperties(Properties envURLProperties) {
		UrlRepository.envURLProperties = envURLProperties;
	}
	
}
