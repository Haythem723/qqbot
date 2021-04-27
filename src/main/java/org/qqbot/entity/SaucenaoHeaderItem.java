package org.qqbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaucenaoHeaderItem {
  private String similarity;
  private String thumbnail;
  private int index_id;
  private String index_name;
  private int dupes;

  public String getSimilarity() {
    return similarity;
  }

  public void setSimilarity(String similarity) {
    this.similarity = similarity;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public int getIndex_id() {
    return index_id;
  }

  public void setIndex_id(int index_id) {
    this.index_id = index_id;
  }

  public String getIndex_name() {
    return index_name;
  }

  public void setIndex_name(String index_name) {
    this.index_name = index_name;
  }

  public int getDupes() {
    return dupes;
  }

  public void setDupes(int dupes) {
    this.dupes = dupes;
  }
}
