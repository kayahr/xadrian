import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
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
        File enFile = new File(args[0]);
        File fnFile = new File(args[1]);
        File outFile = new File(args[2]);
        
        BufferedReader enReader = new BufferedReader(new InputStreamReader(new FileInputStream(enFile)));
        BufferedReader fnReader = new BufferedReader(new InputStreamReader(new FileInputStream(fnFile)));
        OutputStream out = new FileOutputStream(outFile);
        
        String line;
        while ((line = fnReader.readLine()) != null)
        {
            String enLine = enReader.readLine();
            String[] parts = line.split("=", 2);
            if (parts.length != 2)
            {
                out.write(line.getBytes());
            }
            else
            {
                String[] enParts = enLine.split("=", 2);
                out.write(enParts[0].getBytes());
                out.write('=');
                out.write(parts[1].getBytes());
            }
            out.write('\n');
        }
        
        enReader.close();
        fnReader.close();
        out.close();
    }
}
