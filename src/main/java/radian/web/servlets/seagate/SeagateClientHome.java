package radian.web.servlets.seagate;

import radian.tcmis.server7.share.helpers.*;

public class SeagateClientHome extends radian.web.servlets.share.ClientHome
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