package org.qqbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaucenaoDataItem {
  // pixiv内容
  private List<String> ext_urls;
  private String title;
  private int pixiv_id;
  private String member_name;
  private int member_id;
  //anidb内容
  private String source;
  private int anidb_aid;
  private String part;
  private String year;
  private String est_time;
}
