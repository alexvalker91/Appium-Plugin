import epam.auto.utt.config.Config;
import epam.auto.utt.reflect.UttClassLoader;
import epam.auto.utt.run.runners.TestRunner;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;

public class SimpleTests {

    @Test
    public void simpleTest() {
        UttClassLoader cl = new UttClassLoader(new URL[0], ClassLoader.getSystemClassLoader());
        String testpath ="C:\\Users\\AliaksandrKreyer\\Desktop\\my\\repositories\\Appium-Plugin\\untitled\\src\\test\\resources\\android_simple_test.xml";
        new TestRunner(new File(testpath), new Config().setClassLoader(cl)).run();
    }
}
