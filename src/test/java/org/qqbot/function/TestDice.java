package org.qqbot.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.qqbot.entity.DiceResultItem;

public class TestDice {
	@Test
	public void testDice() {
		DiceResultItem item = Dice.getRoll("1d2", "测试用", "123321");
		System.out.println(System.nanoTime());
		System.out.println(item);
	}
}
