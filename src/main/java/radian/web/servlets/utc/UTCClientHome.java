package radian.web.servlets.utc;

import radian.tcmis.server7.share.helpers.*;

public class UTCClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "UTC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new UTCServerResourceBundle();
   }
}