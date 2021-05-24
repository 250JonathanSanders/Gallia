/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Name: SaveLoad.java
Author: Jonathan Sanders
Date: 24.05.21
Purpose: Saving and loading
handling for the game
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
////////////IMPORTS////////////

import java.io.*;

public class SaveLoad {
	
	//Method for saving
	public static void Save() {
		//saveFile object (uses current character name)
		File saveFile = new File("Saves\\" + Engine.strName + ".txt");
		
		//If file does not exist
		if (!saveFile.exists()) {
			System.out.println(Engine.strName + ".txt does not exist. Creating file!");
			try { //In case the file can't be created
				saveFile.createNewFile();
				System.out.println(Engine.strName + ".txt created.");
			} catch (IOException e) {
				System.out.println("File can not be created!");
				System.err.println("IOException: " + e.getMessage());
			}
		} else {
			System.out.print(Engine.strName + ".txt found!");
		}
		//Another if is required, in case the file could not be created
		if (saveFile.exists()) {
			FileWriter out;
			BufferedWriter writeFile;
			
			
			System.out.println("Saving...");
			//Create output file stream and send text to stream
			try {
				//Open text stream
				out = new FileWriter(saveFile);
				writeFile = new BufferedWriter(out);
				//Encrypt and write necessary contents
				System.out.println("Saving location...");
				writeFile.write(Crypto.Encrypt(Engine.strLocation));
				writeFile.newLine();
				System.out.println("Saving room...");
				writeFile.write(Crypto.Encrypt(Engine.strRoom));
				writeFile.newLine();
				System.out.println("Saving description...");
				writeFile.write(Crypto.Encrypt(Engine.strDesc));
				writeFile.newLine();
				System.out.println("Saving inventory...");
				//Boolean to String "conversion"
				if(Engine.blSword == true) {
					writeFile.write(Crypto.Encrypt("True"));
				} else{
					writeFile.write(Crypto.Encrypt("False"));
				}
				writeFile.newLine();
				writeFile.write(Crypto.Encrypt(Engine.intLow));
				writeFile.newLine();
				writeFile.write(Crypto.Encrypt(Engine.intHigh));
				writeFile.newLine();
				if(Engine.blPotion == true){
					writeFile.write(Crypto.Encrypt("True"));
				} else{
					writeFile.write(Crypto.Encrypt("False"));
				}
				writeFile.newLine();
				writeFile.write(Crypto.Encrypt(Engine.intPotion));
				writeFile.newLine();
				if (Engine.blTorch == true){
					writeFile.write(Crypto.Encrypt("True"));
				} else{
					writeFile.write(Crypto.Encrypt("False"));
				}
				writeFile.newLine();
				System.out.println("Saving health...");
				writeFile.write(Crypto.Encrypt(Engine.intHP));
				writeFile.newLine();
				System.out.println("Saving coordinates...");
				writeFile.write(Crypto.Encrypt(Engine.intX));
				writeFile.newLine();
				writeFile.write(Crypto.Encrypt(Engine.intY));
				writeFile.newLine();
				System.out.println("Saving story...");
				writeFile.write(Crypto.Encrypt(Engine.strStory));
				//Close text stream
				writeFile.close();
				out.close();
			} catch (IOException e) {
				System.out.println("Problem writing to file.");
				System.err.println("IOException: " + e.getMessage());
			}
		}
	}
	
	public static void Load(String name) {
		File loadFile = new File("Saves\\" + name + ".txt");
		FileReader in;
		BufferedReader ReadFile;
		
		try {
			in = new FileReader(loadFile);
			ReadFile = new BufferedReader(in);
			
			Engine.strName = name;
			System.out.println("Loading location...");
			Engine.strLocation = Crypto.Decrypt(ReadFile.readLine());
			System.out.println("Loading room...");
			Engine.strRoom = Crypto.Decrypt(ReadFile.readLine());
			System.out.println("Loading description...");
			Engine.strDesc = Crypto.Decrypt(ReadFile.readLine());
			System.out.println("Loading Inventory...");
			if (Crypto.Decrypt(ReadFile.readLine()).equals("True")) {
				Engine.blSword = true;
			} else {
				Engine.blSword = false;
			}
			Engine.intLow = Crypto.intDecrypt(ReadFile.readLine());
			Engine.intHigh = Crypto.intDecrypt(ReadFile.readLine());
			if (Crypto.Decrypt(ReadFile.readLine()).equals("True")) {
				Engine.blPotion = true;
			} else {
				Engine.blPotion = false;
			}
			Engine.intPotion = Crypto.intDecrypt(ReadFile.readLine());
			if (Crypto.Decrypt(ReadFile.readLine()).equals("True")) {
				Engine.blTorch = true;
			} else {
				Engine.blTorch = false;
			}
			System.out.println("Loading health...");
			Engine.intHP = Crypto.intDecrypt(ReadFile.readLine());
			System.out.println("Loading coordinates...");
			Engine.intX = Crypto.intDecrypt(ReadFile.readLine());
			Engine.intY = Crypto.intDecrypt(ReadFile.readLine());
			
			//Since story is multiline, it needs to be read until the end of the file (which ends in null)
			String strLine = "";
			System.out.println("Loading story...");
			while ((strLine = ReadFile.readLine()) != null) {
				Engine.strStory += Crypto.Decrypt(strLine);
			}
			
			ReadFile.close();
			in.close();
		} catch (Exception e) {
			System.out.println("There was an error acquiring information.");
		}
		System.out.println(Engine.strStory);
	}
}
