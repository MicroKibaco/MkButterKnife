> 改不完的 Bug，写不完的矫情。公众号 [杨正友](http://raw.githubusercontent.com/MicroKibaco/MicroKibaco/main/wechat-public-account.png) 现在专注音视频和 APM ，涵盖各个知识领域；只做全网最Geek的公众号，欢迎您的关注！




之前在学习组件化的时候,有一个组件生命周期插件源码让我百思不得其解,究其原因[Annotation Processing Tools](https://juejin.im/post/6844903892694597639))基础没过关,之前的两篇文章,一篇是ASM一篇是AspectJ,还有关如何自定义Plugin系列,反响还不错,果然理论 + 实践才是王道。现在准备将APT也补上,希望以后也能像大佬一样随意定制化插件。
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
#### 4.2 ButterKnife功能介绍
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4570440be7be4ed5b3998f902e00a0e7~tplv-k3u1fbpfcp-watermark.image)
#### 4.3 用反射实现 ButterKnife
- Bind class
- bind(Activity) method
- 用反射获取Field[],然后获取 Annotation BindView

>  ButterKnife 是依赖注入吗?

什么是依赖注入: 把依赖的决定权交给外部,即依赖注入
- ButterKnife: 自己决定的依赖的获取,只是执行过程交给 ButterKnife
所以ButterKnife 只是一个 View Binding 库,而不是依赖注入



#### 4.4 项目结构
![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3ff901bccc0e48b0bb7c27acd7af79b4~tplv-k3u1fbpfcp-watermark.image)
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cd1199c372ea4ee5b13867aa91d2cc42~tplv-k3u1fbpfcp-watermark.image)

#### 4.5 实现步骤
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

- @SupportedAnnotationTypes
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7ad53030f61141ef8385a21e792e6e09~tplv-k3u1fbpfcp-watermark.image)
- @SupportedSourceVersion
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3190e40a5a3e474fa28ac7092af98415~tplv-k3u1fbpfcp-watermark.image)
- process
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/576b36d1a3c74c3485eed1436a2c51b8~tplv-k3u1fbpfcp-watermark.image)
###### 第一步

通过roundEnvironment.getElementsAnnotatedWith(MkBindView.class)获得所以含有@MkBindView注解的element集合 

```java   
        //得到所有的注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MkBindView.class);
```        

###### 第二步
 通过 classElement.getQualifiedName() 可以获取类的完整包名和类名

```java   
       String fullClassName = classElement.getQualifiedName().toString();
```

###### 第三步

```java   
VariableElement variableElement = (VariableElement) element;
     //获取类信息
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
```

###### 第四步
将element信息以"包名 + 类名"为key保存在mClassCreatorFactoryMap中

```java   
  MkClassCreatorFactory proxy = mClassCreatorFactoryMap.get(fullClassName);
            if (proxy == null) {
                proxy = new MkClassCreatorFactory(mElementUtils, classElement);
                mClassCreatorFactoryMap.put(fullClassName, proxy);
            }
```

###### 第五步
通过mClassCreatorFactoryMap对应的java文件,其中mClassCreatorFactoryMap是MkClassCreatorFactory的Map集合

```java   
    MkBindView bindAnnotation = variableElement.getAnnotation(MkBindView.class);
            //获取 View 的 id
            int id = bindAnnotation.value();
            proxy.putElement(id, variableElement);
```

 
 ###### MkClassCreatorFactory源码查看


