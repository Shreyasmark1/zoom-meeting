package com.example.zoommeeting.util;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static int length(String str) {
        if (isEmpty(str)) {
            return 0;
        }

        return str.length();
    }

    public static String substring(String str, int start, int end) {
        if (isEmpty(str)) {
            return str;
        }

        return str.substring(start, end);
    }

    public static String removeWhiteSpaces(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.replaceAll("\\s", "");
    }

    public static String trim(String str) {
        if (isEmpty(str)) {
            return "";
        }

        return str.trim();
    }

    public static String toUpperCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.toUpperCase();
    }

    public static String toLowerCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.toLowerCase();
    }

    public static String getChars(String str, int noOfCharacters) {
        if (isEmpty(str)) {
            return str;
        }

        if (length(str) >= noOfCharacters) {
            return substring(str, 0, noOfCharacters);
        }

        return str;
    }

    public static String[] split(String source, String pattern) {
        if (isEmpty(source)) {
            return new String[]{};
        }

        return source.split(pattern);
    }

    public static boolean matches(String str1, String str2) {
        if (isEmpty(str1) || isEmpty(str2)) {
            return false;
        }

        return str1.equals(str2);
    }

    public static String replace(String str, String find, String replaceWith) {

        if (isEmpty(str)) {
            return "";
        }

        return str.replace(find, replaceWith);
    }

    public static int toInt(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String join(String delimiter, List<?> items) {

        if (items == null || items.isEmpty()) {
            return "";
        }

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            if (i > 0) str.append(delimiter);
            str.append(items.get(i));
        }

        String finalStr = str.toString();

        return isEmpty(finalStr) ? "" : finalStr;
    }

    // quoted string concatenation
    public static String join(String delimiter, String quotes, List<?> items) {

        if (items == null || items.isEmpty()) {
            return "";
        }

        if (isEmpty(quotes)) quotes = "";

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            if (i > 0) str.append(delimiter);
            str.append(quotes).append(items.get(i)).append(quotes);
        }

        String finalStr = str.toString();

        return isEmpty(finalStr) ? "" : finalStr;
    }

    // decode text sent in GET request
    public static String urlDecode(String text, boolean removeNonAlphanumeric) {

        if (isEmpty(text)) {
            return "";
        }

        if (removeNonAlphanumeric) {
            return removeNonAlphaNumeric(URLDecoder.decode(text, StandardCharsets.UTF_8)).trim();
        } else {
            return URLDecoder.decode(text, StandardCharsets.UTF_8).trim();
        }
    }

    public static List<Integer> stringListToIntegerList(List<String> stringNumbersList) {

        if (stringNumbersList == null || stringNumbersList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> integerList = new ArrayList<>();

        for (String number : stringNumbersList) {
            try {
                integerList.add(Integer.parseInt(number));
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        return integerList;
    }

    // to check whether the passed text is a number or not
    public static boolean isDigit(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String removeNonAlphaNumeric(String text) {

        if (isEmpty(text)) {
            return "";
        }

        return text.replaceAll("[^A-Za-z0-9]", "");
    }

    public static String findFileNameInPath(String filePath) {

        if (isEmpty(filePath)) {
            return "";
        }

        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String prepareQueryLimitString(int pageNumber, int recordLimit) {
        if (pageNumber != 0 && recordLimit != 0) {
            int offset = ((pageNumber > 0) ? (pageNumber - 1) : 0) * recordLimit;
            return " LIMIT " + offset + ", " + recordLimit;
        } else if (recordLimit != 0) {
            return " LIMIT " + recordLimit;
        }
        return "";
    }

    public static String formatToFiveDigits(int number){
        return String.format("%05d", number);
    }

    public static boolean isRegexMatch(String regex, String value){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return  matcher.matches();
    }

    public static String toQueryString(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue().toString())
                .collect(Collectors.joining("&", "?", ""));
    }

}
