package radian.tcmis.both1.helpers;

import java.util.*;
import java.io.*;


public class FileClassLoader extends ClassLoader
{
  Hashtable cache = new Hashtable();

  public Class loadClass(String name, boolean resolve) throws ClassNotFoundException
  {
    try
    {
      Class c=(Class)cache.get(name);
      if(c==null) {
          c=findSystemClass(name);
          if( c!= null) {
            if (name.indexOf("WebConnect") > 0){
               cache.remove(name);
            } else {
              cache.put(name, c);
              return(c);
            }
          }
      }
      String tmp = new String(name.replace('.','/'));
      tmp = "d:/Projects/java/" + tmp;
      FileInputStream fis = new FileInputStream(tmp + ".class");
      File f = new File(tmp + ".class");
      byte bytes[] = new byte[(int)(f.length())];
      int n = fis.read(bytes);
      c = defineClass(name, bytes, 0, bytes.length);
      if (resolve) resolveClass(c);
      cache.put(name,c);
      return c;
    }  catch (NullPointerException e) {
      // System.out.println("null pointer"+e);
    }  catch (java.io.FileNotFoundException e) {
      // System.out.println("file not found"+e);
    }  catch (java.io.IOException e) {
      // System.out.println("io exception "+e);
    }
    return null;
  }
}

