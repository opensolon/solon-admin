package org.noear.solon.admin.client.config;

/**
 * 本地模式配置文件
 *
 * @author shaokeyibb
 * @since 2.3
 */
public class ClientProperties {

    private boolean enabled = true;

    private String mode = AdminClientBootstrapConfiguration.MarkedClientEnabled.LOCAL_MODE;

    private String token = "3C41D632-A070-060C-40D2-6D84B3C07094";

    private String serverUrl = "http://localhost:8080";

    private long heartbeatInterval = 10 * 1000;

    private long retryInterval = 3 * 1000;

    private long connectTimeout = 5 * 1000;

    private long readTimeout = 5 * 1000;

    private boolean showSecretInformation = false;
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getMode() {
        return mode;
    }
    
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getServerUrl() {
        return serverUrl;
    }
    
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    public long getHeartbeatInterval() {
        return heartbeatInterval;
    }
    
    public void setHeartbeatInterval(long heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }
    
    public long getRetryInterval() {
        return retryInterval;
    }
    
    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }
    
    public long getConnectTimeout() {
        return connectTimeout;
    }
    
    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    
    public long getReadTimeout() {
        return readTimeout;
    }
    
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public boolean isShowSecretInformation() {
        return showSecretInformation;
    }
    
    public void setShowSecretInformation(boolean showSecretInformation) {
        this.showSecretInformation = showSecretInformation;
    }
}
