package com.jfk.jarmodifiers;

import com.jfk.reflection.JarExplorer;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

public class FieldRemover extends ArgsManipulator implements JarChangeable{

    private JarExplorer jarExplorer;

    public FieldRemover(JarExplorer jarExplorer){
        this.jarExplorer = jarExplorer;
    }

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws NotFoundException {
//        CtField ctField = ctClass.getDeclaredField(createFieldSrc(scriptLineArr, 2));
        CtField ctField = ctClass.getDeclaredField(scriptLineArr[2]);
        ctClass.removeField(ctField);
        jarExplorer.removeClass(ctClass.getName() + ".class");
        jarExplorer.addClass(ctClass);
    }
}
