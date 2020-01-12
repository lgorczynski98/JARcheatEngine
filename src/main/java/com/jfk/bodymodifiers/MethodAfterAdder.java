package com.jfk.bodymodifiers;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MethodAfterAdder implements BodyChangeable {

    @Override
    public void changeBody(String methodName, String methodBody, CtClass ctClass, CtClass[] parameters) throws Exception {
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, parameters);
            ctMethod.insertAfter(methodBody);
        }
        catch(NotFoundException e) {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctMethod.insertAfter(methodBody);
        }
    }
}
