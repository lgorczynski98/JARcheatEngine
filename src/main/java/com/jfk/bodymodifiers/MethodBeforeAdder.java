package com.jfk.bodymodifiers;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MethodBeforeAdder implements BodyChangeable {

    @Override
    public void changeBody(String methodName, String methodBody, CtClass ctClass, CtClass[] parameters) throws Exception {
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, parameters);
            ctMethod.insertBefore(methodBody);
        }
        catch(NotFoundException e) {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctMethod.insertBefore(methodBody);
        }
    }
}
