package com.littlekingkong.community.model;

import org.springframework.stereotype.Component;

@Component
public class User {

  private long id;
  private String account_id;
  private String name;
  private String token;
  private long gmt_create;
  private long gmt_modified;

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", account_id='" + account_id + '\'' +
            ", name='" + name + '\'' +
            ", token='" + token + '\'' +
            ", gmt_create=" + gmt_create +
            ", gmt_modified=" + gmt_modified +
            '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAccount_id() {
    return account_id;
  }

  public void setAccount_id(String account_id) {
    this.account_id = account_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getGmt_create() {
    return gmt_create;
  }

  public void setGmt_create(long gmt_create) {
    this.gmt_create = gmt_create;
  }

  public long getGmt_modified() {
    return gmt_modified;
  }

  public void setGmt_modified(long gmt_modified) {
    this.gmt_modified = gmt_modified;
  }
}
