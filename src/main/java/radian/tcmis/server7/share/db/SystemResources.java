package radian.tcmis.server7.share.db;

/**
 * This is a singleton utility class that contains resources
 * from a properties file.
 *
 * @author Frank Y. Kim
 */
public class SystemResources extends ResourceLibrary
{
        /** The single instance of this class. */
        private static SystemResources instance = null;
/**
 * This ctor is private to force clients to use getInstance()
 * to access this class.
 */
private SystemResources() {
        super( "TcmIS" );
}
        /**
         * The method through which this class is accessed.
         *
         * @return The single instance of this class.
         */
        public static SystemResources getInstance()
        {
                if (instance == null)
                {
                        synchronized (SystemResources.class)
                        {
                                if (instance == null)
                                {
                                        instance = new SystemResources();
                                }
                        }
                }
                return instance;
        }
}