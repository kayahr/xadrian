/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.EventListenerList;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.html.HTMLDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Complex;
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.dialogs.AddFactoryDialog;
import de.ailis.xadrian.dialogs.OpenComplexDialog;
import de.ailis.xadrian.dialogs.QuantityDialog;
import de.ailis.xadrian.dialogs.SaveComplexDialog;
import de.ailis.xadrian.dialogs.SunsDialog;
import de.ailis.xadrian.dialogs.YieldDialog;
import de.ailis.xadrian.freemarker.TemplateFactory;
import de.ailis.xadrian.listeners.EditorStateListener;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog.Result;
import de.ailis.xadrian.utils.FileUtils;
import freemarker.template.Template;


/**
 * Complex Editor component.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ComplexEditor extends JComponent implements HyperlinkListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -582597303446091577L;
    
    /** The logger */
    private static final Log log = LogFactory.getLog(ComplexEditor.class);

    /** The freemarker template for the content */
    private static final Template template = TemplateFactory
        .getTemplate("complex.ftl");

    /** The event listener list */
    private final EventListenerList listenerList = new EventListenerList();

    /** The text pane */
    private final JTextPane textPane;

    /** The edited complex */
    private final Complex complex;

    /** The file under which this complex was last saved */
    private File file;
    
    /** True if this editor has unsaved changes */
    private boolean changed = false;


    /**
     * Constructor
     * 
     * @param complex
     *            The complex to edit
     */

    public ComplexEditor(final Complex complex)
    {
        super();
        setLayout(new BorderLayout());

        this.complex = complex;

        // Create the text pane
        this.textPane = new JTextPane();
        this.textPane.setEditable(false);
        this.textPane.setBackground(Color.WHITE);
        this.textPane.setContentType("text/html");
        this.textPane.setDoubleBuffered(true);
        this.textPane.addHyperlinkListener(this);

        // Set the base URL of the text pane
        ((HTMLDocument) this.textPane.getDocument()).setBase(Main.class
            .getResource("templates/"));

        // Create the scroll pane
        final JScrollPane scrollPane = new JScrollPane(this.textPane);
        add(scrollPane);

        // Redraw the content
        redraw();
    }
    
    
    /**
     * Adds an editor state listener.
     * 
     * @param listener
     *            The editor state listener to add
     */

    public void addStateListener(final EditorStateListener listener)
    {
        this.listenerList.add(EditorStateListener.class, listener);
    }


    /**
     * Removes an editor state listener.
     * 
     * @param listener
     *            The editor state listener to remove
     */

    public void removeStateListener(final EditorStateListener listener)
    {
        this.listenerList.remove(EditorStateListener.class, listener);
    }


    /**
     * Fire the editor changed event.
     */

    private void fireState()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == EditorStateListener.class)
                ((EditorStateListener) listeners[i + 1]).editorStateChanged(this);
    }
    
    
    /**
     * Mark this editor as changed.
     */
    
    private void doChange()
    {
        if (!this.changed)
        {
            this.changed = true;
            fireState();
        }
    }
    

    /**
     * Redraws the freemarker template.
     */

    private void redraw()
    {
        final String content = TemplateFactory.processTemplate(template,
            this.complex);
        this.textPane.setText(content);
        this.textPane.setCaretPosition(0);
    }


    /**
     * @see HyperlinkListener#hyperlinkUpdate(HyperlinkEvent)
     */

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e)
    {
        if (e.getEventType() != EventType.ACTIVATED) return;

        final URL url = e.getURL();
        final String protocol = url.getProtocol();

        if ("file".equals(protocol))
        {
            final String action = url.getHost();
            if ("addFactory".equals(action))
            {
                addFactory();
            }
            else if ("removeFactory".equals(action))
            {
                removeFactory(Integer.parseInt(url.getPath().substring(1)));
            }
            else if ("acceptFactory".equals(action))
            {
                acceptFactory(Integer.parseInt(url.getPath().substring(1)));
            }
            else if ("changeQuantity".equals(action))
            {
                changeQuantity(Integer.parseInt(url.getPath().substring(1)));
            }
            else if ("changeYield".equals(action))
            {
                changeYield(Integer.parseInt(url.getPath().substring(1)));
            }
            else if ("changeSuns".equals(action))
            {
                changeSuns();
            }
        }
    }


    /**
     * Adds a new factory to the complex.
     */

    public void addFactory()
    {
        final AddFactoryDialog dialog = AddFactoryDialog.getInstance();
        if (dialog.open() == Result.OK)
        {
            for (final Factory factory : dialog.getFactories())
            {
                this.complex.addFactory(factory);
            }
            doChange();
            redraw();
        }
    }


    /**
     * Removes the factory with the specified index.
     * 
     * @param index
     *            The index of the factory to remove
     */

    public void removeFactory(final int index)
    {
        this.complex.removeFactory(index);
        doChange();
        redraw();
    }
    
    
    /**
     * Accepts an automatically created factory.
     * 
     * @param index
     *            The index of the factory to accept
     */

    public void acceptFactory(final int index)
    {
        this.complex.acceptFactory(index);
        doChange();
        redraw();
    }

    /**
     * Changes the quantity of the factory with the specified index.
     * 
     * @param index
     *            The index of the factory to change
     */

    public void changeQuantity(final int index)
    {
        final QuantityDialog dialog = QuantityDialog.getInstance();
        dialog.setQuantity(this.complex.getQuantity(index));
        if (dialog.open() == Result.OK)
        {
            this.complex.setQuantity(index, dialog.getQuantity());
            doChange();
            redraw();
        }
    }


    /**
     * Changes the yield of the factory with the specified index.
     * 
     * @param index
     *            The index of the factory to change
     */

    public void changeYield(final int index)
    {
        final YieldDialog dialog = YieldDialog.getInstance();
        dialog.setYield(this.complex.getYield(index));
        if (dialog.open() == Result.OK)
        {
            this.complex.setYield(index, dialog.getYield());
            doChange();
            redraw();
        }
    }


    /**
     * Changes the suns.
     */

    public void changeSuns()
    {
        final SunsDialog dialog = SunsDialog.getInstance();
        dialog.setSuns(this.complex.getSuns());
        if (dialog.open() == Result.OK)
        {
            this.complex.setSuns(dialog.getSuns());
            doChange();
            redraw();
        }
    }


    /**
     * Saves the complex under the last saved file. If the file was not saved
     * before then saveAs() is called instead.
     */

    public void save()
    {
        if (this.file == null)
            saveAs();
        else
            save(this.file);
    }

    
    /**
     * Prompts for a file name and saves the complex there.
     */

    public void saveAs()
    {
        File file = SaveComplexDialog.getInstance().open();
        if (file != null)
        {
            // Add file extension if none present
            if (FileUtils.getExtension(file) == null)
                file = new File(file.getPath() + ".x3c");

            // Save the file if it does not yet exists are user confirms
            // overwrite
            if (!file.exists()
                || JOptionPane.showConfirmDialog(null, I18N
                    .getString("confirm.overwrite"), I18N
                    .getString("confirm.title"),
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                save(file);
            }
        }
    }


    /**
     * Prompts for a file name and opens a new complex from there. Returns the
     * complex editor or null if no file was loaded.
     * 
     * @return The new complex editor or null if file was not loaded
     */

    public static ComplexEditor open()
    {
        final File file = OpenComplexDialog.getInstance().open();
        if (file != null)
        {
            final SAXReader reader = new SAXReader();
            Document document;
            try
            {
                document = reader.read(file);
                final Complex complex = Complex.fromXML(document);
                complex.setName(FileUtils.getNameWithoutExt(file));
                final ComplexEditor editor = new ComplexEditor(complex);
                editor.file = file;
                return editor;
            }
            catch (final DocumentException e)
            {
                JOptionPane.showMessageDialog(null, I18N
                    .getString("error.cantReadComplex"), I18N
                    .getString("error.title"), JOptionPane.ERROR_MESSAGE);
                log.error(
                    "Unable to load complex from file " + file + ": " + e, e);
                return null;
            }
        }
        return null;
    }


    /**
     * Save the complex in the specified file.
     * 
     * @param file
     *            The file
     */

    private void save(final File file)
    {
        FileWriter out;
        try
        {
            out = new FileWriter(file);
            try
            {
                this.complex.toXML().write(out);
                this.file = file;
                this.changed = false;
                this.complex.setName(FileUtils.getNameWithoutExt(file));
                redraw();
                fireState();
            }
            finally
            {
                out.close();
            }
        }
        catch (final IOException e)
        {
            JOptionPane.showMessageDialog(null, I18N
                .getString("error.cantWriteComplex"), I18N
                .getString("error.title"), JOptionPane.ERROR_MESSAGE);
            log.error(
                "Unable to save complex to file " + file + ": " + e, e);
        }
    }


    /**
     * Returns the edited complex.
     * 
     * @return The edited complex
     */

    public Complex getComplex()
    {
        return this.complex;
    }
    
    
    /**
     * Returns true if this editor has unsaved changes. False if not.
     * 
     * @return True if this editor has unsaved changes. False if not.
     */
    
    public boolean isChanged()
    {
        return this.changed;
    }
    
    
    /**
     * Toggles the addition of automatically calculated base complex.
     */ 
    
    public void toggleAddBaseComplex()
    {
        this.complex.toggleAddBaseComplex();
        this.redraw();
    }
}
