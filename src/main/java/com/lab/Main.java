package com.lab;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import com.lab.utils.Lab;
import com.lab.utils.MenuSelect;

public class Main {

    Scanner scanner = new Scanner(System.in);

    interface Menu <K, V, I> {

        HashMap<K,V> getMap();
        Function<K, I> indexer();

        default V select(I index) {
            for (var entry : getMap().entrySet()) {
                if (indexer().apply(entry.getKey()).equals(index)) {
                    return entry.getValue();
                }
            }

            return null;
        }

        default void put(K key, V value) {
            getMap().put(key, value);
        }

    }

    class MenuActions <K, V, I> implements Menu<K, V, I> {

        private final HashMap<K, V> map;
        private final Function<K, I> indexer;
        MenuActions(Function<K, I> indexer) {
            this.map = new HashMap<K, V>();
            this.indexer = indexer;
        }

        @Override
        public HashMap<K, V> getMap() {
            return this.map;
        }

        @Override
        public Function<K, I> indexer() {
            return indexer;
        }
    }

    record EntryMenu (int index, String label) {
        @Override
        public String toString() {
            return "> " + index + ": " + label;
        }
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Main().init();
    }

    void init() {
        var actions = new MenuActions<EntryMenu, Runnable, Integer>(
                EntryMenu::index
        );

        try {

            String pkg = "com.lab";            

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path     = pkg.replace(".","/");
            URL resource    = classLoader.getResource(path);

            List<Class<?>> clazzs = new ArrayList<>();
            List<Class<?>> labclass = new ArrayList<>();

            if (resource != null) {
                File directory = new File(resource.toURI());
                if (!directory.exists())
                    return;

                this.recursiveScan(directory, pkg, clazzs);

            }

            for(var k : clazzs)
                if(k.isAnnotationPresent(MenuSelect.class) && Lab.class.isAssignableFrom(k))
                    labclass.add(k);

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