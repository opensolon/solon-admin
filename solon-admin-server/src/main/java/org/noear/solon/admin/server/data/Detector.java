package org.noear.solon.admin.server.data;

import java.util.Map;

/**
 * 监视器数据
 *
 * @author shaokeyibb
 * @since 2.3
 */

public class Detector {

    private String name;

    private Map<String, Object> info;

    public Detector() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
