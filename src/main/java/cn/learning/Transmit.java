package cn.learning;

import java.util.ArrayList;
import java.util.List;

/**
 * 值传递或引用传递？
 * java为值传递，而list和StringBuffer中能够改变最终结果的两个方法中，其实是得到的是对象引用的拷贝，所以实际上也是值传递
 */
public class Transmit {
    public static void main(String[] args) {
        int valueOfInteger = 10;
        changeValue(valueOfInteger);
        System.out.println(valueOfInteger);

        String valueOfString = "hello";
        changeValue(valueOfString);
        System.out.println(valueOfString);

        List<String> valueOfList = new ArrayList<>();
        valueOfList.add("one");
        valueOfList.add("two");
        changeValue(valueOfList);
        System.out.println(valueOfList.toString());

        StringBuffer valueOfStringBuffer = new StringBuffer("hello");
        changeValue1(valueOfStringBuffer);
        System.out.println(valueOfStringBuffer);
        changeValue2(valueOfStringBuffer);
        System.out.println(valueOfStringBuffer);

    }

    private static void changeValue(int value){
        value += 1;
    }

    private static void changeValue(String value){
        value += " world";
    }

    private static void changeValue(List<String> value){
        value.add("three");
    }

    private static void changeValue1(StringBuffer value){
        value = new StringBuffer("你好");
    }

    private static void changeValue2(StringBuffer value){
        value.append(" world");
    }
}
