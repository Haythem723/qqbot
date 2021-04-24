package org.qqbot.core;

import org.junit.jupiter.api.Test;

public class TestParseInt {

	@Test
	public void testParseInt() {
		String s = "aaa";
		Integer i = Integer.valueOf(s);
		System.out.println(i);
	}
}
