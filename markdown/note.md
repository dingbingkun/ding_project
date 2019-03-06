#### HttpServlet容器响应Web客户请求流程如下：  
    1. Web客户向Servlet容器发出Http请求；
    2. Servlet容器解析Web客户的Http请求;
    3. Servlet容器创建一个HttpRequest对象，在这个对象中封装Http请求信息；
    4. Servlet容器创建一个HttpResponse对象；
    5. Servlet容器调用HttpServlet的service方法，这个方法中会根据request的Method来判断具体是执行doGet还是doPost，把HttpRequest和HttpResponse对象作为service方法的参数传给HttpServlet对象；
    6. HttpServlet调用HttpRequest的有关方法，获取HTTP请求信息；
    7. HttpServlet调用HttpResponse的有关方法，生成响应数据；
    8. Servlet容器把HttpServlet的响应结果传给Web客户。

#### 懒汉式单例类.在第一次调用的时候实例化自己 
     
    public class Singleton {
        private Singleton() {}
        private static Singleton single=null;
        public static Singleton getInstance() {
            return new Singleton();
        }
    }
    
    //双重校验锁
    public class Singletton{
        private Singleton(){}
        private volatile static Singleton single = null;
        public static Singleton getInstance(){
            if(single==null){
                synchronied(this){
                    if(single==null){
                        single = new Singleton();
                    }
                }
            }
            return single;
        }
    }
#### 饿汉式单例类.在类初始化时，已经自行实例化
    public class Singleton {
        private Singleton() {}
        private static Singleton single = new Singleton1();
        public static Singleton getInstance() {
            return single;
        }
    }

#### 事务
    原子性Atomicity
    一致性Consistency
    隔离性Isolation
    持久性Durability

#### volatile关键字
    volatile能保证可见性和有序性，但不能保证原子性

#### 类加载器
    步骤：加载、验证、准备、解析、初始化  
    类别：
        启动类加载器Bootsrap ClassLoader  
        扩展类加载器Extension ClassLoader  
        系统类加载器App ClassLoader  
        自定义类加载器	  

#### Class.forName和classloader的区别：
    Class.forName除了会把class文件加载到Jvm外，还会执行类中的static方法

#### 事务传播行为：
	PROPAGATION_REQUIRED：支持当前事务，如果不存在，就新建一个
	PROPAGATION_SUPPORTS：支持当前事务，如果不存在，就不使用事务
	PROPAGATION_MANDATORY：支持当前事务，如果不存在，抛出异常
	PROPAGATION_REQUIRES_NEW：如果有事务存在，挂起当前事务，创建一个新的事务
	PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果有事务存在，挂起当前事务
	PROPAGATION_NEVER：以非事务方式运行，如果有事务存在，抛出异常
	PROPAGATION_NESTED：如果当前事务存在，则嵌套事务执行

#### Http协议
    1. 请求行
    2. 请求头
    3. 空行
    4. 消息主体

#### 线程安全自增
    java.util.concurrent.atomic.AtomicInteger.getAndIncrement()

#### Map遍历
    for(Map.Entry<String,Object> entry : map.entrySet()){
       ...
    }

#### 避免redis缓存雪崩：
    1. 事前：实现（主从+哨兵或集群）
    2. 事中：设置本地缓存、限流
    3. 事后：redis持久化，从磁盘中加载

#### 缓存穿透
    查询一个一定不存在的数据，由于缓存是不命中时需要从数据库查询，查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询，造成缓存穿透。

#### 缓存更新：数据库与缓存不一致问题
##### 先更新数据库，再删除缓存：
    情况1：更新数据库成功，删除缓存失败  
         解决方案：不断地重试删除，直到成功。	
    情况2：  
        1. 缓存刚好失效  
        2. 线程A查询数据库，得到旧值  
        3. 线程B将新值写入数据库  
        4. 线程B删除缓存  
        5. 线程A将旧值写入缓存  
        机率小，条件1缓存刚好失效，条件2实际上数据库的写操作会比读操作慢得多，而且还要锁表，而读操作必需在写操作前进入数据库操作，而又要晚于写操作更新缓存

##### 先删除缓存，再更新数据库
    1. 线程A删除缓存
    2. 线程B查询，发现没有缓存
    3. 线程B查询数据库，得到旧值
    3. 线程B将旧值写入缓存
    4. 线程A将新值写入数据库
    解决方案：使用队列

#### 线程5个状态
    1. 新建
    2. 可运行
    3. 运行
    4. 等待
    5. 死亡

#### 避免死锁：
    1. 设置加锁顺序
    2. 设置加锁时限
    3. 死锁检测

### MySQL
#### 分区
##### 横向分区
    1. range分区：根据列值所属范围区间，将元组分配到各个区
    2. list分区：基于列值匹配，
    3. hash分区：对表的一列或多列的hash key进行计算，据此分区
    4. key分区：与hash分区类似，但只能使用主键或是唯一键
    5. 子分区：
##### 纵向分区
    表设计阶段，把一些少用的信息，放到另一张表上，等到需要的时候再去查