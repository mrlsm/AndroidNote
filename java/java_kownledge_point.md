#java 基础 知识点，易错点，面试点 总结

### 1. 关于JVM、JDK和JRE
##### JVM
- 含义： 运行 Java 字节码的虚拟机。
- 目的：针对不同系统的特定实现（Windows，Linux，macOS），使用相同的字节码，它们都会给出相同的结果。使一份程序运行至不同平台。字节码和不同系统的 JVM 实现是 Java 语言“一次编译，随处可以运行”的关键所在。

##### 如何理解Java字节码

> 在 Java 中，JVM可以理解的代码就叫做字节码（即扩展名为 .class 的文件），它不面向任何特定的处理器，只面向虚拟机。Java 语言通过字节码的方式，在一定程度上解决了传统解释型语言执行效率低的问题，同时又保留了解释型语言可移植的特点。所以 Java 程序运行时比较高效，而且，由于字节码并不针对一种特定的机器，因此，Java程序无须重新编译便可在多种不同操作系统的计算机上运行。

Java 程序从源代码到运行一般有下面两步：
1. 源代码 ==> 字节码文件
  - JDK中的javac编译成 .class文件
2. 字节码文件 ==> 可执行的机器码
  - JVM 类加载器首先加载字节码文件，然后通过解释器逐行解释执行，这种方式的执行速度会相对比较慢。而且，有些方法和代码块是经常需要被调用的(也就是所谓的热点代码)，所以后面引进了 JIT 编译器，而JIT 属于运行时编译。当 JIT 编译器完成第一次编译后，其会将字节码对应的机器码保存下来，下次可以直接使用。而我们知道，机器码的运行效率肯定是高于 Java 解释器的。这也解释了我们为什么经常会说 Java 是编译与解释共存的语言。
  - JDK 9引入了一种新的编译模式AOT(Ahead of Time Compilation)，它是直接将字节码编译成机器码，这样就避免了JIT预热等各方面的开销。JDK支持分层编译和AOT协作使用。但是 ，AOT 编译器的编译质量是肯定比不上 JIT 编译器的。

##### JDK 和 JRE
###### JDK (Java Development Kit)
> 功能齐全的Java SDK。等同于 JRE + 编译器（javac）+ 工具（如javadoc和jdb）。能够创建和编译程序。

###### JRE (Java Runtime Environment)
> Java运行时环境。它是运行已编译 Java 程序所需的所有内容的集合，包括 Java虚拟机（JVM），Java类库，java命令和其他的一些基础构件。但是，它不能用于创建新程序。

###### 总结
要运行一下 Java 程序的话，只需要安装 JRE 就可以了。如果需要进行一些 Java 编程方面的工作，那么你就需要安装JDK了。但是，这不是绝对的。有时，即使您不打算在计算机上进行任何Java开发，仍然需要安装JDK。例如，如果要使用JSP部署Web应用程序，那么从技术上讲，您只是在应用程序服务器中运行Java程序。那你为什么需要JDK呢？因为应用程序服务器会将 JSP 转换为 Java servlet，并且需要使用 JDK 来编译 servlet。

### 2. Java和C++的区别
- 都是面向对象的语言，都支持封装、继承和多态
- Java 不提供指针来直接访问内存，程序内存更加安全
- Java 的类是单继承的，C++ 支持多重继承；虽然 Java 的类不可以多继承，但是接口可以多继承。
- Java 有自动内存管理机制，不需要程序员手动释放无用内存

