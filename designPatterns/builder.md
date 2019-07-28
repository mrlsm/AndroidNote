# 设计模式 -- 建造者模式（Builder）

### 定义

将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

### 目的

为了将构建复杂对象的过程和它的部件解耦，是构建过程和部件的表示隔离开。

### 使用场景

1. 相同的方法，不同的执行顺序，产生不同的事件结果时；
2. 多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时；
3. 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的效能，这个时候使用建造者模式非常合适；
4. 当初始化一个对象十分复杂，参数多，且大多参数存在默认值时；

### UML 类图

![类图](http://https://github.com/mrlsm/Note/blob/master/designPatterns/images/builder_uml.jpg)

### 举例

AlertDialog.Builder  使用该Builder来构建复杂的AlertDialog对象。
相关源码：

```
public class AlertDialog extends Dialog implements DialogInterface {
    // Controller, 接受Builder成员变量P中的各个参数
    private AlertController mAlert;

    // 构造函数
    protected AlertDialog(Context context, int theme) {
        this(context, theme, true);
    }

    // 4 : 构造AlertDialog
    AlertDialog(Context context, int theme, boolean createContextWrapper) {
        super(context, resolveDialogTheme(context, theme), createContextWrapper);
        mWindow.alwaysReadCloseOnTouchAttr();
        mAlert = new AlertController(getContext(), this, getWindow());
    }

    // 实际上调用的是mAlert的setTitle方法
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mAlert.setTitle(title);
    }

    // 实际上调用的是mAlert的setCustomTitle方法
    public void setCustomTitle(View customTitleView) {
        mAlert.setCustomTitle(customTitleView);
    }
    
    public void setMessage(CharSequence message) {
        mAlert.setMessage(message);
    }

    // AlertDialog其他的代码省略
    
    // ************  Builder为AlertDialog的内部类   *******************
    public static class Builder {
        // 1 : 存储AlertDialog的各个参数, 例如title, message, icon等.
        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int theme) {
            P = new AlertController.AlertParams(new ContextThemeWrapper(
                    context, resolveDialogTheme(context, theme)));
            mTheme = theme;
        }
        
        // Builder的其他代码省略 ......

        // 2 : 设置各种参数
        public Builder setTitle(CharSequence title) {
            P.mTitle = title;
            return this;
        }
        
        public Builder setMessage(CharSequence message) {...}

        public Builder setIcon(int iconId) {...}
        
        public Builder setPositiveButton(CharSequence text, final OnClickListener listener) {...}
        
        
        public Builder setView(View view) {...}
        
        // 3 : 构建AlertDialog, 传递参数
        public AlertDialog create() {
            // 调用new AlertDialog构造对象， 并且将参数传递个体AlertDialog 
            final AlertDialog dialog = new AlertDialog(P.mContext, mTheme, false);
            // 5 : 将P中的参数应用的dialog中的mAlert对象中
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }
    }
    
}

```
不难发现通过Builder来设置AlertDialog中的title, message, button等参数， 这些参数都存储在类型为AlertController.AlertParams的成员变量P中，AlertController.AlertParams中包含了与之对应的成员变量。在调用Builder类的create函数时才创建AlertDialog, 并且将Builder成员变量P中保存的参数应用到AlertDialog的mAlert对象中，即P.apply(dialog.mAlert)代码段。

### 优缺点
- 优点
	- 良好的封装性， 使用建造者模式可以使客户端不必知道产品内部组成的细节；
	- 建造者独立，容易扩展；
	- 在对象创建过程中会使用到系统中的一些其它对象，这些对象在产品对象的创建过程中不易得到。
- 缺点
	- 会产生多余的Builder对象以及Director对象，消耗内存；
	- 对象的构建过程暴露。

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)