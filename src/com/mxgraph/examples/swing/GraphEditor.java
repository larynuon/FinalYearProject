package com.mxgraph.examples.swing;

import java.awt.Color;
import java.awt.Point;

import java.awt.print.PageFormat;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;

import com.mxgraph.examples.swing.editor.BasicGraphEditor;
import com.mxgraph.examples.swing.editor.EditorPalette;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;


public class GraphEditor extends BasicGraphEditor
{  
    public GraphEditor()
    {
        this("mxGraph Editor", new CustomGraphComponent(new CustomGraph()));
    }

    public GraphEditor(String appTitle, mxGraphComponent component)
    {

        super(appTitle, component);
        final mxGraph graph = graphComponent.getGraph();

        // Creates the palettes
        EditorPalette playersPalette = insertPalette("Players");

        EditorPalette ballPalette = insertPalette("Ball");
      
        EditorPalette shapesPalette = insertPalette("Miscellaneous");
        // Sets the edge template to be used for creating new edges if an edge
        // is clicked in the shape palette
        shapesPalette.addListener(mxEvent.SELECT, new mxIEventListener()
        {
            public void invoke(Object sender, mxEventObject evt)
            {
                Object tmp = evt.getProperty("transferable");

                if (tmp instanceof mxGraphTransferable)
                {
                    mxGraphTransferable t = (mxGraphTransferable) tmp;
                    Object cell = t.getCells()[0];

                    if (graph.getModel().isEdge(cell))
                    {
                        ((CustomGraph) graph).setEdgeTemplate(cell);
                    }
                }
            }
        });

        // Adds some template cells for dropping into the graph
        ballPalette.addTemplate("Ball",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/h_ball.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/h_ball.png",
            20, 20, "");

        playersPalette.addTemplate("Player Blue",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_blue.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_blue.png",
            20, 20, "Blue Player");

        playersPalette.addTemplate("Player Red",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_red.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_red.png",
            20, 20,  "Red Player");

        playersPalette.addTemplate("Player Black",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_black.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_black.png",
            20, 20,  "Black Player");

        playersPalette.addTemplate("Player Green",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_green.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_green.png",
            20, 20,  "Green Player");

        playersPalette.addTemplate("Player Orange",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_orange.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_orange.png",
            20, 20,  "Orange Player");

        playersPalette.addTemplate("Player White",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_white.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_white.png",
            20, 20,  "White Player");

        playersPalette.addTemplate("Player Yellow",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/hp_yellow.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/hp_yellow.png",
            20, 20,  "Yellow Player");

        playersPalette.addTemplate("Dot Blue",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_blue.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_blue.png",
            20, 20,  "Dot Blue");
        playersPalette.addTemplate("Dot Red",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_red.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_red.png",
            20, 20,  "Dot Red");
        playersPalette.addTemplate("Dot Black",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_black.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_black.png",
            20, 20,  "Dot Black");
        playersPalette.addTemplate("Dot Green",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_green.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_green.png",
            20, 20,  "Dot Green");
        playersPalette.addTemplate("Dot Orange",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_orange.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_orange.png",
            20, 20,  "Dot Orange");
        playersPalette.addTemplate("Dot White",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_white.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_white.png",
            20, 20,  "Dot White");
        playersPalette.addTemplate("Dot Yellow",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/dot_yellow.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/dot_yellow.png",
            20, 20,  "Dot Yellow");
      
        shapesPalette.addEdgeTemplate("Straight",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/straight.png")),"straight",
            75, 75, "");

        shapesPalette.addEdgeTemplate("Horizontal Connector",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/connect.png")),
            null, 100, 100, "");

        shapesPalette.addEdgeTemplate("Vertical Connector",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/vertical.png")),
            "vertical", 100, 100, "");

        shapesPalette.addTemplate("Textbox",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/rectangle.png")),
            null, 160, 120, "");

        shapesPalette.addTemplate("Marker Blue",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_blue.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_blue.png",
            20, 20,  "Marker Blue");

        shapesPalette.addTemplate("Marker Red",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_red.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_red.png",
            20, 20,  "Marker Red");

        shapesPalette.addTemplate("Marker Green",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_green.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_green.png",
            20, 20,  "Marker Green");

        shapesPalette.addTemplate("Marker Orange",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_orange.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_orange.png",
            20, 20,  "Marker Orange");

        shapesPalette.addTemplate("Marker White",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_white.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_white.png",
            20, 20,  "Marker White");

        shapesPalette.addTemplate("Marker Yellow",
            new ImageIcon(GraphEditor.class
            .getResource("/com/mxgraph/examples/swing/images/marker_yellow.png")),
            "roundImage;image=/com/mxgraph/examples/swing/images/marker_yellow.png",
            20, 20,  "Marker Yellow");
    }


