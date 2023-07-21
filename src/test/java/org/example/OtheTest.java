package org.example;

import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class OtheTest {

  @Container
  public JdbcDatabaseContainer<?> container = new PostgreSQLContainer<>()
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
    dataSource.setInitialSize(2);
    dataSource.setMaxIdle(5);
    dataSource.setMaxTotal(10);

    GenericObjectPoolConfig<PoolableConnection> poolConfig = new GenericObjectPoolConfig<>();
    poolConfig.setMinIdle(2);
    poolConfig.setMaxIdle(5);
    poolConfig.setMaxTotal(10);

    PooledObjectFactory<PoolableConnection> objectFactory = new PoolableConnectionFactory(
        new DataSourceConnectionFactory(dataSource),
        null
    );

    this.jdbi = Jdbi.create(new PoolingDataSource<>(
        new GenericObjectPool<>(
            objectFactory,
            poolConfig
        ))
    );

    jdbi.installPlugin(new SqlObjectPlugin());
  }

  @Test
  public void testMe() {
    System.out.println("Hello");

    selectFromDb();
    selectFromDb();
    selectFromDb();

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
