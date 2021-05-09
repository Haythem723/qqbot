package org.qqbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaucenaoDataItem {
  // 判断是pixiv还是anidb
  private boolean isPixiv = true;
  // pixiv内容
  private List<String> ext_urls;
  private String title;
  private int pixiv_id;
  private String member_name;
  private int member_id;
  //anidb内容
  private boolean isAniDB = false;
  private String source;
  private int anidb_aid;
  private String part;
  private String year;
  private String est_time;
  //eh内容
  private boolean isH = false;
  private String eng_name;
  private String jp_name;

  public boolean isAniDB() {
    return isAniDB;
  }

  public void setAniDB(boolean aniDB) {
    isAniDB = aniDB;
  }

  public boolean isH() {
    return isH;
  }

  public void setH(boolean h) {
    isH = h;
  }

  public String getEng_name() {
    this.isH = true;
    return eng_name;
  }

  public void setEng_name(String eng_name) {
    this.isH = true;
    this.eng_name = eng_name;
  }

  public String getJp_name() {
    return jp_name;
  }

  public void setJp_name(String jp_name) {
    this.jp_name = jp_name;
  }

  public boolean isIsdefault() {
    return isdefault;
  }

  public void setIsdefault(boolean isdefault) {
    this.isdefault = isdefault;
  }

  //其它来源
  private boolean isdefault = true;

  public boolean isPixiv() {
    return isPixiv;
  }

  public boolean isDefault(){
    return isdefault;
  }

  public void setDefault(boolean defaults){
    isdefault = defaults;
  }

  public void setPixiv(boolean pixiv) {
    isPixiv = pixiv;
  }

  public List<String> getExt_urls() {
    List<String> res = ext_urls;
    if (res == null) {
      res = new ArrayList<String>();
      res.add("无");
    }
    return res;
  }

  public void setExt_urls(List<String> ext_urls) {
    this.ext_urls = ext_urls;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getPixiv_id() {
    return pixiv_id;
  }

  public void setPixiv_id(int pixiv_id) {
    this.pixiv_id = pixiv_id;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public int getMember_id() {
    return member_id;
  }

  public void setMember_id(int member_id) {
    this.member_id = member_id;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public boolean getAniDB_id(){
    return isAniDB;
  }

  public void setAniDB_id(boolean ani_db){
    isAniDB = ani_db;
  }

  public int getAnidb_aid() {
    return anidb_aid;
  }

  public void setAnidb_aid(int anidb_aid) {
    this.anidb_aid = anidb_aid;
    this.isPixiv = false;
  }

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getEst_time() {
    return est_time;
  }

  public void setEst_time(String est_time) {
    this.est_time = est_time;
  }
}
