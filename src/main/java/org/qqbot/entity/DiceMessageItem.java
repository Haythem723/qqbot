package org.qqbot.entity;

public class DiceMessageItem {
  private int id;
  private String receiverId;
  private int minThreshold;
  private int maxThreshold;
  private String message;

  public DiceMessageItem() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public int getMinThreshold() {
    return minThreshold;
  }

  public void setMinThreshold(int minThreshold) {
    this.minThreshold = minThreshold;
  }

  public int getMaxThreshold() {
    return maxThreshold;
  }

  public void setMaxThreshold(int maxThreshold) {
    this.maxThreshold = maxThreshold;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
