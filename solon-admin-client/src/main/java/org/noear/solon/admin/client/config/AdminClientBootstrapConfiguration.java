package org.noear.solon.admin.client.config;

import okhttp3.OkHttpClient;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 配置文件检查和注入
 *
 * @author shaokeyibb
 * @since 2.3
 */
@Configuration
public class AdminClientBootstrapConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AdminClientBootstrapConfiguration.class);

    @Inject
    AppContext appContext;

    @Inject(value = "${solon.admin.client}", required = false)
    ClientProperties clientProperties = new ClientProperties();

    @Bean
    public ClientProperties clientProperties() {
        if (MarkedClientEnabled.LOCAL_MODE.equals(clientProperties.getMode())) {
            log.debug("Injected localClientProperties: {}", clientProperties);
        } else {
            log.debug("Injected cloudClientProperties: {}", clientProperties);
        }

        String serverUrl = (String) Solon.app().shared().get("solon-admin-server-url");
        if (Utils.isNotEmpty(serverUrl)) {
            clientProperties.setServerUrl(serverUrl);
        }

        return clientProperties;
    }

    @Bean
    public MarkedClientEnabled markedClientEnabled(@Inject ClientProperties clientProperties) {
        if (clientProperties == null || !clientProperties.isEnabled()) {
            return null;
        }
        return new MarkedClientEnabled(clientProperties.getMode());
    }

    public static class MarkedClientEnabled {
        public static final String LOCAL_MODE = "local";
        public static final String CLOUD_MODE = "cloud";
        public final String mode;

        public MarkedClientEnabled(String mode) {
            this.mode = mode;

            log.info("Solon Admin client has been successfully enabled in {} mode.", this.mode);
        }

        public String getMode() {
            return mode;
        }
    }

    @Bean
    public OkHttpClient okHttpClient(@Inject(required = false) MarkedClientEnabled marker,
                                     @Inject ClientProperties clientProperties) {
        if (marker == null) return null;

        return new OkHttpClient.Builder()
                .connectTimeout(clientProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(clientProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }
}
