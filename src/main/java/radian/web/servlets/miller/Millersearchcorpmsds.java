package radian.web.servlets.miller;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class Millersearchcorpmsds  extends radian.web.servlets.miller.msds.millerSearchMsds
{
   public String getClient()
   {
      return "Miller";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MillerServerResourceBundle();
   }
}