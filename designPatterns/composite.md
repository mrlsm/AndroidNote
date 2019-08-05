# 设计模式 -- 组合模式

### 定义

将对象组合成树形结构来表示 “部分-整体” 的层次结构，使得用户对单个对象和组合对象的使用具有一致性。

### 使用场景

- 表示对象的部分-整体层次结构时
- 从一个整体中能够独立出部分模块或者功能时

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/composite_uml.jpg)

由上图，组合模式 主要包含这两种角色：

- **Component**（组合部件）：它是一个抽象角色，为要组合的对象提供统一的接口。
- **Leaf**（叶子）：在组合中表示子节点对象，叶子节点不能有子节点。
- **Composite**（合成部件）：定义有枝节点的行为，用来存储部件，实现在Component接口中的有关操作，如增加（Add）和删除（Remove）。

### 优缺点
- 优点：
	- 可详细的定义分层次的复杂对象，用户可以忽略层次的差异。
	- 基本对象可以被组合成复杂组合对象，这个组合对象也可以被组合，这样不断递归，客户端调用基本对象的地方都可以使用组合对象。
	- 方便将新的叶子构件放入容器构件中。
- 缺点：
    - 使得设计更加复杂。客户端需要花更多时间理清类之间的层次关系。


### Android 源码中的组合者模式的实现

##### View 和 ViewGroup 的嵌套组合

Android源码中View是一个类（不是抽象类，不是接口），即我们的抽象构件。ViewGroup是View的子类，是一个抽象类，作为所有View的容器，即我们的树枝构件。而具体到特定的控件，比如Button、TextView等是我们的树叶构件。

ViewGroup实现了一个叫ViewManager的接口，ViewGroup实现这个接口的方法，所有就有了管理View的能力。

那么Google的开发者为什么要这么设计Android的View体系呢？组合模式可以让高层模块忽略了层次的差异，方便对整个层次结构进行控制。比如我们要向ViewGoup添加一个TextView或者添加一个LinearLayout，对这个ViewGroup来说是一样的。

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)