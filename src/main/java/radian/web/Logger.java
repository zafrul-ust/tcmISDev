package radian.web;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.Properties;

public class Logger extends java.lang.Object {
    public static org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getLogger("");
    private static org.apache.log4j.Logger localLogger = org.apache.log4j.Logger.getLogger( Logger.class );
    private static Logger logger;

    // prevent this class from being instantiated
    private Logger() {
    }

    /**
     * Return a single instance of this class to all callers. If this is the first
     * request for an instance, then create a singleton <code>Logger</code> object
     * and initialize it from the specified <code>Properties</code> object.
     *
     * @param   p   The <code>Properties</code> object
     * @return  A singleton instance of this class.
     */
    public static Logger newInstance(Properties p) throws MissingResourceException {
        if( logger == null )
            logger = new Logger().initLogger(p);
        return logger;
    }

    public static Logger newInstance(URL url) throws MissingResourceException {
		if( logger == null )
				logger = new Logger().initLogger(url);
		return logger;
		}

    /**
     * Initialize Log4j from the specified <code>Properties</code> object.
     *
     * @param   p   The <code>Properties</code> object
     * @return  This object to facilitate method cascading.
     */
    protected Logger initLogger(Properties p) throws MissingResourceException {
        if (p == null) {
            throw new MissingResourceException("No log4j configuration properties!", "java.util.Properties", "log4j");
        }
        org.apache.log4j.PropertyConfigurator.configure(p);
        return this;
    }

    protected Logger initLogger(URL url) throws MissingResourceException {
		 if (url == null) {
				 throw new MissingResourceException("No log4j configuration properties!", "java.util.Properties", "log4j");
		 }
		 org.apache.log4j.PropertyConfigurator.configure(url);
		 return this;
	 }

}