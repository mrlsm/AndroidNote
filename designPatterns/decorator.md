# 设计模式 -- 装饰模式

### 定义

动态的给一个对象添加一些额外的职责，就增加功能来说，装饰模式相比生成子类更为灵活。

### 使用场景

- 需要透明且动态的扩展类的功能时。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/decorator_uml.jpg)

由上图，访问者模式 主要包含这几种角色：

- **Component**（抽象构件）角色：给出一个抽象接口，以规范准备接收附加责任的对象。
- **Concrete Component**（具体构件）角色：定义一个将要接收附加责任的类。
- **Decorator**（装饰）角色：持有一个构件（Component）对象的实例，并实现一个与抽象构件接口一致的接口。
- **Concrete Decorator**（具体装饰）角色：负责给构件对象添加上附加的责任。

### 优缺点
- 优点：
    - Decorator模式与继承关系的目的都是要扩展对象的功能，但是Decorator可以提供比继承更多的灵活性。
	- 通过使用不同的具体装饰类以及这些装饰类的排列组合，设计师可以创造出很多不同行为的组合。
- 缺点：
	- 这种比继承更加灵活机动的特性，也同时意味着更加多的复杂性。
	- 装饰模式会导致设计中出现许多小类，如果过度使用，会使程序变得很复杂。


### Android 源码中的访问者模式的实现

##### 由Context抽象类扩展出的ContextWrapper
继承结构如下图所示：
![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/decorator_context.jpg)

1. Context就是我们的抽象组件，它提供了应用运行的基本环境，是各组件和系统服务通信的桥梁，隐藏了应用与系统服务通信的细节，简化了上层应用的开发。所以Contex就是“装饰模式”里的Component。
2. Context类是个抽象类，android.app.ContextImpl派生实现了它的抽象接口。ContextImpl对象会与Android框架层的各个服务（包括组件管理服务、资源管理服务、安装管理服务等）建立远程连接，通过对Android进程间的通信机制（IPC）和这些服务进行通信。所以ContextImpl就是“装饰模式”里的ConcreteComponent。
3. 如果上层应用期望改变Context接口的实现，就需要使用android.content.ContextWrapper类，它派生自Context，其中具体实现都是通过组合的方式调用ContextImpl类的实例（在ContextWrapper中的private属性mBase）来完成的。这样的设计，使得ContextImpl与ContextWrapper子类的实现可以单独变化，彼此独立。所以可以看出ContextWrapper就是“装饰模式”里的Decorator。
4. Android的界面组件Activity、服务组件Service以及应用基类Application都派生于ContextWrapper，它们可以通过重载来修改Context接口的实现。所以可以看出Activity、服务组件Service以及应用基类Application就是“装饰模式”里的具体装饰角色A、B、C。

详情可见： [装饰模式在Android源码中的应用](https://www.cnblogs.com/yemeishu/archive/2012/12/30/2839489.html)

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)