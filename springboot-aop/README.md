# SpringBoot 中使用 AOP

引入依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.2.12.RELEASE</version>
</dependency>
```

# AOP 基本注解

> `AOP`(Aspect-Oriented Programming，面向切面编程)，它利用一种"横切"的技术，将那些多个类的共同行为封装到一个可重用的模块。便于减少系统的重复代码，降低模块之间的耦合度，并有利于未来的可操作性和可维护性。

下面是 AOP 相关注解介绍：

- @Aspect：标注在类上，向 Spring 声明该类是切面类；
- @Pointcut：切点，参数是一个表达式，声明围绕该表达式指定的方法进行相关操作
  - execution()：表达式主体，表达式可以是某个注解，或者是某个包下某个类的某个方法
- @Before：前置通知，拦截目标方法执行之前执行；
- @After：后置通知，拦截目标方法执行之后执行，如果目标方法异常，则后置通知不再执行，则跳转到 @AfterThrowing 所在的方法处；
- @Around：环绕通知，相当于前置通知和后置通知的结合体。
  - 环绕通知必须有返回参数，不然会没有数据；
  - 环绕通知必须执行 proceed() 方法，也就是调用的那个方法，不然会报错；
- @AfterReturning：目标方法返回后执行，也就是环绕通知或后置通知后执行，发生异常则不执行；
- @AfterThrowing：在方法抛出异常后执行；

注：通知执行的顺序为：@Around - @Before - 切点逻辑代码 - @After - @AfterReturning

# 自定义注解介绍

下面两个是自定义注解时需要的注解，用以声明注解作用范围和保留级别

- @Target：这个注解就是表明该注解类能够作用的范围，也就是能够注解在哪，比如 类、方法、参数等。

  里面的参数是可以多选的，使用方法比如 @Target({ElementType.METHOD,ElementType.TYPE})

  | 使用方法                             | 作用范围             |
  | ------------------------------------ | -------------------- |
  | @Target(ElementType.TYPE)            | 接口、类、枚举、注解 |
  | @Target(ElementType.FIELD)           | 字段、枚举的常量     |
  | @Target(ElementType.METHOD)          | 方法                 |
  | @Target(ElementType.PARAMETER)       | 方法参数             |
  | @Target(ElementType.CONSTRUCTOR)     | 构造函数             |
  | @Target(ElementType.LOCAL_VARIABLE)  | 局部变量             |
  | @Target(ElementType.ANNOTATION_TYPE) | 注解                 |
  | @Target(ElementType.PACKAGE)         | 包                   |

- @Retention：这个注解是保留说明，也就是表明这个注解所注解的类能在哪里保留。
  - RetentionPolicy.SOURCE：这种类型的 Annotations 只在源代码级别保留,编译时就会被忽略
  - RetentionPolicy.CLASS：这种类型的 Annotations 编译时被保留,在 class 文件中存在,但 JVM 将会忽略
  - RetentionPolicy.RUNTIME： 这种类型的 Annotations 将被JVM保留,所以他们能在运行时被 JVM 或其他使用反射机制的代码所读取和使用。





