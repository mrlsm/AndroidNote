# 设计模式 -- 原型模式

### 定义

用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。

### 目的

创建复杂的或者构造耗时的实例，复制一个已经存在的实例可使程序运行更高效。

### 使用场景

1. 类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等，通过原型拷贝避免这些消耗；
2. 通过 new 产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式；
3. 一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用，即保护性拷贝。

### UML 类图

![类图](https://github.com/mrlsm/Note/blob/master/designPatterns/images/prototype_uml.jpg)

### 举例

**被 clone 的对象即为原型**
clone 分为浅拷贝和深拷贝，在使用时，为了减少错误，尽量使用**深拷贝**，来避免操作副本时对原始对象的影响。

在Android源码中，我们以熟悉的Intent来分析源码中的原型模式。

```
@Override
public Object clone() {
    return new Intent(this);
}

/**
 * Copy constructor.
 */
public Intent(Intent o) {
    this.mAction = o.mAction;
    this.mData = o.mData;
    this.mType = o.mType;
    this.mPackage = o.mPackage;
    this.mComponent = o.mComponent;
    this.mFlags = o.mFlags;
    if (o.mCategories != null) {
        this.mCategories = new ArraySet<String>(o.mCategories);
    }
    if (o.mExtras != null) {
        this.mExtras = new Bundle(o.mExtras);
    }
    if (o.mSourceBounds != null) {
        this.mSourceBounds = new Rect(o.mSourceBounds);
    }
    if (o.mSelector != null) {
        this.mSelector = new Intent(o.mSelector);
    }
    if (o.mClipData != null) {
        this.mClipData = new ClipData(o.mClipData);
    }
}
```

由上面代码可以看出，Intent的内部没有调用super.clone()来进行拷贝对象，而是使用new方式新建。
使用 clone 和 new 需要根据构造对象的成本决定，如果对象的构造成本较高或者麻烦，那么使用 clone 函数效率较高，反之则可以使用 new 的形式。

### 优缺点
- 原型模式是在内存二进制流的拷贝，要比直接 new 一个对象性能好很多，特别是要在一个循环体内产生大量的对象时，原型模式可以更好地体现其优点；
- 但是这既是它的优点也是缺点，直接在内存中拷贝，构造函数是不会执行的，在实际开发当中应该注意这个潜在的问题。优点就是减少了约束，缺点也是减少了约束，需要大家在实际应用时考虑。

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)