<p align="center">
  <img width="200" src="https://github.com/rohan-bhautoo/Karaoke-Application/blob/master/Screenshots/karaoke-logo.png">
</p>
<h1 align="center">Karaoke Application</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.8.0-brightgreen.svg" />
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?logo=java&logoColor=white" />
  <img alt="JDK" src="https://img.shields.io/badge/JDK->=11.0.14-blue.svg" />
  <img alt="JavaFX-SDK" src="https://img.shields.io/badge/JavaFX_SDK->=17.0.2-blue.svg" />
</p>

## Description
> A Karaoke Application was designed and implemented using Data Structures and JavaFX where a user will be able to search specific songs from a large number of data and to add new songs to the playlist. See images of the application in the [Screenshot](/Screenshots) folder.
> 
> Currently, the [library](/karaokeapp/src/main/java/com/karaokeapp/Library/sample_song_data.txt) has only 4364 songs but it is expected to have over 1 million of songs. Some observations such as the time and space complexity must be taken into consideration before choosing the right data structure. This will determine the order of growth of the application. Implementation of balanced binary search tree data structure was used to store the songs data and its order of growth is ```O(log n)``` where n is the number of songs.

### 🏠 [Homepage](/karaokeapp/src/main/java/com/karaokeapp/KaraokeApp.java)
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Karaoke-Application/blob/master/Screenshots/Main.png">
</p>

## Prerequisite

