package radian.web.servlets.tomkins;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TomkinsSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ tomkinssearchmsds check it is here....");
      return "TOMKINS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TomkinsServerResourceBundle();
   }
}