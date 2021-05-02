package org.qqbot.entity;

import lombok.Data;

@Data
public class SensitiveItem {
    private int id;
    private String rootword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRootword() {
        return rootword;
    }

    public void setRootword(String rootword) {
        this.rootword = rootword;
    }
}
