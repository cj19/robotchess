package com.darvasroland.consoleapp.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author darvasr
 * <p>
 * Class that loads class from file
 * {@link ClassLoader}
 * {@link java.io.File}
 */
public class FileClassLoader extends ClassLoader {

    private List<Class> classes;

    private File directory;

    private ClassLoader classLoader;

    private List<String> classNames;

    public FileClassLoader(FileClassLoaderBuilder fileClassLoaderBuilder) {
        this.classes = fileClassLoaderBuilder.classes;
        this.directory = fileClassLoaderBuilder.directory;
        this.classLoader = fileClassLoaderBuilder.classLoader;
        this.classNames = fileClassLoaderBuilder.classNames;
    }

    public void listAvailableClasses() {
        System.out.println("Available classes:");
        int i = 1;
        for (String className : getClassNames()) {
            System.out.println(i + ". " + className);
            i++;
        }
    }

    public List<Class> getClasses() {
        return classes;
    }

    public File getDirectory() {
        return directory;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public static class FileClassLoaderBuilder {

        private List<Class> classes = new ArrayList<>();

        private File directory;

        private ClassLoader classLoader;

        private List<String> classNames = new ArrayList<>();


        public FileClassLoaderBuilder() {
            //default constructor
        }

        public FileClassLoaderBuilder setClassLoaderAndFile(File file) {
            try {
                URL url = file.toURI().toURL();
                URL[] urls = new URL[]{url};

                this.classLoader = new URLClassLoader(urls);
                this.directory = file;
            } catch (MalformedURLException e) {
                System.out.println("Can't create URL from File: " + e.getMessage());
            }
            return this;
        }

        public FileClassLoaderBuilder loadClassesFromDirectory() {
            for (File file : Objects.requireNonNull(this.directory.listFiles())) {
                if (file.isFile()) {
                    this.classNames.add(file.getName().substring(0, file.getName().lastIndexOf('.')));
                }
            }
            return this;
        }

        public FileClassLoaderBuilder loadClasses() {
            try {
                for (String className : this.classNames) {
                    this.classes.add(getSystemClassLoader().loadClass("com.darvasroland.consoleapp.player." + className));
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found " + e.getMessage());
            }
            return this;
        }

        public FileClassLoader build() {
            return new FileClassLoader(this);
        }
    }
}
