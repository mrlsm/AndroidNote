# 设计模式 -- 中介者模式

### 定义

中介者模式包装了一系列对象互相作用的方式，使得这些对象不必相互明显作用。从而使它们可以松散偶合。当某些对象之间的作用发生改变时，不会立即影响其他的一些对象之间的作用。保证这些作用可以彼此独立的变化。、中介者模式将多对多的相互作用转化为一对多的相互作用。
中介者模式将对象的行为和协作抽象化，把对象在小尺度的行为上与其他对象的相互作用分开处理。

### 使用场景

- 当对象之间的交互操作很多且每个对象的行为操作都依赖彼此时，为防止在修改一个对象的行为时，同时涉及修改很多其他对象的行为，可采用中介者模式，来解决紧耦合问题。
- 将对象之间的多对多关系变成一对多关系，中介者对象将系统从网状结构变成以调停者为中心的星形结构，达到降低系统的复杂性，提高可扩展性的作用。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/mediator_uml.jpg)

由上图，中介者模式 主要包含这两种角色：

- **Mediator**：抽象的中介者角色，定义了同事对象到中介者的接口。
- **ConcreteMediator**：具体的中介者角色，从具体的同事对象接收消息，同时向具体的同事对象发出命令。
- **Colleague**：抽象同事类角色，定义了中介者对象的接口，只知道中介而不知道其他同事对象。
- **ConcreteColleagueA，ConcreteColleagueB**：具体的同事类角色，每个具体同事类都知道本身在小范围内的行为，而不知道他在大范围中的行为。

#### 模板代码：

抽象的中介者：
```
public interface Mediator {
  void change();
}
```
具体的中介者：
```
public class ConcreteMediator implements Mediator {
  public ConcreteColleagueA concreteColleagueA;
  public ConcreteColleagueB concreteColleagueB;
  public void setConcreteColleagueA(ConcreteColleagueA concreteColleagueA) {
    this.concreteColleagueA = concreteColleagueA;
  }
  public void setConcreteColleagueB(ConcreteColleagueB concreteColleagueB) {
    this.concreteColleagueB = concreteColleagueB;
  }
  @Override
  public void change() {
    concreteColleagueA.action();
    concreteColleagueB.action();
  }
}
```
抽象的同事：
```
public abstract class Colleague {
  public Mediator mediator;
  public Colleague(Mediator mediator) {
    this.mediator = mediator;
  }
  public abstract void action();
}
```
具体的同事A：
```
public class ConcreteColleagueA extends Colleague {
  public ConcreteColleagueA(Mediator mediator) {
    super(mediator);
  }
  @Override
  public void action() {
    System.out.println("交给中介做A的事情");
  }
}
```
具体的同事B：
```
public class ConcreteColleagueB extends Colleague {
  public ConcreteColleagueB(Mediator mediator) {
    super(mediator);
  }
  @Override
  public void action() {
    System.out.println("交给中介做B的事情");
  }
}
```

### 优缺点
- 优点：
    - 适当地使用中介者模式可以避免同事类之间的过度耦合，使得各同事类之间可以相对独立地使用。
	- 使用中介者模式可以将对象的行为和协作进行抽象，能够比较灵活的处理对象间的相互作用。
	- 使用中介者模式可以将对象间多对多的关联转变为一对多的关联，使对象间的关系易于理解和维护。
- 缺点：
    - 大多数情况下，将对象间的依赖关系封装的同事类内部就可以的，没有必要非引入中介者模式。滥用中介者模式，只会让事情变的更复杂。

#### 备注

中介者模式将多对多的相互作用转化为一对多的相互作用，将系统从网状结构变为以中介者为中心的星形结构（这里就是主板），达到降低系统的复杂性，提高可扩展性。

### Android 源码中的中介者模式的实现

##### Keyguard解锁屏

详细机制参考：[Android4.0 Keyguard解锁屏机制](https://www.jb51.net/article/131631.htm)
