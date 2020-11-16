package com.jadan.client.model.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Properties;

@Data
@JsonSerialize
public class ManifestInfo {
    private String createdBy;
    private String JDKBuild;
    private String applicationName;
    private String implementationBuild;
    private String implementationVersion;

    public ManifestInfo(Properties properties) {
        this.applicationName = properties.getProperty("Application-Name");
        this.implementationVersion = properties.getProperty("Implementation-Version");
        this.implementationBuild = properties.getProperty("Implementation-Build");
        this.JDKBuild = properties.getProperty("Jdk-Build");
        this.createdBy = properties.getProperty("Created-By");
    }
}
