# 设计模式 -- 桥接模式

### 定义

将抽象部分与实现部分分离，使它们都可以独立的变化。

### 使用场景

- 如果一个系统需要在构件的抽象化角色和具体化角色之间添加更多的灵活性，避免在两个层次之间建立静态的联系。
- 设计要求实现化角色的任何改变不应当影响客户端，或者实现化角色的改变对客户端是完全透明的。
- 需要跨越多个平台的图形和窗口系统上。
- 一个类存在两个独立变化的维度，且两个维度都需要进行扩展。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/bridge_uml.jpg)

由上图，桥接模式 主要包含这几种角色：

- **Abstraction**(抽象化)角色：抽象化给出的定义，并保存一个对实现化对象的引用。 修正抽象化(Refined Abstraction)角色：扩展抽象化角色，改变和修正父类对抽象化的定义。
- **Implementor**(实现化)角色：这个角色给出实现化角色的接口，但不给出具体的实现。必须指出的是，这个接 口不一定和抽象化角色的接口定义相同，实际上，这两个接口可以非常不一样。实现化角色应当只给出底层操作，而抽象化角色应当只给出基于底层操作的更高一层的操作。
- **ConcreteImplementor**(具体实现化)角色：这个角色给出实现化角色接口的具体实现。

### 优缺点
- 优点：
	- 实现了抽象和实现部分的分离
		- 桥接模式分离了抽象部分和实现部分，从而极大的提供了系统的灵活性，让抽象部分和实现部分独立开来，分别定义接口，这有助于系统进行分层设计，从而产生更好的结构化系统。对于系统的高层部分，只需要知道抽象部分和实现部分的接口就可以了。
	- 更好的可扩展性
		- 由于桥接模式把抽象部分和实现部分分离了，从而分别定义接口，这就使得抽象部分和实现部分可以分别独立扩展，而不会相互影响，大大的提供了系统的可扩展性。
	- 可动态的切换实现
		- 由于桥接模式实现了抽象和实现的分离，所以在实现桥接模式时，就可以实现动态的选择和使用具体的实现。
	- 实现细节对客户端透明，可以对用户隐藏实现细节。
- 缺点：
    - 桥接模式的引入增加了系统的理解和设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计和编程。
	- 桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围有一定的局限性。


### Android 源码中的桥接模式的实现

##### ListView和BaseAdapter

首先ListAdapter.java：
```
public interface ListAdapter extends Adapter{
    //继承自Adapter，扩展了自己的两个实现方法
      public boolean areAllItemsEnabled();
      boolean isEnabled(int position);
}  
```
这里先来看一下父类AdapterView
```
public abstract class AdapterView<T extends Adapter> extends ViewGroup {  
    //这里需要一个泛型的Adapter
      public abstract T getAdapter();
     public abstract void setAdapter(T adapter);  
}  
```
接着来看ListView的父类AbsListView，继承自AdapterView
```
public abstract class AbsListView extends AdapterView<ListAdapter>   
    //继承自AdapterView,并且指明了T为ListAdapter
    /**
     * The adapter containing the data to be displayed by this view
     */
    ListAdapter mAdapter;  
    //代码省略
    //这里实现了setAdapter的方法，实例了对实现化对象的引用
    public void setAdapter(ListAdapter adapter) {
        //这的adapter是从子类传入上来，也就是listview，拿到了具体实现化的对象
        if (adapter != null) {
            mAdapterHasStableIds = mAdapter.hasStableIds();
            if (mChoiceMode != CHOICE_MODE_NONE && mAdapterHasStableIds &&
                    mCheckedIdStates == null) {
                mCheckedIdStates = new LongSparseArray<Integer>();
            }
        }
        if (mCheckStates != null) {
            mCheckStates.clear();
        }
        if (mCheckedIdStates != null) {
            mCheckedIdStates.clear();
        }
    }
```

大家都知道，构建一个listview，adapter中最重要的两个方法，getCount()告知数量，getview()告知具体的view类型，接下来看看AbsListView作为一个视图的集合是如何来根据实现化对象adapter来实现的具体的view呢？
```
protected void onAttachedToWindow() {
    super.onAttachedToWindow();
        
        //省略代码，
        //这里在加入window的时候，getCount()确定了集合的个数
        mDataChanged = true;
        mOldItemCount = mItemCount;
        mItemCount = mAdapter.getCount();
    }
}
```
接着来看
```
View obtainView(int position, boolean[] isScrap) {
    //代码省略
    //这里根据位置显示具体的view,return的child是从持有的实现对象mAdapter里面的具体实现的
    ​//方法getview来得到的。
    final View child = mAdapter.getView(position, scrapView, this);
    //代码省略
    return child;
}  
```
接下来在ListView中，onMeasure调用了obtainView来确定宽高，在扩展自己的方法来排列这些view。


##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)  
                                                                   

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)