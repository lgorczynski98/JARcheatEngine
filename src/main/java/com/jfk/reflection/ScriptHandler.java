package com.jfk.reflection;

import com.jfk.bodymodifiers.*;
import com.jfk.jarmodifiers.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtPrimitiveType;

import java.awt.event.KeyEvent;

public class ScriptHandler {

    private JarExplorer jarExplorer;
    private JarModifier jarModifier;
    private ClassPool classPool;

    public ScriptHandler(JarExplorer jarExplorer){
        this.jarExplorer = jarExplorer;
        classPool = ClassPool.getDefault();
        this.jarModifier = new JarModifier(jarExplorer, classPool);
    }

    public void handle(String scriptLine){
        String[] scriptLineArr = scriptLine.split(" ");
        switch(scriptLineArr[0]){
            case "add-package":{        //dodanie dodanego pakietu
                jarExplorer.addPAckage(scriptLineArr[1]);       //dziala i winrar to widzi, ale jesli pusty to jar tego nie uwzglednia
                break;
            }
            case "remove-package":{     //usuniecie dodanego pakietu
                jarExplorer.removePackage(scriptLineArr[1]);
                break;
            }
            case "add-class":{      //dodanie dodanej klasy
                try {
                    CtClass clazz = classPool.makeClass(scriptLineArr[1]);
                    jarExplorer.addClass(clazz);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "remove-class":{       //usuniecie dodanej klasy
//                removeClass(scriptLineArr[1] + ".class");
                jarExplorer.permanentRemoveClass(scriptLineArr[1]);
                break;
            }
            case "add-interface":{      //dodanie dodanego interface'u
                try {
                    CtClass clazz = classPool.makeInterface(scriptLineArr[1]);
                    jarExplorer.addClass(clazz);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "remove-interface":{       //usuniecie dodanego interface'u
//                removeClass(scriptLineArr[1]);
                jarExplorer.permanentRemoveClass(scriptLineArr[1]);
                break;
            }
            case "add-method":{     //dodanie metody
                try {
                    jarModifier.modifyJar(new MethodAdder(), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "remove-method":{      //usuniecie metody
                try {
                    jarModifier.modifyJar(new MethodRemover(jarExplorer), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "set-method-body":{        //nadpisanie ciala metody
                try {
                    jarModifier.modifyJar(new BodyModifier(jarExplorer, new MethodBodySetter()), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "add-before-method":{          //dopisanie kodu na poczatek wskazanej metody
                try {
                    jarModifier.modifyJar(new BodyModifier(jarExplorer, new MethodBeforeAdder()), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "add-after-method":{       //dopisanie kodu na koncu wskazanej metody
                try {
                    jarModifier.modifyJar(new BodyModifier(jarExplorer, new MethodAfterAdder()), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "add-field":{      //dodanie pola
                try {
                    jarModifier.modifyJar(new FieldAdder(), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "remove-field":{      //usuniecie pola
                try {
                    jarModifier.modifyJar(new FieldRemover(jarExplorer), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "add-ctor":{       //dodanie konstruktora
                try {
                    jarModifier.modifyJar(new ConstructorAdder(), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "remove-ctor":{        //usuniecie konstruktora
                try {
                    jarModifier.modifyJar(new ConstructorRemover(jarExplorer), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "set-ctor-body":{      //nadpisanie ciala konstruktora
                try {
                    jarModifier.modifyJar(new BodyModifier(jarExplorer, new ConstructorBodySetter()), scriptLineArr);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void removeClass(String className){
        className = className.replace('.', '/');
        String path = className.substring(0, className.lastIndexOf("/")) + ".class";
        jarExplorer.removeClass(path);
    }
}
