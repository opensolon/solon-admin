package org.noear.solon.admin.client.config;

import org.noear.solon.Solon;
import org.noear.solon.admin.client.annotation.EnableAdminClient;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.event.AppLoadEndEvent;
import org.noear.solon.core.event.EventBus;
import org.noear.solon.health.detector.DetectorManager;

/**
 * @author noear
 * @since 2.3
 */
public class XPluginImpl implements Plugin {
    @Override
    public void start(AppContext context) throws Throwable {
        if(Solon.app().source().isAnnotationPresent(EnableAdminClient.class) == false){
            return;
        }

        //弃用
        if(Solon.cfg().getBool("solon.admin.client.enabled",true) == false){
            return;
        }

        if(Solon.cfg().getBool("solon.admin.client.enable",true) == false){
            return;
        }

        context.beanScan("org.noear.solon.admin.client");

        context.onEvent(AppLoadEndEvent.class, e->{
            DetectorManager.start("*");
        });
    }
}
