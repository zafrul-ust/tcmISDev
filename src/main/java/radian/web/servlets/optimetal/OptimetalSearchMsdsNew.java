package radian.web.servlets.optimetal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OptimetalSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Optimetalsearchmsds check it is here....");
      return "Optimetal";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OptimetalServerResourceBundle();
   }
}