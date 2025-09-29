package org.noear.solon.admin.server.data;

import org.noear.solon.lang.Nullable;

/**
 * 应用程序数据传输 Dto
 *
 * @param <T> 要传输的数据类型
 * @author shaokeyibb
 * @since 2.3
 */
public class ApplicationWebsocketTransfer<T> {

    @Nullable
    private Application scope;

    private String type;

    private T data;


    public ApplicationWebsocketTransfer(Application scope, String type, T data) {
        this.scope = scope;
        this.type = type;
        this.data = data;
    }

    public ApplicationWebsocketTransfer() {
    }

    public Application getScope() {
        return scope;
    }

    public void setScope(Application scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
