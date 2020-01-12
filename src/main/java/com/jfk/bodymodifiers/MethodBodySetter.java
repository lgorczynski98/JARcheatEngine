package com.jfk.bodymodifiers;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MethodBodySetter implements BodyChangeable {

    @Override
    public void changeBody(String methodName, String methodBody, CtClass ctClass, CtClass[] parameters) throws Exception {
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, parameters);
            ctMethod.setBody(methodBody);
        }
        catch(NotFoundException e) {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctMethod.setBody(methodBody);
        }
    }
}
