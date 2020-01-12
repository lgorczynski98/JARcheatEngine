package com.jfk.jarmodifiers;

import com.jfk.reflection.JarExplorer;
import javassist.CtClass;
import javassist.CtConstructor;

public class ConstructorRemover extends ArgsManipulator implements JarChangeable{

    private JarExplorer jarExplorer;

    public ConstructorRemover(JarExplorer jarExplorer){
        this.jarExplorer = jarExplorer;
    }

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws Exception {
        CtConstructor ctConstructor = ctClass.getDeclaredConstructor(getArguments(scriptLineArr));
        ctClass.removeConstructor(ctConstructor);
        jarExplorer.removeClass(ctClass.getName() + ".class");
        jarExplorer.addClass(ctClass);
    }
}
