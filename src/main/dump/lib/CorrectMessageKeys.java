import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */


/**
 * CorrectMessageKeys
 * 
 * @author k
 * @version $Revision$
 */

public class CorrectMessageKeys
{
    public static void main(String[] args) throws Exception
    {
        Properties mapping = new Properties();
        mapping.load(new FileReader("mapping.properties"));
        
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        Writer out = new FileWriter(args[1]);
        
        String line;
        while ((line = in.readLine()) != null)
        {
            String[] parts = line.split(" = ", 2);
            if (parts.length != 2)
            {
                out.write(line);
            }
            else
            {
                String newKey = mapping.getProperty(parts[0]);
                if (newKey == null)
                    throw new RuntimeException("No ID mapping found for " + parts[0]);
                out.write(newKey);
                out.write(" = ");
                out.write(parts[1]);
            }
            out.write('\n');
        }
        in.close();
        out.close();
    }
}
