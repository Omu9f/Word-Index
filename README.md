# **Word Index**

## **Summary**
For this coursework, a word indexing program was designed to index the words in text files from a given 
directory. The program used a map data structure (DT) to store the file name, word, and line number in 
which the word appeared in the text file. A map based on LinkedList and HashTable data structures were
used, and their performance was analyzed and compared. For text files with a small number of words, the 
performance between both map DT’s were comparable. However, as the words increased the HashTable 
DT proved to be exponentially more efficient

---

## **Design Choices**
*WordIndex.java*
This class was modified to hold its switch case within a separate method instead of in the 
main method. This was done to improve the code readability and introduce private static variables.

*WordException.java*
This class was designed to support both a default error message and a custom error 
message. The default error message was useful to avoid code duplication.

*Entry.java*
Due to the concept of a word entry being present in both the LinkedList and HashTable map 
implementations, a separate Entry.java class was created to reduce code duplication. Entry.java allows 
the retrieval of a word and its positions and supports DEFUNCT, a flag variable used by HashTables.

*ListWordMap.java*
This class implements a map LM based on the Java’s collection framework 
java.util.LinkedList. All the methods from IWordMap.java were implemented and the class was designed 
with simplicity and efficiency in mind.

*HashWordMap.java*
This class implements a map HT using an array to act as the table. The class implements all the 
methods from IWordMap.java, IHashMonitor.java, and additional helper methods. The implementation 
uses open addressing and double addressing. Once the load factor reaches the max load factor, the table 
size is doubled to the next prime using helper methods to rehash and get the new capacity.

*HashWordMap.java*
This class uses the DEFUNCT variable from Entry. It was observed that when the capacity 
reaches a large size like 10000, hashing is slightly faster when the prime number is 109. For this reason, 
an if condition was added to the hashCode(String s) method and improve the HashTable performance.

---

## **Run the program**
### Choose the target folder
The main method for this program is located in WordIndex.java. The folder from which the text files will be read from can be modified in line 8. Select between TextFiles or TextFiles_Shakespeare to run the program against preloaded data. You can add text files in TextFiles_Examples folder to run the program against your own data.

### Choose the commands
The commands will be read from commands.txt. Modify commands.txt with the following supported commands are: addall, add, remove, search, and overview.

addall: add the words from all the text files from the target folder
```
addall
```

add: add the words from a specified file
```
add lec00.txt
```

remove: remove the words from a specified file name
```
remove lec00.txt
```

search: search the top n occurrences a word has in the added text files
```
search 3 algorithms
```

overview: get an overview of all the words indexed
```
overview
``` 

---

## **Evaluate Performance**
The performance between the LinkedList and Hashtable implementations can be evaluated in the test folder. ListWordMapTest.java and HashWordMapTest.java both have a test named 'test10' which inserts and deletes N number of words in the map.

You can uncomment these tests and modify the value of N to check the run time difference. Example evaluation result:
![alt text](/result.jpg)

---

## **Conclusion**
This project was really fun to design and optimize. The performance difference of the LinkedList and Hashtable becomes truly apparent at extremely large numbers. Try the project out and have fun!
