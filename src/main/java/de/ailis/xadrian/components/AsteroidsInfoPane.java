/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.freemarker.TemplateFactory;
import de.ailis.xadrian.interfaces.SectorProvider;
import de.ailis.xadrian.utils.SwingUtils;
import freemarker.template.Template;

/**
 * Component which displays information about asteroids of a sector.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class AsteroidsInfoPane extends JComponent implements SectorProvider
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The freemarker template for the asteroids info */
    private static final Template template = TemplateFactory
            .getTemplate("asteroids.ftl");

    /** The sector to display the asteroids for */
    private Sector sector;

    /** The text pane */
    private JTextPane textPane;

    /**
     * Constructor
     */
    public AsteroidsInfoPane()
    {
        super();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(420, 120));

        final JTextPane textPane = this.textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBorder(null);
        textPane.setContentType("text/html");
        textPane.setDoubleBuffered(true);
        SwingUtils.setPopupMenu(textPane, new JTextPanePopupMenu(textPane));
 
        final HTMLDocument document = (HTMLDocument) this.textPane.getDocument();

        // Set the base URL of the text pane
        document.setBase(Main.class.getResource("templates/"));
        
        // Modify the body style so it matches the system font
        final Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { font-family: " + font.getFamily() + 
            "; font-size: " + font.getSize() + "pt; }";
        document.getStyleSheet().addRule(bodyRule);                
        
        final JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane);

        redraw();
    }

    /**
     * Redraws the freemarker template.
     */
    private void redraw()
    {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("sector", this.sector);
        final String content = TemplateFactory.processTemplate(template,
            model);
        this.textPane.setText(content);
        this.textPane.setCaretPosition(0);
    }

    /**
     * Sets the sector to display the asteroids for. Specify null to reset to no
     * sector.
     *
     * @param sector
     *            The sector to set;
     */
    @Override
    public void setSector(final Sector sector)
    {
        if (sector == this.sector) return;
        if (sector != null && sector.equals(this.sector)) return;
        this.sector = sector;
        redraw();
    }

    /**
     * Returns the currently selected sector.
     *
     * @return The sector or null if none set
     */
    @Override
    public Sector getSector()
    {
        return this.sector;
    }

    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */
    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        Game game = GameFactory.getInstance().getGame("x3tc");
        final AsteroidsInfoPane component = new AsteroidsInfoPane();
        component.setSector(game.getSectorFactory().getSector("sec-2-11"));
        SwingUtils.testComponent(component);
    }

    /**
     * @see de.ailis.xadrian.interfaces.SectorProvider#canChangeSector()
     */
    @Override
    public boolean canChangeSector()
    {
        return true;
    }
}
