package com.jfk.jarmodifiers;

import javassist.CtClass;
import javassist.CtNewConstructor;

public class ConstructorAdder extends ArgsManipulator implements JarChangeable {

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws Exception {
//        ctClass.addConstructor(CtNewConstructor.make(createFieldSrc(scriptLineArr, 2), ctClass));
        ctClass.addConstructor(CtNewConstructor.make(getArguments(scriptLineArr), null, "{}", ctClass));
    }
}
