package com.jfk.jarmodifiers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;

public class MethodAdder extends ArgsManipulator implements JarChangeable {

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws CannotCompileException {
        ctClass.addMethod(CtMethod.make(createFieldSrc(scriptLineArr, 2), ctClass));
    }
}
