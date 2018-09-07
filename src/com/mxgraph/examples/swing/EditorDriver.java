/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mxgraph.examples.swing;


/**
 *
 * @author Lary
 */
import javax.swing.JApplet;
public class EditorDriver extends JApplet
{

    private final int WIDTH = 735, HEIGHT = 635;

    public void init()
    {
        
        GraphEditor application = new GraphEditor();
        getContentPane().add (application);
        setSize (WIDTH, HEIGHT);

    }

}
