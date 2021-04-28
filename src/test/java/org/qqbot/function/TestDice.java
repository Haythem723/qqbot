package org.qqbot.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDice {
	@Test
	public void testDice() {
		String roll = Dice.getRoll("1d2", "测试用", "123321");
		System.out.println(System.nanoTime());
		System.out.println(roll);
	}
}
