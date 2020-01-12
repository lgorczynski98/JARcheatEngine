package com.jfk.jarmodifiers;

import javassist.CtClass;

public interface JarChangeable {

    void change(String[] scriptLineArr, CtClass ctClass) throws Exception;

}
