package org.example;

import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Jdbi3IntegrationTest {

  @Container
  public JdbcDatabaseContainer<?> container = new PostgreSQLContainer<>("postgres:9.6.12")
      .withDatabaseName("product_db")
      .withUsername("user")
      .withPassword("password")
      .withInitScript("init_script.sql");
  private Jdbi jdbi;

  @BeforeEach
  public void setUp() {

    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl(container.getJdbcUrl());
    dataSource.setPassword("password");
    dataSource.setUsername("user");
    dataSource.setMinIdle(2);
    dataSource.setMaxIdle(4);
    dataSource.setMaxTotal(6);
    //dataSource.setMaxWaitMillis(5000);

    this.jdbi = Jdbi.create(dataSource)
        .installPlugin(new SqlObjectPlugin())
        .installPlugin(new PostgresPlugin());
  }

  @Test
  public void testMe() {
    for (int i = 0; i < 15; i++) {
      selectFromDb(); // put breakpoint here
    }
  }

  private void selectFromDb() {
    try {
      List<Data> data = jdbi.withExtension(Dao.class, Dao::getAll);
      for (Data datum : data) {
        System.out.println(datum);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
