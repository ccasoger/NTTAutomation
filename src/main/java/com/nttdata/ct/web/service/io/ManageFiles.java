package com.nttdata.ct.web.service.io;

import com.nttdata.ct.web.service.util.UtilWeb;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ManageFiles {

    private ManageFiles() {
        throw new IllegalStateException("Utility class");
    }

    public static String readAsString(String fileResourcesPath) {
        try {
            Resource resource = new ClassPathResource(fileResourcesPath);
            InputStream inputStream = resource.getInputStream();
            return new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new IllegalStateException("Some error reading the file " + e.getCause() + " - " + e.getMessage());
        }
    }


    public static String getJsonContentAsString(String jsonPath) {
        File file = new File(jsonPath);
        String content = StringUtils.EMPTY;
        try {
            content = new String(Files.readAllBytes(Paths.get(file.toURI())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            UtilWeb.logger(ManageFiles.class).warning("Ocurrio un error al leer el archivo.");
        }

        if (content.isEmpty()) UtilWeb.logger(ManageFiles.class).log(Level.WARNING, "El archivo JSON esta vacio.");

        return content;
    }
}