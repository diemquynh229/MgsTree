/**
 * 
 */
package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author quynhduong
 *
 */
public class MsgTree {

	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	public MsgTree root;
	/*
	 * Can use a static char idx to the tree string for recursive solution, but it
	 * is not strictly necessary
	 */
	private static int staticCharIdx = 0;
	public String encodingString;

	/**
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}

	/**
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {
		this.encodingString = encodingString;
		if (encodingString.charAt(staticCharIdx) != '^') {
			this.payloadChar = encodingString.charAt(staticCharIdx);
		} else {
			this.payloadChar = '^';
			staticCharIdx++;
			this.left = new MsgTree(encodingString);
			staticCharIdx++;
			this.right = new MsgTree(encodingString);
		}

	}

	/**
	 * Method to print characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("character     code");
		System.out.println("------------------");
		String result = "";
		MsgTree index = root;
		int k = 0;
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == '0') {
				index = index.left;
			} else {
				index = index.right;
			}
			if (index.payloadChar != '^') {
				System.out.println(index.payloadChar + "             " + code.substring(k, i + 1));
				result += index.payloadChar;
				k = i + 1;
				index = root;
			}

		}
		System.out.println();
		System.out.println("MESSAGE: ");
		System.out.print(result);
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan1 = new Scanner(System.in);

		System.out.println("Please enter file name to decode: ");

		String fileName = scan1.nextLine();

		if (fileName == null) {

			throw new FileNotFoundException("File name is null");

		}
		File file = new File(fileName);

		scan1.close();
		String line1 = "";
		String line2 = "";
		Scanner scan2 = new Scanner(file);

		while (scan2.hasNextLine()) {
			String line = scan2.nextLine();
			line = line.replaceAll("\\s", "");
			if (line.charAt(0) != '0' && line.charAt(0) != '1') {
				line1 += line;
			} else {
				line2 = line;

			}
		}

//			System.out.println(line1);
//			System.out.println(line2);

		MsgTree tree = new MsgTree(line1);
		tree.printCodes(tree, line2);
	}
}
