package org.noear.solon.admin.server.config;

import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.util.Map;

/**
 * 配置文件
 *
 * @author shaokeyibb
 * @since 2.3
 */
@Inject(value = "${solon.admin.server}", required = false)
@Configuration
public class ServerProperties {
    /**
     * 是否启用
     */
    private boolean enabled = true;
    /**
     * 模式
     */
    private String mode = AdminServerBootstrapConfiguration.MarkedServerEnabled.LOCAL_MODE;

    private long heartbeatInterval = 10 * 1000;

    private long clientMonitorPeriod = 2 * 1000;

    private long connectTimeout = 5 * 1000;

    private long readTimeout = 5 * 1000;

    /**
     * 介绍路径
     */
    private String uiPath = "/";
    /**
     * base 签权
     */
    private Map<String, String> basicAuth;
    
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
    
    public long getHeartbeatInterval() {
        return heartbeatInterval;
    }
    
    public void setHeartbeatInterval(long heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }
    
    public long getClientMonitorPeriod() {
        return clientMonitorPeriod;
    }
    
    public void setClientMonitorPeriod(long clientMonitorPeriod) {
        this.clientMonitorPeriod = clientMonitorPeriod;
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
    
    public String getUiPath() {
        return uiPath;
    }
    
    public void setUiPath(String uiPath) {
        this.uiPath = uiPath;
    }
    
    public Map<String, String> getBasicAuth() {
        return basicAuth;
    }
    
    public void setBasicAuth(Map<String, String> basicAuth) {
        this.basicAuth = basicAuth;
    }
}
