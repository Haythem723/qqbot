package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.constant.ConstantJoke;
import org.qqbot.entity.Command;
import org.qqbot.entity.JokeLibItem;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.mybatis.ImpJokeMapper;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.Random;

public class CommandJoke implements CommandInvoker{
        @Override
        public Promise invoke(MessageEvent event, Command command) {
            ArrayList<String> args = command.getArgs();
            if(args.size() == 0){
                int id = new Random(System.nanoTime()).nextInt(ConstantJoke.MAXIMUM_JOKE_LIB + 1);
                JokeLibItem jokeLibItem = new ImpJokeMapper().getJoke(String.valueOf(id));
                if(jokeLibItem != null){
                    String res = jokeLibItem.toString();
                    return new SimplePromise<String>(result ->{
                        MiraiMain.getInstance().quickReply(event, result);
                    }).resolve(res);
                }
            }
            return null;
        }
}
