package com.huayun.winvm.operator;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
 
/**
 * @author JavaAlpha
 * @date 2011-12-14
 * @version V 1.0
 * java代码 调用dos的ipconfig /all 命令，获取网卡详细信息
 */ 
 
public class Ipconfig { 
 
    /**
     * @param args
     */ 
    public static void main(String[] args) { 
    	
    	String c = getLocalMachineInfo("以太网适配器  :");  
        System.out.println("以太网适配器："+c);
        //获取机器名 
        String hostName = getLocalMachineInfo("主机名 . . . . . . . . . . . . :");  
        System.out.println("机器名："+hostName); 
        //获取网卡型号 
        String desc = getLocalMachineInfo("Description . . . . . . . . . . . :");  
        System.out.println("网卡型号："+desc); 
        //获取MAC地址 
        String mac = getLocalMachineInfo("Physical Address. . . . . . . . . :");  
        System.out.println("MAC地址："+mac); 
        //获取IP地址 
        String ip = getLocalMachineInfo("IP Address. . . . . . . . . . . . :");  
        System.out.println("IP地址："+ip); 
        //获取子网掩码 
        String subnetMask = getLocalMachineInfo("Subnet Mask . . . . . . . . . . . :");  
        System.out.println("子网掩码："+subnetMask); 
        //获取默认网关 
        String defaultGateway = getLocalMachineInfo("Default Gateway . . . . . . . . . :");  
        System.out.println("机器名："+defaultGateway); 
        //获取DNS服务器 
        String DNSServers = getLocalMachineInfo("DNS Servers . . . . . . . . . . . :");  
        System.out.println("机器名："+DNSServers); 
    } 
 
    static String getLocalMachineInfo(String str){ 
        String line =""; 
        int n; 
        try { 
            Process ps = Runtime.getRuntime().exec("cmd /c ipconfig /all"); 
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream())); 
            while(null != (line = br.readLine())) { 
                if (line.indexOf(str)!=-1) { 
                    n = line.indexOf(":"); 
                    line = line.substring(n+2); 
                    break; 
                } 
            } 
            ps.waitFor(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return line; 
    } 
     
}