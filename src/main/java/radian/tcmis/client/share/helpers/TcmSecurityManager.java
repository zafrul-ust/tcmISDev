package radian.tcmis.client.share.helpers;

import java.io.*;
import java.rmi.*;
import java.net.*;

public class TcmSecurityManager extends RMISecurityManager {

       public synchronized void checkAccess(Thread t){}
       public synchronized void checkAccess(ThreadGroup g){}
       public synchronized void checkExit(int status){}
       public synchronized void checkProprietiesAccess(){}
       public synchronized void checkAccept (String host,int port){}
       public synchronized void checkConnect (String host,int port){}
       public synchronized boolean checkTopLevelWindow(Object win){
          return true;
       }
       public synchronized void checkPackageAccess(String pkg){}
       public synchronized void checkAwtEventQueueAccess(){}
       public synchronized void checkRead(FileDescriptor fd){}
       public synchronized void checkRead(String file){}
       public synchronized void checkExec(String cmd){}

       public synchronized void checkCreateClassLoader() {}
       public synchronized void checkLink(String lib){}
       public synchronized void checkPropertyAccess(String key) {}
       public synchronized void checkWrite(String file){}
       public void checkDelete(String file) {}
       public synchronized void checkWrite(FileDescriptor fd){}
       public synchronized void checkListen(int port){}
       public void checkMulticast(InetAddress maddr){}
       public void checkMulticast(InetAddress maddr, byte ttl) {}
       public void checkConnect(String host, int port, Object context){}
       public synchronized void checkPackageDefinition(String pkg){}
       public synchronized void checkSetFactory() {}
       public void checkPrintJobAccess() {}
       public void checkSystemClipboardAccess(){}
       public void checkMemberAccess(Class clazz,  int which){}
       public void checkSecurityAccess(String provider) {}
}

























