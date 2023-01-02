import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map; 
public class file  {
	String fileName = "001";
	int fileNumber = 1;	
	HashTable b = new HashTable(2477);
	String setName () {
		fileNumber+=1;
		fileName = String.format("%03d",fileNumber);				;
		return fileName;
		
		
	}
	//It divides the sentence entered by the user into words one by one, collects how many times each word occurs in which file, assigns it to a new dictionary and outputs the highest file.
	public void searchEngineAlgorithm(String[] words) {
		Dictionary<String, Integer> algorithm =  new Dictionary<String, Integer>(100);
		String mostRelevantFile= "u didn't entered valid value";
		for(int i =0;i<words.length;i++) {
			if(b.findIndex(words[i])!=-1) {	
				for(int k = 1;k<=100;k++) {
					if((b.dictForOccourences[b.findIndex(words[i])].getValue(String.format("%03d", k))!=null)) {
						if(algorithm.getValue(String.format("%03d", k))!=null)
							algorithm.add(String.format("%03d", k),(int)(algorithm.getValue(String.format("%03d", k))+(int)(b.dictForOccourences[b.findIndex(words[i])].getValue(String.format("%03d", k)))));

						else
							algorithm.add(String.format("%03d", k),(int)(b.dictForOccourences[b.findIndex(words[i])].getValue(String.format("%03d", k))));
					}
						
				}
			}
		}
		int max = 0;
		for(int i = 1; i<=100;i++) {			
			if(algorithm.getValue(String.format("%03d", i))!=null&&algorithm.getValue(String.format("%03d", i))>max) {
				max = algorithm.getValue(String.format("%03d", i));
				mostRelevantFile = String.format("%03d", i);
			}									
		}
		System.out.println("the most relation file with the sentence you are looking for "+mostRelevantFile);
		
	}
	//Calls the method that reads all the words in 100 files
	public void go() {
		for(int i =0; i<100;i++) {
			readFile(fileName);
			if(checkrehash()) {
				b.rehash();		
				i = -1;
				fileNumber = 1;
				fileName = String.format("%03d", fileNumber);
			}			
		}
		//words.trimToSize();
			
	}
	public boolean checkrehash() {
		if(b.loadFactor()>= (double)5/10)
			return true;
		return false;
	}
	public void readSearch(String filename) {
		
		BufferedReader reader;				
		long lines = 0;
		String readedData = null;
		try {
			BufferedReader readerLast = new BufferedReader(new FileReader(filename+".txt"));
			reader = new BufferedReader(new FileReader(filename+".txt"));
			while (reader.readLine()!= null) {
				// read next line
				lines++;
			}
			readedData = readerLast.readLine();			
			for(int i =1; i<lines;i++)
				readedData = readedData+ " "+ readerLast.readLine();

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> data = Arrays.asList(readedData.toLowerCase(Locale.ENGLISH).split(" "));
		long[] searchTime = new long[data.size()];
		long minTime = 99999999;
		long maxTime = 0;
		long averageTime =0;
		b.collisionCount = 0;
		for(int i = 0;i<data.size();i++) {
			long time1 = System.nanoTime();
			b.findIndex(data.get(i));
			long time2= System.nanoTime();
			searchTime[i] = time2-time1;
			if(searchTime[i]>maxTime)
				maxTime= searchTime[i];
			if(searchTime[i]<minTime)
				minTime= searchTime[i];
		}
		long sum = 0;
		for(int i = 0; i<searchTime.length;i++) {
			sum = sum+searchTime[i];			
		}
		averageTime = sum/searchTime.length;		
		System.out.println(minTime+" "+maxTime+" "+averageTime+" "+ b.collisionCount);
			

		
	}
	//First of all, I pull the txt files you have uploaded to us, split them and purify them from the words in the stop words file. I call my insert method for each word from the array in the method where I read the files.
	public void readFile(String fileName) {
		BufferedReader reader;
		BufferedReader stopReader;
		long dataLines = 0;
		long stopDataLines = 0;
		String readedData = null;
		String readedStopData = null;
		try {
			BufferedReader readerLast = new BufferedReader(new FileReader(fileName+".txt"));
			reader = new BufferedReader(new FileReader(fileName+".txt"));
			BufferedReader stopReaderLast = new BufferedReader(new FileReader("stop_words_en.txt"));
			stopReader = new BufferedReader(new FileReader("stop_words_en.txt"));
			while (reader.readLine()!= null) {
				// read next line
				dataLines++;
			}
			while (stopReader.readLine()!= null) {
				// read next line
				stopDataLines++;
			}
			readedData = readerLast.readLine();	
			readedStopData = stopReaderLast.readLine();
			for(int i =1; i<dataLines;i++)
				readedData = readedData+" "+ readerLast.readLine();
			for(int i =1; i<stopDataLines;i++)
				readedStopData =readedStopData+" "+stopReaderLast.readLine();
			
			stopReader.close();
			readerLast.close();
			stopReaderLast.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
        String DELIMITERS = "[-+=" +

		        " " +        //space

		        "\r\n " +    //carriage return line fit

				"1234567890" + //numbers

				"’'\"" +       // apostrophe

				"(){}<>\\[\\]" + // brackets

				":" +        // colon

				"," +        // comma

				"‒–—―" +     // dashes

				"…" +        // ellipsis

				"!" +        // exclamation mark

				"." +        // full stop/period

				"«»" +       // guillemets

				"-‐" +       // hyphen

				"?" +        // question mark

				"‘’“”" +     // quotation marks

				";" +        // semicolon

				"/" +        // slash/stroke

				"⁄" +        // solidus

				"␠" +        // space?   

				"·" +        // interpunct

				"&" +        // ampersand

				"@" +        // at sign

				"*" +        // asterisk

				"\\" +       // backslash

				"•" +        // bullet

				"^" +        // caret

				"¤¢$€£¥₩₪" + // currency

				"†‡" +       // dagger

				"°" +        // degree

				"¡" +        // inverted exclamation point

				"¿" +        // inverted question mark

				"¬" +        // negation

				"#" +        // number sign (hashtag)

				"№" +        // numero sign ()

				"%‰‱" +      // percent and related signs

				"¶" +        // pilcrow

				"′" +        // prime

				"§" +        // section sign

				"~" +        // tilde/swung dash

				"¨" +        // umlaut/diaeresis

				"_" +        // underscore/understrike

				"|¦" +       // vertical/pipe/broken bar

				"⁂" +        // asterism

				"☞" +        // index/fist

				"∴" +        // therefore sign

				"‽" +        // interrobang

				"※" +          // reference mark

		        "]";
        List<String> data = Arrays.asList(readedData.toLowerCase(Locale.ENGLISH).split(DELIMITERS));
        List<String> stopData = Arrays.asList(readedStopData.toLowerCase(Locale.ENGLISH).split("\\s+"));
        for(int i =0; i <data.size();i++) {
        	if(!stopData.contains(data.get(i))&&data.get(i)!="") {        		 		
        		b.insertLP(data.get(i), fileName);        		        		        		        	 	
        	 	if(checkrehash())
            		break;            		            			
        	}        
         		
        }                        
      
        setName();  
	 }	
		
		
		
	}

