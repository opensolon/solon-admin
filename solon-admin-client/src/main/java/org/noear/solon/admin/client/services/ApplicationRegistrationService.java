package org.noear.solon.admin.client.services;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.*;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.admin.client.config.AdminClientBootstrapConfiguration;
import org.noear.solon.admin.client.config.ClientProperties;
import org.noear.solon.admin.client.data.Application;
import org.noear.solon.admin.client.data.EnvironmentInformation;
import org.noear.solon.admin.client.utils.JsonUtils;
import org.noear.solon.admin.client.utils.NetworkUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.LoadBalance;
import org.noear.solon.core.handle.Result;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * 应用程序注册服务
 *
 * @author shaokeyibb
 * @since 2.3
 */
@Slf4j
@Component
public class ApplicationRegistrationService {
    @Inject
    private OkHttpClient client;

    @Inject("${solon.app.name}")
    private String applicationName;

    @Inject
    private ClientProperties properties;

    private Application.ApplicationBuilder getApplicationBuilder() {
        return Application.builder()
                .name(this.applicationName)
                .token(properties.getToken())
                .baseUrl(NetworkUtils.getHostAndPort());
    }

    /**
     * 获取当前应用程序信息
     *
     * @return 当前应用程序信息
     */
    public Application getCurrentApplication() {
        return getApplicationBuilder().build();
    }

    /**
     * 向 Solon Admin Server 注册当前应用程序
     */
    public void register() {
        log.info("Attempting to register this client as an application with Solon Admin server...");
        // 向 Server 发送注册请求
        // 循环重试，直到注册成功
        boolean registerResult = false;
        while (!registerResult) {
            val serverUrl = getServerUrl();
            if (serverUrl != null) {
                String clientMetadata = Solon.cfg().getProp("solon.app").toString();
                try (Response response = client.newCall(new Request.Builder()
                        .url(new URL(serverUrl + "/solon-admin/api/application/register"))
                        .put(RequestBody.create(JsonUtils.toJson(
                                getApplicationBuilder()
                                        .metadata(clientMetadata)
                                        .showSecretInformation(properties.isShowSecretInformation())
                                        .environmentInformation(EnvironmentInformation.create())
                                        .build()
                        ), MediaType.parse("application/json")))
                        .build()).execute()) {

                    registerResult = assertResponse("register application", response, false);
                } catch (Exception ex) {
                    log.warn("Unexpected error occurred during the application registration: {}", ex.getMessage());
                    registerResult = false;
                }
            }
            try {
                Thread.sleep(properties.getRetryInterval());
            } catch (InterruptedException ex) {
                log.warn(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 向 Solon Admin Server 注销
     */
    public void unregister() {
        log.info("Attempting to unregister this client from Solon Admin server...");
        val serverUrl = getServerUrl();
        if (serverUrl != null) {
            // 向 Server 发送注销请求
            try (Response response = client.newCall(new Request.Builder()
                    .url(new URL(serverUrl + "/solon-admin/api/application/unregister"))
                    .delete(RequestBody.create(JsonUtils.toJson(getCurrentApplication()),
                            MediaType.parse("application/json")))
                    .build()).execute()) {

                assertResponse("unregister application", response, false);
            } catch (Exception ex) {
                log.warn("Unexpected error occurred during the application de-registration: {}", ex.getMessage());
            }
        }
    }

    /**
     * 向 Solon Admin Server 发送心跳
     * @return 心跳是否成功
     */
    public boolean heartbeat() {
        log.trace("Attempting to send heartbeat to Solon Admin server...");
        val serverUrl = getServerUrl();
        if (serverUrl != null) {
            // 向 Server 发送心跳请求
            try (Response response = client.newCall(new Request.Builder()
                    .url(new URL(serverUrl + "/solon-admin/api/application/heartbeat"))
                    .post(RequestBody.create(JsonUtils.toJson(
                            getCurrentApplication()
                    ), MediaType.parse("application/json")))
                    .build()).execute()) {

                return assertResponse("send heartbeat", response, true);
            } catch (Exception ex) {
                log.warn("Unexpected error occurred during the heartbeat sending: {}", ex.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取 Solon Admin Server 实际地址
     * @return Solon Admin Server 地址
     */
    private String getServerUrl() {
        val serverUri = URI.create(this.properties.getServerUrl());
        String serverUrl;
        if (AdminClientBootstrapConfiguration.MarkedClientEnabled.CLOUD_MODE.equals(properties.getMode())
                && serverUri.getScheme().equals("lb")) {
            // cloud 模式，并且配置的 serverUrl 为 lb://xxx 负载地址
            // 通过 LoadBalance 从注册中心获取实际的 Solon Admin Server 地址
            val server = LoadBalance.get(Solon.cfg().appGroup(), serverUri.getHost()).getServer();
            if (server != null) {
                serverUrl = server;
            } else {
                log.warn("Not found Solon Admin server instance: {}:{}", Solon.cfg().appGroup(), serverUri.getHost());
                return null;
            }
        } else {
            // local 模式或配置的 serverUrl 为 http://xxx 地址，则直接返回
            serverUrl = serverUri.toString();
        }
        return serverUrl.replaceAll("/+$", "");
    }

    /**
     * 确认响应是否成功
     *
     * @param type     请求类型
     * @param response 响应
     * @param traceLog true 成功后打印 trace 日志，false 打印 info 日志
     * @return 响应是否成功
     */
    private boolean assertResponse(String type, Response response, boolean traceLog) throws IOException {
        if (!response.isSuccessful()) {
            log.error("Failed to {} to Solon Admin server. response: {}", type, response);
        }
        val body = response.body();
        if (body != null) {
            String res = new String(response.body().bytes());
            Result<?> result = ONode.load(res).toObject(Result.class);
            if (result.getCode() == 200) {
                if (traceLog) {
                    log.trace("Successfully {} to Solon Admin server.", type);
                } else {
                    log.info("Successfully {} to Solon Admin server.", type);
                }
                return true;
            } else {
                log.error("Failed to {} to Solon Admin server. adminResponse: {}", type, res);
                return false;
            }
        } else {
            log.error("Failed to {} to Solon Admin server. adminResponse: null", type);
            return false;
        }
    }

}
