package com.sigar.hp;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
  
/**
 * 
 * @author hp
 * 使用Sigar获得:
 * 			CPU的基本信息、使用百分比、使用时间 
 * 			内存信息
 * 			磁盘IO
 * 			网卡IO
 */
public class SigarTest {  
    private CpuInfo info;  
    private CpuPerc perc;  
    private Cpu timer;  
    private Mem mem;
    private FileSystem config;    
    private FileSystemUsage stat;
    private NetInterfaceStat netstat; 
    private NetInterfaceConfig netconfig; 
    private long rxbps;    
    private long txbps;  

  
    public SigarTest() {  
//    	System.out.println(System.getProperty("java.library.path"));//可以把sigar-amd64-winnt.dll放到C:\Windows\system32目录下。
    }  
  
    public void calcuate(Sigar sigar) throws SigarException, InterruptedException {  
        //cpu,mem
    	info = sigar.getCpuInfoList()[0];  
        perc = sigar.getCpuPerc();  
        timer = sigar.getCpu(); 
        mem = sigar.getMem(); 
        
        System.out.println("info:"+info);
        System.out.println("perc:"+perc);
        System.out.println("timer:"+timer);
        System.out.println("mem:"+mem);
        
        //磁盘IO
        /*FileSystem[] fsArr = sigar.getFileSystemList();
		for (FileSystem fs : fsArr) {
			config = fs;
			try {
				stat = sigar.getFileSystemUsage(fs.getDirName());
				System.out.println("stat:"+stat);
			} catch (SigarException e) {
				e.printStackTrace();
			}
		}*/
		//网卡IO
		String[] netIfs = sigar.getNetInterfaceList();    
		for (String name : netIfs) {
			netconfig = sigar.getNetInterfaceConfig(name);
			long start = System.currentTimeMillis();
			NetInterfaceStat statStart = sigar.getNetInterfaceStat(name);
			long rxBytesStart = statStart.getRxBytes();
			long txBytesStart = statStart.getTxBytes();
			Thread.sleep(1000);
			long end = System.currentTimeMillis();
			NetInterfaceStat statEnd = sigar.getNetInterfaceStat(name);
			long rxBytesEnd = statEnd.getRxBytes();
			long txBytesEnd = statEnd.getTxBytes();
			
			NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(name);
//			if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
//					|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
//					|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
//				continue;
//			}

			String MAC = cfg.getHwaddr();
			String des = cfg.getDescription();
			
			
			rxbps = (rxBytesEnd - rxBytesStart) * 8 / (end - start) * 1000;
			txbps = (txBytesEnd - txBytesStart) * 8 / (end - start) * 1000;
			netstat = sigar.getNetInterfaceStat(name);
			System.out.println(name+","+des+","+MAC+":netstat:"+netstat);
		}		
    }  
   
    public static void main(String[] args) throws Exception {  
        Sigar sigar = new Sigar();  
        SigarTest t = new SigarTest();
        t.calcuate(sigar);
    }  
  
}  