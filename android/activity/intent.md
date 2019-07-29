# Intent 详解

> 各个组件、进程之间通信的纽带

### Intent 数据结构
- action： 所要执行的行为动作
- data: 要使用的数据（Uri）
- category： 关于目标组件的信息
- component： 目标组件的类名
- extras： Bundle数据

一般两种方式将数据写入 Intent
- Intent的putExtra方法，存入普通键值对，value可以是int，short，float，String简单类型，也可以是Bean对象复杂类型。
- Intent的putExtras方法，存入一个Bundle对象，它封装了多个键值对。


## Intent 相关原理解析

### App信息表的构建

系统启动之后就会注册各种系统服务，如  WindowManagerService、ActivityManagerService、PackageManagerService ，其中有一个就是PackageManagerService ，简称 PMS 。

PMS 启动之后，会扫描系统中已安装的 APK 目录，包括系统 APP 的安装目录 /sysytem/app, 第三方应用的目录 /data/app ，PMS 会解析 apk 包下的 AndroidManifest.xml 文件得到 App 的相关信息，而每个 AndroidManifest.xml 又包含了 Activity，Service 等组件的注册信息，当 PMS 扫描并解析完这些信息之后就构建好了整个 apk 的信息树

大致流程如下：
![](https://github.com/mrlsm/Note/blob/master/android/activity/images/intent_start.jpg)

### PMS 解析 apk 信息的过程

PackageManagerService 的功能
1. 获取 /data 目录
2. 加载 FrameWork 资源 framework-res.apk
3. 加载核心库 core-libart.jar
4. 扫描系统app的安装路径
5. 扫描第三方aoo的安装路径
6. 将所有Activity节点、BroadcastReceiver节点、Service节点、ContentProvider节点保存到该类中

总结： PMS将所有已安装的apk的相关信息保存到系统中，当用户使用Intent跳转到某Activity或者Service时，系统就会在整个信息表中进行查找，复核要去的组件则会被启动起来，这样就通过Intent将各个组件连接到一起。

### startActivity 的过程

详情可见 [startActivity启动过程分析](http://gityuan.com/2016/03/12/start-activity/)

总结：
启动流程：
1. 点击桌面App图标，Launcher进程采用Binder IPC向system_server进程发起startActivity请求；
2. system_server进程接收到请求后，向zygote进程发送创建进程的请求；
3. Zygote进程fork出新的子进程，即App进程；
4. App进程，通过Binder IPC向sytem_server进程发起attachApplication请求；
5. system_server进程在收到请求后，进行一系列准备工作后，再通过binder IPC向App进程发送scheduleLaunchActivity请求；
6. App进程的binder线程（ApplicationThread）在收到请求后，通过handler向主线程发送LAUNCH_ACTIVITY消息；
7. 主线程在收到Message后，通过发射机制创建目标Activity，并回调Activity.onCreate()等方法。

可查看下方流程图
![](https://github.com/mrlsm/Note/blob/master/android/activity/images/startActivity.jpg)

### Intent 数据传递原理

1. 通过Intent在组件间传递的数据，保存在Intent内部Bundle的ArrayMap结构。本质，数据写入Parcel，跨进程传递类型，数据在底层Parcel保存。
2. Intent#writeToParcel方法，将Intent到系统数据和我们的自定义数据，全部写入Parcel，自定义数据时，遍历ArrayMap，将key和value写入Parcel。
3. Parcelable类型数据传递，本质，写入String类型的key，再将实体每个字段，根据类型对应writeXxx方法，依次写入。
4. Intent实现Parcelable接口，Ams服务回调App进程时，组件生命周期前，新建，从Parcel读取初始化内部变量如mFlags，readBundle方法创建内部Bundle，将Parcel设置内部即mParcelledData，此时不解析，在读取数据时，Bundle的#unparcel方法，将解析它。
5. 从Parcel读取自定义数据到ArrayMap时，先读取String的key值，再从Parcel读取int类型type，简单类型通过JNI#方法直接读取内容，复杂类型如Parcelable，将利用实体内部的静态内部类CREATOR创建实例，依次读取Parcel中该实体字段。
6. Activity组件启动#onCreate方法，通过getIntent方法，获取的Intent和发起者组件在startActivity方法传入的是两个完全不同到对象，Activity组件实例化后，attach方法赋值，即mIntent。

详情可参考： [Intent 数据传递原理](https://www.jianshu.com/p/ae1aa13a3f51)

## 涉及问题点

### Intent如何实现对象传递？

- **implements Serializable**：这是Java的序列化技术，将Java对象序列化为二进制文件。让对象实现Serializable接口，使用**ObjectInputStream** 和 **ObjectOutputStream** 进行对象读写。
- **implements Parcelable**：这是Android提供的用作封装数据的容器，封装后的数据可以通过Intent或IPC来传递。只有基本类型和实现了Parcelable接口的类才能被放入Parcel中。

**ps：** 为什么Java对象有的实现了的Serializable接口还要实现Parcelable接口？

Parcelable使用起来虽然更复杂一点，但是它的性能更好。

如何实现 Parcelable 接口？

1. 实现 **describeContents** 方法；
2. 实现抽象方法 writeToParcel，用于获取对象的当前状态并写入一个Parcel容器中；
3. 给该目标类添加一个静态域 *CREATOR *，它是一个实现了Parcelable.Creator接口的对象；
4. 添加一个参数为一个Parcel对象的构造函数，CREATOR会调用这个构造函数来重新改造我们的对象。

### 为何Intent不能直接在组件间传递对象而要通过序列化机制？

Intent在启动其他组件时，会离开当前应用程序进程，进入ActivityManagerService进程（intent.prepareToLeaveProcess()），这也就意味着，**Intent所携带的数据要能够在不同进程间传输，即bundle的数据要在进程间传递**。
Android是基于Linux系统，不同进程之间的java对象是无法传输，所以我们此处要对对象进行序列化，从而实现对象在**应用程序进程 和 ActivityManagerService进程** 之间传输。
而Parcel或者Serializable都可以将对象序列化，其中，Serializable使用方便，但性能不如Parcel容器，后者也是Android系统专门推出的用于进程间通信等的接口。

#### 进程之间具体传递对象的方法是？

在不同进程之间，常规数据类型可以直接传递，如整数，以传递字符串为例，要从A进程传递到B进程，只需在B进程的内存区开辟一样大小的空间，然后复制过去即可。
但是，对象却不能直接跨进程传递。即使成员变量值能传递过去，成员方法是无法传递过去的，此时如果B进程要调用成员方法则出错。
具体传递对象的方法：
1. 在进程Ａ中把类中的非默认值的属性和类的唯一标志打成包（这就叫序列化）；
2. 把这个包传递到进程Ｂ；
3. 进程Ｂ接收到包后，根据类的唯一标志把类创建出来（java反射机制）；
4. 然后把传来的属性更新到类对象中。
这样进程Ａ和进程Ｂ中就包含了两个完全一样的类对象。


##### 参考
[《Android源码设计模式解析与实战》](https://book.douban.com/subject/26644935/)
[startActivity启动过程分析](http://gityuan.com/2016/03/12/start-activity/)

##### 备注
详细可见我的github [Note](https://github.com/mrlsm/Note)


