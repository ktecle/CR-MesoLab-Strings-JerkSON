package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {



    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
        String name = findName(rawItem);
        Double price =0.0;
        if(!findPrice(rawItem).equals(" ")){
            price = Double.parseDouble(findPrice(rawItem));
        }
        else  price =0.0;

        String type = findType(rawItem);
        String expirationDate = findExpirationDate(rawItem);
        return new Item(name,price,type,expirationDate);
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }
    public String findName(String rawItem) throws ItemParseException{

        Pattern namePattern = Pattern.compile("(?<=([Nn][Aa][Mm][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexMatcher1 = namePattern.matcher(rawItem);
        if(regexMatcher1.find()){
            String name = regexMatcher1.group().replaceAll("\\d","o");
            return name;
        }else throw new ItemParseException();
    }

    public String findPrice(String rawItem){
        Pattern pricePattern = Pattern.compile("(?<=([Pp][Rr][Ii][Cc][Ee][^A-Za-z])).*?(?=[^0-9.])");
        Matcher regexMatcher2 = pricePattern.matcher(rawItem);
        if(regexMatcher2.find()) {
            return regexMatcher2.group();
        }else return null;

    }
    public String findType(String rawItem){
        Pattern typePattern = Pattern.compile("(?<=([Tt][Yy][Pp][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexMatcher3 = typePattern.matcher(rawItem);
        if(regexMatcher3.find()){
            return (regexMatcher3).group();
        }else return null;
    }

    public String findExpirationDate(String rawItem){
        Pattern expirationDatePattern = Pattern.compile("(?<=([Ee][Xx][Pp][Ii][Rr][Aa][Tt][Ii][Oo][Nn][^A-Za-z]))(.)*");
        Matcher regexMatcher4 = expirationDatePattern.matcher(rawItem);
        if(regexMatcher4.find()){
            return (regexMatcher4).group();
        }else return null;

    }





}
