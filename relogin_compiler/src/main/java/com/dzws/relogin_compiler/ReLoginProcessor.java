package com.dzws.relogin_compiler;

import com.dzws.relogin_annotation.ReLoad;
import com.dzws.relogin_annotation.ReLogin;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
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
import javax.lang.model.element.VariableElement;
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
public class ReLoginProcessor extends AbstractProcessor {

  private Elements mElementUtils;
  private Types mTypeUtils;
  private Messager mMessager;
  private Filer mFilerUtils;

  @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    mElementUtils = processingEnv.getElementUtils();
    mTypeUtils = processingEnv.getTypeUtils();
    mFilerUtils = processingEnv.getFiler();
    mMessager = processingEnv.getMessager();
    mMessager.printMessage(Diagnostic.Kind.WARNING,"ReLoginProcessor init");
  }

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    String reloadMethodName = "",reLoginName = "";
    int reLoginCode = -1;
    mMessager.printMessage(Diagnostic.Kind.WARNING,"ReLoginProcessor process");
    Set<? extends Element> mReLoginElement =
        roundEnvironment.getElementsAnnotatedWith(ReLogin.class);
    Set<? extends Element> mReLoadElement =
        roundEnvironment.getElementsAnnotatedWith(ReLoad.class);
    for (Element element : mReLoginElement) {
      ReLogin reLogin = element.getAnnotation(ReLogin.class);
      reLoginName = ((TypeElement)element).getQualifiedName().toString();
      reLoginCode = reLogin.reLoginCode();
      mMessager.printMessage(Diagnostic.Kind.WARNING,"ReLoginProcessor reLoginName : " + reLoginName + " reLoginCode : " + reLoginCode);
    }


    for (Element element : mReLoadElement) {
      reloadMethodName = element.getSimpleName().toString();
      mMessager.printMessage(Diagnostic.Kind.WARNING,"ReLoginProcessor reloadMethodName : " + reloadMethodName);
    }

    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, "args")
        .addStatement("$T.out.println($S)", System.class, "ReLoginProcessor reloadMethodName : " + reloadMethodName + " reLoginName : " + reLoginName + " reLoginCode : " + reLoginCode + " mReLoadElement.size() : " + mReLoadElement.size())
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
        .build();

    createSourceFile(helloWorld.name, javaFile.toString());

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

  @Override public Set<String> getSupportedAnnotationTypes() {
    LinkedHashSet<String> supportTypes = new LinkedHashSet<>();
    supportTypes.add(ReLogin.class.getCanonicalName());
    supportTypes.add(ReLoad.class.getCanonicalName());
    return supportTypes;
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }
}
