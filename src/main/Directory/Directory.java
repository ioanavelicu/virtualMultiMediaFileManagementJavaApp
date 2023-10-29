package main.Directory;

import main.File.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Directory {
    private String path;
    private String name;

    private List<File> listOfFiles;

    public Directory() {
    }

    public Directory(String name, String path) {
        this.path = path;
        this.name = name;
        this.listOfFiles = new ArrayList<>();
    }

    public Directory(String name, String path, File file) {
        this.name = name;
        this.path = path;
        this.listOfFiles.add(file);
    }

    public List<File> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(List<File> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Directory details: " +
                "path: " + path +
                ", name: " + name ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Directory other = (Directory) obj;
        return name.equals(other.name) && Objects.equals(path, other.path);
    }
}
