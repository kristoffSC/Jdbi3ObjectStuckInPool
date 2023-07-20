package org.example;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface Dao {

  @SqlQuery("SELECT * FROM application")
  @RegisterBeanMapper(Data.class)
  List<Data> getAll();
}
