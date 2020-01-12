package com.jfk.reflection;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        JarExplorer jarExplorer;
        List<String> argsList = Arrays.asList(args);

        if(Arrays.asList(args).contains("--i")){
           String jarPath = args[argsList.indexOf("--i") + 1];
           jarExplorer = new JarExplorer(jarPath);

           handleListings(argsList, jarExplorer, args);

            if(argsList.contains("--o")){
                handleOutput(argsList, jarExplorer, args, jarPath);
            }
            jarExplorer.close();
        }
    }

    private static void handleListings(List<String> argsList, JarExplorer jarExplorer, String[] args){
        if(argsList.contains("--list-packages")){
            jarExplorer.displayPackages();
        }
        if(argsList.contains("--list-classes")){
            jarExplorer.displayClasses();
        }
        if(argsList.contains("--list-methods")){
            String className = args[argsList.indexOf("--list-methods") + 1];
            jarExplorer.displayMethods(className);
        }
        if(argsList.contains("--list-fields")){
            String className = args[argsList.indexOf("--list-methods") + 1];
            jarExplorer.displayFields(className);
        }
        if(argsList.contains("--list-ctors")){
            String className = args[argsList.indexOf("--list-ctors") + 1];
            jarExplorer.displayConstructors(className);
        }
    }

    private static void handleOutput(List<String> argsList, JarExplorer jarExplorer, String[] args, String jarPath){
        String outputFileName = args[argsList.indexOf("--o") + 1];
        if(jarPath.equals(outputFileName)){
            System.out.println("Nie mozna nadpisywac pliku wejsciowego");
            return;
        }
        try {
            jarExplorer.prepareJarOutPutStream(outputFileName);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        if(argsList.contains("--script")){
            handleScript(argsList, jarExplorer, args);
        }

        try {
            jarExplorer.saveNewJar();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleScript(List<String> argsList, JarExplorer jarExplorer, String[] args){
        String scriptFile = args[argsList.indexOf("--script") + 1];
        File file = new File(scriptFile);
        ScriptHandler scriptHandler = new ScriptHandler(jarExplorer);

        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine())
                scriptHandler.handle(scanner.nextLine());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
