package main.File;

import main.Directory.Directory;

public class File {
    private String extension;
    private String name;
    private String rootDirectoryPath;

    public File(String rootDirectoryPath, String extension, String name) {
        this.rootDirectoryPath = rootDirectoryPath;
        this.extension = extension;
        this.name = name;
    }

    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }

    public String getExtension() {
        return extension;
    }

    public String getName() {
        return name;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRootDirectoryPath(String rootDirectoryPath) {
        this.rootDirectoryPath = rootDirectoryPath;
    }

    @Override
    public String toString() {
        return "File details: " +
                "name: '" + name + '\'' +
                ", extension: '" + extension + '\'' +
                ", root directory path: " + rootDirectoryPath;

    }
}
