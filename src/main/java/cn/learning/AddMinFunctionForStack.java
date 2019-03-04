package cn.learning;

import java.util.Stack;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 * @param <E>
 */
public class AddMinFunctionForStack<E> extends Stack<E>{

    public Integer min(){
        int min = (Integer) this.peek();
        int temp;
        for (Object aStack : this) {
            temp = (Integer) aStack;
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }


    public static void main(String[] args) {
        AddMinFunctionForStack<Integer> addMinFunctionForStack = new AddMinFunctionForStack<>();
        addMinFunctionForStack.push(41);
        addMinFunctionForStack.push(3);
        addMinFunctionForStack.push(2);
        addMinFunctionForStack.push(4);
        addMinFunctionForStack.push(5);
        addMinFunctionForStack.push(54);
        addMinFunctionForStack.push(6);
        addMinFunctionForStack.push(9);
        addMinFunctionForStack.push(14);
        addMinFunctionForStack.push(15);
        System.out.println(addMinFunctionForStack.min());
    }
}
