# 设计模式 -- 访问者模式

### 定义

封装一些作用于某种数据结构中的各元素的操作，它可以在不改变这个数据结构的前提下定义作用于这些元素的新的操作。

### 使用场景

- 对象结构比较稳定，但经常需要在此对象结构上定义新的操作。
- 需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免这些操作”污染“这些对象的类，也不希望在增加新操作时修改这些类。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/visitor_uml.jpg)

由上图，访问者模式 主要包含这几种角色：

- **Visitor**：接口或抽象类，定义了对每一个元素的访问行为，参数就是可访问的元素，方法个数理论上是个元素个数一样的。因此，访问者模式要求被访问的对象结构要稳定，如果经常增删元素，必然会导致频繁修改Visitor接口，就不适合用访问者模式了。
- **ConcreteVisitor**：具体的访问者，定义具体的对每一个元素的具体访问行为。
- **Element**：抽象的元素接口或抽象类，定义了一个接待访问者的方法，让每个元素都可以被访问者访问。
- **ElementA，ElementB**：具体的元素类，提供接收访问方法的具体实现。这个具体实现通常是调用访问者提供的访问该元素的方法。

### 优缺点
- 优点：
    - 各角色职责分离，符合单一职责原则。
 	- 具有优秀的扩展性、灵活性。
	- 使得数据结构和作用于结构上的操作解耦，使得操作集合可以独立变化。
- 缺点：
    - 具体元素对访问者公布细节，违反了迪米特原则。
	- 具体元素变更时导致修改成本大。
	- 违反了依赖倒置原则，为了达到“区别对待”而依赖了具体类，没有依赖抽象。

#### 备注

如果要增加一个访问者，你新创建一个实现了Visitor接口的类，然后实现两个visit方法来对不同的元素进行不同的操作，从而达到数据对象与数据操作相分离的效果。如果不使用访问者模式，而又想对不同元素进行不同的操作，那么必定会使用if-else和类型转换，这使得代码难以升级维护。

### Android 源码中的访问者模式的实现

##### Element

安卓中的著名开源库ButterKnife、Dagger、Retrofit都是基于APT（Annotation Processing Tools）实现。而编译注解核心依赖APT。当我们通过APT处理注解时，最终会将获取到的元素转换为相应的Element元素，以便获取到它们对应信息。

ElementVisitor就是访问者类型，ElementVisitor源码如下：

```
public interface ElementVisitor<R, P> {
  /**
   * Visits an element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visit(Element e, P p);
  /**
   * A convenience method equivalent to {@code v.visit(e, null)}.
   * @param e the element to visit
   * @return a visitor-specified result
   */
  R visit(Element e);
  /**
   * Visits a package element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visitPackage(PackageElement e, P p);
  /**
   * Visits a type element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visitType(TypeElement e, P p);
  /**
   * Visits a variable element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visitVariable(VariableElement e, P p);
  /**
   * Visits an executable element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visitExecutable(ExecutableElement e, P p);
  /**
   * Visits a type parameter element.
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   */
  R visitTypeParameter(TypeParameterElement e, P p);
  /**
   * Visits an unknown kind of element.
   * This can occur if the language evolves and new kinds
   * of elements are added to the {@code Element} hierarchy.
   *
   * @param e the element to visit
   * @param p a visitor-specified parameter
   * @return a visitor-specified result
   * @throws UnknownElementException
   * a visitor implementation may optionally throw this exception
   */
  R visitUnknown(Element e, P p);
}

```

在ElementVisitor中定义了多种visit接口，每个接口处理一种元素类型，那么这就是典型的访问者模式。

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)