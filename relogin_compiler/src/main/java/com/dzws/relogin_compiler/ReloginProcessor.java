package com.dzws.relogin_compiler;

import com.dzws.annotation.Reload;
import com.dzws.annotation.ReloadInheritable;
import com.dzws.annotation.Relogin;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-20 12:07
 */
@AutoService(Processor.class)
public class ReloginProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private Types mTypeUtils;
    private Messager mMessager;
    private Filer mFilerUtils;
    private String methodClassName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnv.getElementUtils();
        mTypeUtils = processingEnv.getTypeUtils();
        mFilerUtils = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mMessager.printMessage(Diagnostic.Kind.WARNING, "ReloginProcessor init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String reloadMethodName = "", reLoginName = "", reLoadMethodClassName = "";
        int reLoginCode = -1;
        CodeBlock.Builder builder = CodeBlock.builder();
        /**
         * 用来获取login classname 和reloginCode
         */
        Set<? extends Element> mReloginElement =
                roundEnvironment.getElementsAnnotatedWith(Relogin.class);
        /**
         * 用来获取reload classname和methodname
         */
        Set<? extends Element> mReLoadElement =
                roundEnvironment.getElementsAnnotatedWith(Reload.class);
        /**
         * 用来获取可继承注解 classname及其methodname
         */
        Set<? extends Element> reloadInheritableElements = roundEnvironment.getElementsAnnotatedWith(ReloadInheritable.class);

        for (Element element : mReloginElement) {
            Relogin reLogin = element.getAnnotation(Relogin.class);
            reLoginName = ((TypeElement) element).getQualifiedName().toString();
            reLoginCode = reLogin.reloginCode();
        }

        for (Element element : reloadInheritableElements) {
            String className = ((TypeElement)element).getQualifiedName().toString();
            ReloadInheritable reloadInheritable = element.getAnnotation(ReloadInheritable.class);
            String reloadMethod = reloadInheritable.reloadMethod();
            builder.add("put($S,$S);", className, reloadMethod);
        }

        for (Element element : mReLoadElement) {
            String className =
                    ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
            reloadMethodName = element.getSimpleName().toString();
            builder.add("put($S,$S);", className, reloadMethodName);
        }

        CodeBlock mapInitCodeBlock =
                CodeBlock.builder()
                        .add("{{")
                        .add(builder.build())
                        .add("}}; return reloadMethodMap;", builder.build())
                        .build();
        MethodSpec getReloadMethodMap = MethodSpec.methodBuilder("getReloadMethodMap")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(HashMap.class)
                .addCode(
                        "HashMap reloadMethodMap = new HashMap<String,String>()")
                .addCode(mapInitCodeBlock).build();

        MethodSpec getReloginClassName =
                MethodSpec.methodBuilder("getReloginClassName")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("return $S", reLoginName)
                        .build();
        MethodSpec getReloginCode = MethodSpec.methodBuilder("getReloginResponseCode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("return $L", reLoginCode)
                .build();

        ArrayList<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.add(getReloginCode);
        methodSpecs.add(getReloginClassName);
        methodSpecs.add(getReloadMethodMap);

        TypeSpec reLoginHelper =
                TypeSpec.classBuilder("ReloginHelper")
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(IReloginHelper.class)
                        .addMethods(methodSpecs).build();

        JavaFile javaFile = JavaFile.builder("com.dzws.relogin", reLoginHelper).build();

        createSourceFile(reLoginHelper.name, javaFile.toString());

        return true;
    }

    private void createSourceFile(String fileName, String javaFile) {
        try {
            JavaFileObject sourceFile = mFilerUtils.createSourceFile(fileName);
            Writer writer = sourceFile.openWriter();
            writer.write(javaFile);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Relogin.class.getCanonicalName());
        supportTypes.add(Reload.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
