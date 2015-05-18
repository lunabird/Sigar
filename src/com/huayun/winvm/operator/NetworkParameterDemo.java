package com.huayun.winvm.operator;

import java.math.BigInteger;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
 
public class NetworkParameterDemo {
  public static void main(String[] args) throws Exception {
    Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    while (en.hasMoreElements()) {
      NetworkInterface ni = en.nextElement();
      printParameter(ni); 
      System.out.println("****************************************************");
    }
  }
 
  public static void printParameter(NetworkInterface ni) throws SocketException {
    System.out.println(" Name = " + ni.getName());
    System.out.println(" Display Name = " + ni.getDisplayName());
    System.out.println(" Is up = " + ni.isUp());
    System.out.println(" Support multicast = " + ni.supportsMulticast());
    System.out.println(" Is loopback = " + ni.isLoopback());
    System.out.println(" Is virtual = " + ni.isVirtual());
    System.out.println(" Is point to point = " + ni.isPointToPoint());
    if(ni.getHardwareAddress()!=null){
    	String mac = bytesToHexString(ni.getHardwareAddress());
    	System.out.println(" Hardware address = " + mac);
    }else{
    	System.out.println(" Hardware address = " + ni.getHardwareAddress());
    }
    System.out.println(" MTU = " + ni.getMTU());
 
    System.out.println("\nList of Interface Addresses:");
    List<InterfaceAddress> list = ni.getInterfaceAddresses();
    Iterator<InterfaceAddress> it = list.iterator();
 
    while (it.hasNext()) {
      InterfaceAddress ia = it.next();
      System.out.println(" Address = " + ia.getAddress());
      System.out.println(" Broadcast = " + ia.getBroadcast());
      System.out.println(" Network prefix length = " + ia.getNetworkPrefixLength());
      System.out.println("");
    }
  }
  
  public static String bytesToHexString(byte[] src){       
      StringBuilder stringBuilder = new StringBuilder();       
      if (src == null || src.length <= 0) {       
          return null;       
      }       
      for (int i = 0; i < src.length; i++) {       
          int v = src[i] & 0xFF;       
          String hv = Integer.toHexString(v);       
          if (hv.length() < 2) {       
              stringBuilder.append(0);       
          }       
          stringBuilder.append(hv);       
      }       
      return stringBuilder.toString();       
  }
}
  
  

