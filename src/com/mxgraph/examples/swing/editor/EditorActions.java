/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mxgraph.examples.swing.editor;


/**
 *
 * @author Lary
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.swing.view.mxCellEditor;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import javax.swing.JEditorPane;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author Administrator
 *
 */
public class EditorActions
{

	/**
	 *
	 * @param e
	 * @return Returns the graph for the given action event.
	 */
	public static final BasicGraphEditor getEditor(ActionEvent e)
	{
		if (e.getSource() instanceof Component)
		{
			Component component = (Component) e.getSource();

			while (component != null
					&& !(component instanceof BasicGraphEditor))
			{
				component = component.getParent();
			}

			return (BasicGraphEditor) component;
		}

		return null;
	}




	@SuppressWarnings("serial")
	public static class PrintAction extends AbstractAction
	{


		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e
						.getSource();
				PrinterJob pj = PrinterJob.getPrinterJob();

				if (pj.printDialog())
				{
					PageFormat pf = graphComponent.getPageFormat();
					Paper paper = new Paper();
					double margin = 36;
					paper.setImageableArea(margin, margin, paper.getWidth()
							- margin * 2, paper.getHeight() - margin * 2);
					pf.setPaper(paper);
					pj.setPrintable(graphComponent, pf);

					try
					{
						pj.print();
					}
					catch (PrinterException e2)
					{
						System.out.println(e2);
					}
				}
			}
		}

	}



	@SuppressWarnings("serial")
	public static class SaveAction extends AbstractAction
	{
		protected boolean showDialog;

		protected String lastDir = null;

		public SaveAction(boolean showDialog)
		{
			this.showDialog = showDialog;
		}


		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);

			if (editor != null)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();
				mxGraph graph = graphComponent.getGraph();
				FileFilter selectedFilter = null;
				FileFilter vmlFileFilter = new DefaultFileFilter(".html",
						"VML " + mxResources.get("file") + " (.html)");
				String filename = null;

				if (showDialog || editor.getCurrentFile() == null)
				{
					String wd;

					if (lastDir != null)
					{
						wd = lastDir;
					}
					else if (editor.getCurrentFile() != null)
					{
						wd = editor.getCurrentFile().getParent();
					}
					else
					{
						wd = System.getProperty("user.dir");
					}

					JFileChooser fc = new JFileChooser(wd);

					// Adds the default file format
					FileFilter defaultFilter = new DefaultFileFilter(".mxe",
							"mxGraph Editor " + mxResources.get("file")
									+ " (.mxe)");
					fc.addChoosableFileFilter(defaultFilter);

					// Adds special vector graphics formats and HTML
					fc.addChoosableFileFilter(new DefaultFileFilter(".svg",
							"SVG " + mxResources.get("file") + " (.svg)"));
					fc.addChoosableFileFilter(vmlFileFilter);
					fc.addChoosableFileFilter(new DefaultFileFilter(".html",
							"HTML " + mxResources.get("file") + " (.html)"));

					// Adds a filter for each supported image format
					Object[] imageFormats = ImageIO.getReaderFormatNames();

					// Finds all distinct extensions
					HashSet<String> formats = new HashSet<String>();

					for (int i = 0; i < imageFormats.length; i++)
					{
						String ext = imageFormats[i].toString().toLowerCase();
						formats.add(ext);
					}

					imageFormats = formats.toArray();

					for (int i = 0; i < imageFormats.length; i++)
					{
						String ext = imageFormats[i].toString();
						fc.addChoosableFileFilter(new DefaultFileFilter("."
								+ ext, ext.toUpperCase() + " "
								+ mxResources.get("file") + " (." + ext + ")"));
					}

					// Adds filter that accepts all supported image formats
					fc
							.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
									mxResources.get("allImages")));
					fc.setFileFilter(defaultFilter);
					int rc = fc.showDialog(null, mxResources.get("save"));

					if (rc != JFileChooser.APPROVE_OPTION)
					{
						return;
					}
					else
					{
						lastDir = fc.getSelectedFile().getParent();
					}

					filename = fc.getSelectedFile().getAbsolutePath();
					selectedFilter = fc.getFileFilter();

					if (selectedFilter instanceof DefaultFileFilter)
					{
						String ext = ((DefaultFileFilter) selectedFilter)
								.getExtension();

						if (!filename.toLowerCase().endsWith(ext))
						{
							filename += ext;
						}
					}

					if (new File(filename).exists()
							&& JOptionPane.showConfirmDialog(graphComponent,
									mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION)
					{
						return;
					}
				}
				else
				{
					filename = editor.getCurrentFile().getAbsolutePath();
				}

				try
				{
					String ext = filename
							.substring(filename.lastIndexOf('.') + 1);

					if (ext.equalsIgnoreCase("svg"))
					{
						mxUtils.writeFile(mxUtils.getXml(mxCellRenderer
								.createSvgDocument(graph, null, 1, null, null)
								.getDocumentElement()), filename);
					}
					else if (selectedFilter == vmlFileFilter)
					{
						mxUtils.writeFile(mxUtils.getXml(mxCellRenderer
								.createVmlDocument(graph, null, 1, null, null)
								.getDocumentElement()), filename);
					}
					else if (ext.equalsIgnoreCase("html"))
					{
						mxUtils.writeFile(mxUtils.getXml(mxCellRenderer
								.createHtmlDocument(graph, null, 1, null, null)
								.getDocumentElement()), filename);
					}
					else if (ext.equalsIgnoreCase("mxe")
							|| ext.equalsIgnoreCase("xml"))
					{
						mxCodec codec = new mxCodec();
						String xml = mxUtils.getXml(codec.encode(graph
								.getModel()));

						mxUtils.writeFile(xml, filename);

						editor.setModified(false);
						editor.setCurrentFile(new File(filename));
					}
					else
					{
						Color bg = null;

						if ((!ext.equalsIgnoreCase("gif") && !ext
								.equalsIgnoreCase("png"))
								|| JOptionPane.showConfirmDialog(
										graphComponent, mxResources
												.get("transparentBackground")) != JOptionPane.YES_OPTION)
						{
							bg = graphComponent.getBackground();
						}

						BufferedImage image = mxCellRenderer
								.createBufferedImage(graph, null, 1, bg,
										graphComponent.isAntiAlias(), null,
										graphComponent.getCanvas());

						if (image != null)
						{
							ImageIO.write(image, ext, new File(filename));
						}
						else
						{
							JOptionPane.showMessageDialog(graphComponent,
									mxResources.get("noImageData"));
						}
					}
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(graphComponent,
							ex.toString(),
							mxResources.get("error"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}


	@SuppressWarnings("serial")
	public static class HistoryAction extends AbstractAction
	{

		protected boolean undo;

		public HistoryAction(boolean undo)
		{
			this.undo = undo;
		}

		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);

			if (editor != null)
			{
				if (undo)
				{
					editor.getUndoManager().undo();
				}
				else
				{
					editor.getUndoManager().redo();
				}
			}
		}
	}

        @SuppressWarnings("serial")
	public static class FontStyleAction extends AbstractAction
	{
	
		protected boolean bold;

		public FontStyleAction(boolean bold)
		{
			this.bold = bold;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e
						.getSource();
				Component editorComponent = null;

				if (graphComponent.getCellEditor() instanceof mxCellEditor)
				{
					editorComponent = ((mxCellEditor) graphComponent
							.getCellEditor()).getEditor();
				}

				if (editorComponent instanceof JEditorPane)
				{
					JEditorPane editorPane = (JEditorPane) editorComponent;
					int start = editorPane.getSelectionStart();
					int ende = editorPane.getSelectionEnd();
					String text = editorPane.getSelectedText();

					if (text == null)
					{
						text = "";
					}

					try
					{
						HTMLEditorKit editorKit = new HTMLEditorKit();
						HTMLDocument document = (HTMLDocument) editorPane
								.getDocument();
						document.remove(start, (ende - start));
						editorKit.insertHTML(document, start, ((bold) ? "<b>"
								: "<i>")
								+ text + ((bold) ? "</b>" : "</i>"), 0, 0,
								(bold) ? HTML.Tag.B : HTML.Tag.I);
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}

					editorPane.requestFocus();
					editorPane.select(start, ende);
				}
				else
				{
					mxIGraphModel model = graphComponent.getGraph().getModel();
					model.beginUpdate();
					try
					{
						graphComponent.stopEditing(false);
						graphComponent.getGraph().toggleCellStyleFlags(
								mxConstants.STYLE_FONTSTYLE,
								(bold) ? mxConstants.FONT_BOLD
										: mxConstants.FONT_ITALIC);
					}
					finally
					{
						model.endUpdate();
					}
				}
			}
		}
	}
        
	@SuppressWarnings("serial")
	public static class NewAction extends AbstractAction
	{

		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);

			if (editor != null)
			{
				if (!editor.isModified()
						|| JOptionPane.showConfirmDialog(editor, mxResources
								.get("loseChanges")) == JOptionPane.YES_OPTION)
				{
					mxGraph graph = editor.getGraphComponent().getGraph();
					// Check modified flag and display save dialog
					mxCell root = new mxCell();
					root.insert(new mxCell());
					graph.getModel().setRoot(root);

					editor.setModified(false);
					editor.setCurrentFile(null);
				}
			}
		}
	}


	@SuppressWarnings("serial")
	public static class OpenAction extends AbstractAction
	{

		protected String lastDir;

		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);

			if (editor != null)
			{
				if (!editor.isModified()
						|| JOptionPane.showConfirmDialog(editor, mxResources
								.get("loseChanges")) == JOptionPane.YES_OPTION)
				{
					mxGraph graph = mxGraphActions.getGraph(e);

					if (graph != null)
					{
						String wd = (lastDir != null) ? lastDir : System
								.getProperty("user.dir");

						JFileChooser fc = new JFileChooser(wd);

						// Adds file filter for supported file format
						FileFilter defaultFilter = new DefaultFileFilter(
								".mxe", "mxGraph Editor "
										+ mxResources.get("file") + " (.mxe)");
						fc.addChoosableFileFilter(defaultFilter);

						int rc = fc.showDialog(null, mxResources
								.get("openFile"));

						if (rc == JFileChooser.APPROVE_OPTION)
						{
							lastDir = fc.getSelectedFile().getParent();

							try
							{
								Document document = mxUtils.parse(mxUtils
										.readFile(fc.getSelectedFile()
												.getAbsolutePath()));

								mxCodec codec = new mxCodec(document);
								codec.decode(document.getDocumentElement(),
										graph.getModel());

								editor.setModified(false);
								editor.setCurrentFile(fc.getSelectedFile());
							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}

						}
					}
				}
			}
		}
	}

	@SuppressWarnings("serial")
	public static class ToggleAction extends AbstractAction
	{


		protected String key;

		protected boolean defaultValue;


		public ToggleAction(String key)
		{
			this(key, false);
		}

		public ToggleAction(String key, boolean defaultValue)
		{
			this.key = key;
			this.defaultValue = defaultValue;
		}

		public void actionPerformed(ActionEvent e)
		{
			mxGraph graph = mxGraphActions.getGraph(e);

			if (graph != null)
			{
				graph.toggleCellStyles(key, defaultValue);
			}
		}
	}
    

	@SuppressWarnings("serial")
	public static class SetStyleAction extends AbstractAction
	{

		protected String value;

		public SetStyleAction(String value)
		{
			this.value = value;
		}

		public void actionPerformed(ActionEvent e)
		{
			mxGraph graph = mxGraphActions.getGraph(e);

			if (graph != null && !graph.isSelectionEmpty())
			{
				graph.setCellStyle(value);
			}
		}
	}



	@SuppressWarnings("serial")
	public static class KeyValueAction extends AbstractAction
	{

		protected String key, value;

		public KeyValueAction(String key)
		{
			this(key, null);
		}

		public KeyValueAction(String key, String value)
		{
			this.key = key;
			this.value = value;
		}

		public void actionPerformed(ActionEvent e)
		{
			mxGraph graph = mxGraphActions.getGraph(e);

			if (graph != null && !graph.isSelectionEmpty())
			{
				graph.setCellStyles(key, value);
			}
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("serial")
	public static class PromptValueAction extends AbstractAction
	{

		/**
		 *
		 */
		protected String key, message;

		/**
		 *
		 * @param key
		 */
		public PromptValueAction(String key, String message)
		{
			this.key = key;
			this.message = message;
		}

		/**
		 *
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof Component)
			{
				mxGraph graph = mxGraphActions.getGraph(e);

				if (graph != null && !graph.isSelectionEmpty())
				{
					String value = (String) JOptionPane.showInputDialog(
							(Component) e.getSource(),
							mxResources.get("value"), message,
							JOptionPane.PLAIN_MESSAGE, null, null, "");

					if (value != null)
					{
						if (value.equals(mxConstants.NONE))
						{
							value = null;
						}

						graph.setCellStyles(key, value);
					}
				}
			}
		}
	}


	@SuppressWarnings("serial")
	public static class ColorAction extends AbstractAction
	{

		protected String name, key;

		public ColorAction(String name, String key)
		{
			this.name = name;
			this.key = key;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e
						.getSource();
				mxGraph graph = graphComponent.getGraph();

				if (!graph.isSelectionEmpty())
				{
					Color newColor = JColorChooser.showDialog(graphComponent,
							name, null);

					if (newColor != null)
					{
						graph.setCellStyles(key, mxUtils.hexString(newColor));
					}
				}
			}
		}
	}


}