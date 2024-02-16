# Poker Hand Challenge

## Introduction

The goal of this project is to produce the Hand Rank value of any given 5 cards in a deck.

## Getting Started

### Prerequisites

- Java SDK version 21 
- Maven 3.9.6.

## Install and Execution

To get the project up and running on your machine;

1. Clone the repository
    ```sh
    git clone https://github.com/DavidKane289/PokerHandChallenge.git
    ```
2. Navigate to the project directory
3. Compile and package the project using Maven
    ```sh
    mvn package
    ```
   This will create a JAR file within the .\target directory
4. Execute the JAR file by supplying a file or without supplying any files to use `challenge-samples.csv`
   ```sh
    java -jar .\target\PokerHandChallenge-1.0.jar C:\challenge-samples.csv
    java -jar .\target\PokerHandChallenge-1.0.jar
    ```

## Expected Input

Each line of a file supplied to the application is expected to contain 5 valid card descriptions. 
Each description is in the form CS, where;
- C is the name of the card (2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A) 
- S is the suit (H, D, S, C for Hearts, Diamonds, Spades and Clubs respectively)

Example Input
   ```text
   3H JS 3C 7C 5D
   JH 2C JD 2H 4C
   9H 9D 3S 9S 9C
   9C 3H 9S 9H 3S
   ```

## Expected Output

When given valid input the application interprets each line of the hand and outputs the rank of that hand.

   ```text
   3H JS 3C 7C 5D => One Pair
   JH 2C JD 2H 4C => Two Pair
   9H 9D 3S 9S 9C => Four of a Kind
   9C 3H 9S 9H 3S => Full House
   ```

## Exceptions
Some exceptions might occur and these are segregated into two sections, critical (which halt processing) and warnings (written to the console but the application continues to process the remainder of the file).

### Critical
- File not found: Incorrect file path, or file not in location
- File is empty: File has zero bytes
- Too many arguments: Multiple file paths may have been supplied

### Warnings
- Incorrect number of data points in a line
    ```text
    Line #5 (4H KS 2C TH) failed with exception: java.lang.IllegalArgumentException: Expected 5 Cards but received 4
    ```
- Invalid length of a data point in a line
    ```text
    Line #4 (4H KS 2C TH 5X2D) failed with exception: java.lang.IllegalArgumentException: Invalid input length, expected 2 but was 4
    ```
- Unmatched card value
    ```text
    Line #2 (4H KS UC TH 5X) failed with exception: org.challenge.exceptions.UnmatchedCardValueException: Card Rank U was not able to be matched
    or
    Line #3 (4H KS 2C TH 5X) failed with exception: org.challenge.exceptions.UnmatchedCardValueException: Card Suit X was not able to be matched
    ```
- Duplicate data point in a line
    ```text
    Line #6 (4H KS 2C TH 4H) failed with exception: org.challenge.exceptions.DuplicateCardException: Hand contains duplicate card(s)
    ```