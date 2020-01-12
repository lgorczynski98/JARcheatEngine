package com.jfk.jarmodifiers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;

public class FieldAdder extends ArgsManipulator implements JarChangeable {

    @Override
    public void change(String[] scriptLineArr, CtClass ctClass) throws CannotCompileException {
        ctClass.addField(CtField.make(createFieldSrc(scriptLineArr, 2), ctClass));
    }
}
