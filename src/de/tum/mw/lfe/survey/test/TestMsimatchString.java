package de.tum.mw.lfe.survey.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.tum.mw.lfe.survey.utils.Utilities;

public class TestMsimatchString {

	@Test
	public void test1() {
		int count = Utilities.StringMismatchCounter("aaaaaaaaaaaaaaaaaaaaaaaaa", "a");
		assertEquals(24, count);
	}
	
	@Test
	public void test1_2() {
		int count = Utilities.StringMismatchCounter("aaaabaaaaaaaaaaaaaaaaaaaa", "a");
		assertEquals(24, count);
	}

	@Test
	public void test1_3() {
		int count = Utilities.StringMismatchCounter("aaaabaaaaaaaaaaaaaaaaaaaa", "aa");
		assertEquals(23, count);
	}
	
	@Test
	public void test1_4() {
		int count = Utilities.StringMismatchCounter("aaaabaaaaaaaaaaaaaaaaaaab", "a");
		assertEquals(24, count);
	}
	
	
	@Test
	public void test2() {
		int count = Utilities.StringMismatchCounter("aaaaaaaaaaaaaaaaaaaaaaaaa", "");
		assertEquals(25, count);
	}
	
	@Test
	public void test2_2() {
		int count = Utilities.StringMismatchCounter("aaaaabaaaaaaaaaaaaaaaaaaa", "");
		assertEquals(25, count);
	}
	
	@Test
	public void test3() {
		int count = Utilities.StringMismatchCounter("a", "aaaaaaaaaaaaaaaaaaaaaaaaa");
		assertEquals(24, count);
		}
	
	@Test
	public void test3_2() {
		int count = Utilities.StringMismatchCounter("a", "aaaaaaaabaaaaaaaaaaaaaaaa");
		assertEquals(24, count);
		}

	@Test
	public void test3_3() {
		int count = Utilities.StringMismatchCounter("aa", "aaaaaaaabaaaaaaaaaaaaaaaa");
		assertEquals(23, count);
		}
	
	@Test
	public void test4() {
		int count = Utilities.StringMismatchCounter("abcdefg", "abcdefg");
		assertEquals(0, count);
		}
	
	@Test
	public void test5() {
		int count = Utilities.StringMismatchCounter("abcdefg", "abcdfg");
		assertEquals(1, count);
		}
	@Test
	public void test5_2() {
		int count = Utilities.StringMismatchCounter("abcdefg", "abcddfg");
		assertEquals(2, count); // missing e + additional d
		}
	@Test
	public void test6() {
		int count = Utilities.StringMismatchCounter("abcdefg", "aabcdefg");
		assertEquals(1, count);
		}
	@Test
	public void test6_2() {
		int count = Utilities.StringMismatchCounter("abcdefgh", "aabcdefg");
		assertEquals(2, count);
		}
	@Test
	public void test7() {
		int count = Utilities.StringMismatchCounter("aabcdefg", "abcdefg");
		assertEquals(1, count);
		}

}
