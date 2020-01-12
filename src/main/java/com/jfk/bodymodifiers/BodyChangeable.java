package com.jfk.bodymodifiers;

import javassist.CtClass;

public interface BodyChangeable {

    void changeBody(String methodName, String methodBody, CtClass ctClass, CtClass[] parameters) throws Exception;

}
