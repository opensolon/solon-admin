package org.noear.solon.admin.client.registration;

import org.noear.solon.admin.client.config.AdminClientBootstrapConfiguration;
import org.noear.solon.admin.client.config.ClientProperties;
import org.noear.solon.admin.client.services.ApplicationRegistrationService;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.event.AppLoadEndEvent;
import org.noear.solon.core.event.AppPrestopEndEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自动向 Solon Admin Server 注册客户端信息
 *
 * @author shaokeyibb
 * @since 2.3
 */
@Configuration
public class AutoRegistrationConfiguration {
    private final Timer timer = new Timer();

    @Inject
    AppContext appContext;

    @Inject
    private ClientProperties clientProperties;

    @Bean
    public void afterInjection(
            @Inject(required = false) AdminClientBootstrapConfiguration.MarkedClientEnabled marked,
            @Inject ApplicationRegistrationService applicationRegistrationService
    ) {
        if (marked == null) return;

        // 订阅事件
        appContext.onEvent(AppLoadEndEvent.class, e -> onStart(applicationRegistrationService));
        appContext.onEvent(AppPrestopEndEvent.class, e -> onStop(applicationRegistrationService));
    }

    public void onStart(ApplicationRegistrationService applicationRegistrationService) {
        // 开启新的守护线程，不阻塞主程序执行，不影响业务
        Thread subThread = new Thread(() -> {
            // 注册应用程序，直至注册成功
            applicationRegistrationService.register();
            // 计划心跳
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!applicationRegistrationService.heartbeat()) {
                        // 心跳失败时，进行重新注册
                        // 注册应用程序，直至注册成功
                        applicationRegistrationService.register();
                    }
                }
            }, 0, clientProperties.getHeartbeatInterval());
        });
        subThread.setDaemon(true);
        subThread.start();
    }

    public void onStop(ApplicationRegistrationService applicationRegistrationService) {
        // 注销应用程序
        applicationRegistrationService.unregister();
        // 取消心跳计时器
        timer.cancel();
    }
}
