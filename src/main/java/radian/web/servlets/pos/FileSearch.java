package radian.web.servlets.pos;
/**
 * Title:
 * Description:
 * Copyright:  Copyright (c) 2001
 * Company:     URS Corporation
 * @author Rajput
 * @version 1.0
 */
import java.io.*;
import java.util.*;

/*
  DO NOT USE THIS CLASS IN A MULTI_THREADED ENVIRONMENT
  getDirectoryContent() method has been tested.
*/

public class FileSearch
{

    private FileSearch()
    {
    }

    /**
     returns a Vector containing all directories and files included
     within the directory passed as parameter. The Recurse parameter
     indicates the method to recurse all directories embedded within
     this one (hierarchical structure)
     Always pass directory name to the method for File parameter
     */
    public static Vector getDirectoryContent(File file,boolean recurse)
    {
        Vector v= new Vector();
        System.out.println ("Is it a Directory :"+file.isDirectory());
        if (file.isDirectory())
        {
            String[] content = file.list();
            for (int i=0; i<content.length; i++)
            {
               System.out.println (content[i]);
               v.addElement(content[i]);
            }//for
        }//is directory
        return v;
    }//getDirectoryContent

}

