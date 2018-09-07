package com.mxgraph.examples.swing.editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.mxgraph.examples.swing.editor.EditorActions.ColorAction;
import com.mxgraph.examples.swing.editor.EditorActions.KeyValueAction;
import com.mxgraph.examples.swing.editor.EditorActions.PromptValueAction;
import com.mxgraph.examples.swing.editor.EditorActions.SetStyleAction;
import com.mxgraph.examples.swing.editor.EditorActions.ToggleAction;

import com.mxgraph.util.mxConstants;

import com.mxgraph.util.mxResources;


public class EditorMenuBar extends JMenuBar
{
        /**
	 * Adds menu items to the given format menu. This is factored out because
	 * the format menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateFormatMenu(JMenu menu, BasicGraphEditor editor)
	{


                JMenu submenu = (JMenu) menu.add(new JMenu(mxResources
				.get("background")));

		submenu.add(editor.bind(mxResources.get("fillcolor"), new ColorAction(
				"Fillcolor", mxConstants.STYLE_FILLCOLOR),
				"/com/mxgraph/examples/swing/images/fillcolor.gif"));
                submenu = (JMenu) menu.add(new JMenu(mxResources.get("label")));

		submenu.add(editor.bind(mxResources.get("fontcolor"), new ColorAction(
				"Fontcolor", mxConstants.STYLE_FONTCOLOR),
				"/com/mxgraph/examples/swing/images/fontcolor.gif"));


		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("labelFill"), new ColorAction(
				"Label Fill", mxConstants.STYLE_LABEL_BACKGROUNDCOLOR)));
                submenu.add(editor.bind(mxResources.get("labelBorder"),
				new ColorAction("Label Border",
						mxConstants.STYLE_LABEL_BORDERCOLOR)));


                submenu = (JMenu) menu.add(new JMenu(mxResources.get("line")));

		submenu.add(editor.bind(mxResources.get("linecolor"), new ColorAction(
				"Linecolor", mxConstants.STYLE_STROKECOLOR),
				"/com/mxgraph/examples/swing/images/linecolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("dashed"), new ToggleAction(
				mxConstants.STYLE_DASHED)));
		submenu.add(editor.bind(mxResources.get("linewidth"),
				new PromptValueAction(mxConstants.STYLE_STROKEWIDTH,
						"Linewidth")));
                		                
                menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("connector")));

		submenu.add(editor.bind(mxResources.get("straight"),
				new SetStyleAction("straight"),
				"/com/mxgraph/examples/swing/images/straight.gif"));

		submenu.add(editor.bind(mxResources.get("horizontal"),
				new SetStyleAction(""),
				"/com/mxgraph/examples/swing/images/connect.gif"));
		submenu.add(editor.bind(mxResources.get("vertical"),
				new SetStyleAction("vertical"),
				"/com/mxgraph/examples/swing/images/vertical.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("entityRelation"),
				new SetStyleAction("edgeStyle=mxEdgeStyle.EntityRelation"),
				"/com/mxgraph/examples/swing/images/entity.gif"));
		submenu.add(editor.bind(mxResources.get("arrow"),
                                new SetStyleAction(
				"arrow"), "/com/mxgraph/examples/swing/images/arrow.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("plain"), new ToggleAction(
				mxConstants.STYLE_NOEDGESTYLE)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("linestart")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OPEN),
				"/com/mxgraph/examples/swing/images/open_start.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC),
				"/com/mxgraph/examples/swing/images/classic_start.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.ARROW_BLOCK),
				"/com/mxgraph/examples/swing/images/block_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND),
				"/com/mxgraph/examples/swing/images/diamond_start.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OVAL),
				"/com/mxgraph/examples/swing/images/oval_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(
				mxConstants.STYLE_STARTARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(
				mxConstants.STYLE_STARTSIZE, "Linestart Size")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("lineend")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN),
				"/com/mxgraph/examples/swing/images/open_end.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC),
				"/com/mxgraph/examples/swing/images/classic_end.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK),
				"/com/mxgraph/examples/swing/images/block_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.ARROW_DIAMOND),
				"/com/mxgraph/examples/swing/images/diamond_end.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OVAL),
				"/com/mxgraph/examples/swing/images/oval_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(
				mxConstants.STYLE_ENDARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(
				mxConstants.STYLE_ENDSIZE, "Linestart Size")));

                menu.addSeparator();
                
                submenu = (JMenu) menu.add(new JMenu(mxResources.get("alignment")));
		submenu.add(editor.bind(mxResources.get("left"), new KeyValueAction(
				mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT),
				"/com/mxgraph/examples/swing/images/left.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new KeyValueAction(
				mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER),
				"/com/mxgraph/examples/swing/images/center.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new KeyValueAction(
				mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT),
				"/com/mxgraph/examples/swing/images/right.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new KeyValueAction(
				mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP),
				"/com/mxgraph/examples/swing/images/top.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new KeyValueAction(
				mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE),
				"/com/mxgraph/examples/swing/images/middle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new KeyValueAction(
				mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM),
				"/com/mxgraph/examples/swing/images/bottom.gif"));

                menu.addSeparator();
               
                menu.add(editor.bind(mxResources.get("rotation"),
				new PromptValueAction(mxConstants.STYLE_ROTATION,
						"Rotation (0-360)")));
 }

}
