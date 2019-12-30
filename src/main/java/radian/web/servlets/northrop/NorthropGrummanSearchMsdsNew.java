package radian.web.servlets.northrop;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NorthropGrummanSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ NorthropGrummansearchmsds check it is here....");
      return "NORTHROP_GRUMMAN";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NorthropGrummanServerResourceBundle();
   }
}