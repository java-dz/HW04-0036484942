package hr.fer.zemris.java.cstr;

/**
 * The <tt>CString</tt> class represents character strings. CString values
 * cannot be changed after they are created. String buffers support mutable
 * strings. Because CString objects are immutable they can be shared. For
 * example:
 * <blockquote><pre>
 *     CString str = CString.fromString("abc");
 * </pre></blockquote><p>
 * is equivalent to:
 * <blockquote><pre>
 *     char data[] = {'a', 'b', 'c'};
 *     CString str = new CString(data);
 * </pre></blockquote><p>
 * While all public <tt>CString</tt> constructors create a copy of the given
 * character array, in order to prevent string mutability, most methods reuse
 * the character array of the CString the method is performed upon.
 * <p>
 * The class <tt>CString</tt> includes methods for examining individual
 * characters of the sequence, for searching CStrings, for extracting substrings,
 * getting a new CString with characters or sequence of characters replaced.
 * <p>
 * String searching consists of:
 * <ul>
 * <li>getting a character at a specified index,
 * <li>getting the index of a character starting from the first index,
 * <li>getting the index of a character starting from the specified index,
 * <li>getting the index of a substring starting from the specified index,
 * <li>checking if the CString starts with a specified character sequence,
 * <li>checking if the CString ends with a specified character sequence,
 * <li>checking if the CString contains a specified character sequence and
 * <li>checking if the CString is empty.
 * </ul>
 * <p>
 * Extracting substrings consists of:
 * <ul>
 * <li>getting a substring of characters from starting index to end index,
 * where the end index is not included
 * <li>getting the first <tt>n</tt>, or <tt>n</tt> <i>left</i> characters
 * <li>getting the last <tt>n</tt>, or <tt>n</tt> <i>right</i> characters
 * </ul>
 * <p>
 * There are various CString operations, some of which are in the example below:
 * <blockquote><pre>
 *     CString abc = CString.fromString("abc");
 *     System.out.println(abc);
 *     CString cde = CString.fromString("cde");
 *     System.out.println(abc.add(cde));
 *     CString c = abc.substring(2, 3);
 *     CString d = cde.substring(1, 2);
 *     CString aab = abc.replaceAll('b', 'a');
 *     CString a = abc.replaceAll(
 *             CString.fromString("bc"), CString.fromString(""));
 * </pre></blockquote><p>
 *
 * @author Mario Bobic
 */
public class CString {

	/** The value is used for character storage. */
	private final char data[];
	/**
	 * Offset of the character array that make up this CString instance. At the
	 * same time this is the begin index of this CString instance.
	 */
	private final int offset;
	/** Number of characters that make up this CString instance. */
	private final int length;
	/**
	 * End index of the character array that make up this CString instance. This
	 * is a cached value for <tt>offset+length-1</tt>. May be negative if there
	 * are no characters of this CString instance.
	 */
	private final int endIndex;

	/**
	 * Constructs an instance of CString by creating a new internal copy of the
	 * given array, with the given offset and length that is guaranteed not to
	 * be altered in any unwanted way.
	 * <p>
	 * If the <tt>data</tt> parameter is <tt>null</tt>, an
	 * {@linkplain IllegalArgumentException} is thrown. If the specified length
	 * is negative or is greater than the length of the character data array,
	 * the {@linkplain StringIndexOutOfBoundsException} is thrown. Also, if the
	 * specified offset is negative, or offset + length are greater than the
	 * length of the array, {@linkplain StringIndexOutOfBoundsException} is thrown.
	 * 
	 * @param data character array to be copied and set for this CString
	 * @param offset offset of the character array that make up this instance
	 * @param length number of characters that make up this instance
	 * @throws IllegalArgumentException if <tt>data</tt> is <tt>null</tt>
	 * @throws StringIndexOutOfBoundsException if offset and/or length are invalid
	 */
	public CString(char[] data, int offset, int length) {
		checkArgument(data);
		if (offset < 0) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (length < 0) {
			throw new StringIndexOutOfBoundsException(length);
		}
		if (data.length < offset + length) {
			throw new StringIndexOutOfBoundsException(offset + length);
		}
		this.data = copyOfArray(data, offset, length);
		this.offset = 0;
		this.length = length;
		this.endIndex = offset+length-1;
	}
	
