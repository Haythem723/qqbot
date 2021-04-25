package org.qqbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeLibItem {

    private int id;
    private String jokeBody;

    @Override
    public String toString(){
        return this.jokeBody;
    }
}
