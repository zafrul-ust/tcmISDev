/*
 * FeedDigester.java
 *
 * Created on August 24, 2005, 4:36 PM
 */

package com.tcmis.client.fec.ebp;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
/**
 *
 * @author  mnajera
 */
public class FeedDigester {
   
   private Digester d;   
   private Feed feed;
   private String feedFile;
   /** Creates a new instance of FeedDigester */
   public FeedDigester(String file) {
      this.feedFile = file;
      init();
   }

   public Feed getFeed() {
      return feed;
   }
   
   private void init() {
      // Create a Digester instance
     d = new Digester();

     // Prime the digester stack with an object for rules to
     // operate on. Note that it is quite common for "this"
     // to be the object pushed.
     feed = new Feed();
     d.push(feed);

     // Add rules to the digester that will be triggered while
     // parsing occurs.
     addRules(d);

     // Process the input file.
     try {
         File srcfile = new File(feedFile);
         d.parse(srcfile);
     }
     catch(IOException ioe) {
         System.out.println("Error reading input file:" + ioe.getMessage());
         System.exit(-1);
     }
     catch(SAXException se) {
         System.out.println("Error parsing input file:" + se.getMessage());
         System.exit(-1);
     }                
    }

   private static void addRules(Digester d) {
        d.addCallMethod("SCXML/BoundDoc/DocBody/DocBodyPayload", "setPayload", 0);
    }
  
}
