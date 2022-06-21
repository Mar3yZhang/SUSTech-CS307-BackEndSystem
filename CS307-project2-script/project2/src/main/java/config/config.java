package config;

import com.zaxxer.hikari.HikariConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


//hikari在使用完后要关掉， 但是底层不会关闭连接
public final class config {

    static final Properties properties = new Properties();
    public static HikariConfig config;


    ///对Hikari数据库连接池做初始化
    public static void CP_init() throws IOException {
        InputStream in = config.class.getClassLoader().getResourceAsStream("db.properties");
        config = new HikariConfig();
        properties.load(in);
        config.setDataSourceClassName(properties.getProperty("driver"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.addDataSourceProperty("serverName", properties.getProperty("host"));
        config.addDataSourceProperty("portNumber", properties.getProperty("port"));
        config.addDataSourceProperty("databaseName", properties.getProperty("dbname"));
    }
}

