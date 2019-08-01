# 设计模式 -- 观察者模式

### 定义

定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖它的对象都会得到通知并自动更新。

### 使用场景

- 关联行为场景，需注意关联行为是可拆分的，而不是组合关系。
- 事件多级触发场景。
- 跨系统的消息交换场景。如消息队列、事件总线的处理机制。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/observer_uml.jpg)

由上图，观察者模式 主要包含这几种角色：

- **Subject**（被观察的对象接口）:规定ConcreteSubject的统一接口; 每个Subject可以有多个Observer；
- **ConcreteSubject**（具体被观察对象）:维护对所有具体观察者的引用的列表；状态发生变化时会发送通知给所有注册的观察者。
- **Observer**（观察者接口）:规定ConcreteObserver的统一接口；定义了一个update()方法，在被观察对象状态改变时会被调用。
- **ConcreteObserver**（具体观察者）:维护一个对ConcreteSubject的引用；特定状态与ConcreteSubject同步；实现Observer接口，update()方法的作用：一旦检测到Subject有变动，就更新信息。

### 优缺点
- 优点：
    - 观察者与被观察者之间是抽象耦合，增加了系统灵活性和可扩展性。
- 缺点：
    - 如一个主题被大量观察者注册，则通知所有观察者会花费较高代价，如果某些观察者的响应方法被阻塞，整个通知过程即被阻塞，其它观察者不能及时被通知，一般考虑才有**异步**的方式

### Android 源码中的观察者模式的实现

##### ListView

ListView 调用 setAdapter 方法，将一个观察者注入 Adapter 中维护的观察者序列，这样 ListView 和 Adapter 就建立起了关联，当有了数据变化，我们调用了 Adapter 的 notifyDataSetChanged 方法，内部会遍历出观察者，ListView是观察者，Adapter 是被观察者。ListView对Adapter订阅。有了数据更新后，Adapter通知ListView，告诉 ListView ，你可以更新视图了，之后 ListView调用 requestLayout 方法重新绘制界面，完成视图的更新。

##### Broadcast

详情可见 [四大组件之BroadcastRecord](http://gityuan.com/2017/06/03/broadcast_record/)

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)