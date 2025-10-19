package org.noear.solon.admin.server.utils;

import org.noear.snack4.ONode;

import java.lang.reflect.Type;

/**
 * @author noear
 * @since 2.3
 */
public class JsonUtils {
    public static String toJson(Object obj) {
        return ONode.serialize(obj);
    }

    public static <T> T fromJson(String json, Type objClz){
        return ONode.deserialize(json, objClz);
    }
}
