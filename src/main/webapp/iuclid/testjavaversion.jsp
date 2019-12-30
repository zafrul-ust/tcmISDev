<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>

<script type='text/javascript' language='javascript'>
    var NOT_INSTALLED = 0;

    var WRONG_VERSION = -1;

    var INSTALLED = 1;

    /**
     * Determines if the Java plugin with the required version number is
     * installed for use in the browser.
     * @param requiredVersion the minimum version number required - must only
     * consist of major and minor version, with no patch version (that means
     * n1.n2.n3 is in 1.4.2 - do NOT use 1.4.2_04). If minor version is 0, do not
     * include (i.e. give 1.3 instead of 1.3.0)
     * @return NOT_INSTALLED if no Java plugin detected, INSTALLED if plugin
     * detected and correct version, WRONG_VERSION if installed but wrong
     * version
     */
    function isJavaPluginInstalled(requiredVersion) {
        if (navigator.userAgent.indexOf("MSIE") != -1) {
            // MSIE plugin lookup
            try {
                var plugin = new ActiveXObject("JavaPlugin");
                if (plugin) {
                    // so now we know we have the plugin - let's see if we have the minimum version
                    // we'll have to "brute-force" a maximum version number
                    // n1.n2 - major version number
                    // n3 - minor version (maintainance version)
                    // n4n5 - patch version

                    var curr = -1;
                    for (var n1 = 1; n1 >= 1 && curr == -1; --n1) {
                        for (var n2 = 9; n2 >= 0 && curr == -1; --n2) {
                            for (var n3 = 9; n3 >= 0 && curr == -1; --n3) {
                                for (var n4 = 9; n4 >= 0 && curr == -1; --n4) {
                                    for (var n5 = 9; n5 >= 0 && curr == -1; --n5) {
                                        var version = "" + n1 + n2 + n3 + "_" + n4 + n5;
                                        try {
                                            var versioned = new ActiveXObject("JavaPlugin." + version);
                                            curr = new Number("" + n1 + n2 + n3);
                                        }
                                        catch (e) {
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (curr == null) curr = "120";

/*
                    var array = requiredVersion.split(".");
                    var convert = new Number(array[0]) * 100;
                    if (array.length > 1) convert += new Number(array[1]) * 10;
                    if (array.length > 2) convert += new Number(array[2]);
*/
alert("current version:" + curr);
                    if (curr >= parseInt(requiredVersion))
                        return INSTALLED;
                    else
                        return WRONG_VERSION;
                }
                return NOT_INSTALLED;
            }
            catch (e) {
                if (e.message == "Automation server can't create object")
                    return NOT_INSTALLED;
                else {
                    alert(e.message);
                    throw e;
                }
            }
        }
        else {
            // NS/Mozilla (presumably other) plugin lookup
            var plugin = navigator.plugins["Java Plug-in"];
            if (plugin) {
                // so now we know we have the plugin - let's see if we have the minimum version
                var mime = navigator.mimeTypes["application/x-java-applet;version=" + requiredVersion];
                if (mime) return INSTALLED;
                return WRONG_VERSION;
            }
            return NOT_INSTALLED;
        }
    }
  </script>
   </head>
   <body>
If your java version is less that 1.5 you should get a message below.
<br>
<SCRIPT language="javascript"><!---
//alert(isJavaPluginInstalled(150));
if(isJavaPluginInstalled(150) < 1) {
  document.write("<font color=\"ffiooo\">It seems like you don't have the necessary version of Java installed to run this program.</font>");
  document.write("<br>");
  document.write("<a href=\"http://javadl.sun.com/webapps/download/AutoDL?BundleId=12797\">Intall Java 1.6 from Sun</a>");
}
--></SCRIPT>
         </p>

         <br>
      </div>

      &nbsp;
      <br>


   </body>
</html>
