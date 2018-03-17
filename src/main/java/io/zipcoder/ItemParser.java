package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    public static int counter = 0;
    private HashMap<String, ArrayList<Item>> groceryList = new HashMap<String, ArrayList<Item>>();


    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {

        if (findName(rawItem) == null || findPrice(rawItem) == null) {
            throw new ItemParseException();
        }

        String name = findName(rawItem);
        Double price = Double.parseDouble(findPrice(rawItem));
        String type = findType(rawItem);
        String expirationDate = findExpirationDate(rawItem);

        return new Item(name, price, type, expirationDate);
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public String findName(String rawItem) {

        Pattern namePattern = Pattern.compile("(?<=([Nn][Aa][Mm][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexMatcher1 = namePattern.matcher(rawItem);
        if (regexMatcher1.find()) {
            if (!regexMatcher1.group().equals("")) {
                String name = regexMatcher1.group().replaceAll("\\d", "o");
                return name.toLowerCase();
            }

        }
        return null;
    }

    public String findPrice(String rawItem) {
        Pattern pricePattern = Pattern.compile("(?<=([Pp][Rr][Ii][Cc][Ee][^A-Za-z])).*?(?=[^0-9.])");
        Matcher regexMatcher2 = pricePattern.matcher(rawItem);
        if (regexMatcher2.find()) {
            if (!regexMatcher2.group().equals("")) {
                return regexMatcher2.group();
            }
        }
        return null;
    }

    public String findType(String rawItem) {
        Pattern typePattern = Pattern.compile("(?<=([Tt][Yy][Pp][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexMatcher3 = typePattern.matcher(rawItem);
        if (regexMatcher3.find()) {
            return (regexMatcher3).group().toLowerCase();
        } else return null;
    }

    public String findExpirationDate(String rawItem) {
        Pattern expirationDatePattern = Pattern.compile("(?<=([Ee][Xx][Pp][Ii][Rr][Aa][Tt][Ii][Oo][Nn][^A-Za-z]))(.)+[^#]");
        Matcher regexMatcher4 = expirationDatePattern.matcher(rawItem);
        if (regexMatcher4.find()) {
            return (regexMatcher4).group();
        } else return null;

    }

    public Map<String, ArrayList<Item>> getMap() throws Exception {
        Main main = new Main();

        ArrayList<String> listOfItems = parseRawDataIntoStringArray(main.readRawDataToString());
        for (String anItem : listOfItems) {
            try {
                Item newItem = parseStringIntoItem(anItem);
                    if (!groceryList.containsKey(newItem.getName())) {
                        ArrayList<Item> myItem = new ArrayList<Item>();
                        myItem.add(newItem);
                        groceryList.put(newItem.getName(), myItem);
                    } else {
                        groceryList.get(newItem.getName()).add(newItem);
                }
            } catch (ItemParseException e) {
                counter++;
            }
        }
        return groceryList;
    }


}
