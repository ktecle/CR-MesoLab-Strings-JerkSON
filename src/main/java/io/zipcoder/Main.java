package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.Map;


public class Main {

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
        System.out.println(output);
        // TODO: parse the data in output into items, and display to console.

        ItemParser parser = new ItemParser();
        Main main = new Main();

        ArrayList<String> itemList = parser.parseRawDataIntoStringArray(output);


//        for (String item : itemList) {
//
//            System.out.println(parser.parseRawDataIntoStringArray(main.readRawDataToString()));
//             System.out.println(parser.parseStringIntoItem(item));
//        }
//        System.out.println(ItemParser.counter);
//        Map<String, ArrayList<Item>> grocery = parser.getMap();
//        for (String key : grocery.keySet()) {
//            System.out.println(key);
//        }
//        ArrayList<String> itemListCopy = parser.parseRawDataIntoStringArray(output);
//        for (Map.Entry<String, ArrayList<Item>> mapKey : parser.getMap().entrySet()) {
//            System.out.println(mapKey.getKey());
//            for (Item item : mapKey.getValue()) {
//                System.out.println(item.getPrice());
//            }
//        }
//        System.out.println(ItemParser.counter);


        System.out.println(parser.generateReport());
   }



}
