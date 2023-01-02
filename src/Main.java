import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		file a = new file();		
		a.go();
		//i was used this method to populate the performance table
		//a.readSearch("search");				  		 
		Scanner scanner = new Scanner (System.in);
		System.out.println("Please enter the sentence you want to search");
		String line = scanner.nextLine();
		line = line.toLowerCase(Locale.ENGLISH);
		String[] userArr = line.split(" ");		
		a.searchEngineAlgorithm(userArr);				
	}
			
					

		
		
		
		
		
		//System.out.println();
		
		
		
	}
		
        
        
        





