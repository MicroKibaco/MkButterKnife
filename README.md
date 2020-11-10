



之前在学习组件化的时候,有一个组件生命周期插件源码让我百思不得其解,究其原因基础没过关,之前的两篇文章,一篇是ASM一篇是AspectJ,还有关如何自定义Plugin系列,反响还不错,果然理论 + 实践才是王道。现在准备将APT也补上,希望以后也能像大佬一样随意定制化插件。
### 一.APT概述

APT 是 Annotation Processor Tool 的缩写,是一种处理注解的工具,准确的来说,它是javac 的一个工具,用于在编译时扫描和处理注解。注解处理器以java代码(编译过的代码)作为输入,以生成.java文件为输出。
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c5259454b23e431aa52323b56f830c5c~tplv-k3u1fbpfcp-watermark.image)
### 二. Element

为什么要了解Element,自定义注解器,需要继承 AbstractProcessor,对于AbstractProcessor来说,最重要的是 process 方法,process 方法处理的核心就是Element对象,下面我们看一下Element具体接口方法或子接口吧~

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1f3efff34e3840b4849c5f6c5823ffb7~tplv-k3u1fbpfcp-watermark.image)

### 三. AnnotationProcessor
#### 1.1 什么是Annotation(注解)
元数据,标记可以在编译,类加载,运行时被读取,并执行相应的处理
#### 1.2 Annotation有什么用?
通过使用注解,可以在不改变原有的逻辑下,在源文件中嵌入一些补充信息,代码分析采集可以利用这些补充信息进行验证或者进行部署
#### 1.3 Annotation类型
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/59f04d6b234d4622b99ba066f09574e6~tplv-k3u1fbpfcp-watermark.image)
#### 1.4 Annotation Processor 实质原理
Javac编译器编根据注解(Annotation)获取相关数据,解决重复编码的问题
### 四. 自定义ButterKnife实战
#### 4.1 ButterKnife是什么?
ButterKnife是一个编译时依赖注入框架,主要目的是用来简化android中类似findViewById、setOnclickListener等的template代码。
#### 4.1 ButterKnife是什么?
ButterKnife是一个编译时依赖注入框架,主要目的是用来简化android中类似findViewById、setOnclickListener等的template代码。
#### 4.2 ButterKnife功能介绍
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4570440be7be4ed5b3998f902e00a0e7~tplv-k3u1fbpfcp-watermark.image)
### 用反射实现 ButterKnife
- Bind class
- bind(Activity) method
- 用反射获取Field[],然后获取 Annotation BindView

###  ButterKnife 是依赖注入吗?

什么是依赖注入: 把依赖的决定权交给外部,即依赖注入
- ButterKnife: 自己决定的依赖的获取,只是执行过程交给 ButterKnife
所以ButterKnife 只是一个 View Binding 库,而不是依赖注入



#### 3.1 项目结构
![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3ff901bccc0e48b0bb7c27acd7af79b4~tplv-k3u1fbpfcp-watermark.image)
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cd1199c372ea4ee5b13867aa91d2cc42~tplv-k3u1fbpfcp-watermark.image)

#### 3.2 实现步骤
##### 第一步: 自定义BindView注解 @MkBindView
在apt-annotation新建一个注解 @MkBindView
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/43d0046181424c6099bb8849c7b3742b~tplv-k3u1fbpfcp-watermark.image)
##### 第二步: 添加模块依赖关系
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d7a6f5e230a84971b0a787854aa07b7b~tplv-k3u1fbpfcp-watermark.image)
##### 第三步: 创建注解处理器
在apt-processor里定义注解处理器MkBindViewProcessor

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d63a9d066ff249ffbc8ef748c3a3ba43~tplv-k3u1fbpfcp-watermark.image)
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e9b29c76d62b45208c1493f64499b1fb~tplv-k3u1fbpfcp-watermark.image)

来看一下MkBindViewProcessor的父类AbstractProcessor实现吧

![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7654dd7d541742c4ac8bdcd31058fb36~tplv-k3u1fbpfcp-watermark.image)

##### 第四步: 编写 Bind View 业务代码
##### 第五步: 在应用程序使用注解处理器
##### 第六步: 利用 javapoet 将 .Bind 文件生成 .class 文件
###### 6.1 添加依赖关系
###### 6.2 添加构建生成java代码
###### 6.3 生成java文件




