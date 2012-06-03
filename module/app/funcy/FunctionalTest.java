package funcy;

import static play.test.Helpers.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import play.test.FakeApplication;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class FunctionalTest {

	private static FakeApplication app;

	@BeforeClass
	public static void startApp() {
		Map<String, String> conf = configuration();
		conf.remove("basic.auth");
		conf.putAll(inMemoryDatabase());
		app = fakeApplication(conf);
		start(app);
	}

	public static Map<String, String> configuration() {
		Map<String, String> conf = new HashMap<String, String>();
		Config config = ConfigFactory.parseFileAnySyntax(new File(
				"conf/application.conf"));
		config = config.resolve();
		for (Entry<String, ConfigValue> entry : config.entrySet()) {
			ConfigValue value = entry.getValue();
			conf.put(entry.getKey(), value.unwrapped().toString());
		}
		return conf;
	}

	@AfterClass
	public static void stopApp() {
		stop(app);
	}

}
