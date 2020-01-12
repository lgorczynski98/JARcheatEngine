package com.jfk.bodymodifiers;

import javassist.CtClass;
import javassist.CtConstructor;

public class ConstructorBodySetter implements BodyChangeable {
    @Override
    public void changeBody(String methodName, String methodBody, CtClass ctClass, CtClass[] parameters) throws Exception {
        CtConstructor ctConstructor = ctClass.getDeclaredConstructor(parameters);
        ctConstructor.setBody(methodBody);
    }
}
