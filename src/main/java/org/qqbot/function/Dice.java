package org.qqbot.function;

import java.util.Random;

public class Dice {
    private static int roll(int time, int face){
        Random random = new Random();
        Long seed = System.nanoTime();
        random.setSeed(seed);
        int sum = 0;
        for(int i = 0; i< time; i++){
            int res = random.nextInt(face) + 1;
            sum += res;

            seed = System.nanoTime();
            random.setSeed(seed);
        }
        return sum;
    }

    public static String getRoll(String s){ //可接受输入：1d10、1D10
        String temp = s.toLowerCase();
        try{
            int time = Integer.parseInt(temp.substring(0, temp.indexOf("d")));
            int face = Integer.parseInt(temp.substring(temp.indexOf("d") + 1));
            int res = roll(time, face);
            return s + " = " + res;// 输出结果eg: 1d10 = 2
        }catch (NumberFormatException | StringIndexOutOfBoundsException e){
            return "非法输入";//用户的锅
        }catch (Exception e){
            return "Roll模块出错，请联系管理员";//我的锅
        }
    }
}
