# 设计模式 -- 享元模式

### 定义

使用共享对象可有效的支持大量的细粒度对象。

### 使用场景

- 系统中存在大量的相似对象
- 对象没有特定的身份，状态都较接近
- 需要缓冲池的场景

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/adapter_uml.jpg)

由上图，享元模式 主要包含这三种角色：

- **Flyweight**是抽象享元角色。它是产品的抽象类，同时定义出对象的外部状态和内部状态（外部状态及内部状态相关内容见后方）的接口或实现；
- **ConcreteFlyweight**是具体享元角色，是具体的产品类，实现抽象角色定义的业务；
- **UnsharedConcreteFlyweight**是不可共享的享元角色，一般不会出现在享元工厂中；
- **FlyweightFactory**是享元工厂，它用于构造一个池容器，同时提供从池中获得对象的方法。

### 优缺点
- 优点：
    - 大大减少了对象的创建，降低了程序内存的占用，提高效率。
- 缺点：
    - 提高了系统的复杂度。需要分离出内部状态和外部状态，而外部状态具有固化特性，不应该随着内部状态的改变而改变。

### Android 源码中的享元模式的实现

##### Handler 中 Message

在Android中时常会用Handler来实现线程间通信，如在子线程中进行网络请求，然后发送一个Message通知主线程进行UI刷新。其中Message消息池正是享元模式思想的体现。

创建Message对象有两种方式，一种是new出新对象，一种是Message.obtain()获取对象，后者则是官方推荐的方式。
```
    public static final Object sPoolSync = new Object();
    private static Message sPool;
    private static int sPoolSize = 0;
    // sometimes we store linked lists of these things
    Message next;
    /**
     * Return a new Message instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }
```
sPool和next都是Message对象，sPool作为链表（消息池）头指针，next指向下一个可用的Message，最后一个可用的Message的next则指向null。
接下来看看Message是如何进行存入的。当创建Message的时候不会被存入消息池，而是在使用完回收时（这个回收不是指虚拟机回收对象）被存入。

```
    public void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new IllegalStateException("This message cannot be recycled because it "
                        + "is still in use.");
            }
            return;
        }
        recycleUnchecked();
    }

    /**
     * Recycles a Message that may be in-use.
     * Used internally by the MessageQueue and Looper when disposing of queued Messages.
     */
    void recycleUnchecked() {
        // Mark the message as in use while it remains in the recycled object pool.
        // Clear out all other details.
        flags = FLAG_IN_USE;
        what = 0;
        arg1 = 0;
        arg2 = 0;
        obj = null;
        replyTo = null;
        sendingUid = -1;
        when = 0;
        target = null;
        callback = null;
        data = null;

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }
```
recycle方法将Message对象回收到消息池（链表），先判断该消息是否还在使用，如果还在使用则抛出异常，否则调用recycleUnchecked方法处理该消息。recycleUnchecked方法先清空和重置Message对象的字段，接着如果当前消息池大小小于MAX_POOL_SIZE（值为50），则将自身的next指向当前消息池头部（如果有，没则指向null），然后将sPool指向自身，即链表中的头插法。

##### 总结：
Message通过在内部构建一个链表来维护一个被回收的Message对象的消息池，当用户调用obtain方法时会优先从池中取，如果池中没有可复用的对象才会创建新的Message对象。这些新创建的对象会在被使用完之后被回收到这个大小为50的对象池。对象池的对象复用可以避免内存占用高、频繁GC等问题。
