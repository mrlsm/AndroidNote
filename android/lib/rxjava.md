# RxJava 内核原理

> 一个实现异步操作的库

在正文开始之前，放上 GitHub 链接和引入依赖的 gradle 代码： 

Github：
- [https://github.com/ReactiveX/RxJava](https://github.com/ReactiveX/RxJava)
- [https://github.com/ReactiveX/RxAndroid](https://github.com/ReactiveX/RxAndroid)

引入依赖：
```
// Please replace x and y with the latest version numbers: Maven Central
implementation "io.reactivex.rxjava3:rxjava:3.x.y"

implementation 'io.reactivex.rxjava2:rxjava:2.x.x'
```

### 优势
随着程序逻辑变得越来越复杂，它依然能够保持简洁。

### 如何做到线程异步切换

> 通过一种扩展的观察者模式来实现的。

#### 1. 创建 Observer
#### 1. 创建 Observable
#### 1. Subscribe (订阅)