### JavaFX
> JavaFX is an open source, next generation client application platform for desktop, mobile and embedded systems built on Java. Download it [here](https://openjfx.io/).
> 
> For Linux:
```sh
export PATH_TO_FX=path/to/javafx-sdk-17/lib
```
> For Windows:
```sh
set PATH_TO_FX="path\to\javafx-sdk-17\lib"
```

### Java Development Kit (JDK) 
> JDK version 11 is used for this project as it includes the JavaFX library. Download it [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html).
> 
> For Windows:
```sh
set JAVA_HOME="C:\[Path to folder]\Java\jdk-11.0.14
```
> Enter the Environment Variables in System Properties.
> 
> Add **%JAVA_HOME%\bin** into Path.
```sh
%JAVA_HOME%\bin
```
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Env%20Variable.png">
</p>

## Data Structure
> A data structure is a particular way of organizing data in a computer so that it can be used effectively. The data structure implemented is a balanced binary search tree to store the song’s data from the library. A balanced binary search tree is a tree that automatically keeps its height small. The cost of this algorithm is guaranteed to be logarithmic which means that as the input size increases the time taken to complete this algorithm becomes constant. 

### Red-Black Binary Search Tree (BST)
> Red-black trees are an evolution of binary search trees that aim to keep the tree balanced without affecting the complexity of the primitive operations. This is done by coloring each node in the tree with either red or black and preserving a set of properties that guarantee that the deepest path in the tree is not longer than twice the shortest one.
> 
> The [ST.java](/karaokeapp/src/main/java/com/karaokeapp/DataStructure/ST.java) class represents an ordered symbol table of generic key-value pairs. It supports the usual put, get, contains, delete, size, isEmpty methods and it also provides ordered methods for finding the minimum, maximum, floor, and ceiling. A keys method is added for iterating over all of the keys. 
> 
> A symbol table implements the associative array abstraction 
> - when associating a value with a key that is already in the symbol table,
> - the convention is to replace the old value with the new value.
> 
> Unlike ```java{@link java.util.Map}```, this class uses the convention that values cannot be ```java{@code null}``` setting the value associated with a key to ```java{@code null}``` is equivalent to deleting the key from the symbol table. It requires that the key type implements the ```java{@code Comparable}``` interface and calls the ```java{@code compareTo()}``` and method to compare two keys. It does not call either ```java{@code equals()}``` or ```java{@code hashCode()}```. This implementation uses a red-black BST.

#### Worst Case Scenario

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| PUT      | 
| :------- |
| GET      |   
| CONTAINS |   
| REMOVE   |
| MININUM  |
| MAXIMUM  |
| CEILING  |
| FLOOR    |
      
</td><td>

| &Theta;(log N) |
| :------------- |
    
</td></tr>
</table>

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| SIZE     | 
| :------- |
| ISEMPTY  |  
      
</td><td>

| &Theta;(1) |
| :--------- |
    
</td></tr>
</table>

### Balanced Binary Search Tree (BST)
> A balanced binary tree, also referred to as a height-balanced binary tree, is defined as a binary tree in which the height of the left and right subtree of any node differ by not more than 1.
> 
> The conditions for a height-balanced binary tree:
> - difference between the left and the right subtree for any node is not more than one.
> - the left subtree is balanced.
> - the right subtree is balanced.

<p align="center">
  <img height="300" src="https://github.com/rohan-bhautoo/Karaoke-Application/blob/master/Screenshots/balanced-binary-tree.png">
</p>

> The [SET.java](/karaokeapp/src/main/java/com/karaokeapp/DataStructure/SET.java) class represents an ordered set of comparable keys. It supports the usual add, contains, and delete methods. It also provides ordered methods for finding the minimum, maximum,floor, and ceiling and set methods for union, intersection, and equality. 
> 
> Even though this implementation include the method ```{@code equals()}```, it does not support the method ```{@code hashCode()}``` because sets are mutable. This implementation uses a balanced binary search tree. It requires that the key type implements the ```{@code Comparable}``` interface and calls the ```{@code compareTo()}``` and method to compare two keys. It does not call either ```{@code equals()}``` or ```{@code hashCode()}```.

#### Worst Case Scenario

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| ADD      | 
| :------- |
| CONTAINS |   
| DELETE   |
| MININUM  |
| MAXIMUM  |
| CEILING  |
| FLOOR    |
      
</td><td>

| &Theta;(log N) |
| :------------- |
    
</td></tr>
</table>

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| SIZE     | 
| :------- |
| ISEMPTY  |  
      
</td><td>

| &Theta;(1) |
| :--------- |
    
</td></tr>
</table>

### Doubly Linked List
> A Doubly Linked List (DLL) contains an extra pointer, typically called previous pointer, together with next pointer and data which are there in singly linked list.
> 
> The [LinkedList.java](/karaokeapp/src/main/java/com/karaokeapp/DataStructure/LinkedList.java) class represents the implementation of a doubly linked-list. It supports the add, addFirst, addLast, remove, removeFirst, removeLast, removeAt, indexOf, along with methods to peek first element and last element. It also has the methods for isEmpty, size and clear.
<p align="center">
  <img height="300" src="https://github.com/rohan-bhautoo/Karaoke-Application/blob/master/Screenshots/doubly-linked-list.png">
</p>

#### Worst Case Scenario

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| INDEXOF  | 
| :------- |
| REMOVE   |
| REMOVEAT |
      
</td><td>

| &Theta;(N) |
| :--------- |
    
</td></tr>
</table>

<table align="center">
  <tr>
    <th>Operation</th>
    <th>Worst Case</th>
  </tr>
  <tr><td>
         
| ADD         | 
| :---------- |
| ADDFIRST    | 
| ADDLAST     | 
| PEAKFIRST   | 
| PEAKLAST    | 
| REMOVEFIRST | 
| REMOVELAST  | 
| CONTAINS    | 
| SIZE        | 
| ISEMPTY     |  
      
</td><td>

| &Theta;(1) |
| :--------- |
    
</td></tr>
</table>

## Usage
> Compile all the java files using:
> 
> For Linux:
```sh
javac --module-path $PATH_TO_FX --add-modules javafx.controls javafx.media *.java
```
> For Windows:
```sh
javac --module-path %PATH_TO_FX% --add-modules javafx.controls javafx.media *.java
```
> **Make sure to add the required modules**
> 
> Run the application
> 
> For Linux:
```sh
java --module-path $PATH_TO_FX --add-modules javafx.controls javafx.media KaraokeApp
```
> For Windows:
```sh
java --module-path %PATH_TO_FX% --add-modules javafx.controls javafx.media KaraokeApp.java --filePath=
```
> where filePath is the Songs Library path

## Author

👤 **Rohan Bhautoo**
* Github: [@rohan-bhautoo](https://github.com/rohan-bhautoo)
* LinkedIn: [@rohan-bhautoo](https://linkedin.com/in/rohan-bhautoo)

## Show your support
Give a ⭐️ if this project helped you!
