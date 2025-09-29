package org.noear.solon.admin.client.data;

import org.noear.solon.Solon;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用环境信息
 *
 * @author shaokeyibb
 * @since 2.3
 */
public final class EnvironmentInformation {

    // 系统环境变量
    private final Map<String, String> systemEnvironment;

    // 系统属性
    private final Map<String, String> systemProperties;

    // 应用配置
    private final Map<String, String> applicationProperties;
    
    public EnvironmentInformation(Map<String, String> systemEnvironment, Map<String, String> systemProperties, Map<String, String> applicationProperties) {
        this.systemEnvironment = systemEnvironment;
        this.systemProperties = systemProperties;
        this.applicationProperties = applicationProperties;
    }

    public static EnvironmentInformation create() {
        return create(Solon.cfg().getBool("solon.admin.client.showSecretInformation", false));
    }

    public static EnvironmentInformation create(boolean showSecretInformation) {
        Map<String, String> systemEnvironment = new HashMap<>();
        Map<String, String> systemProperties = new HashMap<>();
        Map<String, String> applicationProperties = new HashMap<>();

        System.getenv().forEach((key, value) -> systemEnvironment.put(key, showSecretInformation ? value : "******"));
        System.getProperties().forEach((key, value) -> systemProperties.put(key.toString(), showSecretInformation ? value.toString() : "******"));
        Solon.cfg().forEach((key, value) -> applicationProperties.put(key.toString(), showSecretInformation ? value.toString() : "******"));

        return new EnvironmentInformation(systemEnvironment, systemProperties, applicationProperties);
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
