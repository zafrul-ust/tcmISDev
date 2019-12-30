package radian.tcmis.server7.share.db;

import java.util.ResourceBundle;
import java.util.Enumeration;
/**
 * This is a utility class that provides convenience methods for
 * retrieving different types of values from a ResourceBundle.
 * This class is meant to minimize the amount of data conversion
 * that needs to be done when working with ResourceBundles.
 *
 * @author Frank Y. Kim
 */
public class ResourceLibrary {
    /** The resource bundle we want to use. */
    private ResourceBundle resourceBundle;
public ResourceLibrary(String file)
{
    resourceBundle = ResourceBundle.getBundle( file );
}
    public boolean getBoolean( String name )
    {
        return Boolean.valueOf( getString( name ) ).booleanValue();
    }
    public double getDouble( String name )
    {
        return Double.valueOf( getString( name ) ).doubleValue();
    }
    public float getFloat( String name )
    {
        return Float.valueOf( getString( name ) ).floatValue();
    }
    public int getInt( String name )
    {
        return Integer.valueOf( getString( name ) ).intValue();
    }
    public Enumeration getKeys()
    {
        return resourceBundle.getKeys();
    }
    public long getLong( String name )
    {
        return Long.valueOf( getString( name ) ).longValue();
    }
    public String getString( String name )
    {
        return resourceBundle.getString( name );
    }
}