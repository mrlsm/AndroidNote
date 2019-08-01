# 设计模式 -- 命令模式

### 定义

将一个请求封装成一个对象，从而让用户使用不同的请求把客户端参数化；对请求排队或者记录请求日志，以及支持可撤销的操作。

### 使用场景

- 需要抽象出待执行的动作，然后以参数的形式提供出来——类似于过程设计中的回调机制，而命令模式正是回调机制的一个面向对象的替代品。
- 在不同的时刻指定、排列和执行请求。一个命令对象可以有与初始请求无关的生存期。
- 需要支持取消操作。
- 支持修改日志功能，这样当系统崩溃时，这些修改可以被重做一遍。
- 需要支持事务操作。

### UML 类图

![](https://github.com/mrlsm/Note/blob/master/designPatterns/images/command_uml.jpg)

由上图，命令模式 主要包含这几种角色：
- **Receiver**：接收者角色，该类负责具体实施或执行一个请求，说得通俗点就是，执行具体逻辑的角色，任何一个类都可以成为一个接收者，而在接收者类中封装具体操作逻辑的方法我们则称为行动方法。
- **Command**：命令角色，定义所有具体命令类的抽象接口。
- **ConcreteCommand**：具体命令角色，该类实现了Command接口，在execute方法中调用接收者角色的相关方法，在接收者和命令执行的具体行为之间加以弱耦合。而execute则通常称为执行方法，具体可能还包含很多相关的操作，比如保存数据、关闭文件、结束进程等，如果将这一系列具体的逻辑处理看作接收者，那么调用这些具体逻辑的方法就可以看作是执行方法。
- **Invoker**：请求者角色，该类的职责就是调用命令对象执行具体的请求，相关的方法我们称为行动方法。
- **Client**：客户端类，Client可以创建具体的命令对象，并且设置命令对象的接收者。不能把Clinet理解为我们平常说的客户端，这里的Client是一个组装命令对象和接受者对象的角色，或者你把它理解为一个装配者。

### 优缺点
- 优点：
    - 命令模式的封装性很好，更弱的耦合性，更灵活的控制性以及更好的扩展性。
- 缺点：
    - 每个语法都要产生一个非终结符表达式，语法规则比较复杂时，就可能产生大量的类文件，为维护带来了非常多的麻烦。
	- 类的膨胀，大量衍生类的创建。

### Android 源码中的命令模式的实现

##### PackageHandler

PackageManagerService中，其对包的相关消息处理右其内部类PackageHandler承担，其将需要处理的请求作为对象通过消息传递给相关的方法，而对于包的安装、移动以及包大小的测量则分别封装为HandlerParams的具体子类InstallParams、MoveParams和MeasureParams。

```
// 一个抽象命令者
private abstract class HandlerParams {
    private static final int MAX_RETRIES = 4;
    /**
     * Number of times startCopy() has been attempted and had a non-fatal
     * error.
     */
    private int mRetries = 0;
    final boolean startCopy() {
      boolean res;
      try {
        if (DEBUG_INSTALL) Slog.i(TAG, "startCopy");
        if (++mRetries > MAX_RETRIES) {
          Slog.w(TAG, "Failed to invoke remote methods on default container service. Giving up");
          mHandler.sendEmptyMessage(MCS_GIVE_UP);
          handleServiceError();
          return false;
        } else {
          handleStartCopy();
          res = true;
        }
      } catch (RemoteException e) {
        if (DEBUG_INSTALL) Slog.i(TAG, "Posting install MCS_RECONNECT");
        mHandler.sendEmptyMessage(MCS_RECONNECT);
        res = false;
      }
      handleReturnCode();
      return res;
    }
    final void serviceError() {
      if (DEBUG_INSTALL) Slog.i(TAG, "serviceError");
      handleServiceError();
      handleReturnCode();
    }
    abstract void handleStartCopy() throws RemoteException;
    abstract void handleServiceError();
    abstract void handleReturnCode();
}

```

##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)