package com.jfk.jarmodifiers;

import com.jfk.reflection.JarExplorer;
import javassist.CtClass;
import javassist.CtMethod;

public class MethodRemover extends ArgsManipulator implements JarChangeable {

    private JarExplorer jarExplorer;

    public MethodRemover(JarExplorer jarExplorer){
        this.jarExplorer = jarExplorer;
    }

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws Exception {
//        CtMethod ctMethod = ctClass.getDeclaredMethod(createFieldSrc(scriptLineArr, 2));
        CtMethod ctMethod = ctClass.getDeclaredMethod(scriptLineArr[2].substring(0, scriptLineArr[2].indexOf('(')), getArguments(scriptLineArr));
        ctClass.removeMethod(ctMethod);
        jarExplorer.removeClass(ctClass.getName() + ".class");
        jarExplorer.addClass(ctClass);
    }
}
