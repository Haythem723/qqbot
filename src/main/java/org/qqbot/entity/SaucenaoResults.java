package org.qqbot.entity;

import com.sun.tools.javac.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaucenaoResults {
  private int status;
  private List<SaucenaoHeaderItem> header;
  private List<SaucenaoDataItem> data;
}
