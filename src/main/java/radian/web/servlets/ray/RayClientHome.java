package radian.web.servlets.ray;

import radian.tcmis.server7.share.helpers.*;

public class RayClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Ray";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new RayServerResourceBundle();
   }
}