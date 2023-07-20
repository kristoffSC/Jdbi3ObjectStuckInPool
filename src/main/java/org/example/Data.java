package org.example;

public class Data {

  private long id;

  private String name;

  private String details;

  public Data() {

  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(final String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return "Data{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", json='" + details + '\'' +
           '}';
  }
}