    public static class CustomGraphComponent extends mxGraphComponent
    {
        /**
        * @param graph
        */
        public CustomGraphComponent(mxGraph graph)
        {
            super(graph);

            PageFormat page = new PageFormat();
            // Sets switches typically used in an editor
            setPageVisible(true);
            page.setOrientation(PageFormat.LANDSCAPE) ;
            setPageFormat(page);
            setPageScale(.86);

            setToolTips(true);
            getConnectionHandler().setCreateTarget(true);

            // Loads the defalt stylesheet from an external file
            mxCodec codec = new mxCodec();
            Document doc = mxUtils.loadDocument(GraphEditor.class.getResource(
            "/com/mxgraph/examples/swing/resources/default-style.xml")
            .toString());
            codec.decode(doc.getDocumentElement(), graph.getStylesheet());

            // Sets the background to white
            getViewport().setOpaque(false);
            setBackground(Color.WHITE);
        }

        /**
        * Overrides drop behavior to set the cell style if the target
        * is not a valid drop target and the cells are of the same
        * type (e.g. both vertices or both edges).
        */
        public Object[] importCells(Object[] cells, double dx, double dy,
        Object target, Point location)
        {
            if (target == null && cells.length == 1 && location != null)
            {
                target = getCellAt(location.x, location.y);

                if (target instanceof mxICell && cells[0] instanceof mxICell)
                {
                    mxICell targetCell = (mxICell) target;
                    mxICell dropCell = (mxICell) cells[0];

                    if (targetCell.isVertex() == dropCell.isVertex()
                                || targetCell.isEdge() == dropCell.isEdge())
                    {
                        mxIGraphModel model = graph.getModel();
                        model.setStyle(target, model.getStyle(cells[0]));
                        graph.setSelectionCell(target);

                        return null;
                    }
                }
            }

            return super.importCells(cells, dx, dy, target, location);
        }

    }

    /**
    * A graph that creates new edges from a given template edge.
    */
    public static class CustomGraph extends mxGraph
    {
        /**
        * Holds the edge to be used as a template for inserting new edges.
        */
        protected Object edgeTemplate;

        /**
        * Custom graph that defines the alternate edge style to be used when
        * the middle control point of edges is double clicked (flipped).
        */
        public CustomGraph()
        {
            setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
        }

        /**
        * Sets the edge template to be used to inserting edges.
        */
        public void setEdgeTemplate(Object template)
        {
            edgeTemplate = template;
        }

        /**
        * Overrides the method to use the currently selected edge template for
        * new edges.
        *
        * @param graph
        * @param parent
        * @param id
        * @param value
        * @param source
        * @param target
        * @param style
        * @return
        */
        public Object createEdge(Object parent, String id, Object value,
        Object source, Object target, String style)
        {
            if (edgeTemplate != null)
            {
                mxCell edge = (mxCell) cloneCells(new Object[] { edgeTemplate })[0];
                edge.setId(id);

                return edge;
            }

            return super.createEdge(parent, id, value, source, target, style);
        }
    }

}
