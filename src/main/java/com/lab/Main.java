package com.lab;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    interface Menu <K, V> {

        HashMap<K,V> getMap();

        default V select(K key) {
            return getMap().get(key);
        }

        default void put(K key, V value) {
            getMap().put(key, value);
        }

    }

    class MyMenu <K, V> implements Menu<K, V> {

        private final HashMap<K, V> map;

        MyMenu() {
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
        var myMenu = new MyMenu<String, String>();

        try {

            String pkg = "com.lab";            

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path     = pkg.replace(".","/");
            URL resource    = classLoader.getResource(path);

            List<Class<?>> clazzs = new ArrayList<>();

            if (resource != null) {
                File directory = new File(resource.toURI());
                if (directory.exists()) {
                    this.recursiveScan(directory, pkg, clazzs);
                }
            }

            if(clazzs.isEmpty()) {
                System.out.println("anything");
            }

            for(var k : clazzs) {
                System.out.println(k);
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