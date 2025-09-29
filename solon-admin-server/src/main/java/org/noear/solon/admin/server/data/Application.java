package org.noear.solon.admin.server.data;

import java.util.Collection;
import java.util.Objects;

public class Application {

    private String name;

    private String token;

    private String baseUrl;

    private String metadata;

    private Status status = Status.DOWN;

    private long startupTime = System.currentTimeMillis();

    private long lastHeartbeat;

    private long lastUpTime;

    private long lastDownTime;

    private boolean showSecretInformation;

    private EnvironmentInformation environmentInformation;

    private Collection<Detector> monitors;

    public Application() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(long startupTime) {
        this.startupTime = startupTime;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public long getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(long lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public long getLastDownTime() {
        return lastDownTime;
    }

    public void setLastDownTime(long lastDownTime) {
        this.lastDownTime = lastDownTime;
    }

    public boolean isShowSecretInformation() {
        return showSecretInformation;
    }

    public void setShowSecretInformation(boolean showSecretInformation) {
        this.showSecretInformation = showSecretInformation;
    }

    public EnvironmentInformation getEnvironmentInformation() {
        return environmentInformation;
    }

    public void setEnvironmentInformation(EnvironmentInformation environmentInformation) {
        this.environmentInformation = environmentInformation;
    }

    public Collection<Detector> getMonitors() {
        return monitors;
    }

    public void setMonitors(Collection<Detector> monitors) {
        this.monitors = monitors;
    }

    public void replace(Application application) {
        this.metadata = application.metadata;
        this.status = application.status;
        this.startupTime = application.startupTime;
        this.lastHeartbeat = application.lastHeartbeat;
        this.lastUpTime = application.lastUpTime;
        this.lastDownTime = application.lastDownTime;
        this.showSecretInformation = application.showSecretInformation;
        this.environmentInformation = application.environmentInformation;
        this.monitors = application.monitors;
    }

    public enum Status {
        UP,
        DOWN
    }

    public String toKey() {
        return name + "@" + baseUrl;
    }

    @Override
    public String toString() {
        return "Application{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
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
}

