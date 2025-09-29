package org.noear.solon.admin.server.data;

import java.util.Map;
/**
 * 应用环境信息
 *
 * @author shaokeyibb
 * @since 2.3
 */
public class EnvironmentInformation {

    private final Map<String, String> systemEnvironment;

    private final Map<String, String> systemProperties;

    private final Map<String, String> applicationProperties;


    public EnvironmentInformation(
            Map<String, String> systemEnvironment,
            Map<String, String> systemProperties,
            Map<String, String> applicationProperties) {
        this.systemEnvironment = systemEnvironment;
        this.systemProperties = systemProperties;
        this.applicationProperties = applicationProperties;
    }

    public Map<String, String> getSystemEnvironment() {
        return systemEnvironment;
    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public Map<String, String> getApplicationProperties() {
        return applicationProperties;
    }

}
