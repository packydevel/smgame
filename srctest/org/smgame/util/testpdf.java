/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.util;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import com.adobe.acrobat.Viewer;

/**
 *
 * @author packyuser
 */
public class testpdf extends Viewer {

    public testpdf() throws Exception {
    }

    public static void main(String args[]) {
        Frame f = new Frame("Sample Acrobat Reader");

        f.setLayout(new BorderLayout());
        Label top = new Label("Acrobat Reader created using adobe.Acrobat.Viewer", Label.CENTER);
        top.setBackground(Color.red);
        f.add(top, BorderLayout.NORTH);
        f.add(new Label("Adobe Acrobat Reader - Alpha release - 1998", Label.CENTER), BorderLayout.SOUTH);
        try {

            // Construct a acrobat object aka Acrobar Reader
            // note that you must also call its activate
            // method before you show the containing panel,
            // in this case the frame object.

            // The acrobat object is declared as final
            // so that it could be referenced in the
            // following windowClosing method.

            final Viewer acrobat = new Viewer();

            f.addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {

                    if (acrobat != null) {

                        // The deactivate method will ensure that the
                        // acrobat.properties file is saved
                        // upon exit.

                        acrobat.deactivate();
                    }

                    System.exit(0);
                }
            });

            if (args.length > 0) {
                try {

                    // assumes that args[0] is the name of a file

                    FileInputStream in = new FileInputStream(args[0]);
                    acrobat.setDocumentInputStream(in);

                } catch (FileNotFoundException x) {
                    System.out.println("File not found!");
                // The viewer will display a blank screen.
                // You can then use the Viewer's pop-up menu
                // to open a local or remote PDF file.
                }
            }

            f.add(acrobat, BorderLayout.CENTER);

            // you must call activate to enable the Viewer object
            // to layout its sub-components and the further initialization
            // needed for it to be displayed.

            acrobat.activate(); //WithoutBars();

        } catch (Exception x) {
            f.add(new Label("Unable to create an Acrobat Reader"), "Center");
        }

        f.setSize(400, 400);
        f.show();

    }
}

