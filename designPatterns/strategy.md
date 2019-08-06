# 设计模式 -- 策略模式

### 定义

定义一组算法，将每个算法都封装起来，并且使它们之间可以互换。策略模式让算法独立于使用它的客户而独立变化。本质上就是**分离算法，选择实现**

### 使用场景

- 针对同一类型问题的多种处理方式，仅仅是具体行为有差别时
- 需要安全的封装多种同一类型的操作时
- 出现同一抽象类有多个子类，而又需要使用if-else或者switch-case来选择具体子类时

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/strategy_uml.jpg)

由上图，策略模式 主要包含三种角色：
- **Context**（上下文角色）：用来操作策略的上下文环境；
- **Strategy**（抽象策略角色）：规定策略或算法的行为；
- **ConcreteStrategy**（具体策略角色）：具体的策略或算法实现；

### 优缺点
- 优点
	- 结构清晰明了、使用简单直观。
 	- 耦合度相对而言较低，扩展方便。
	- 操作封装也更为彻底，数据更为安全。。
- 缺点
	- 随着策略的增加，子类也会变得繁多。

### Android 源码中的策略模式的实现

插值器（TimeInterpolator） 
对Animation对象设置不同的插值器就可以实现不同的动态效果。
LinearInterpolator、AccelerateInterpolator、CycleInterpolator等实现Interpolator，通过getInterpolator（float input）获取当前的时间百分比，以此来计算动画的属性值。
