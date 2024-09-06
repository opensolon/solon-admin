package org.noear.solon.admin.server.config;

import org.noear.solon.Utils;
import org.noear.solon.admin.server.utils.BasicAuthUtils;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.route.PathRule;
import org.noear.solon.core.route.RouterInterceptor;
import org.noear.solon.core.route.RouterInterceptorChain;

/**
 * 鉴权拦截器
 *
 * @author noear
 * @since 3.0
 */
public class AuthRouterInterceptor implements RouterInterceptor {
    private String uiPath;
    private ServerProperties serverProperties;

    public AuthRouterInterceptor(String uiPath, ServerProperties serverProperties) {
        this.uiPath = uiPath;
        this.serverProperties = serverProperties;
    }

    @Override
    public PathRule pathPatterns() {
        if (Utils.isEmpty(uiPath)) {
            return null;
        } else {
            return new PathRule().include(uiPath);
        }
    }

    @Override
    public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
        if (!BasicAuthUtils.basicAuth(ctx, serverProperties)) {
            BasicAuthUtils.response401(ctx);
        } else {
            chain.doIntercept(ctx, mainHandler);
        }
    }
}
