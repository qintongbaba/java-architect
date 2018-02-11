##java架构
### 一、java并发编程（concurrent）
#### 1.1 线程安全
> 线程安全的概念:当多个线程访问某一个类(方法或对象)时，这个类始终都能表现出正确行为，那么这个类(方法或对象)就是线程安全的。
> synchronized:可以在任意对象及方法上加锁，而加锁的这段代码称为"互斥区"或"临界区"。

示例:[MyThread](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/MyThread.java)

**分析**:  

​	   如果run方法没有添加synchronized修饰的时候，那么这个MyThread不是一个线程安全的类
​	   如果添加了synchronized修饰,当多个线程访问MyThread的run方法时候，以排队的方式进行处理（这里的排队是按照cpu分配的先后而定的）。
  一个线程想要执行synchronized修饰的方法里的代码：
1. 尝试获取锁
2. 如果获取到锁，那么就开始执行synchronized中的代码，如果获取不到，这个线程就会不断的尝试获取锁，直到拿到为止,而且是多个线程同时去竞争这把锁。（也就会出现锁竞争问题）

#### 1.2 多个线程多个锁

> 多个线程多个锁：每个线程都可以拿到自己指定的锁，分别获取锁后，执行synchrionized方法的内容

示例：[MultiThread](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/MultiThread.java)

**分析:**

​	 关键子synchronized取得的锁都是对象锁，而不是把一段代码（方法）当作锁。所以代码中哪个线程先执行synchronized关键子的方法，哪个线程就持有该方法所属对象的锁（Lock）,两个对象，线程就是获取到两个不同的锁，它们互不影响。

​	有一种情况锁是相同的，就是在静态方法上添加synchronized关键字，表示锁定.class类，类级别的锁（独占.class类）。

#### 1.3 对象锁的同步和异步

- **同步：synchronized** 

> 同步的概念就是共享，我们需要牢牢记住“共享”两字，如果不是为了共享资源，就没有必要进行同步。

- **异步：asynchronized**

> 异步的概念就是独立，相互之间不受到任何的制约。就好像在http中的页面发起的ajax请求，我们还可以继续浏览和操作页面内容，二者之间没有关系。

  *同步的目的就是为了线程安全，其实对于线程安全来说，需要满足两个特性*
1. **原子性（同步）**
2. **可见性**

示例:[MyObject](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/MyObject.java)

**分析:**

​	A线程先持有object对象的Lock锁，B线程如果在这个时候调用对象中的同步（synchronized）方法，则需要等待，也就是同步。

​	A线程先持有object对象的Lock锁，B线程可以以异步的方式调用对象中非 synchronized修饰的方法。

#### 1.4 脏读

> 对于对象的同步和异步方法，我们在设计自己的程序的时候，一定需要考虑问题的整体性，不然就会出现数据不一致的错误，很经典的错误就是脏读（dirty read）。

示例:[DirtyRead](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/DirtyRead.java)

**分析：**

​	我们对一个对象的方法加锁的时候，需要考虑业务的整体性，即为setValue和getValue方法同时加锁synchronized同步关键字，保证业务的原子性，不然会出现业务错误。

#### 1.5 synchronized其他概念

- **synchronized锁重入**

> 关键字synchronized拥有锁重入的功能，也就是在使用synchronized时，当一个线程得到一个对象的锁后，再次请求此对象可以再次得到锁。

示例：[SyncDubbo1](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/SyncDubbo1.java)        [SyncDubbo2](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/SyncDubbo2.java)

>  出现异常，锁会自动释放

示例:  [SyncException1](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/SyncException1.java)      [SyncException2](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/SyncException2.java)

  **分析：**

  	对于web应用程序，异常释放锁的情况，如果不及时处理，很可能对你的应用程序业务逻辑产生严重的错误，比如你现在执行队列任务，很多对象都去等待第一个对象正确执行完毕再去释放锁，但是第一个对象由于异常出现，导致业务逻辑没有正常结束完毕，就释放锁，那么可想而知后续的对象执行的都是错误的逻辑。所以这一点一定要引起注意，在编写代码的时候，一定要考虑周全。

#### 1.6 synchronized代码块

> 使用synchronized声明方法在某些情况下是有弊端的，比如A线程调用同步方法调用一个很长时间的任务，那么B线程就必须等待比较长时间才能执行，这样的情况下可以使用synchronized代码块去优化执行时间，也就是通常所说的减小锁粒度。

示例: [Optimize]()

> synchronized可以使用任意Object对象进行加锁，用法比较灵活

示例: [ObjectLock]()

> **特别注意:**不要使用String常量加锁，会出现死循环问题。

示例:[StringLock]()

>锁对象改变问题：当使用一个对象进行加锁的时候，要注意对象本身发生变化的时候，那么锁就不一样了。如果对象本身不发生变化，那么就依然为同步的，即使对象的属性发生变化也不影响。

示例:[ModifyLock]()  

>死锁问题

示例:[DeadLock]()

