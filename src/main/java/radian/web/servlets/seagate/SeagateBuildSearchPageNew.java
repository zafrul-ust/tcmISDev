package radian.web.servlets.seagate;

import radian.tcmis.server7.share.helpers.*;

public class SeagateBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Seagate";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SeagateServerResourceBundle();
   }
}