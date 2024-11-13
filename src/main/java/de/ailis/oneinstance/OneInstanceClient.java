/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.oneinstance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This thread handles the communication with a single client.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
final class OneInstanceClient implements Runnable
{
    /** The logger. */
    private static final Log LOG = LogFactory.getLog(OneInstanceClient.class);

    /** The application id. */
    private String appId;

    /** The client socket. */
    private Socket socket;

    /**
     * Constructor.
     * 
     * @param socket
     *            The client socket.
     * @param appId
     *            The application id.
     */
    OneInstanceClient(final Socket socket, String appId)
    {
        this.socket = socket;
        this.appId = appId;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            try
            {
                // Send the application ID.
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    this.socket.getOutputStream(), "UTF-8"));
                out.println(this.appId);
                out.flush();

                // Read the data from the client
                InputStream in = this.socket.getInputStream();
                ObjectInputStream objIn = new ObjectInputStream(in);
                File workingDir = (File) objIn.readObject();
                String[] args = (String[]) objIn.readObject();

                // Call event handler
                boolean result =
                    OneInstance.getInstance().fireNewInstance(workingDir, args);

                // Send the result
                out.println(result ? "start" : "exit");
                out.flush();

                // Wait for client disconnect.
                in.read();
            }
            finally
            {
                this.socket.close();
            }
        }
        catch (IOException e)
        {
            LOG.error(e.toString(), e);
        }
        catch (ClassNotFoundException e)
        {
            LOG.error(e.toString(), e);
        }
    }
}