	/**
	 * Constructs an instance of CString by creating a new internal copy of the
	 * given array, with the length of this CString equal to the length of the
	 * array, that is guaranteed not to be altered in any unwanted way.
	 * <p>
	 * If the <tt>data</tt> parameter is <tt>null</tt>, an
	 * {@linkplain IllegalArgumentException} is thrown.
	 * 
	 * @param data character array to be copied and set for this CString
	 * @throws IllegalArgumentException if <tt>data</tt> is <tt>null</tt>
	 */
	public CString(char[] data) {
		this(checkArgument(data), 0, data.length);
	}
	
	/**
	 * Constructs an instance of CString depending on the given
	 * <tt>original</tt> CString:
	 * <ul>
	 * <li>if the internal character array of the original CString is larger
	 * than needed, the new instance allocates its own character array of
	 * minimal required size and copies data
	 * <li>otherwise it reuses the character array of the original CString
	 * </ul><p>
	 * If the <tt>original</tt> parameter is <tt>null</tt>, an
	 * {@linkplain IllegalArgumentException} is thrown.
	 * 
	 * @param original the original CString that is used for this creation
	 * @throws IllegalArgumentException if <tt>original</tt> is <tt>null</tt>
	 */
	public CString(CString original) {
		checkArgument(original);

		offset = 0;
		length = original.length;
		endIndex = offset+length-1;

		if (original.length == original.data.length) {
			data = original.data;
		} else {
			data = original.toCharArray();
		}
	}
	
	/**
	 * Constructs an instance of CString by <i>redirecting</i> the character
	 * data array to this instance. By using this constructor it is guaranteed
	 * that no one will be able to change contents of the <tt>data</tt> array
	 * after the CString has been initialized.
	 * 
	 * @param offset offset of the character array that make up this instance
	 * @param length number of characters that make up this instance
	 * @param data character array that is guaranteed not to be changed
	 */
	private CString(int offset, int length, char[] data) {
		this.data = data;
		this.offset = offset;
		this.length = length;
		this.endIndex = offset+length-1;
	}
	
	/**
	 * Returns a new instance of CString created from the given
	 * {@linkplain String} instance by calling the
	 * {@linkplain String#toCharArray()} method. This method throws an exception
	 * in case the given string <tt>s</tt> is a <tt>null</tt> reference.
	 * 
	 * @param s string to be converted to a CString
	 * @return a new instance of CString created from the string <tt>s</tt>
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public static CString fromString(String s) {
		checkArgument(s);
		return new CString(s.toCharArray());
	}
	
	/**
	 * Returns the length of this CString. The length is equal to the number of
	 * characters representing this CString.
	 * 
	 * @return the length of this CString
	 */
	public int length() {
		return length;
	}
	
	/**
	 * Returns the <tt>char</tt> value at the specified <tt>index</tt>. An index
	 * ranges from <tt>0</tt> to <tt>length() - 1</tt>. The first <tt>char</tt>
	 * value of the sequence is at index <tt>0</tt>, the next at index
	 * <tt>1</tt>, and so on, just like array indexing.
	 * <p>
	 * This method throws {@linkplain StringIndexOutOfBoundsException} if the
	 * <tt>index</tt> argument is negative or greater than or equal to the
	 * length of this CString.
	 * 
	 * @param index index of the <tt>char</tt> value to be returned
	 * @return the <tt>char</tt> value at the specified index of this string
	 * @throws StringIndexOutOfBoundsException if the index is not within CString
	 */
	public char charAt(int index) {
		if ((index < 0) || (index >= length)) {
            throw new StringIndexOutOfBoundsException(index);
        }
		return data[index+offset];
	}
	
	/**
	 * Converts this string to a new character array and returns it. The
	 * returned character array is safe to be altered, as this string does not
	 * return its internal character array but its copy with the length equal to
	 * the number of characters in this CString.
	 * 
	 * @return this string converted to a new character array
	 */
	public char[] toCharArray() {
		return copyOfArray(data, offset, length);
	}
	
	/**
	 * Returns a string representation of this CString.
	 * 
	 * @return a string representation of this CString
	 */
	public String toString() {
		return new String(data, offset, length);
	}
	
