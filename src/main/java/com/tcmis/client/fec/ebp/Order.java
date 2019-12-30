/*
 * Order.java
 *
 * Created on June 13, 2005, 3:29 PM
 */

package com.tcmis.client.fec.ebp;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  mike.najera
 */
public class Order implements Serializable {
   
   private OrderHeader header;
   private Vector lines;
   private OrderSummary summary;
   
   /** Creates a new instance of Order */
   public Order() {
      lines = new Vector();
   }
   
   public void addHeader(OrderHeader header) {
      this.header = header;
   }
   
   public void addLine(Line line) {
      lines.add(line);
   }
   
   public void addSummary(OrderSummary summary) {
      this.summary = summary;
   }
   
   public OrderHeader getHeader() {
      return header;
   }
   
   public Vector getLines() {
      return lines;
   }
   
   public OrderSummary getSummary() {
      return summary;
   }
}
