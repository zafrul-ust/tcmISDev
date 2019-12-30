package radian.tcmis.client.share.helpers;

import java.util.*;
import java.io.*;

import Acme.Crypto.DesCipher;
import Acme.Crypto.Rc4Cipher;
import Acme.Crypto.EncryptedOutputStream;
import Acme.Crypto.EncryptedInputStream;

public class TcmisEncrypt {

  public static boolean onOff = false;
  private String badKey = "abcdefghijk";
  private byte[] msg = null;
  protected boolean worksOnInt = false;

  public TcmisEncrypt(){

  }

  public TcmisEncrypt(byte[] msg, String key){
    this.msg = msg;
    if (key != null) this.badKey = key;
  }

  public byte[] encryptString(byte[] msg) {
    return encryptString(msg,badKey);
  }
  public byte[] decryptString(byte[] msg) {
    return decryptString(msg,badKey);
  }

  public byte[] encrypt() {
    return encryptString(msg,badKey);
  }

  public byte[] decrypt() {
    return decryptString(msg,badKey);
  }

  public byte[] encryptString(byte[] msg, String key) {
    StringBuffer sb = new StringBuffer();
    DesCipher dc;
    Rc4Cipher r4;
    byte[] out = null;

    try {

    dc = new DesCipher(key);
    r4 = new Rc4Cipher(key);
    PipedInputStream pis = new PipedInputStream();
    PipedOutputStream pos = new PipedOutputStream(pis);
    EncryptedOutputStream eos = new EncryptedOutputStream(dc,pos);
//    EncryptedOutputStream eos = new EncryptedOutputStream(r4,pos);
    eos.setEncrypting(onOff);
    //writing
    byte[] clr = msg;
    byte[] real;
    // System.out.println("String to encr:"+msg);
    if (worksOnInt){
       String hh = new String("");
       for (int ii=0;ii<clr.length;ii++){
          Byte B = new Byte(clr[ii]);
          Integer I = new Integer(B.intValue());
          hh = hh + (ii>0?"_":"") + I.toString();
       }
       real = hh.getBytes();
       // System.out.println("String to encr on it:"+hh);
    } else {
       real = clr;
    }
    int clrLen = real.length;
    eos.write(real,0,clrLen);
    eos.flush();
    eos.close();

    //reading
    int in = 0;
    int count = 0;
    while (true) {
      in = pis.read();
      if (in == -1) break;
      sb.append((char)in);
      count++;
    }

    }catch(Exception e){ e.printStackTrace();}
    String muddy = sb.toString();
    return muddy.getBytes();

    }


  public byte[] decryptString(byte[] msg, String key) {
    StringBuffer sb = new StringBuffer();
    DesCipher dc;
    Rc4Cipher r4;
    byte[] out = null;

    try {


   dc = new DesCipher(key);
    //r4 = new Rc4Cipher(key);
    PipedOutputStream pos = new PipedOutputStream();
    PipedInputStream pis = new PipedInputStream(pos);
    EncryptedInputStream eis = new EncryptedInputStream(dc,pis);
//    EncryptedInputStream eis = new EncryptedInputStream(r4,pis);
    //writing
     byte[] mud = msg;
    int mudLen = mud.length;
   pos.write(mud,0,mudLen);
    pos.flush();
    pos.close();

    //reading
    eis.setDecrypting(onOff);

    int in = 0;
    while (true) {
      in = eis.read();
      if (in == -1) {
        break;
      }
      sb.append((char)in);
    }

    // System.out.println("String to dencr int:"+sb);
    if (worksOnInt){
      StringTokenizer st = new StringTokenizer(new String(sb),"_");
      String hh = new String("");
      while (st.hasMoreTokens()) {
             Integer I = new Integer(st.nextToken());
             Byte B = new Byte(I.byteValue());
             hh = hh + (char) B.byteValue();
      }
      sb = new StringBuffer(hh);
    }

    // System.out.println("String to dencr:"+sb);
    }catch(Exception e){e.printStackTrace();}

    String clear = sb.toString();
    return clear.getBytes();


  }

  public void worksOnInt(boolean f){
      worksOnInt = f;
  }


}




















