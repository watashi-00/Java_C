package com.lab;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lab.utils.Lab;
import com.lab.utils.MenuSelect;

public class Main {

    Scanner scanner = new Scanner(System.in);
    String pkg = "com.lab";

    List<Class<?>> classList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Main().init();
    }

    void init() {
        var actions = new MenuActions<EntryMenu, Runnable, Integer>(
                EntryMenu::index
        );

        try {

            config();

            List<Class<?>> labClasses = prepareClassList(MenuSelect.class, Lab.class);

            for(int i = 0; i < labClasses.size(); i++) {
                var k = labClasses.get(i);
                Method[] methods = k.getDeclaredMethods();

                Object instance = k.getDeclaredConstructor().newInstance();

                for(var m : methods) {
                    if(!m.getName().equals("run")) continue;
                    Runnable action = () -> {
                        try {
                            m.invoke(instance);
                        } catch ( Exception e) {
                            throw new RuntimeException(e);
                        }
                    };
                    String label = k.getAnnotation(MenuSelect.class).label();
                    actions.put(new EntryMenu(i, label), action);
                }

            }

            for (;;) {
                System.out.println("Select using number before name. -1 to close");

                for(var entry : actions.getMap().entrySet()) {
                    System.out.println(entry.getKey());
                }

                System.out.print("> ");

                while(!scanner.hasNextInt()) {
                    System.out.println("Please input a number between 0 and " + (actions.getMap().size()-1));
                    scanner.nextLine();
                }

                int i = scanner.nextInt();
                scanner.nextLine();

                if(i == -1) {
                    return;
                }

                var action = actions.select(i);
                if (action != null) action.run();

            }

        } catch (Exception e) {
            //ignore all
        }

    }

    private List<Class<?>> prepareClassList(Class<? extends Annotation> annotation, Class<?> interfaceClass) {
        var list = new ArrayList<Class<?>>();
        for(var k : classList)
            if(k.isAnnotationPresent(annotation) && interfaceClass.isAssignableFrom(k))
                list.add(k);

        return list;
    }

    private void warmupClassList() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = pkg.replace(".", "/");
            URL resource = classLoader.getResource(path);

            if (resource != null) {
                File directory = new File(resource.toURI());
                if (!directory.exists())
                    return;

                this.recursiveScan(directory, pkg, classList);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void config(){
        warmupClassList();
    }

    void recursiveScan(File directory, String pkg, List<Class<?>> classes) throws Exception {
        File[] files = directory.listFiles();
        if(files == null) return;

        for(File file : files) {
            if(file.isDirectory()) {
                recursiveScan(file, pkg + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = pkg + "." + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
    }
    
}