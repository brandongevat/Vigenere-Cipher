
/*===========================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Brandon Gevat
| Language: Java
|
| To Compile: javac pa01.java
|
| To Execute: java -> java pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Spring 2022
| Instructor: McAlpin
| Due Date: 02-27-2022
|
+=============================================================================*/
import java.io.*;
import java.util.*;

class pa01 {

	// E(k, p) = (k(ch) + p(ch)) mod 26
	public static Text encrypt(Text pt, Text k) {
		Text ct = new Text();
		int i = 0, a, b, c;

		while (i < ct.maxLen) {
			a = pt.arr[i];
			b = k.arr[i];
			c = (((a - 97) + (b - 97)) % 26) + 97;
			ct.arr[i] = (char) c;
			i++;
		}

		return ct;
	}

	// D(k, c) = (c(ch) - k(ch)) mod 26
	public static void displayText(Text text) {
		for (int i = 0; i < text.maxLen; i++) {
			if (i % 80 == 0) {
				System.out.println();
			}
			if (text.arr[i] == '\u0000')
				break;
			System.out.print(text.arr[i]);
		}
		System.out.println();
	}

	// Sets up given Vigenere Cipher.
	public static void test(String kFilename, String ptFilename) throws FileNotFoundException {
		Text k = new Text();
		Text pt = new Text();

		k.readText(kFilename);
		pt.readText(ptFilename);

		System.out.println("\n\nVigenere Key:");
		displayText(k);

		k.padKey();
		pt.padPlaintext();

		Text ct = encrypt(pt, k);

		System.out.println("\n\nPlaintext:");
		displayText(pt);

		System.out.println("\n\nCiphertext:");
		displayText(ct);

	}

	public static void main(String[] args) throws FileNotFoundException {
		test(args[0], args[1]);
	}

	public static class Text {
		char[] arr;
		int len, maxLen = 512;

		public Text() {
			this.arr = new char[512];
			this.len = 0;
		}

		// Pads the given plaintext with x, repeating until the plaintext array is full.
		public void padPlaintext() {
			while (len < maxLen) {
				arr[this.len] = 'x';
				this.len++;
			}
		}

		// Pads the given key with itself, repeating until the key array is full.
		public void padKey() {
			int i = 0;
			while (this.len < this.maxLen) {
				this.arr[this.len] = this.arr[i];
				i++;
				this.len++;
				// System.out.println("i: " + i + "len: " + len);
			}
		}

		// Reads in given text.
		public void readText(String filename) throws FileNotFoundException {
			File file = new File(filename);
			Scanner sc = new Scanner(file);

			char curr;
			int currInt;
			String currLine;

			while (sc.hasNext()) {
				currLine = sc.nextLine();
				for (int i = 0; i < currLine.length(); i++) {
					if (this.len == this.maxLen)
						break;
					curr = currLine.charAt(i);
					currInt = curr;

					if (currInt >= 65 && currInt <= 90) {
						this.arr[this.len] = Character.toLowerCase(curr);
						this.len++;
					}

					if (currInt >= 97 && currInt <= 122) {
						this.arr[this.len] = curr;
						this.len++;
					}
				}
			}

			sc.close();
		}
	}
}