### 3. 接口和抽象类的区别
- 接口的方法默认是 public，所有方法在接口中不能有实现(Java 8 开始接口方法可以有默认实现），而抽象类可以有非抽象的方法。
- 接口中除了static、final变量，不能有其他变量，而抽象类中则不一定。
- 一个类可以实现多个接口，但只能实现一个抽象类。接口自己本身可以通过extends关键字扩展多个接口。
- 接口方法默认修饰符是public，抽象方法可以有public、protected和default这些修饰符（抽象方法就是为了被重写所以不能使用private关键字修饰！）。
- 从设计层面来说，**抽象是对类的抽象，是一种模板设计，而接口是对行为的抽象，是一种行为的规范。**

### 4. String StringBuffer 和 StringBuilder的差异
##### String
> 使用 final 关键字修饰字符数组来保存字符串，内部持有一个final 类型的字符数组，所以 String 对象是不可变的。
> StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串char[]value 但是没有用 final 关键字修饰，所以这两种对象都是可变的。
#####  StringBuffer
>  内部方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。
> 每次都会对 StringBuffer 对象本身进行操作，而不是生成新的对象并改变对象引用。
#####  StringBuilder
> 没有对方法进行加同步锁，所以是非线程安全的。
> 对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。

##### 使用总结
- 操作少量的数据: 适用String
- 单线程操作字符串缓冲区下操作大量数据: 适用StringBuilder
- 多线程操作字符串缓冲区下操作大量数据: 适用StringBuffer

### 5. == 与 equals 的区别
##### ==
> 它的作用是判断两个对象的地址是不是相等。即，判断两个对象是不是同一个对象(基本数据类型==比较的是值，引用数据类型==比较的是内存地址)。
##### equals
> 判断两个对象是否相等
> 1. 类没有覆盖 equals() 方法。则通过 equals() 比较该类的两个对象时，等价于通过“==”比较这两个对象。
> 2. 类覆盖了 equals() 方法。一般，我们都覆盖 equals() 方法来比较两个对象的内容是否相等；若它们的内容相等，则返回 true (即，认为这两个对象相等)。
##### String 的 equals 方法说明
- String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值。
- 当创建 String 类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个 String 对象。
- Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals。

### 6. hashCode() 和 equals()
##### hashCode() 的作用
> hashCode() 的作用是获取哈希码，也称为散列码；它实际上是返回一个int整数。这个哈希码的作用是确定该对象在哈希表中的索引位置。

equals上述已有说明
更多可见 [Java hashCode() 和 equals()的若干问题解答](https://www.cnblogs.com/skywang12345/p/3324958.html)

### 7. 线程的状态
![image.png](https://upload-images.jianshu.io/upload_images/5142935-a739f3524769e8f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
由图片可知：
- 线程创建之后它将处于 **NEW（新建）** 状态，调用 start() 方法后开始运行，线程这时候处于** READY（可运行）** 状态。可运行状态的线程获得了 cpu 时间片（timeslice）后就处于** RUNNING（运行）** 状态。
- 当线程执行 wait()方法之后，线程进入 **WAITING（等待）**状态。进入等待状态的线程需要依靠其他线程的通知才能够返回到运行状态，而 TIME_WAITING(超时等待) 状态相当于在等待状态的基础上增加了超时限制，比如通过 sleep（long millis）方法或 wait（long millis）方法可以将 Java 线程置于**TIMED WAITING** 状态。当超时时间到达后 Java 线程将会返回到 RUNNABLE 状态。当线程调用同步方法时，在没有获取到锁的情况下，线程将会进入到** BLOCKED（阻塞）** 状态。线程在执行 Runnable 的run()方法之后将会进入到 **TERMINATED（终止）** 状态。

### 8. Java关键字 
可见 [Java 关键字总结](https://www.jianshu.com/p/69dd654ea149)

### 9. Arraylist 与 LinkedList 区别
1. 底层结构
  - ArrayList: 底层使用的是 Object 数组
  - LinkedList: 底层使用的是 双向链表 数据结构（JDK1.6之前为循环链表，JDK1.7取消了循环。）
2. 是否保证线程安全
 - ArrayList 和 LinkedList 都是不同步的，不保证线程安全。
3. 操作时间复杂度
 - ArrayList 采用数组存储，所以插入和删除元素的时间复杂度受元素位置的影响。 比如：执行add(E e) 方法的时候， ArrayList 会默认在将指定的元素追加到此列表的末尾，这种情况时间复杂度就是O(1)。但是如果要在指定位置 i 插入和删除元素的话（add(int index, E element) ）时间复杂度就为 O(n-i)。因为在进行上述操作的时候集合中第 i 和第 i 个元素之后的(n-i)个元素都要执行向后位/向前移一位的操作。
- LinkedList 采用链表存储，所以插入，删除元素时间复杂度不受元素位置的影响，都是近似 O（1）而数组为近似 O（n）。
4. 是否支持快速随机访问（快速随机访问就是**通过元素的序号快速获取元素对象**(对应于get(int index) 方法)）
> 具有快速随机访问主要看是否实现了 RandomAccess 接口，但是源码 RandomAccess 接口中什么都没有定义。所以， RandomAccess 接口是一个标识， 标识实现这个接口的类具有随机访问功能。
- ArrayList 支持
  - ArrayList 底层是数组，而 LinkedList 底层是链表。数组天然支持随机访问，时间复杂度为 O（1），所以称为快速随机访问。
  - 链表需要遍历到特定位置才能访问特定位置的元素，时间复杂度为 O（n），所以不支持快速随机访问。
  - ArrayList 实现了 RandomAccess 接口，就表明了他具有快速随机访问功能。 RandomAccess 接口只是标识，并不是说 ArrayList 实现 RandomAccess 接口才具有快速随机访问功能的！
- LinkedList 不支持
5. 内存空间占用
- ArrayList的空间浪费主要体现在在list列表的结尾会预留一定的容量空间。
- LinkedList的空间花费则体现在它的每一个元素都需要消耗比ArrayList更多的空间（因为要存放直接后继和直接前驱以及数据）。

### 10. HashMap 和 Hashtable 的区别
1. 线程是否安全
  - HashMap 是非线程安全的，  因为线程安全的问题，HashMap 要比 HashTable 效率高一点。当需要多线程操作的时候可以使用线程安全的ConcurrentHashMap。ConcurrentHashMap虽然也是线程安全的，但是它的效率比Hashtable要高好多倍。因为ConcurrentHashMap使用了分段锁，并不对整个数据进行锁定。
  - HashTable 是线程安全的；HashTable 内部的方法基本都经过synchronized 修饰。另外，现在HashTable 基本被淘汰，基本都会使用HashMap
2. 对Null key 和Null value的支持
  - HashMap 中，null 可以作为键，这样的键只有一个，可以有一个或多个键所对应的值为 null。
  - HashTable 中 put 进的键值只要有一个 null，直接抛出 NullPointerException。
3. 底层数据结构
  - JDK1.8 之前 HashMap 底层是 数组和链表 结合在一起使用也就是 链表散列。
  - HashTable 底层是一个单向链表
4. 初始容量大小和每次扩充容量大小的不同 
  - 创建时如果不指定容量初始值：  
    - HashMap 默认的初始化大小为16。之后每次扩充，容量变为原来的2倍。
    - Hashtable 默认的初始大小为11，之后每次扩充，容量变为原来的2n+1。
  - 创建时如果给定了容量初始值：
    - HashMap 会将其扩充为2的幂次方大小（HashMap 中的tableSizeFor()方法保证，下面给出了源代码）。也就是说 HashMap 总是使用2的幂作为哈希表的大小,后面会介绍到为什么是2的幂次方。
    - Hashtable 会直接使用你给定的大小。
##### 备注
关于详细分析HashMap 可见： [Java 8系列之重新认识HashMap](https://zhuanlan.zhihu.com/p/21673805)

### 11. 如何理解分析Java内存区域
> Java 虚拟机在执行 Java 程序的过程中会把它管理的内存划分成若干个不同的数据区域。

- 线程私有的：
  - 程序计数器
用于记录当前线程执行的位置 （**唯一一个不会出现OutOfMemoryError的内存区域，它的生命周期随着线程的创建而创建，随着线程的结束而死亡。**）
  - 虚拟机栈（栈内存）
 Java 方法执行的内存模型、编译器可知的各种数据类型。
  - 本地方法栈
虚拟机使用到的 Native 方法服务。
- 线程共享的：
  - 堆
存放对象实例，几乎所有的对象实例以及数组都在这里分配内存。
  - 方法区
是各个线程共享的内存区域，用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。
  - 直接内存
频繁地使用的内存部分

##### 详情可见： [可能是把 Java 内存区域讲的最清楚的一篇文章](https://github.com/Snailclimb/JavaGuide/blob/3965c02cc0f294b0bd3580df4868d5e396959e2e/Java%E7%9B%B8%E5%85%B3/%E5%8F%AF%E8%83%BD%E6%98%AF%E6%8A%8AJava%E5%86%85%E5%AD%98%E5%8C%BA%E5%9F%9F%E8%AE%B2%E7%9A%84%E6%9C%80%E6%B8%85%E6%A5%9A%E7%9A%84%E4%B8%80%E7%AF%87%E6%96%87%E7%AB%A0.md)

### 12. Thread sleep() 和 wait() 方法的区别 
- 两者最主要的区别在于：sleep 方法没有释放锁，而 wait 方法释放了锁 。
- 两者都可以暂停线程的执行。
- sleep()是线程线程类（Thread）的方法，调用会暂停此线程指定的时间，但监控依然保持，不会释放对象锁，到时间自动恢复，或者可以使用wait(long timeout)超时后线程会自动苏醒；wait()是Object的方法，调用会放弃对象锁，进入等待队列，线程不会自动苏醒，需要别的线程调用同一个对象上的 notify() 或者 notifyAll() 方法。
- Wait 通常被用于线程间交互/通信，sleep 通常被用于暂停执行。