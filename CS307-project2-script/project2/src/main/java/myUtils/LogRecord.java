package myUtils;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


//配置文件还没玩懂 日志会报错
public class LogRecord {
    public static void initLog() {
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
            fileInputStream = new FileInputStream("log.properties");
            properties.load(fileInputStream);
            PropertyConfigurator.configure(properties);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
