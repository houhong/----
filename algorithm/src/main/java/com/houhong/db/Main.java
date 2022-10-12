package com.houhong.db;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-11 10:20
 **/
public class Main {
}

class Solution {


    public String longestPalindrome(String str) {

        if (str.length() < 2) {
            return str;
        }

        String result = "";

        for (int index = 0; index < str.length(); index++) {
            //以str.charAt(index) 为中心的值
            String s1 = palindrome(str, index, index);

            String s2 = palindrome(str, index, index + 1);

            result = result.length() > s1.length() ? result : s1;
            result = result.length() > s2.length() ? result : s2;
        }

        return result;

    }

    public String palindrome(String str, int left, int right) {

        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }

        return str.substring(left + 1, right);

    }

}