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

​	对于web应用程序，异常释放锁的情况，如果不及时处理，很可能对你的应用程序业务逻辑产生严重的错误，比如你现在执行队列任务，很多对象都去等待第一个对象正确执行完毕再去释放锁，但是第一个对象由于异常出现，导致业务逻辑没有正常结束完毕，就释放锁，那么可想而知后续的对象执行的都是错误的逻辑。所以这一点一定要引起注意，在编写代码的时候，一定要考虑周全。

#### 1.6 synchronized代码块

> 使用synchronized声明方法在某些情况下是有弊端的，比如A线程调用同步方法调用一个很长时间的任务，那么B线程就必须等待比较长时间才能执行，这样的情况下可以使用synchronized代码块去优化执行时间，也就是通常所说的减小锁粒度。

示例: [Optimize](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/Optimize.java)

> synchronized可以使用任意Object对象进行加锁，用法比较灵活

示例: [ObjectLock](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/ObjectLock.java)

> **特别注意:**不要使用String常量加锁，会出现死循环问题。

示例:[StringLock](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/StringLock.java)

>锁对象改变问题：当使用一个对象进行加锁的时候，要注意对象本身发生变化的时候，那么锁就不一样了。如果对象本身不发生变化，那么就依然为同步的，即使对象的属性发生变化也不影响。

示例:[ChangeLock](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/ChangeLock.java)        [ModifyLock](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/ModifyLock.java)  

>死锁问题

示例:[DeadLock](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/DeadLock.java)

#### 1.7 volatile关键字

> volatile关键字:主要作用是使变量在多个线程之间可见

示例:[RunThread](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/thread/RunThread.java)

**分析：**

### 二、线程之间的通信

#### 2.1线程之间的通信

> 线程之间通信概念：线程是操作系统中独立的个体，但是这些个体如果不经过特殊处理就不能成为一个整体，线程之间的通信就成为整体必用的方式之一。当线程存在通信指挥，系统间交互性会更加的强大，在提高CPU利用率的同时还会使开发人员对线程任务处理的过程中进行有效的把控与监督。

**使用wait/notify方法实现线程间的通信。（注意这两个方法都试Object类的方法）**

**1.wait和notify必须配合synchronized关键字使用**

**2.wait方法释放锁，notify方法不释放锁**

示例:[ListAdd1](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/communicate/ListAdd1.java)        [ListAdd2](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/communicate/ListAdd2.java)

#### 2.2 使用wait和notify模拟Queue

> 去模拟一个BlockingQueue:首先它是一个队列，并且支持阻塞的机制，阻塞的放入和得到数据。

示例：[MyQueue](https://github.com/qintongbaba/java-architect/blob/master/java-concurrent/src/main/java/org/wuqinghua/communicate/MyQueue.java)

#### 2.3 ThreadLocal

> ThreadLocal概念:线程局部变量，是一种多线程间并发访问变量的解决方案。与其synchronized等加锁方式不同，ThreadLocal完全不提供锁，而使用空间换时间的手段，为每个线程提供变量的独立副本，以保障线程安全。
>
> 从性能上说，ThreadLocal不具有绝对的优势，在并发不是很高的情况下，加锁的效率会更加好。但是在高并发量或者竞争激烈的场景下，使用ThreadLocal可以在一定程度上减少锁竞争。

#### 2.4 单例&多线程

​	