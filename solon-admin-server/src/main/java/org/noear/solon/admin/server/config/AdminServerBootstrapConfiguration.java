package org.noear.solon.admin.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.OkHttpClient;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 配置文件检查和注入
 *
 * @author shaokeyibb
 * @since 2.3
 */
@Configuration
public class AdminServerBootstrapConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AdminServerBootstrapConfiguration.class);

    @Condition(onExpression = "${solon.admin.server.enabled:true} == 'true'")
    @Bean
    public MarkedServerEnabled markedServerEnabled(@Inject("${solon.admin.server.mode:local}") String mode) {
        return new MarkedServerEnabled(mode);
    }

    @Bean
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor(@Inject(required = false) MarkedServerEnabled marker) {
        if (marker == null) return null;
        return new ScheduledThreadPoolExecutor(1);
    }

    public static class MarkedServerEnabled {
        public static final String LOCAL_MODE = "local";
        public static final String CLOUD_MODE = "cloud";
        private final String mode;

        public MarkedServerEnabled(String mode) {
            this.mode = mode;

            log.info("Solon Admin server has been successfully enabled in {} mode.", this.mode);
        }
        
        public String getMode() {
            return mode;
        }
    }

    @Bean
    public OkHttpClient okHttpClient(@Inject(required = false) MarkedServerEnabled marker) {
        if (marker == null) return null;
        ServerProperties config = Solon.context().getBean(ServerProperties.class);

        return new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }

}