	/**
	 * Returns the index within this CString of the first occurrence of the
	 * specified character. If a character with value <tt>c</tt> occurs in the
	 * character sequence represented by this <tt>CString</tt> object, then the
	 * index of the first such occurrence is returned. If no such character
	 * occurs in this CString, then <tt>-1</tt> is returned.
	 *
	 * @param c character whose index is to be returned
	 * @return the index of the first occurrence of the character in the
	 *         character sequence represented by this object, or <tt>-1</tt> if
	 *         the character does not occur.
	 */
	public int indexOf(char c) {
		for (int i = offset; i <= endIndex; i++) {
			if (data[i] == c) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the index within this CString of the first occurrence of the
	 * specified substring, starting from the beginning. If no such substring
	 * occurs in this CString, then <tt>-1</tt> is returned.
	 * <p>
	 * If the CString parameter <tt>s</tt> is <tt>null</tt>, an
	 * {@linkplain IllegalArgumentException} is thrown.
	 * <p>
	 * If the given CString's length is greater than this CString's length,
	 * <tt>-1</tt> is returned.
	 * 
	 * @param s substring whose index is to be returned
	 * @return the index of the first occurrence of the substring in the
	 *         character sequence represented by this object, or <tt>-1</tt> if
	 *         the substring does not occur.
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public int indexOf(CString s) {
		return indexOf(s, 0);
	}
	
	/**
	 * Returns the index within this CString of the first occurrence of the
	 * specified substring, starting at the specified index. If no such
	 * substring occurs in this CString, then <tt>-1</tt> is returned.
	 * <p>
	 * If the CString parameter <tt>s</tt> is <tt>null</tt>, an
	 * {@linkplain IllegalArgumentException} is thrown. If the specified index
	 * is negative or is greater than or equal to the length of this CString,
	 * the {@linkplain StringIndexOutOfBoundsException} is thrown.
	 * <p>
	 * If the given CString is determined empty by calling the
	 * {@link CString#isEmpty() s.isEmpty()} method, <tt>fromIndex</tt> is
	 * returned. In case the specified <tt>fromIndex</tt> is negative, 0 is
	 * returned because the first occurrence of an empty string in any string
	 * is always at index 0.
	 * <p>
	 * If the given CString's length is greater than this CString's length,
	 * <tt>-1</tt> is returned.
	 * 
	 * @param s substring whose index is to be returned
	 * @param fromIndex the index from which to start the search
	 * @return the index of the first occurrence of the substring in the
	 *         character sequence represented by this object, or <tt>-1</tt> if
	 *         the substring does not occur.
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 * @throws StringIndexOutOfBoundsException
	 *             if the specified index is negative or out of bounds
	 */
	public int indexOf(CString s, int fromIndex) {
		checkArgument(s);
		if (s.length > length) {
			return -1;
		}
		if (fromIndex >= length) {
			throw new StringIndexOutOfBoundsException(fromIndex);
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (s.isEmpty()) {
			return fromIndex;
		}
		
		int j = s.offset;
		for (int i = fromIndex+offset; i <= endIndex; i++) {
			if (s.data[j] == data[i]) {
				j++;
				if (j == s.length) {
					return i-s.length+1;
				}
			} else {
				j = s.offset;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns true if this CString starts with the specified CString. False
	 * otherwise. This method throws an {@linkplain IllegalArgumentException} if
	 * the CString parameter <tt>s</tt> is <tt>null</tt>.
	 * 
	 * @param s the CString that is tested if this CString starts with
	 * @return true if this CString starts with the specified CString
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public boolean startsWith(CString s) {
		checkArgument(s);
		if (s.length > length) {
			return false;
		}
		
		for (int i = 0; i < s.length; i++) {
			if (data[i+offset] != s.data[i+s.offset]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns true if this CString ends with the specified CString. False
	 * otherwise. This method throws an {@linkplain IllegalArgumentException} if
	 * the CString parameter <tt>s</tt> is <tt>null</tt>.
	 * 
	 * @param s the CString that is tested if this CString ends with
	 * @return true if this CString ends with the specified CString
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public boolean endsWith(CString s) {
		checkArgument(s);
		if (s.length > length) {
			return false;
		}
		
		for (int i = 0; i < s.length; i++) {
			if (data[endIndex-i] != s.data[s.endIndex-i]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns true if this CString contains the specified CString. False
	 * otherwise. This method throws an {@linkplain IllegalArgumentException} if
	 * the CString parameter <tt>s</tt> is <tt>null</tt>.
	 * 
	 * @param s the CString that is tested if this CString contains
	 * @return true if this CString contains the specified CString
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public boolean contains(CString s) {
		return indexOf(s) > -1;
	}
	
	/**
	 * Returns <tt>true</tt> if, and only if, {@link #length()} is <tt>0</tt>.
	 * False otherwise.
	 *
	 * @return <tt>true</tt> if {@link #length()} is <tt>0</tt>.
	 */
	public boolean isEmpty() {
		return length == 0;
	}
	
	/**
	 * Returns a new CString which represents a part of the original CString.
	 * The substring begins at the specified <tt>beginIndex</tt> and extends to
	 * the character at index <tt>endIndex - 1</tt>. Thus the length of the
	 * substring is <tt>endIndex-beginIndex</tt>.
	 * <p>
	 * The complexity of this method is <tt>O(1)</tt>.
	 * <p>
	 * This method throws {@linkplain StringIndexOutOfBoundsException} if
	 * <tt>startIndex</tt> is negative, <tt>endIndex</tt> is greater than the
	 * length of this CString, or if the new length of the substring would be
	 * negative.
	 * <p>
	 * Examples:
	 * <blockquote><pre>
	 * CString.fromString("laptop").substring(3, 6) returns "top"
	 * CString.fromString("stroke").substring(3, 5) returns "ok"
	 * </pre></blockquote>
	 *
	 * @param startIndex the starting index, inclusive
	 * @param endIndex the ending index, exclusive
	 * @return the specified substring
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
            throw new StringIndexOutOfBoundsException(startIndex);
        }
        if (endIndex > length) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int newLength = endIndex - startIndex;
        if (newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        }
		
        if ((startIndex == 0) && (endIndex == length)) {
        	return this;
        } else {
        	return new CString(offset + startIndex, newLength, data);
        }
	}
	
	/**
	 * Returns a {@link #substring} of the first <tt>n</tt>, or <tt>n</tt>
	 * <i>left</i> characters of this CString. If the given number of characters
	 * is negative or greater than the length of the string, the
	 * {@linkplain StringIndexOutOfBoundsException} is thrown.
	 * 
	 * @param n number of first characters to be returned as a substring
	 * @return a substring of the first <tt>n</tt> characters
	 * @throws StringIndexOutOfBoundsException
	 *             if <tt>n &lt; 0</tt> or <tt>n &gt; this.length()</tt>
	 */
	public CString left(int n) {
		if (n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return substring(0, n);
	}
	
	/**
	 * Returns a {@link #substring} of the last <tt>n</tt>, or <tt>n</tt>
	 * <i>right</i> characters of this CString. If the given number of
	 * characters is negative or greater than the length of the string, the
	 * {@linkplain StringIndexOutOfBoundsException} is thrown.
	 * 
	 * @param n number of last characters to be returned as a substring
	 * @return a substring of the last <tt>n</tt> characters
	 * @throws StringIndexOutOfBoundsException
	 *             if <tt>n &lt; 0</tt> or <tt>n &gt; this.length()</tt>
	 */
	public CString right(int n) {
		if (n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return substring(length-n, length);
	}
	
	/**
	 * Concatenates the specified string to the end of this string.
	 * <p>
	 * If the length of the argument string is <tt>0</tt>, then this
	 * <tt>CString</tt> object is returned. Otherwise, a new <tt>CString</tt>
	 * object is returned that represents a character sequence that is the
	 * concatenation of the character sequence represented by this
	 * <tt>CString</tt> object and the character sequence represented by the
	 * argument CString.
	 * <p>
	 * This method throws an {@linkplain IllegalArgumentException} if the
	 * given parameter <tt>s</tt> is <tt>null</tt>.
	 *
	 * @param s the CString that is concatenated to the end this CString
	 * @return a string that represents the concatenation of this object's
	 *         characters followed by the string argument's characters
	 * @throws IllegalArgumentException if <tt>s</tt> is <tt>null</tt>
	 */
	public CString add(CString s) {
		checkArgument(s);
		if (s.isEmpty()) {
			return this;
		}
		
		char[] expandedData = copyOfArray(data, offset, length+s.length);
		System.arraycopy(s.data, s.offset, expandedData, length, s.length);
		
		return new CString(0, expandedData.length, expandedData);
	}
	
	/**
	 * Returns a string resulting from replacing all occurrences of
	 * <tt>oldChar</tt> in this string with <tt>newChar</tt>.
	 * <p>
	 * If the character <tt>oldChar</tt> is the same as the character
	 * <tt>newChar</tt>, then a reference to this <tt>CString</tt> object is
	 * returned. Otherwise, a <tt>CString</tt> object is returned that
	 * represents a character sequence identical to the character sequence
	 * represented by this <tt>CString</tt> object, except that every occurrence
	 * of <tt>oldChar</tt> is replaced by an occurrence of <tt>newChar</tt>.
	 * 
	 * @param oldChar the old character
	 * @param newChar the new character
	 * @return a string derived from this string by replacing every
     *         occurrence of <tt>oldChar</tt> with <tt>newChar</tt>
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (oldChar == newChar) {
			return this;
		}
		
		char[] newInstance = this.toCharArray();
		
		for (int i = 0; i < newInstance.length; i++) {
			if (newInstance[i] == oldChar) {
				newInstance[i] = newChar;
			}
		}
		
		return new CString(0, newInstance.length, newInstance);
	}
	
	/**
	 * Returns a string resulting from replacing all occurrences of
	 * <tt>oldStr</tt> in this string with <tt>newStr</tt>.
	 * <p>
	 * If the character <tt>oldStr</tt> is the same as the CString
	 * <tt>newStr</tt>, then a reference to this <tt>CString</tt> object is
	 * returned. Otherwise, by the time this method ends, a <tt>CString</tt>
	 * object is returned that represents a character sequence identical to the
	 * character sequence represented by this <tt>CString</tt> object, except
	 * that every occurrence of <tt>oldStr</tt> is replaced by an occurrence of
	 * <tt>newStr</tt>.
	 * <p>
	 * This method throws an {@linkplain IllegalArgumentException} if either of
	 * the given parameters <tt>oldStr</tt> and <tt>newStr</tt> are <tt>null</tt>.
	 * 
	 * @param oldStr the old CString
	 * @param newStr the new CString
	 * @return a string derived from this string by replacing every occurrence
	 *         of <tt>oldStr</tt> with <tt>newStr</tt>
	 * @throws IllegalArgumentException
	 *             if either <tt>oldStr</tt> or <tt>newStr</tt> is <tt>null</tt>
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		checkArgument(oldStr); checkArgument(newStr);
		
		/* If oldStr.equals(newStr). */
		if (oldStr.contains(newStr) && newStr.contains(oldStr)) {
			return this;
		}
		
		/* Empty oldStr needs special case replacing. */
		boolean isEmpty = false;
		if (oldStr.isEmpty()) {
			isEmpty = true;
		}
		
		CString newInstance = this;
		
		int lastIndex = -1;
		for (int i = 0; i < newInstance.length; i++) {
			lastIndex = newInstance.indexOf(oldStr, lastIndex);
			if (lastIndex >= i) {
				newInstance = replaceAndShift(newStr, newInstance, lastIndex, lastIndex+oldStr.length);
				lastIndex += newStr.length;
				if (isEmpty) lastIndex += 1;
				i = lastIndex-1;
			} else {
				return newInstance;
			}
		}
		
		/* Add newStr to the last place of newInstance. */
		if (isEmpty) {
			newInstance = replaceAndShift(newStr, newInstance, lastIndex, lastIndex);
		}
		
		return newInstance;
	}

	/**
	 * Returns a new array with the character sequence of <tt>source</tt>
	 * starting from the <tt>fromIndex</tt> (inclusive) and ending on
	 * <tt>toIndex</tt> (exclusive) replaced with the <tt>destination</tt>
	 * character sequence.
	 * <p>
	 * This method operates in XXXXXXXX simple steps:
	 * <ol>
	 * <li>allocates a new character array containing the <tt>destination</tt>
	 * character sequence with enough room to fit the <tt>source</tt> character
	 * sequence
	 * <li>shifts the newly created character array to the right starting from
	 * the <tt>toIndex</tt>.
	 * <li>copies the contents of the <tt>source</tt> to <tt>destination</tt>
	 * starting from the <tt>fromIndex</tt> (inclusive) and ending on
	 * <tt>toIndex</tt> (exclusive)
	 * </ol>
	 * 
	 * @param source source CString to be inserted and replaced to destination
	 * @param destination destination CString where to insert the source
	 * @param fromIndex starting index of the replacement, inclusive
	 * @param toIndex ending index of the replacement, exclusive
	 * @return a new array with the character sequence of <tt>source</tt>
	 *         starting from the <tt>fromIndex</tt> (inclusive) and ending on
	 *         <tt>toIndex</tt> (exclusive) replaced with the
	 *         <tt>destination</tt> character sequence
	 */
	private CString replaceAndShift(CString source, CString destination, int fromIndex, int toIndex) {
		char[] src = source.toCharArray();
		char[] dest = destination.toCharArray();
		
		// variables for expanding the destination array
		int lengthToReplace = toIndex-fromIndex;
		int extraSpace = src.length - lengthToReplace;
		// move the previous data that should not be replaced to the right
		dest = shiftRight(dest, toIndex, extraSpace);
		// copy src characters to dest to the freed room
		System.arraycopy(src, 0, dest, fromIndex, src.length);
		
		return new CString(0, dest.length, dest);
	}
	
	/**
	 * Checks if the argument is <tt>null</tt> and throws an
	 * {@linkplain IllegalArgumentException} if the test returns true.
	 * If checking is successful, the same object is returned.
	 * <p>
	 * This method is used by a variety of other methods in this class to
	 * check if the passed argument is a valid argument.
	 * 
	 * @param <T> the object type
	 * @param arg argument to be checked if it is a <tt>null</tt> reference
	 * @return the object given as argument, if not <tt>null</tt>
	 * @throws IllegalArgumentException if the argument is <tt>null</tt>
	 */
	private static <T> T checkArgument(T arg) {
		if (arg == null) {
			throw new IllegalArgumentException("Argument must not be null.");
		}
		return arg;
	}
	
	/**
	 * Copies the specified array of characters, truncating or padding with
	 * zeros (if necessary) so the copy has the specified length. For all
	 * indices that are valid in both the original array and the copy, the two
	 * arrays will contain identical values.
	 * <p>
	 * This method throws <tt>NegativeArraySizeException</tt> if
	 * <tt>newLength</tt> is negative, or <tt>NullPointerException</tt> if
	 * <tt>original</tt> is <tt>null</tt>.
	 * 
	 * @param original the array to be copied
	 * @param offset offset from where to start copying
	 * @param newLength the length of the copy to be returned
	 * @return a copy of the original array
	 * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
	 * @throws NullPointerException if <tt>original</tt> is null
	 */
	private char[] copyOfArray(char[] original, int offset, int newLength) {
		char[] newArr = new char[newLength];

		int min = Math.min(original.length, newLength);
		System.arraycopy(original, offset, newArr, 0, min);
		
		return newArr;
	}
	
	/**
	 * Creates a new array and shifts the given character array to the right
	 * <tt>amount</tt> of times. The shifting starts at the end of the given
	 * array (length-1) and ends at the specified index. Before shifting, the
	 * character array is expanded by <tt>amount</tt> to fit all elements to the
	 * array. This method finally erases the elements from the specified
	 * <tt>index</tt> to <tt>index+amount-1</tt> by overwriting 0 to these
	 * positions.
	 * 
	 * @param arr array to be shifted
	 * @param index index where to end shifting
	 * @param amount amount of characters to be shifted to the right
	 * @return the shifted array
	 */
	private char[] shiftRight(char[] arr, int index, int amount) {
		char[] newArr = copyOfArray(arr, 0, arr.length+amount);
		
		for (int i = arr.length-1; i >= index; i--) {
			newArr[i+amount] = arr[i];
		}
		/* Erase the shifted elements' duplicates. */
		for (int i = index; i < index+amount; i++) {
			newArr[i] = 0;
		}
		
		return newArr;
	}

}
