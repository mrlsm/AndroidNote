# 设计模式 -- 迭代器模式

### 定义

提供一种方法顺序访问一个容器对象中的各个元素，而又不需要暴露该对象的内部表示。

### 使用场景

- 遍历一个容器对象时。
	- 当需要为聚合对象提供多种遍历方式时。
	- 当需要为遍历不同的聚合结构提供一个统一的接口时。
	- 当访问一个聚合对象的内容而无须暴露其内部细节的表示时。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/iterator_uml.jpg)

由上图，迭代器模式 主要包含这几种角色：

- **Aggregate**（抽象聚合）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
- **ConcreteAggregate**（具体聚合）角色：实现抽象聚合类，返回一个具体迭代器的实例。
- **Iterator**（抽象迭代器）角色：定义访问和遍历聚合元素的接口，通常包含 hasNext()、first()、next() 等方法。
- **Concretelterator**（具体迭代器）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。

#### 模式代码：

迭代器接口：
```
public interface Iterator<T> {
  /**
   * 是否还有下一个元素
   * @return true表示有，false表示没有
   **/
  boolean hasNext();
  /**
   * 返回当前位置的元素并将位置移至下一位
   **/
  T next();
}
```
具体迭代器类：
```
public class ConcreteIterator<T> implements Iterator<T>{
  private List<T> list;
  private int cursor = 0;
  public ConcreteIterator(List<T> list) {
    this.list = list;
  }
  @Override
  public boolean hasNext() {
    return cursor != list.size();
  }
  @Override
  public T next() {
    T obj = null;
    if (this.hasNext()) {
      obj = this.list.get(cursor++);
    }
    return obj;
  }
}
```
容器接口：
```
public interface Aggregation<T> {
  /**
   * 添加一个元素
   **/
  void add(T obj);
  /**
   * 移除一个元素
   **/
  void remove(T obj);
  /**
   * 获取容器的迭代器
   **/
  Iterator<T> iterator();
}
```
具体容器类：
```
public class ConcreteAggregation<T> implements Aggregation<T>{
  private List<T> list = new ArrayList<>();
  @Override
  public void add(T obj) {
    list.add(obj);
  }
  @Override
  public void remove(T obj) {
    list.remove(obj);
  }
  @Override
  public Iterator<T> iterator() {
    return new ConcreteIterator<>(list);
  }
}
```
客户类：
```
public class Client {
  public static void main(String args[]) {
    Aggregation<String> a = new ConcreteAggregation<>();
    a.add("a");
    a.add("b");
    a.add("c");
    Iterator<String> iterator = a.iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next());
    }
  }
}
```

### 优缺点
- 优点：
    - 支持对容器对象的多种遍历。弱化了容器类与遍历算法之间的关系。
    - 访问一个聚合对象的内容而无须暴露它的内部表示，遍历任务交由迭代器完成，这简化了聚合类。
	- 它支持以不同方式遍历一个聚合，甚至可以自定义迭代器的子类以支持新的遍历。
	- 封装性良好，为遍历不同的聚合结构提供一个统一的接口。增加新的聚合类和迭代器类都很方便，无须修改原有代码。
- 缺点：
    - 增加了类的个数，这在一定程度上增加了系统的复杂性。
	- 遍历过程是一个单向且不可逆的遍历。

### Android 源码中的迭代器模式的实现

##### Cursor

当我们使用SQLiteDatabase的query方法查询数据库时，会返回一个Cursor游标对象，该游标的实质就是一个具体的迭代器，我们可以使用它来遍历数据库查询所得的结果集。

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)