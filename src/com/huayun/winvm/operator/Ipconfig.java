package com.huayun.winvm.operator;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
 
/**
 * @author JavaAlpha
 * @date 2011-12-14
 * @version V 1.0
 * java���� ����dos��ipconfig /all �����ȡ������ϸ��Ϣ
 */ 
 
public class Ipconfig { 
 
    /**
     * @param args
     */ 
    public static void main(String[] args) { 
    	
    	String c = getLocalMachineInfo("��̫��������  :");  
        System.out.println("��̫����������"+c);
        //��ȡ������ 
        String hostName = getLocalMachineInfo("������ . . . . . . . . . . . . :");  
        System.out.println("��������"+hostName); 
        //��ȡ�����ͺ� 
        String desc = getLocalMachineInfo("Description . . . . . . . . . . . :");  
        System.out.println("�����ͺţ�"+desc); 
        //��ȡMAC��ַ 
        String mac = getLocalMachineInfo("Physical Address. . . . . . . . . :");  
        System.out.println("MAC��ַ��"+mac); 
        //��ȡIP��ַ 
        String ip = getLocalMachineInfo("IP Address. . . . . . . . . . . . :");  
        System.out.println("IP��ַ��"+ip); 
        //��ȡ�������� 
        String subnetMask = getLocalMachineInfo("Subnet Mask . . . . . . . . . . . :");  
        System.out.println("�������룺"+subnetMask); 
        //��ȡĬ������ 
        String defaultGateway = getLocalMachineInfo("Default Gateway . . . . . . . . . :");  
        System.out.println("��������"+defaultGateway); 
        //��ȡDNS������ 
        String DNSServers = getLocalMachineInfo("DNS Servers . . . . . . . . . . . :");  
        System.out.println("��������"+DNSServers); 
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