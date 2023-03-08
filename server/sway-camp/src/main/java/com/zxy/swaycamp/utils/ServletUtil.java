package com.zxy.swaycamp.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 客户端工具类
 *
 * @author XinYuan Zhao
 * @since 2023/1/29
 */
public class ServletUtil {
    private ServletUtil(){}

    /**
     * IP归属地查询
     */
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    private static final String UNKNOWN = "unknown";
    private static final UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer
            .newBuilder()
            .hideMatcherLoadStats()
            .withCache(10000)
            .withField(UserAgent.AGENT_NAME_VERSION)
            .build();

    /**
     * Describe: Request 客户端地址(获取ip地址)
     *
     * @return {@link String}
     */
    public static String getIp() {
        HttpServletRequest request = SwayUtil.getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getHttpCityInfo(String ip) {
        String api = String.format(IP_URL, ip);
        cn.hutool.json.JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }


    /**
     * Request 浏览器类型
     *
     * @return {@link String}
     */
    public static String getBrowser() {
        String browser = "";
        String userAgent = SwayUtil.getRequest().getHeader("User-Agent");
        if (userAgent.contains("Firefox")) {
            browser = "火狐浏览器";
        } else if (userAgent.contains("Chrome")) {
            browser = "谷歌浏览器";
        } else if (userAgent.contains("Trident")) {
            browser = "IE 浏览器";
        } else {
            browser = "你用啥浏览器";
        }
        UserAgent.ImmutableUserAgent parse = userAgentAnalyzer.parse(userAgent);
        String value = parse.get(UserAgent.AGENT_NAME_VERSION).getValue();
        return browser + "(" + value + ")";
    }

    /**
     * Request 访问来源 ( 客户端类型 )
     *
     * @return {@link String}
     */
    public static String getSystem() {
        String userAgent = SwayUtil.getRequest().getHeader("User-Agent");
        if (SwayUtil.getRequest().getHeader("User-Agent").toLowerCase().contains("windows")) {
            return "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            return "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            return "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            return "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            return "IPhone";
        } else {
            return "UnKnown, More-Info: " + userAgent;
        }
    }
}
