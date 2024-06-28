package com.siyu.service_blog.util;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class IpUtil {

    private static Searcher searcher = null;
    
    @PostConstruct
	private void initIp2regionResource() { 
        try {
            InputStream inputStream = new ClassPathResource("/ip2region/ip2region.xdb").getInputStream();
		    byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
		    searcher = new Searcher(null, null, dbBinStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getRemoteAddr();  
            if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){  
                    //根据网卡取本机配置的IP  
                InetAddress inet = null;
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {
                    e.printStackTrace();  
                }  
                if(inet != null)
                    ipAddress = inet.getHostAddress();  
            }  
        }  

        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){  
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
            }  
        }  
        return ipAddress; 
    }

    public static String getIpAddress(String ip) {
        if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            return "localhost";
        }
        try {
            return searcher.search(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

}
