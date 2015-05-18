package com.huayun.winvm.operator;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class MonitorInfo 
{
	
	public static String getCPU()
	{
		 Sigar sigar = new Sigar(); 
		 CpuPerc cpu;
		 String CPU=null;
		 try { 
			   cpu = sigar.getCpuPerc(); 
			   CPU=CpuPerc.format(cpu.getCombined());
			
			  } catch (SigarException e)
			  { 
			   e.printStackTrace(); 
			  }
		  return CPU;
	}
	
	public static String getMemory()
	{
		Sigar sigar = new Sigar(); 
		  Mem mem;
		  String Memory=null;
		  DecimalFormat df = new DecimalFormat("0.00");
		  try{
		   mem = sigar.getMem(); 
		Memory=df.format(mem.getFree()*100.0/mem.getTotal())+"%";
	
		  }
		  catch(Exception e)
		  {
			 e.printStackTrace(); 
		  }
		  return Memory; 
   }
/*	public static ArrayList<String> getDiskInfo()
	{
		ArrayList<String> DiskInfo=new ArrayList<String>();
		try{
			
		  Sigar sigar = new Sigar(); 
		  FileSystem fslist[] = sigar.getFileSystemList(); 
		  FileSystemUsage usage=null;
		  for (FileSystem fs:fslist) 
		  {
			  usage = sigar.getFileSystemUsage(fs.getDirName()); 
			  String s=fs.getDevName().replace("\\", "")+usage.getUsed()*100/usage.getTotal()+"%";
			  DiskInfo.add(s);
		  }
		  }
		catch(Exception e)
		{
			
		}
		  return DiskInfo;
		
	}*/
	public static String getMAC() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			String MAC = null;
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
						|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}

				MAC = cfg.getHwaddr();
				break;
			}
			return MAC != null ? MAC : null;
		} catch (Exception e) {
			return null;
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

	public static String getHostName()
	{
		
		Sigar sigar = null; 
		  try { 
		   return InetAddress.getLocalHost().getCanonicalHostName(); 
		  } catch (UnknownHostException e) { 
		   try { 
		    sigar = new Sigar(); 
		    return sigar.getFQDN(); 
		   } catch (SigarException ex) { 
		    return null; 
		   } finally { 
		    sigar.close(); 
		   } 
		  } 
		
		
	}
	public static String getIP()
	{
		 String address = null; 
		  try { 
		   address = InetAddress.getLocalHost().getHostAddress(); 
		  if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) { 
		    return address; 
		   } 
		  } catch (UnknownHostException e) { 
		  
		  } 
		  Sigar sigar = new Sigar(); 
		  try { 
		   address = sigar.getNetInterfaceConfig().getAddress(); 
		  } catch (SigarException e) { 
		   address = NetFlags.LOOPBACK_ADDRESS; 
		  } finally { 
		   sigar.close(); 
		  } 
		  return address; 
		
	}
	
	static String getOSName()
	{
		 return System.getProperty("os.name");
	}
	static String getOSNumber()
	{
		 OperatingSystem OS = OperatingSystem.getInstance(); 
		 return  OS.getDataModel();
	}
		
//	public static void main(String[] args)
//	{
//	
//		 System.out.println(getOSName()); 
//		 System.out.println(getOSNumber()); 
//		
//	}

}

