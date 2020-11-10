package com.github.microkibaco.android.processor;

import com.github.microkibaco.android.annotation.MkBindView;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

//@AutoService(Processor.class)
@SuppressWarnings("unused")
@SupportedAnnotationTypes({"com.github.microkibaco.android.annotation.MkBindView"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MkBindViewProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Map<String, MkClassCreatorFactory> mClassCreatorFactoryMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
    }
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mClassCreatorFactoryMap.clear();
        //得到所有的注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MkBindView.class);
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            //获取类信息
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //类的完整包名+类名
            String fullClassName = classElement.getQualifiedName().toString();
            MkClassCreatorFactory proxy = mClassCreatorFactoryMap.get(fullClassName);
            if (proxy == null) {
                proxy = new MkClassCreatorFactory(mElementUtils, classElement);
                mClassCreatorFactoryMap.put(fullClassName, proxy);
            }
            MkBindView bindAnnotation = variableElement.getAnnotation(MkBindView.class);
            //获取 View 的 id
            int id = bindAnnotation.value();
            proxy.putElement(id, variableElement);
        }


        //使用 javapoet 创建java文件
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
}
