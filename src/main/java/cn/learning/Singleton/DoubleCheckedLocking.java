package cn.learning.Singleton;

/**
 * 双重检测锁单例
 */
public class DoubleCheckedLocking {
    private DoubleCheckedLocking(){}

    private volatile static DoubleCheckedLocking doubleCheckedLocking;

    public DoubleCheckedLocking getInstance(){
        if(doubleCheckedLocking ==null){
            synchronized (this){
                if(doubleCheckedLocking ==null){
                    doubleCheckedLocking = new DoubleCheckedLocking();
                }
            }
        }
        return doubleCheckedLocking;
    }
}
