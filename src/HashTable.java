import java.util.*;
import java.io.*;

public class HashTable{
	Dictionary[] dictForOccourences;
	String[] vals;	
	int currentSize,maxSize;
	int count;
	int collisionCount = 0;
	
	HashTable (int capacity) {
		if(!isPrimeNumber(capacity))
			capacity = getNextPrime(capacity);
		maxSize = capacity;
		currentSize = 0;
		vals = new String[maxSize];
		Dictionary<String, Integer>[] tempDictionary = (Dictionary<String, Integer>[]) new Dictionary[capacity];
		dictForOccourences = tempDictionary;
		
	}	
	public double loadFactor () {
		return (double)currentSize/maxSize;
	}
	//This function resets the size of the hashtable and the dictionary I keep for the repetitions of words when we reach the load factor.
	public void rehash() {
		currentSize = 0;
		if(isPrimeNumber(maxSize*2))
			maxSize = maxSize*2;
		else
			maxSize = getNextPrime(maxSize*2);
		dictForOccourences =(Dictionary<String, Integer>[]) new Dictionary[maxSize];
		vals = new String[maxSize];		
	}
	//While increasing the size of our hashtable, this function checks if the size we are increasing is a prime number.
	boolean isPrimeNumber(int capacity) {
		for(int i = 2; i*i<capacity; i++) {		
			if(capacity%i==0)
				return false;							
	    }
		return true;	 
    }
	//, if not, it returns the nearest larger number.
	int getNextPrime(int num) {
		for(int i = num;; i++) {		
			if(isPrimeNumber(i))
				return i;							
	    }			 
    }
	//It converts the word we retrieved from the file into an integer key with the SSF method.
	public long hashcodeSSF(String word) {
		char[] charArray = word.toCharArray();
		long hashCode = 0;
		for(int i =0; i<charArray.length;i++) {
			char a =charArray[i];
			int b = a-96; 
			hashCode = hashCode+b;		
		}
		return hashCode;
				
	}
	//It converts the word we retrieved from the file into an integer key with the PAF method.
	public long hashcodePAF(String word) {
			
			char[] charArray = word.toCharArray();
			long hashCode=0;
			for(int i =0;i<word.length();i++) {
				hashCode = (long)(hashCode+(((int)(charArray[i])-96)*(Math.pow(31,word.length()-1-i))));
			}			
			return hashCode;
	    }				
	void makeEmpty() {
		currentSize = 0;
		vals = new String[maxSize];		
	}
	public int getSize() {return currentSize;}
	public boolean isEmpty() {return getSize()==0;}
	public boolean isFull()
	   {
	       return currentSize == maxSize;
	   }
	private long firstHashToGetKeyDH(long hashCode) {
		long key = (hashCode%maxSize);
		return key;
		
		
		
	}
	private long secondHashToGetKeyDH(long hashCode) {
		long key =  (7 -(hashCode%7));
		return key;
		
		
		
	}
	//My insert method first checks whether the word word is in my hasharray or not. If not, it finds the key and index of that word and returns by increasing its index according to the collision method we use until it finds an empty place in the array, and when it finds it, it places it in that index and inserts the number of that word according to the index in the array and the name of the file it came from in the dictionary where I keep the repetitions of the words according to the filenames. If it exists, it finds the index of that word in the array and increases it by adding 1 to the previous value of my element, which is the file number I keyed, to the same index of my dictionary array according to that index.
	public void insertLP(String word,String fileName) {
		if(findIndex(word)==-1) {
			long key = hashcodePAF(word);
			long index = key%maxSize;
			while(vals[(int)index]!=null) {				
				++index;
				index%=maxSize;
			}
			vals[(int)index] = word;
			currentSize++;
			dictForOccourences[findIndex(word)] = new Dictionary<String, Integer>(100);
			dictForOccourences[findIndex(word)].add(fileName, 1);
		}
		else {
			if(dictForOccourences[findIndex(word)].contains(fileName))
				dictForOccourences[findIndex(word)].add(fileName, (int)(dictForOccourences[findIndex(word)].getValue(fileName))+1);
			else
				dictForOccourences[findIndex(word)].add(fileName, 1);
			count = 0;				
		}		
								
	}
	public void insertDH(String word,String fileName) {						
		if(findIndexDH(word)==-1) {
			long key = hashcodePAF(word);
			long index = (firstHashToGetKeyDH(key))%maxSize;
			while(vals[(int)index]!=null) {
				index = (index+secondHashToGetKeyDH(key))%maxSize;				
			}
			vals[(int)index] = word;
			currentSize++;
			dictForOccourences[findIndexDH(word)] = new Dictionary<String, Integer>(100);
			dictForOccourences[findIndexDH(word)].add(fileName, 1);
		}				
		else {
			if(dictForOccourences[findIndexDH(word)].contains(fileName))
				dictForOccourences[findIndexDH(word)].add(fileName, (int)dictForOccourences[findIndexDH(word)].getValue(fileName)+1);
			else
				dictForOccourences[findIndexDH(word)].add(fileName, 1);
		}
	}
	//If it has word parameter in hasharrray, it returns its index, otherwise it returns -1
	public int findIndexDH(String word) {
		long key = hashcodePAF(word);
		long index = firstHashToGetKeyDH(key);
		
		while(vals[(int)index]!=null) {
			if(vals[(int)index].equalsIgnoreCase(word))			
				return (int)index;
			collisionCount++;			
			index = (index+secondHashToGetKeyDH(key))%maxSize;
		}
		return -1;
	}
	//If it has word parameter in hasharrray, it returns its index, otherwise it returns -1
	public int findIndex(String word) {
		long key = hashcodePAF(word);
		long index = key%maxSize;		
		while(vals[(int)index]!=null) {
			if(vals[(int)index].equalsIgnoreCase(word))			
				return (int)index;			
			collisionCount++;
			index++;
			index%= maxSize;
		}
		return -1;	
	}	
		
	
}	
