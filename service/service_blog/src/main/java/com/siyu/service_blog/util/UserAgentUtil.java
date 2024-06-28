package com.siyu.service_blog.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Component
public class UserAgentUtil {
    private UserAgentAnalyzer userAgentAnalyzer;

    public UserAgentUtil() {
        this.userAgentAnalyzer = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR)
                .withField(UserAgent.AGENT_NAME_VERSION)
                .withField(UserAgent.DEVICE_NAME)
                .build();
	}

    public Map<String, String> parseUserAgent(String UA) {
        UserAgent userAgent = userAgentAnalyzer.parse(UA);
        String os = userAgent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR);
        String browser = userAgent.getValue(UserAgent.AGENT_NAME_VERSION);
        String device = userAgent.getValue(UserAgent.DEVICE_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("browser", browser);
        map.put("device", device);
        map.put("os", os);
        return map;
    }
}
