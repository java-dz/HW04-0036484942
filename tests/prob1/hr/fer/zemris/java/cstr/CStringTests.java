package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class CStringTests {

	@Test(expected=IllegalArgumentException.class)
	public void testNullToFirstConstructor() {
		// must throw
		new CString(null, 0, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullToSecondConstructor() {
		// must throw
		new CString((char[]) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullToThirdConstructor() {
		// must throw
		new CString((CString) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullFromString() {
		// must throw
		CString.fromString(null);
	}
	
	@Test
	public void testLength() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		assertEquals(chars.length, cstring.length());
	}

	@Test(expected=Exception.class)
	public void testFirstConstructorMoreLengthThanLetters() {
		char[] chars = {'A', 'B', 'C'};
		// must throw
		new CString(chars, 0, 10);
	}

	@Test
	public void testFirstConstructorLessLengthThanOriginal() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars, 0, 2);
		assertEquals(2, cstring.length());
	}
	
	@Test
	public void testCharAt() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		assertEquals('A', cstring.charAt(0));
		assertEquals('B', cstring.charAt(1));
		assertEquals('C', cstring.charAt(2));
	}
	
	@Test
	public void testToCharArray() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		char[] cArray = cstring.toCharArray();
		
		assertEquals(chars[0], cArray[0]);
		assertEquals(chars[1], cArray[1]);
		assertEquals(chars[2], cArray[2]);
		assertEquals(chars[3], cArray[3]);
	}
	
	@Test
	public void testEmptyStringToCharArray() {
		// constructor must not care if the chars length is 0
		char[] chars = new char[0];
		new CString(chars);
		String s = "";
		CString.fromString(s);
	}
	
	@Test
	public void testToString() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		assertEquals(new String(chars), cstring.toString());
	}
	
	@Test
	public void testImmutableCString() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		String abcd = cstring.toString();
		
		chars[0] = 'Z';
		String zbcd = cstring.toString();
		
		assertEquals(abcd, zbcd);
	}
	
	@Test
	public void testIndexOf() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		assertEquals(0, cstring.indexOf('A'));
		assertEquals(1, cstring.indexOf('B'));
		assertEquals(2, cstring.indexOf('C'));
		
		assertEquals(-1, cstring.indexOf('Z'));
	}
	
	@Test
	public void testStartsWith() {
		String s = "Abra kadabra";
		CString cstring = CString.fromString(s);
		CString cstart = CString.fromString("Abra");
		assertEquals(true, cstring.startsWith(cstart));
	}
	
	@Test
	public void testEndsWith() {
		String s = "Abra kadabra";
		CString cstring = CString.fromString(s);
		CString cend = CString.fromString("kadabra");
		assertEquals(true, cstring.endsWith(cend));
	}
	
	@Test
	public void testContains() {
		CString cstring = CString.fromString("Abra kadabra");
		CString c1 = CString.fromString("Abra");
		CString c2 = CString.fromString("Abra ");
		CString c3 = CString.fromString("bra");
		CString c4 = CString.fromString(" k");
		CString c5 = CString.fromString("kadabra");
		CString c6 = CString.fromString("Abra kadabra");

		assertEquals(true, cstring.contains(c1));
		assertEquals(true, cstring.contains(c2));
		assertEquals(true, cstring.contains(c3));
		assertEquals(true, cstring.contains(c4));
		assertEquals(true, cstring.contains(c5));
		assertEquals(true, cstring.contains(c6));
	}
	
	@Test
	public void testNotContains() {
		CString cstring = CString.fromString("Abra kadabra");
		CString c1 = CString.fromString("ABRA");
		CString c2 = CString.fromString(" Abra");
		CString c3 = CString.fromString("abrakadabra");
		CString c4 = CString.fromString("Abra Abra");
		CString c5 = CString.fromString("kadabra kadabra");
		CString c6 = CString.fromString("brabra");

		assertEquals(false, cstring.contains(c1));
		assertEquals(false, cstring.contains(c2));
		assertEquals(false, cstring.contains(c3));
		assertEquals(false, cstring.contains(c4));
		assertEquals(false, cstring.contains(c5));
		assertEquals(false, cstring.contains(c6));
	}
	
	@Test
	public void testEmptyStartsWith() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		CString empty = CString.fromString("");
		
		assertEquals(true, cstring.startsWith(empty));
	}
	
	@Test
	public void testEmptyEndsWith() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		CString empty = CString.fromString("");
		
		assertEquals(true, cstring.endsWith(empty));
	}
	
	@Test
	public void testEmptyContains() {
		char[] chars = {'A', 'B', 'C'};
		CString cstring = new CString(chars);
		CString empty = CString.fromString("");
		
		assertEquals(true, cstring.contains(empty));
	}
	
	@Test
	public void testSubstring() {
		CString cstring = CString.fromString("Abra kadabra");
		assertEquals("Abra", cstring.substring(0, 4).toString());
		assertEquals(" ", cstring.substring(4, 5).toString());
		assertEquals("kadabra", cstring.substring(5, 12).toString());
		assertEquals("", cstring.substring(12, 12).toString());
	}
	
	@Test
	public void testSubstringWithEndIndexGreaterThanLength() {
		CString abc = CString.fromString("ABC");
		CString c = abc.substring(2, 3);
		
		assertEquals("C", c.toString());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testSubstringNegativeArguments() {
		// must throw
		CString.fromString("Abra kadabra").substring(-1, -3);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testSubstringStartGreaterThanEndArguments() {
		// must throw
		CString.fromString("Abra kadabra").substring(2, 1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testSubstringEndTooBigArgument() {
		// must throw
		CString.fromString("Abra kadabra").substring(2, 16);
	}
	
	@Test
	public void testLeft() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		CString cnew = cstring.left(2);
		
		assertEquals(2, cnew.length());
		assertEquals('A', cnew.charAt(0));
		assertEquals('B', cnew.charAt(1));
	}
	
	@Test
	public void testLeftN0() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		CString cnew = cstring.left(0);
		
		assertEquals(0, cnew.length());
		assertEquals("", cnew.toString());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testLeftNMinus5() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		// must throw
		cstring.left(-5);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testLeftTooMuch() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		// must throw
		cstring.left(5);
	}
	
	@Test
	public void testRight() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		CString cnew = cstring.right(2);
		
		assertEquals(2, cnew.length());
		assertEquals('C', cnew.charAt(0));
		assertEquals('D', cnew.charAt(1));
	}
	
	@Test
	public void testRightN0() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		CString cnew = cstring.right(0);
		
		assertEquals(0, cnew.length());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRightNMinus5() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		// must throw
		cstring.right(-5);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRightTooMuch() {
		char[] chars = {'A', 'B', 'C', 'D'};
		CString cstring = new CString(chars);
		// must throw
		cstring.right(5);
	}
	
	@Test
	public void testAdd() {
		CString cstring1 = CString.fromString("Abra");
		CString cstring2 = CString.fromString(" kadabra");
		CString added = cstring1.add(cstring2);
		
		assertEquals("Abra kadabra", added.toString());
		assertNotEquals(cstring1, added);
	}
	
	@Test
	public void testAddEmpty() {
		CString cstring1 = CString.fromString("Abra");
		CString cstring2 = CString.fromString("");
		CString added = cstring1.add(cstring2);
		
		assertEquals(cstring1.toString(), added.toString());
	}
	
	@Test
	public void testReplaceAllChar() {
		CString cstring = CString.fromString("Abra kadabra");
		CString replaced = cstring.replaceAll('a', 'Z');
		
		assertEquals("AbrZ kZdZbrZ", replaced.toString());
	}
	
	@Test
	public void testReplaceAllSameChar() {
		CString cstring = CString.fromString("Abra kadabra");
		CString replaced = cstring.replaceAll('a', 'a');
		
		assertEquals(cstring.toString(), replaced.toString());
	}

	@Test
	public void testReplaceAllString() {
		CString cstring = CString.fromString("Abra kadabra");
		CString replaced1 = cstring.replaceAll(CString.fromString("Abra"), CString.fromString("kadabra"));
		CString replaced2 = cstring.replaceAll(CString.fromString("bra"), CString.fromString("ZZZ"));
		
		assertEquals("kadabra kadabra", replaced1.toString());
		assertEquals("AZZZ kadaZZZ", replaced2.toString());
	}
	
	@Test
	public void testReplaceAllStringAbabab() {
		CString cstring = CString.fromString("ababab");
		CString replaced1 = cstring.replaceAll(CString.fromString("ab"), CString.fromString("abab"));
		CString replaced2 = cstring.replaceAll(CString.fromString("abab"), CString.fromString("ab"));
		
		assertEquals("abababababab", replaced1.toString());
		assertEquals("abab", replaced2.toString());
	}
	
	@Test
	public void testReplaceAllStringSomethingWithEmpty() {
		CString cstring = CString.fromString("Abra kadabra");
		CString replaced = cstring.replaceAll(CString.fromString("bra"), CString.fromString(""));
		
		assertEquals("A kada", replaced.toString());
	}
	
	@Test
	public void testReplaceAllStringEmptyWithSomething() {
		CString cstring = CString.fromString("123 456");
		CString replaced = cstring.replaceAll(CString.fromString(""), CString.fromString("abc"));
		
		assertEquals("abc1abc2abc3abc abc4abc5abc6abc", replaced.toString());
	}
	
//	@Test
	public void testFrano() {
		String novi = "franoseffranobossfranozmaj";
		CString s = new CString(novi.toCharArray());
		
		String frano = "frano";
		String caleta ="caleta";
		
		CString fra = new CString(frano.toCharArray());
		CString cal = new CString(caleta.toCharArray());
		
		CString lala = s.replaceAll(fra, cal);
		assertEquals("caletasefcaletabosscaletazmaj", lala.toString());
	}
}
