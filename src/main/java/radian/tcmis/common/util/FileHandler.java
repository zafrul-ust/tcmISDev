package radian.tcmis.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.net.URL;

/***************************************************************************
 * CLASSNAME: FileHandler<BR>
 * Function: <BR>
 * instances: <BR>
 * methods: copy.<BR>
 ***************************************************************************/


public class FileHandler {
  public FileHandler() {
  }

/******************************************************************************
 *           Makes a copy of a file.<BR>
 * @param source  Source to copy.
 * @param destination Copy of source.
****************************************************************************/
  public static synchronized void copy(File source, File destination) throws IOException {
    FileChannel in = null;
    FileChannel out = null;
    try {
      in = new FileInputStream(source).getChannel();
      out = new FileOutputStream(destination).getChannel();
      long size = in.size();
      MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
      out.write(buf);
    }
    finally {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }


  /******************************************************************************
   *           Copies an url to a file.<BR>
   * @param url  url to copy file from.
   * @param destination Copy of source.
  ****************************************************************************/
    public static synchronized void copy(URL url, File destination) throws IOException {
      InputStream inputStream = null;
      FileOutputStream fileOutputStream = null;
      try {
        inputStream = url.openStream();
        fileOutputStream = new FileOutputStream(destination);
        int character = 0;
        while((character = inputStream.read()) != -1) {
          fileOutputStream.write(character);
        }
      }
      finally {
        if (inputStream != null) {
          inputStream.close();
        }
        if (fileOutputStream != null) {
          fileOutputStream.close();
        }
      }
    }

}