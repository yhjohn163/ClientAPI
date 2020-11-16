package com.jadan.client.controller;

import com.jadan.client.model.view.ManifestInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping(value = "/resource-status", produces ={"application/json"})
public class ResourceStatusController  {
    private static ManifestInfo manifestInfo;

    @ApiOperation("Show the Resource Status of the Application.")
    @GetMapping
    public ResponseEntity<ManifestInfo> getResourceStatus() throws Exception {
        return ResponseEntity.ok(getManifest());
    }

    private ManifestInfo getManifest() throws Exception {
        if (manifestInfo == null) {
            String MANIFEST_FILE_NAME = "app-manifest.properties";
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(MANIFEST_FILE_NAME)) {
                if (inputStream == null) {
                    throw new Exception(MANIFEST_FILE_NAME + " was not found in classpath");
                }

                Properties properties = new Properties();
                properties.load(inputStream);
                manifestInfo = new ManifestInfo(properties);

            } catch (Exception e) {
                manifestInfo = null;
                throw new Exception("Failed to read from manifest file.", e);
            }
        }

        return manifestInfo;
    }
}