```java   
public class MkClassCreatorFactory {
    private String mBindingClassName;
    private String mPackageName;
    private TypeElement mTypeElement;
    private Map<Integer, VariableElement> mVariableElementMap = new HashMap<>();

    MkClassCreatorFactory(Elements elementUtils, TypeElement classElement) {
        this.mTypeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(mTypeElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = mTypeElement.getSimpleName().toString();
        this.mPackageName = packageName;
        this.mBindingClassName = className + "_SensorsDataViewBinding";
    }

    public void putElement(int id, VariableElement element) {
        mVariableElementMap.put(id, element);
    }

    /**
     * 创建 Java 代码
     *
     * @return String
     */
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("/**\n" +
                " * Auto Created by SensorsData APT\n" +
                " */\n");
        builder.append("package ").append(mPackageName).append(";\n");
        builder.append('\n');
        builder.append("public class ").append(mBindingClassName);
        builder.append(" {\n");

        generateBindViewMethods(builder);
        builder.append('\n');
        builder.append("}\n");
        return builder.toString();
    }

    /**
     * 加入Method
     *
     * @param builder StringBuilder
     */
    private void generateBindViewMethods(StringBuilder builder) {
        builder.append("\tpublic void bindView(");
        builder.append(mTypeElement.getQualifiedName());
        builder.append(" owner ) {\n");
        for (int id : mVariableElementMap.keySet()) {
            VariableElement element = mVariableElementMap.get(id);
            String viewName = element.getSimpleName().toString();
            String viewType = element.asType().toString();
            builder.append("\t\towner.");
            builder.append(viewName);
            builder.append(" = ");
            builder.append("(");
            builder.append(viewType);
            builder.append(")(((android.app.Activity)owner).findViewById( ");
            builder.append(id);
            builder.append("));\n");
        }
        builder.append("  }\n");
    }

    /**
     * 使用 javapoet 创建 Java 代码
     * javapoet
     *
     * @return TypeSpec
     */
    public TypeSpec generateJavaCodeWithJavapoet() {
        TypeSpec bindingClass = TypeSpec.classBuilder(mBindingClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateMethodsWithJavapoet())
                .build();
        return bindingClass;

    }

    /**
     * 使用 javapoet 创建 Method
     *
     * @return MethodSpec
     */
    private MethodSpec generateMethodsWithJavapoet() {
        ClassName owner = ClassName.bestGuess(mTypeElement.getQualifiedName().toString());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bindView")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(owner, "owner");

        for (int id : mVariableElementMap.keySet()) {
            VariableElement element = mVariableElementMap.get(id);
            String viewName = element.getSimpleName().toString();
            String viewType = element.asType().toString();
            methodBuilder.addCode("owner." + viewName + " = " + "(" + viewType + ")(((android.app.Activity)owner).findViewById( " + id + "));");
        }
        return methodBuilder.build();
    }


    public String getPackageName() {
        return mPackageName;
    }

    public String getProxyClassFullName() {
        return mPackageName + "." + mBindingClassName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }
}
```


以上代码主要是从Elements, TypeElement 中获得 一些想要的信息,如: packName,Activity 名,变量类型,id等,通过StringBuilder拼接代码,每个对象分别代表一个对应的.java文件

##### 第四步: 编写 Bind View 业务代码

- 新增bindView方法
在上面的 MkBindViewProcessor 我们动态创建了 xxxActivity_MKViewBinding.java文件,在我们的反射里面我们需要调用bindView完成View的绑定

```java 
public class MkButterKnife {
    public static void bindView(Activity activity) {

        Class clazz = activity.getClass();
        try {
            Class<?> bindViewClass = Class.forName(clazz.getName() + "_MkViewBinding");
            Method method = bindViewClass.getMethod("bindView", activity.getClass());
            method.invoke(bindViewClass.newInstance(), activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

```

- 添加apt-annotation 注解依赖

```groovy
dependencies { 
    implementation project(':apt-annotation')
}
```

##### 第五步: 在应用程序使用注解处理器

配置完整的配置脚本内容

```groovy
dependencies {
 
    implementation project(':apt-annotation')
    implementation project(':apt-sdk')
    annotationProcessor project(':apt-processor')
}

```

注解处理器依赖的模块是 annotationProcessor,最后在Activity里使用 @MkBindView

```java
public class MainActivity extends AppCompatActivity {
    @MkBindView(R.id.button)
    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MkButterKnife.bindView(this);

        button.setText("New Text");
    }
}
```


![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aa64b3fe30d24eb896c78e4712307161~tplv-k3u1fbpfcp-watermark.image)



##### 第六步: 利用 javapoet 将 .Bind 文件生成 .class 文件
###### 6.1 添加依赖关系

```groovy
dependencies {
    implementation project(':apt-annotation')
    implementation 'com.google.auto.service:auto-service:1.0-rc2'
    implementation 'com.squareup:javapoet:1.10.0'
}
```



###### 6.2 添加构建生成java代码
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/24ee0057f3094648bcc22b0283f2dd7e~tplv-k3u1fbpfcp-watermark.image)
###### 6.3 生成java文件

```java
       // 使用 javapoet 创建java文件
        for (String key : mClassCreatorFactoryMap.keySet()) {
            MkClassCreatorFactory proxyInfo = mClassCreatorFactoryMap.get(key);
            JavaFile javaFile = JavaFile.builder(proxyInfo.getPackageName(), proxyInfo.generateJavaCodeWithJavapoet()).build();
            try {
                //　生成文件
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
```
### 总结

APT的核心用一句话总结就是:

理解Annotation Processing原理: 编译过程中读源码,然后生成代码,再编译

本文以ButterKnife为例,通过Processor自动生成findViewById模板代码,然后通过 square公司的 javapoet 插件将 Binding.java 文件转换成 java代码,APT应用范围非常广泛,尤其是组件化开发领域,所以小伙伴们务必掌握。


> 本文相关代码已经上传[MkButterKnife](http://github.com/MicroKibaco/MkButterKnife)，有帮助的话Star一波吧。