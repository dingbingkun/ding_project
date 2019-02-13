package cn.learning.Singleton;


/**
 * 静态内部类单例
 */
public class StaticInnerClass {
    private StaticInnerClass(){}

    private static class SingletonHelper{
        private static final StaticInnerClass INSTANCE = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
