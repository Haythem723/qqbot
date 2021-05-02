package org.qqbot.function;

import org.junit.jupiter.api.Test;
import org.qqbot.core.CommandNull;
import org.qqbot.entity.Command;

import java.util.ArrayList;

public class TextCommand {
    @Test
    public void textCommand(){
        CommandNull commandNull = new CommandNull();
        Command command = new Command();
        ArrayList<String> list = new ArrayList<>();
        list.add("/小黑的牛子");
        command.setArgs(list);
        commandNull.invoke(null, command);
    }
}
