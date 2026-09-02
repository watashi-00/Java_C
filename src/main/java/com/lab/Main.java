package com.lab;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.lab.utils.Lab;
import com.lab.utils.MenuSelect;

public class Main {

    Scanner scanner = new Scanner(System.in);

    interface Menu <K, V> {

        HashMap<K,V> getMap();

        default V select(K key) {
            return getMap().get(key);
        }

        default void put(K key, V value) {
            getMap().put(key, value);
        }

    }

    class MenuActions <K, V> implements Menu<K, V> {

        private final HashMap<K, V> map;

        MenuActions() {
            this.map = new HashMap<K, V>();
        }

        @Override
        public HashMap<K, V> getMap() {
            return this.map;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");

        new Main().init();
    }


    void init() {
        var actions = new MenuActions<String, Runnable>();
        var menu    = new HashMap<Integer, String>();

        try {

            String pkg = "com.lab";            

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path     = pkg.replace(".","/");
            URL resource    = classLoader.getResource(path);

            List<Class<?>> clazzs = new ArrayList<>();
            List<Class<?>> labclass = new ArrayList<>();

            if (resource != null) {
                File directory = new File(resource.toURI());
                if (directory.exists()) {
                    this.recursiveScan(directory, pkg, clazzs);
                }
            }

            for(var k : clazzs) {
                
                if(k.isAnnotationPresent(MenuSelect.class) && Lab.class.isAssignableFrom(k)) {
                    labclass.add(k);
                }

            }

            for(int i = 0; i < labclass.size(); i++) {
                var k = labclass.get(i);
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
                    actions.put(label, action);
                    menu.put(i, label);
                }

            }

            menu.forEach((k, v) -> {
                System.out.println(k + ": " + v);
            });

            System.out.println("Select using number before name");

            if(scanner.hasNextInt()) {
                var action = actions.select(menu.get(scanner.nextInt()));
                action.run();
            }
            

        } catch (Exception e) {
            //ignore all
        }

    }

    void recursiveScan(File directory, String pkg, List<Class<?>> clazzs) throws Exception {
        File[] files = directory.listFiles();
        if(files == null) return;

        for(File file : files) {
            if(file.isDirectory()) {
                recursiveScan(file, pkg + "." + file.getName(), clazzs);
            } else if (file.getName().endsWith(".class")) {
                String className = pkg + "." + file.getName().substring(0, file.getName().length() - 6);
                clazzs.add(Class.forName(className));
            }
        }
    }
    
}