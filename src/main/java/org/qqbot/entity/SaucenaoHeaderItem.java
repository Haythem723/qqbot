package org.qqbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaucenaoHeaderItem {
  private String similarity;
  private String thumbnail;
  private int index_id;
  private String index_name;
  private int dupes;
}
