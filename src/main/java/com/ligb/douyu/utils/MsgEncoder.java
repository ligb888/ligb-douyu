package com.ligb.douyu.utils;

import java.util.Map;

class MsgEncoder {
    private String buffer;

    MsgEncoder() {
        buffer = new String();
    }

    String encode(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buffer += entry.getKey().replaceAll("/", "@S").replaceAll("@", "@A") + "@="
                    + entry.getValue().replaceAll("/", "@S").replaceAll("@", "@A")
                    +"/";
        }

        buffer += '\0';
        return buffer;
    }
}
