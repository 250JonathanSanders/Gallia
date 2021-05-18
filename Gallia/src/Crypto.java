/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Name: Crypto.java
Author: Jonathan Sanders
Date: 17.05.21
Purpose: Encrypt and decrypt
text

Notes: strChar and strChar2 are
used because the primitive char
class can not be added (char + char)
whereas string can (string + string)
to result in a string. Char addition
will only give an int.
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/


public class Crypto {
	public String Encrypt(String input){

		//Loop length, two character int value numbers
		int intLength = 0, intChar = 0, intChar2 = 0, intActive = 0;
		//Encrypted string, the characters in string form
		String strEncrypt = "", strChar = "", strChar2 = "";
		
		char chrActive = ' ';
		
		intLength = input.length();
		//Actual encryption loop
		for(int i = 0; i < intLength; i++){
			//Random character
			intChar = (int) ((122 - 65) * Math.random() + 65) + 10;
			//Cant be strChar = (char) intChar, since strChar is string.
			strChar += (char) intChar;
			//Same thing as above
			intChar2 = (int) ((122 - 65) * Math.random() + 65) + 10;
			strChar2 += (char) intChar2;
			
			//get active char and shift it up 10
			intActive = (int) input.charAt(i) + 10;
			chrActive = (char) intActive;
			
			strEncrypt += strChar + strChar2 + chrActive;
			
			//Since strChar and strChar2 use +=, they must be reset.
			strChar = "";
			strChar2 = "";
		}
		return(strEncrypt);
	}
	
	public String Decrypt(String input){
		int intLength = 0;
		String strDecrypt = "";
		
		intLength = input.length();
		//Loop to read every third character in the string and shift down by 10
		for (int i = 2; i < intLength; i+= 3){
			strDecrypt += (char) ((int) input.charAt(i) - 10);
		}
		
		return(strDecrypt);
	}
}
