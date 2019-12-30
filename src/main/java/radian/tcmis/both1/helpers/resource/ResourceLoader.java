package radian.tcmis.both1.helpers.resource;

import javax.swing.ImageIcon;
import java.net.URL;
import radian.tcmis.both1.helpers.resource.ResourceException;
import java.io.InputStream;
import sun.audio.*;



/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS/Radian Corp
 * @author       Rajendra Rajput
 * @version      1.0
 */
/*
  This class is used to get images and play sound from jar
*/
public class ResourceLoader
{

  private static ClassLoader resClassLodr ;
  public ResourceLoader()
  {
  }

  static
  {
    resClassLodr = ResourceLoader.class.getClassLoader();
  }

  public static synchronized ImageIcon getImageIcon( String imageUrl )
  {

   URL resUrl = null;
   ImageIcon resIcon = null;
   try
   {
       resUrl = resClassLodr.getResource( imageUrl );
       if ( null == resUrl)
       {
          throw new ResourceException ( "Invalid imageUrl : " + imageUrl);
       }
       resIcon = new ImageIcon( resUrl );
       if ( null == resIcon)
       {
          throw new ResourceException ( "Unable to get image : " + imageUrl );
       }
   }
   catch ( ResourceException e)
   {
      e.printStackTrace();
      //System.out.println(e.toString());
   }
   catch ( Exception e)
   {
      e.printStackTrace();
      //System.out.println(e.toString());
   }
   return resIcon;
  }

  public static synchronized ImageIcon getImageIcon( String imageUrl, String Name )
  {

   URL resUrl = null;
   ImageIcon resIcon = null;
   try
   {
       resUrl = resClassLodr.getResource( imageUrl );
       if ( null == resUrl)
       {
          throw new ResourceException ( "Invalid imageUrl : " + imageUrl + "-" + Name);
       }
       resIcon = new ImageIcon( resUrl, Name );
       if ( null == resIcon)
       {
          throw new ResourceException ( "Unable to get image : " + imageUrl + "-" + Name );
       }
   }
   catch ( ResourceException e)
   {
      e.printStackTrace();
      //System.out.println(e.toString());
   }
   catch ( Exception e)
   {
      e.printStackTrace();
      //System.out.println(e.toString());
   }
   return resIcon;
  }


  //
  public static synchronized boolean playAudioFile( String audioUrl )
  {
   boolean result = true;
   URL resUrl = null;

   InputStream ins = null;
   AudioStream as = null;
   try
   {
       resUrl = resClassLodr.getResource( audioUrl );
       if ( null == resUrl)
       {
          result = false;
          throw new ResourceException ( "Invalid audioUrl : " + audioUrl );
       }
       ins = resUrl.openStream();
       if ( null == ins)
       {
          result = false;
          throw new ResourceException ( "Unable to get sound file : " + audioUrl );
       }
       as = new AudioStream(ins);
       if ( null == ins)
       {
          result = false;
          throw new ResourceException ( "Unable to create sound : " + audioUrl );
       }
       AudioPlayer.player.start(as);
   }
   catch ( ResourceException e)
   {
      result = false;
      e.printStackTrace();
      //System.out.println(e.toString());
      System.err.println("Can't open requested audio stream" + audioUrl);
   }
   catch ( Exception e)
   {
      result = false;
      e.printStackTrace();
      //System.out.println(e.toString());
      System.err.println("Can't open requested audio stream : " + audioUrl );
   }

   return result;

  }
  //
}