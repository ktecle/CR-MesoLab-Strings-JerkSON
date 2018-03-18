package io.zipcoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemParserTest {

    private String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawSingleItemIrregularSeperatorSample = "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";

    private String rawBrokenSingleItem =    "naMe:;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                                      +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                                      +"NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
    private ItemParser itemParser;

    @Before
    public void setUp(){
        itemParser = new ItemParser();
    }

    @Test
    public void parseRawDataIntoStringArrayTest(){
        Integer expectedArraySize = 3;
        ArrayList<String> items = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        Integer actualArraySize = items.size();
        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    public void parseStringIntoItemTest() throws ItemParseException{
        Item expected = new Item("milk", 3.23, "food","1/25/2016");
        Item actual = itemParser.parseStringIntoItem(rawSingleItem);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = ItemParseException.class)
    public void parseBrokenStringIntoItemTest() throws ItemParseException{
        itemParser.parseStringIntoItem(rawBrokenSingleItem);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTest(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItem).size();
        assertEquals(expected, actual);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTestIrregular(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItemIrregularSeperatorSample).size();
        assertEquals(expected, actual);
    }
    @Test
    public void findNameTest(){
        String expected = "milk";
        String actual = itemParser.findName(rawSingleItem);
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void findPriceTest(){
        String expected = "3.23";
        String actual = itemParser.findPrice(rawSingleItem);
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void findTypeTest(){
        String expected = "food";
        String actual = itemParser.findType(rawBrokenSingleItem);
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void findExpirationDate(){
        String expected = "1/11/2016";
        String actual= itemParser.findExpirationDate(rawSingleItemIrregularSeperatorSample);
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void buildMapTest1 ()throws Exception{
        Map<String,ArrayList<Item>>testMap= new HashMap<String, ArrayList<Item>>();
        testMap = itemParser.buildMap();
        boolean actual = testMap.containsKey("milk");
        Assert.assertTrue(actual);
    }
    @Test
    public void buildMapTest2() throws Exception{
        Map<String,ArrayList<Item>>testMap= new HashMap<String, ArrayList<Item>>();
        testMap = itemParser.buildMap();
        int expected = 4;
        int actual = testMap.get("apples").size();
        Assert.assertEquals(expected,actual);

    }
    @Test
    public void getOccurencesOfItemsTest() throws ItemParseException {
        ArrayList<Item>test = new ArrayList<Item>();

        Item item1 = itemParser.parseStringIntoItem(rawSingleItem);
        String rawItem2 =("naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##");
        Item item2 = itemParser.parseStringIntoItem(rawItem2);
        test.add(item1);
        test.add(item2);
        int expected = 2;
        int actual = itemParser.getOccurencesOfItems(test);
        Assert.assertEquals(expected,actual);

    }
    @Test
    public void getOccurencesOfPricesTest() throws ItemParseException {

        ArrayList<Item>test = new ArrayList<Item>();

        Item item1 = itemParser.parseStringIntoItem(rawSingleItem);
        String rawItem2 =("naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##");
        String rawItem3 = "naMe:Bread;price:1.23;type:Food^expiration:1/11/2016##";
        Item item2 = itemParser.parseStringIntoItem(rawItem2);
        Item item3 = itemParser.parseStringIntoItem(rawItem3);
        test.add(item1);
        test.add(item2);
        test.add(item3);
        int expected =2;
        int actual = itemParser.getOccurencesOfPrices(test,1.23);
        Assert.assertEquals(expected,actual);
    }

}
