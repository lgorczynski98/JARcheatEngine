package com.jfk.jarmodifiers;

import com.jfk.reflection.JarExplorer;
import javassist.ClassPool;
import javassist.CtClass;

public class JarModifier {

    private JarExplorer jarExplorer;
    private ClassPool classPool;

    public JarModifier(JarExplorer jarExplorer, ClassPool classPool){
        this.jarExplorer = jarExplorer;
        this.classPool = classPool;
    }

    public void modifyJar(JarChangeable jarChangeable, String[] scriptLineArr){
        for (CtClass ctClass : jarExplorer.getClassesToAdd()){
            if (scriptLineArr[1].equals(ctClass.getName() + ".class")){
                try {
                    ctClass.defrost();
                    jarChangeable.change(scriptLineArr, ctClass);
                    return;
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
//            CtClass ctClass = classPool.get(scriptLineArr[1].substring(0, scriptLineArr[1].lastIndexOf('.')));
            CtClass ctClass = classPool.get(scriptLineArr[1]);
            removeClass(ctClass.getName() + ".class");
            jarChangeable.change(scriptLineArr, ctClass);
            jarExplorer.addClass(ctClass);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void removeClass(String className){
        className = className.replace('.', '/');
        String path = className.substring(0, className.lastIndexOf("/")) + ".class";
        jarExplorer.removeClass(path);
    }

}
