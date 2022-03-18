package com.littlekingkong.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {

  private Integer id;
  private String account_id;
  private String name;
  private String token;
  private long gmt_create;
  private long gmt_modified;
  private String avatar_url;

}
