package org.noear.solon.admin.server.controller;

import org.noear.solon.admin.server.data.ApplicationWebsocketTransfer;
import org.noear.solon.admin.server.services.ApplicationService;
import org.noear.solon.admin.server.services.ClientMonitorService;
import org.noear.solon.admin.server.utils.JsonUtils;
import org.noear.solon.annotation.Inject;
import org.noear.solon.net.annotation.ServerEndpoint;
import org.noear.solon.net.websocket.WebSocket;
import org.noear.solon.net.websocket.listener.SimpleWebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 应用程序 WebSocket Controller
 *
 * @author shaokeyibb
 * @since 2.3
 */
@ServerEndpoint("/ws/application")
public class ApplicationWebsocketController extends SimpleWebSocketListener {
    private static final Logger log = LoggerFactory.getLogger(ApplicationWebsocketController.class);

    @Inject
    private ApplicationService applicationService;

    @Inject
    private ClientMonitorService clientMonitorService;

    @Inject("applicationWebsocketSessions")
    private List<WebSocket> sessions;

    @Override
    public void onOpen(WebSocket session) {
        if(log.isDebugEnabled()){
            log.debug("onOpen...");
        }

        sessions.add(session);
    }

    @Override
    public void onMessage(WebSocket session, String message) {
        if(log.isDebugEnabled()) {
            log.debug("onMessage: " + message);
        }

        ApplicationWebsocketTransfer data = JsonUtils.fromJson(message, ApplicationWebsocketTransfer.class);

        // 获取全部应用程序信息
        if (data.getType().equals("getAllApplication")) {
            session.send(JsonUtils.toJson(new ApplicationWebsocketTransfer<>(
                    null,
                    "getAllApplication",
                    applicationService.getApplications()
            )));
        }
    }

    @Override
    public void onClose(WebSocket session) {
        if(log.isDebugEnabled()){
            log.debug("onClose...");
        }

        sessions.remove(session);
    }
}
