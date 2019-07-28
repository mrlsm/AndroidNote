# 面向对象的六大原则

### 1、单一职责原则（Single Responsibility Principle）

> 优化代码第一步

- 内容：主要就是含义就是 “单一” 一个类中应该是一组相关性很高的函数、数据的封装。
- 好处：类的复杂度降低、可读性提高、可维护性提高、扩展性提高、降低了变更引起的风险。
- 注意：单一职责原则提出了一个编写程序的标准，可以不断审视自己的代码，根据具体业务、功能对类进行相应的划分。

### 2、开放封闭原则（Open Close Principle）

> 让程序更灵活、更稳定。

- 内容：当软件需要变化时，尽量通过扩展软件实体来解决需求变化，而不是通过修改已有的代码来完成变化。
- 好处：去除“代码腐化”，保证原有模块的正确性，使之更灵活、更稳定。

### 3、里氏替换原则（Liskov Substitution Principle）

> 构建扩展性更好的系统

- 内容：建立抽象，通过抽象建立规范，具体实现在运行时替换掉抽象。也就是在使用基类的的地方可以任意使用其子类，能保证子类完美替换基类。
- 好处：增强程序的健壮性，即使增加了子类，原有的子类还可以继续运行。
- 注意：如果子类不能完整地实现父类的方法，或者父类的某些方法在子类中已经发生“畸变”，则建议断开父子继承关系 采用依赖、聚合、组合等关系代替继承。


### 4、依赖倒置原则（Dependence Inversion Principle）

> 让项目拥有更好的系统。

- 内容：高层模块不应该依赖底层模块，二者都该依赖其抽象；抽象不应该依赖细节；细节应该依赖抽象；
- 好处：依赖倒置的好处在小型项目中很难体现出来。但在大中型项目中可以减少需求变化引起的工作量。使并行开发更友好。


### 5、接口隔离原则（Interface Segregation Principle）

> 系统有更高的灵活性

- 内容：类间的依赖关系应该建立在最小的接口上，也就是要用最小化接口隔离来实现类的细节，不要建立庞大臃肿的接口，尽量细化接口，接口中的方法尽量少。
- 好处：使系统具有更低的耦合性、更高的灵活性。
- 注意：接口尽量小，但是要有限度。对接口进行细化可以提高程序设计灵活性，但是如果过小，则会造成接口数量过多，使设计复杂化。所以一定要适度；提高内聚，减少对外交互。使接口用最少的方法去完成最多的事情


##### 以上五个原则定义为：SOLID原则，作为面向对象的5个基本原则

### 6、迪米特法则（Law of Demeter）

> 更好的扩展性

- 内容：最少知识原则，一个对象应该对其他对象有最少的了解，也就是一个类对自己依赖的类知道的越少越好。调用者或者依赖者只需要知道它需要的方法即可。
- 好处：低耦合，高内聚

####参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)
[快速理解-设计模式六大原则](https://www.jianshu.com/p/807bc228dbc2)

详细可见我的github [Note](https://github.com/mrlsm/Note)