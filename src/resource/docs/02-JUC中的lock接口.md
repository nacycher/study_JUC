# Lock接口

## Synchronized回顾
synchronized是java中的关键字，是一种同步锁。

可以用来修饰一下几种情况：
#### 修饰一个代码块：
被修饰的代码叫做同步代码块，起作用范围是被{}括起开的代码，作用的对象是调用这个代码块的对象。
#### 修饰一个方法：
被修饰的方法叫同步方法，起作用范围是整个方法，作用的对象是调用这个方法的对象。
##### Synchronized tips：
Synchronized不具备继承属性，即父类方法中使用了Synchronized，子类继承的方法不具有同步，需要显示的再声明在子类方法中。
#### 修饰一个类：
作用范围是整个类，作用的对象是这个类的所有对象。

### synchronized 实例：售票

## 创建线程的几种方式
1.继承Thread类
2.实现runnable接口
3.实现callable接口
4.线程池    


