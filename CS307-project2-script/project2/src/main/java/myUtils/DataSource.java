package myUtils;

import config.config;
import com.zaxxer.hikari.HikariDataSource;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource implements Closeable {
    private static final DataSource INSTANCE = new DataSource();
    private HikariDataSource dataSource;

    //单例设计模式，因为我们只有唯一的一个数据源
    public static DataSource getInstance() {
        return INSTANCE;
    }

    public void DataSourceConfig() throws IOException {
        config.CP_init();
        dataSource = new HikariDataSource(config.config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
