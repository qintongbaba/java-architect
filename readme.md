##java架构
### 1.java-concurrent  java并发编程
#### 1.1 线程安全
> 线程安全的概念:当多个线程访问某一个类(方法或对象)时，这个类始终都能表现出正确行为，那么这个类(方法或对象)就是线程安全的。
> synchronized:可以在任意对象及方法上加锁，而加锁的这段代码称为"互斥区"或"临界区"。
> 示例:[MyThread](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/MyThread.java)

**分析**:  
​	   如果run方法没有添加synchronized修饰的时候，那么这个MyThread不是一个线程安全的类
​	   如果添加了synchronized修饰,当多个线程访问MyThread的run方法时候，以排队的方式进行处理（这里的排队是按照cpu分配的先后而定的）。
  一个线程想要执行synchronized修饰的方法里的代码：
1. 尝试获取锁
2. 如果获取到锁，那么就开始执行synchronized中的代码，如果获取不到，这个线程就会不断的尝试获取锁，直到拿到为止,而且是多个线程同时去竞争这把锁。（也就会出现锁竞争问题）

#### 1.2多个线程多个锁

> 多个线程多个锁：每个线程都可以拿到自己指定的锁，分别获取锁后，执行synchrionized方法的内容

示例：[MultiThread]

**分析:**

​	 关键子synchronized取得的锁都是对象锁，而不是把一段代码（方法）当作锁。所以代码中哪个线程先执行synchronized关键子的方法，哪个线程就持有该方法所属对象的锁（Lock）,在静态方法上添加synchronized关键字，表示锁定.class类，类级别的锁（独占.class类）。



