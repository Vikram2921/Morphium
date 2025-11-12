package com.morphium.runtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModuleResolver {
    private final Path basePath;

    public ModuleResolver() {
        this.basePath = Paths.get(System.getProperty("user.dir"));
    }

    public ModuleResolver(Path basePath) {
        this.basePath = basePath;
    }

    public String resolve(String modulePath) throws IOException {
        Path fullPath = basePath.resolve(modulePath);
        if (!Files.exists(fullPath)) {
            throw new IOException("Module not found: " + modulePath);
        }
        return Files.readString(fullPath);
    }

    public Path getBasePath() {
        return basePath;
    }
}
