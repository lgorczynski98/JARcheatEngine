package com.jfk.reflection;

import javassist.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.*;

public class JarExplorer {

    private JarFile jarFile;
    private ClassPool classPool;
    private JarOutputStream jarOutputStream;

    private List<String> removedClasses;
    private List<String> permanentlyRemovedClasses;
    private List<CtClass> classesToAdd;
    private List<String> packagesToAdd;
    private List<String> removedPackages;

    public JarExplorer(String path){
        this.classPool = ClassPool.getDefault();
        this.removedClasses = new ArrayList<>();
        this.classesToAdd = new ArrayList<>();
        this.packagesToAdd = new ArrayList<>();
        this.removedPackages = new ArrayList<>();
        this.permanentlyRemovedClasses = new ArrayList<>();
        try {
            File file = new File(path);
            this.jarFile = new JarFile(file);
            this.classPool.insertClassPath(path);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayPackages(){
        try {
            for (JarEntry entry : Collections.list(jarFile.entries())){
                if (entry.isDirectory() && !entry.getName().contains("META-INF"))
                    System.out.println(entry.getRealName().replace('/', '.'));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayClasses(){
        try {
            for (JarEntry entry : Collections.list(jarFile.entries())){
                if (entry.getRealName().endsWith(".class"))
                    System.out.println(entry.getRealName().replace('/', '.'));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            jarFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayFields(String className){
        try {
            CtClass clazz = classPool.get(className);

            for (CtField declaredField : clazz.getDeclaredFields()) {
                System.out.println(declaredField.getName());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayMethods(String className){
        try {
            CtClass clazz = classPool.get(className);

            for (CtMethod declaredMethod : clazz.getDeclaredMethods()) {
                System.out.println(declaredMethod.getLongName());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayConstructors(String className){
        try {
            CtClass clazz = classPool.get(className);

            for (CtConstructor declaredConstructor : clazz.getDeclaredConstructors()) {
                System.out.println(declaredConstructor.getLongName());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareJarOutPutStream(String newJarName) throws IOException{
        File destFile = new File(newJarName);
        jarOutputStream = new JarOutputStream(new FileOutputStream(destFile));
    }

    public void saveNewJar()throws IOException{
        Enumeration<JarEntry> entries = jarFile.entries();
        copyFromJar(entries);
        saveNewPackages();
        saveNewClasses();
        jarOutputStream.close();
    }

    private void copyFromJar(Enumeration<JarEntry> entries) throws IOException{
        while(entries.hasMoreElements()){
            JarEntry entry = entries.nextElement();
            if(removedClasses.contains(entry.getName()))
                continue;
//            if(isEntryInRemovedPackage(entry.getName()))
//                continue;
            InputStream inputStream = jarFile.getInputStream(entry);
            jarOutputStream.putNextEntry(new JarEntry(entry.getName()));
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while((bytesRead = inputStream.read(buffer)) != -1 ){
                jarOutputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            jarOutputStream.flush();
            jarOutputStream.closeEntry();
        }
    }

    private void saveNewClasses(){
        for (CtClass ctClass : classesToAdd) {
            try {
                if (isEntryInRemovedPackage(ctClass.getName()) || permanentlyRemovedClasses.contains(ctClass.getName()))
                    continue;
                addClassToJar(ctClass);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveNewPackages() throws IOException{
        for (String packageName : packagesToAdd){
            if(!removedPackages.contains(packageName)){
                jarOutputStream.putNextEntry(new JarEntry(packageName));
                jarOutputStream.flush();
                jarOutputStream.closeEntry();
            }
        }
    }

    private boolean isEntryInRemovedPackage(String entryName){
        for (String packageName : removedPackages){
            if (entryName.replaceAll("/", ".").contains(packageName.replaceAll("/", ".")))
                return true;
        }
        return false;
    }

    private void addClassToJar(CtClass clazz) throws IOException, CannotCompileException {
        jarOutputStream.putNextEntry(new JarEntry(clazz.getName().replace('.', '/') + ".class"));
        jarOutputStream.write(clazz.toBytecode());
        jarOutputStream.flush();
        jarOutputStream.closeEntry();
    }

    public void addClass(CtClass ctClass){
        if(!classesToAdd.contains(ctClass))
            classesToAdd.add(ctClass);
    }

    public void removeClass(String className){
        removedClasses.add(className);
    }

    public void permanentRemoveClass(String className){
        permanentlyRemovedClasses.add(className);
        removedClasses.remove(className.replace('.', '/') + ".class");
    }

    public List<CtClass> getClassesToAdd() {
        return classesToAdd;
    }

    public void addPAckage(String packageName){
        packagesToAdd.add(packageName.replace(".", "/") + "/");
    }

    public void removePackage(String packageName){
        removedPackages.add(packageName.replace('.', '/') + "/");
    }
}
