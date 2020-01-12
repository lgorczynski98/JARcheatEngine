package com.jfk.bodymodifiers;

import com.jfk.jarmodifiers.ArgsManipulator;
import com.jfk.jarmodifiers.JarChangeable;
import com.jfk.reflection.JarExplorer;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;
import java.util.Scanner;

public class BodyModifier extends ArgsManipulator implements JarChangeable {

    private JarExplorer jarExplorer;
    private BodyChangeable bodyChangeable;

    public BodyModifier(JarExplorer jarExplorer, BodyChangeable bodyChangeable){
        this.jarExplorer = jarExplorer;
        this.bodyChangeable = bodyChangeable;
    }

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws Exception {
        String path = scriptLineArr[scriptLineArr.length - 1];
//        String methodName = createFieldSrc(scriptLineArr, 2, scriptLineArr.length - 1);
        String methodName = scriptLineArr[2].substring(0, scriptLineArr[2].indexOf('('));

        File methodBodyFile = new File(path);
        Scanner scanner = new Scanner(methodBodyFile);
        StringBuilder methodBodyBuilder = new StringBuilder();
        while(scanner.hasNextLine()){
            methodBodyBuilder.append(scanner.nextLine());
        }
        String methodBody = methodBodyBuilder.toString();
//        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, getArguments(scriptLineArr));
        bodyChangeable.changeBody(methodName, methodBody, ctClass, getArguments(scriptLineArr));
        jarExplorer.removeClass(ctClass.getName() + ".class");
        jarExplorer.addClass(ctClass);
    }
}
