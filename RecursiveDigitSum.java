import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static int digitSum(String n, int k) {
        // Finding the sum of digits
        int sum = findSum(convertToList(n));
        // Finding the initial super digit where k=1
        sum = findSuperDigit(convertToList(sum));
        // Calculating k times the super digit to find actual result
        return findSuperDigit(convertToList(sum*k));
    }
    
    static List<Integer> convertToList(String n){
        char[] eachDigit = n.toCharArray();
        List<Integer> convertedArr = new ArrayList<Integer>();
        // Parsing each character and making a list
        for(char dig : eachDigit)
            convertedArr.add(Integer.parseInt(dig+""));
        return convertedArr;
    }
    
    static List<Integer> convertToList(int n){
        List<Integer> convertedArr = new ArrayList<Integer>();
        // Dividing by 10 and storing the remainder to convert to list
        do {
            convertedArr.add(n%10);
            n = n/10;
        } while(n > 0);
        return convertedArr;
    }
    
    static int findSuperDigit(List<Integer> numList){
        // finds the initial sum
        int superDig = findSum(numList);
        // when the super digit is single stop recursion and return digit.
        if(superDig/10 >0)
            superDig = findSuperDigit(convertToList(superDig));
        return superDig;
    }
    
    static int findSum(List<Integer> numList){
        int sum = 0;
        // Adding up the list
        for (int num : numList)
            sum += num;
        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String n = in.next();
        int k = in.nextInt();
        int result = digitSum(n, k);
        System.out.println(result);
        in.close();
    }
}
