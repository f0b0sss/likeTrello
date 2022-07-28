package com.likeTrello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
//@PropertySource("application.properties")
public class TestConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

//    @Bean
//    public TaskService taskService(){
//        return new TaskServiceImpl();
//    }

//    @Bean
//    public ColumnService columnService(){
//        return new ColumnServiceImpl();
//    }
//
//    @Bean
//    public BoardService boardService(){
//        return new BoardServiceImpl();
//    }



}
