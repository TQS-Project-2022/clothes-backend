package pt.ua.clothesbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    //@Value("${HOST_MYSQL}")
    @Value("localhost")
    private String hostDB;

    //@Value("${DOCKER_PORT_MYSQL}")
    @Value("3306")
    private int portDB;

    @Value("${DATABASE_NAME}")
    private String nameDB;

    @Value("root")
    private String userDB;

    @Value("${DATABASE_ROOT_PASSWORD}")
    private String passwordDB;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(
                String.format("jdbc:mysql://%s:%d/%s", hostDB, portDB, nameDB)
        );
        dataSourceBuilder.username(userDB);
        dataSourceBuilder.password(passwordDB);
        return dataSourceBuilder.build();
    }


}
