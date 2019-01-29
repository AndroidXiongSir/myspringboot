package com.xiong.exam.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonUtil {
    public JsonUtil() {
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }


    public static JSONObject parseObject(String str) {
        JSONObject jsonObject = null;

        try {
            if (StringUtils.isEmpty(str)) {
                jsonObject = JSON.parseObject(str);
            }
        } catch (Exception var3) {
            ;
        }

        return jsonObject;
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        Object result = null;

        try {
            if (StringUtils.isEmpty(text)) {
                result = JSON.parseObject(text, clazz);
            }
        } catch (Exception var4) {
        }

        return (T) result;
    }

    public static JSONArray parseArray(String str) {
        JSONArray jsonArray = null;

        try {
            if (StringUtils.isEmpty(str)) {
                jsonArray = JSON.parseArray(str);
            }
        } catch (Exception var3) {
            ;
        }

        return jsonArray;
    }

    public static String toJsonString(Object object) {
        String jsonStr = null;

        try {
            if (object != null) {
                jsonStr = JSON.toJSONString(object);
            }
        } catch (Exception var3) {
            ;
        }

        return jsonStr;
    }

    public static String toJsonStringWithExclude(Object object, String... fields) {
        String jsonStr = null;

        try {
            if (object != null) {
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(new String[0]);
                if (fields != null && fields.length > 0) {
                    String[] arr$ = fields;
                    int len$ = fields.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        String field = arr$[i$];
                        filter.getExcludes().add(field);
                    }
                }

                jsonStr = JSON.toJSONString(object, filter, new SerializerFeature[0]);
            }
        } catch (Exception var8) {
        }

        return jsonStr;
    }

    public static String baijiObj2Json(Object baijiObj) {
        return toJsonStringWithExclude(baijiObj, "schema");
    }
}