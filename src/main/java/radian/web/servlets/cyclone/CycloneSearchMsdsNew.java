package radian.web.servlets.cyclone;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CycloneSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Cyclonesearchmsds check it is here....");
      return "Cyclone";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CycloneServerResourceBundle();
   }
}