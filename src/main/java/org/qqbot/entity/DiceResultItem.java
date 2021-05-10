package org.qqbot.entity;

public class DiceResultItem {
  private String resultString;
  private String message;
  private int resultSum;

  public DiceResultItem() {

  }

  public String getMessage() {
    return message;
  }

  public DiceResultItem setMessage(String message) {
    this.message = message;
    return this;
  }

  public DiceResultItem(String resultString, int resultSum) {
    this.resultString = resultString;
    this.resultSum = resultSum;
  }

  public String getResultString() {
    return resultString;
  }

  public DiceResultItem setResultString(String resultString) {
    this.resultString = resultString;
    return this;
  }

  public String getResultSum() {
    return String.valueOf(resultSum);
  }

  public DiceResultItem setResultSum(int resultSum) {
    this.resultSum = resultSum;
    return this;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    if (this.message != null) sb.append(this.message).append("\n");
    sb.append(this.resultString);
    return sb.toString();
  }
}
