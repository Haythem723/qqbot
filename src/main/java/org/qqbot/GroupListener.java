package org.qqbot;

import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.function.Consumer;

public class GroupListener implements Consumer<GroupMessageEvent> {
    @Override
    public void accept(GroupMessageEvent groupMessageEvent) {
        String content = groupMessageEvent.getMessage().serializeToMiraiCode();
        if(content.contains("[mirai:at:2492921801]")){
            switch (groupMessageEvent.getMessage().contentToString()){
                case "@B73-NS" + "/掷骰子":
                    break;
                case "@B73-NS" + "/咩":
                    break;
                case "@B73-NS" + "/帮助":
                    break;
            }
        }
    }
}
