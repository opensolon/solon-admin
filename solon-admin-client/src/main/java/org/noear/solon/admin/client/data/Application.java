package org.noear.solon.admin.client.data;

import java.util.Objects;

/**
 * 应用程序数据
 *
 * @author shaokeyibb
 * @since 2.3
 */
public class Application {

    private final String name;
    private final String token;
    private String baseUrl;
    private final String metadata;
    /**
     * 是否展示敏感信息，如：环境变量
     */
    private final boolean showSecretInformation;
    /**
     * 环境信息
     */
    private final EnvironmentInformation environmentInformation;
    
    private Application(String name, String token, String baseUrl, String metadata, 
                       boolean showSecretInformation, EnvironmentInformation environmentInformation) {
        this.name = name;
        this.token = token;
        this.baseUrl = baseUrl;
        this.metadata = metadata;
        this.showSecretInformation = showSecretInformation;
        this.environmentInformation = environmentInformation;
    }
    
    public static ApplicationBuilder builder() {
        return new ApplicationBuilder();
    }
    
    public String getName() {
        return name;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public boolean isShowSecretInformation() {
        return showSecretInformation;
    }
    
    public EnvironmentInformation getEnvironmentInformation() {
        return environmentInformation;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(token, that.token) &&
               Objects.equals(baseUrl, that.baseUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, token, baseUrl);
    }

    @Override
    public String toString() {
        return "Application{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }

    public static class ApplicationBuilder {
        private String name;
        private String token;
        private String baseUrl;
        private String metadata;
        private boolean showSecretInformation;
        private EnvironmentInformation environmentInformation;
        
        ApplicationBuilder() {
        }
        
        public ApplicationBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ApplicationBuilder token(String token) {
            this.token = token;
            return this;
        }
        
        public ApplicationBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
        
        public ApplicationBuilder metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }
        
        public ApplicationBuilder showSecretInformation(boolean showSecretInformation) {
            this.showSecretInformation = showSecretInformation;
            return this;
        }
        
        public ApplicationBuilder environmentInformation(EnvironmentInformation environmentInformation) {
            this.environmentInformation = environmentInformation;
            return this;
        }
        
        public Application build() {
            return new Application(name, token, baseUrl, metadata, showSecretInformation, environmentInformation);
        }
    }
}
