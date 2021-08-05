package vpl;
/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import java.net.URL;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpIndexDialog extends JDialog {
    private JEditorPane htmlPane;
    private static boolean DEBUG = false;
    private URL helpURL;
    JTree tree;
    DefaultMutableTreeNode top = null;
    DefaultMutableTreeNode poBook = null;
    DefaultMutableTreeNode phBook = null;
    // added by for acidic textureBobh
    DefaultMutableTreeNode acidBook = null;
    DefaultMutableTreeNode acidSubBook = null;
    DefaultMutableTreeNode acidSubBookRinse = null;
    // end add Bobh

    DefaultMutableTreeNode swBook = null;
    DefaultMutableTreeNode swSubBook = null;
    DefaultMutableTreeNode eBook = null;
    DefaultMutableTreeNode eSubBook = null;
    DefaultMutableTreeNode eSubBookRinse = null;
    DefaultMutableTreeNode tBook = null;
    DefaultMutableTreeNode tSubBook = null;
    DefaultMutableTreeNode tSubBook1 = null;
    DefaultMutableTreeNode tSubBookRAC = null;
    DefaultMutableTreeNode dBook = null;
    DefaultMutableTreeNode dSubBook = null;
    DefaultMutableTreeNode peBook = null;
    DefaultMutableTreeNode peSubBook = null;
    DefaultMutableTreeNode alspBook = null;
    DefaultMutableTreeNode alspSubBook = null;
    DefaultMutableTreeNode alfBook = null;
    DefaultMutableTreeNode alfSubBook = null;
    DefaultMutableTreeNode sspBook = null;
    DefaultMutableTreeNode sspSubBook = null;
    DefaultMutableTreeNode sspSubBookDOR = null;
    DefaultMutableTreeNode arcBook = null;
    DefaultMutableTreeNode arcSubBook = null;

    DefaultMutableTreeNode testBook = null;
    DefaultMutableTreeNode thicknessBook = null;
    DefaultMutableTreeNode mclifetimeBook = null;
    DefaultMutableTreeNode sheetresistivityBook = null;
    DefaultMutableTreeNode spectralresponseBook = null;
    DefaultMutableTreeNode pseudoivBook = null;
    DefaultMutableTreeNode dopingprofileBook = null;
    DefaultMutableTreeNode silverresistanceBook = null;
    DefaultMutableTreeNode voccontourBook = null;
    DefaultMutableTreeNode ivcurveBook = null;

    BookInfo poBookInfo = null;
    BookInfo phBookInfo = null;
    BookInfo swBookInfo = null;
    BookInfo swSubBookInfo = null;

    // added by Bobh for acidic texturing
    BookInfo acidBookInfo = null;
    BookInfo acidSubBookInfo = null;
    BookInfo acidSubBookRinseInfo = null;
    // end add by Bobh

    BookInfo eBookInfo = null;
    BookInfo eSubBookInfo = null;
    BookInfo eSubBookRinseInfo = null;
    BookInfo tBookInfo = null;
    BookInfo tSubBookInfo = null;
    BookInfo tSubBook1Info = null;
    BookInfo tSubBookRACInfo = null;
    BookInfo dBookInfo = null;
    BookInfo dSubBookInfo = null;
    BookInfo peBookInfo = null;
    BookInfo peSubBookInfo = null;
    BookInfo alspBookInfo = null;
    BookInfo alspSubBookInfo = null;
    BookInfo alfBookInfo = null;
    BookInfo alfSubBookInfo = null;
    BookInfo sspBookInfo = null;
    BookInfo sspSubBookInfo = null;
    BookInfo sspSubBookDORInfo = null;
    BookInfo arcBookInfo = null;
    BookInfo arcSubBookInfo = null;

    BookInfo testBookInfo = null;
    BookInfo thicknessBookInfo = null;
    BookInfo mclifetimeBookInfo = null;
    BookInfo sheetresistivityBookInfo = null;
    BookInfo spectralresponseBookInfo = null;
    BookInfo pseudoivBookInfo = null;
    BookInfo dopingprofileBookInfo = null;
    BookInfo silverresistanceBookInfo = null;
    BookInfo voccontourBookInfo = null;
    BookInfo ivcurveBookInfo = null;


    public HelpIndexDialog(VirtualProductionLine vpl){
        super(vpl);
        setModal(false);
        setSize(780,500);
        this.setTitle("Virtual Production Line Help");
        this.setContentPane(new JPanel(new GridLayout()));

        //Create the nodes.
        top = new DefaultMutableTreeNode("The Virtual Production Line");
        createNodes(top);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                   (e.getPath().getLastPathComponent());
                Object nodeInfo = node.getUserObject();
                if (nodeInfo.getClass() == String.class) {
                    displayURL(helpURL);
                }
                else{
                    BookInfo book = (BookInfo)nodeInfo;
                    displayURL(book.bookURL);
                    if (DEBUG) {
                        System.out.print(book.bookURL + ":  \n    ");
                    }
                }
                if (DEBUG) {
                    System.out.println(nodeInfo.toString());
                }
            }
        });

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(200); //XXX: ignored in some releases
                                           //of Swing. bug 4101306
        //workaround for bug 4101306:
        //treeView.setPreferredSize(new Dimension(100, 100));

        //splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this frame
        getContentPane().add(splitPane);
    }

	public void setVisible(boolean b)
	{
	    if (b)
	    {
    		Rectangle bounds = (getParent()).getBounds();
    		Dimension size = getSize();
    		setLocation(bounds.x + (bounds.width - size.width)/2,
    			        bounds.y + (bounds.height - size.height)/2);
	    }

		super.setVisible(b);
	}

	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension d = getSize();

		super.addNotify();

		if (fComponentsAdjusted)
			return;
		// Adjust components according to the insets
		Insets insets = getInsets();
		setSize(insets.left + insets.right + d.width, insets.top + insets.bottom + d.height);
		Component components[] = getContentPane().getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets.left, insets.top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}

	// Used for addNotify check.
	boolean fComponentsAdjusted = false;

    private class BookInfo {
        public String bookName;
        public URL bookURL;
        public TreePath treePath;
        public String prefix = "file:"
                               + System.getProperty("user.dir")
                               + System.getProperty("file.separator")
                               + "help"
                               + System.getProperty("file.separator");
        public BookInfo(String book, String filename) {
            bookName = book;
            try {
                bookURL = new URL(prefix + filename);
            } catch (java.net.MalformedURLException exc) {
                System.err.println("Attempted to create a BookInfo "
                                   + "with a bad URL: " + bookURL);
                bookURL = null;
            }
        }

        public String toString() {
            return bookName;
        }
    }

    private void initHelp() {
        String s = null;
        try {
            s = "file:"
                + System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "help"
                + System.getProperty("file.separator")
                + "VirtualProductionLine.html";
            if (DEBUG) {
                System.out.println("Help URL is " + s);
            }
            helpURL = new URL(s);
            displayURL(helpURL);
        } catch (Exception e) {
            System.err.println("Couldn't create help URL: " + s);
        }
    }

    private void displayURL(URL url) {
        try {
		htmlPane.setPage(url);
        } catch (IOException e) {
          	System.err.println("Attempted to read a bad URL: " + url);
        }
    }

    public void setSelectedPage(int batchStatus){
        URL url = null;
        TreePath path = null;
        switch(batchStatus){
            case 1: path=eBookInfo.treePath; url=eBookInfo.bookURL; break;
            case 2: path=eBookInfo.treePath; url=eSubBookRinseInfo.bookURL; break;
            case 3: path=tBookInfo.treePath; url=tBookInfo.bookURL; break;
            // added by Bobh for acid texture
            case 21: path=acidBookInfo.treePath; url=acidBookInfo.bookURL; break;
            case 22: path=acidBookInfo.treePath; url=acidSubBookRinseInfo.bookURL; break;
            case 23: path=dBookInfo.treePath; url=dBookInfo.bookURL; break;
            // end add by Bobh

            case 4: path=tBookInfo.treePath; url=tSubBookRACInfo.bookURL; break;
            case 5: path=dBookInfo.treePath; url=dBookInfo.bookURL; break;
            case 6: path=alspBookInfo.treePath; url=alspBookInfo.bookURL; break;
            case 7: path=alspBookInfo.treePath; url=alspBookInfo.bookURL; break;
            case 8: path=alfBookInfo.treePath; url=alfBookInfo.bookURL; break;
            case 9: path=sspBookInfo.treePath; url=sspSubBookDORInfo.bookURL; break;
            case 10: path=peBookInfo.treePath; url=peBookInfo.bookURL; break;
            case 11: path=arcBookInfo.treePath; url=arcBookInfo.bookURL; break;
            case 12: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 13: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 14: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 15: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 26: path=sspBookInfo.treePath; url=sspSubBookDORInfo.bookURL; break;
            case 27: path=peBookInfo.treePath; url=peBookInfo.bookURL; break;
            case 28: path=arcBookInfo.treePath; url=arcBookInfo.bookURL; break;
            case 29: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 30: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 31: path=sspBookInfo.treePath; url=sspBookInfo.bookURL; break;
            case 32: path=alspBookInfo.treePath; url=alspBookInfo.bookURL; break;
            case 33: path=alspBookInfo.treePath; url=alspBookInfo.bookURL; break;
            case 34: path=alfBookInfo.treePath; url=alfBookInfo.bookURL; break;
        }
        //tree.setSelectionRow(1);
        //tree.addSelectionPath(path);
        tree.expandPath(path);
        tree.setSelectionPath(path);
        //tree.makeVisible(path);
        displayURL(url);
    }

    private void createNodes(DefaultMutableTreeNode top) {
        TreePath tp = new TreePath(top);

        //Overview
        poBookInfo = new BookInfo("Production Overview","ProductionOverview.html");
        poBook = new DefaultMutableTreeNode(poBookInfo);
        top.add(poBook);
        poBookInfo.treePath = tp.pathByAddingChild(poBook);

        //Process Help
        phBookInfo = new BookInfo("Process Help", "ProcessHelp.html");
        phBook = new DefaultMutableTreeNode(phBookInfo);
        top.add(phBook);
        phBookInfo.treePath = tp.pathByAddingChild(phBook);

        swBookInfo = new BookInfo("Silicon Wafers - Level 1...","SILICONWAFERS.html#Level1");
        swBook = new DefaultMutableTreeNode(swBookInfo);
        phBook.add(swBook);
        swBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(swBook);

        swSubBookInfo = new BookInfo("Silicon Wafers - Level 2","SILICONWAFERS.html#Level2");
        swSubBook = new DefaultMutableTreeNode(swSubBookInfo);
        swBook.add(swSubBook);

        eBookInfo = new BookInfo("Saw Damage Removal Etch - Level 1...","SAWDAMAGEREMOVALETCH.html#Level1");
        eBook = new DefaultMutableTreeNode(eBookInfo);
        phBook.add(eBook);
        eBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(eBook);

        eSubBookInfo = new BookInfo("Saw Damage Removal Etch - Level 2","SAWDAMAGEREMOVALETCH.html#Level2");
        eSubBook = new DefaultMutableTreeNode(eSubBookInfo);
        eBook.add(eSubBook);

        eSubBookRinseInfo = new BookInfo("Post Wafer Etch Rinse","SAWDAMAGEREMOVALETCH.html#PostEtchRinse");
        eSubBookRinse = new DefaultMutableTreeNode(eSubBookRinseInfo);
        eBook.add(eSubBookRinse);
        
        // added by Bobh for acid texture
        
        acidBookInfo = new BookInfo("Acid Texturing - Level 1...","ACIDICTEXTURE.html#Level1");
        acidBook = new DefaultMutableTreeNode(acidBookInfo);
        phBook.add(acidBook);
        acidBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(acidBook);

        acidSubBookInfo = new BookInfo("Acid Texturing - Level 2","ACIDICTEXTURE.html#Level2");
        acidSubBook = new DefaultMutableTreeNode(acidSubBookInfo);
        acidBook.add(acidSubBook);

        acidSubBookRinseInfo = new BookInfo("Post Acid Texturing Rinse","ACIDICTEXTURE.html#PostEtchRinse");
        acidSubBookRinse = new DefaultMutableTreeNode(acidSubBookRinseInfo);
        acidBook.add(acidSubBookRinse);
        
        // end add by Bobh
           
        tBookInfo = new BookInfo("Texturing - Level 1...","TEXTURING.html#Level1");
        tBook = new DefaultMutableTreeNode(tBookInfo);
        phBook.add(tBook);
        tBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(tBook);

        tSubBookInfo = new BookInfo("Texturing - Level 2","TEXTURING.html#Level2");
        tSubBook = new DefaultMutableTreeNode(tSubBookInfo);
        tBook.add(tSubBook);

        tSubBook1Info = new BookInfo("Advanced Texturing Techniques - Level 3","TEXTURING.html#AdvancedTexturingTechniques");
        tSubBook1 = new DefaultMutableTreeNode(tSubBook1Info);
        tBook.add(tSubBook1);

        tSubBookRACInfo = new BookInfo("Post Texture Rinse & Acid Clean","TEXTURING.html#RAC");
        tSubBookRAC = new DefaultMutableTreeNode(tSubBookRACInfo);
        tBook.add(tSubBookRAC);

        dBookInfo = new BookInfo("Phosphorous Diffusion - Level 1...","PhosphorusDiffusion.html#Level1");
        dBook = new DefaultMutableTreeNode(dBookInfo);
        phBook.add(dBook);
        dBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(dBook);

        dSubBookInfo = new BookInfo("Phosphorous Diffusion - Level 2","PhosphorusDiffusion.html#Level2");
        dSubBook = new DefaultMutableTreeNode(dSubBookInfo);
        dBook.add(dSubBook);

        peBookInfo = new BookInfo("Plasma Etching","PLASMAETCHINGOFEDGES.html#Level1");
        peBook = new DefaultMutableTreeNode(peBookInfo);
        phBook.add(peBook);
        peBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(peBook);

//        peSubBookInfo = new BookInfo("Plasma Etching - Level 2","PLASMAETCHINGOFEDGES.html#Level2-PlasmaEtchingOfEdges");
  //      peSubBook = new DefaultMutableTreeNode(peSubBookInfo);
    //    peBook.add(peSubBook);

        alspBookInfo = new BookInfo("Aluminium Screen Printing","ALUMINIUMSCREENPRINTING.html#Level1");
        alspBook = new DefaultMutableTreeNode(alspBookInfo);
        phBook.add(alspBook);
        alspBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(alspBook);

        alspSubBookInfo = new BookInfo("Aluminium Screen Printing - Level 2","ALUMINIUMSCREENPRINTING.html#Level2");
        alspSubBook = new DefaultMutableTreeNode(alspSubBookInfo);
        alspBook.add(alspSubBook);

        alfBookInfo = new BookInfo("Aluminium Firing - Level 1...","ALUMINIUMFIRING.html#Level1");
        alfBook = new DefaultMutableTreeNode(alfBookInfo);
        phBook.add(alfBook);
        alfBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(alfBook);

        alfSubBookInfo = new BookInfo("Aluminium Firing - Level 2","ALUMINIUMFIRING.html#Level2");
        alfSubBook = new DefaultMutableTreeNode(alfSubBookInfo);
        alfBook.add(alfSubBook);

        sspBookInfo = new BookInfo("Silver Screen Printing & Firing - Level 1...","SILVERSCREENPRINTINGANDFIRING.html#Level1");
        sspBook = new DefaultMutableTreeNode(sspBookInfo);
        phBook.add(sspBook);
        sspBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(sspBook);

        sspSubBookInfo = new BookInfo("Silver Screen Printing & Firing - Level 2","SILVERSCREENPRINTINGANDFIRING.html#Level2");
        sspSubBook = new DefaultMutableTreeNode(sspSubBookInfo);
        sspBook.add(sspSubBook);

        sspSubBookDORInfo = new BookInfo("Diffusion Oxide Removal Prior to Silver Screen Printing","SILVERSCREENPRINTINGANDFIRING.html#DOR");
        sspSubBookDOR = new DefaultMutableTreeNode(sspSubBookDORInfo);
        sspBook.add(sspSubBookDOR);
        
        arcBookInfo = new BookInfo("Antireflection Coating - Level 1...","AntireflectionCoating.html#Level1");
        arcBook = new DefaultMutableTreeNode(arcBookInfo);
        phBook.add(arcBook);
        arcBookInfo.treePath = phBookInfo.treePath.pathByAddingChild(arcBook);

        arcSubBookInfo = new BookInfo("Antireflection Coating - Level 2","AntireflectionCoating.html#Level2");
        arcSubBook = new DefaultMutableTreeNode(arcSubBookInfo);
        arcBook.add(arcSubBook);

        //Test Help
        testBookInfo = new BookInfo("Tests/Graphs Help", "TestHelp.html");
        testBook = new DefaultMutableTreeNode(testBookInfo);
        top.add(testBook);
        testBookInfo.treePath = tp.pathByAddingChild(testBook);

        thicknessBookInfo = new BookInfo("Thickness Test","WaferThicknessTest.html");
        thicknessBook = new DefaultMutableTreeNode(thicknessBookInfo);
        testBook.add(thicknessBook);
        thicknessBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(thicknessBook);

        mclifetimeBookInfo = new BookInfo("Minority Carrier Lifetime Test","SubstrateMinorityCarrierLifetimeTest.html");
        mclifetimeBook = new DefaultMutableTreeNode(mclifetimeBookInfo);
        testBook.add(mclifetimeBook);
        mclifetimeBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(mclifetimeBook);

        sheetresistivityBookInfo = new BookInfo("Sheet Resistivity Test","SheetResistivityMeasurement.html");
        sheetresistivityBook = new DefaultMutableTreeNode(sheetresistivityBookInfo);
        testBook.add(sheetresistivityBook);
        sheetresistivityBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(sheetresistivityBook);

        spectralresponseBookInfo = new BookInfo("Spectral Response Measurements","SpectralResponseMeasurements.html");
        spectralresponseBook = new DefaultMutableTreeNode(spectralresponseBookInfo);
        testBook.add(spectralresponseBook);
        spectralresponseBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(spectralresponseBook);

        pseudoivBookInfo = new BookInfo("Pseudo IV Curve","PseudoIVCurve.html");
        pseudoivBook = new DefaultMutableTreeNode(pseudoivBookInfo);
        testBook.add(pseudoivBook);
        pseudoivBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(pseudoivBook);

        dopingprofileBookInfo = new BookInfo("Doping Profile","DopingProfile.html");
        dopingprofileBook = new DefaultMutableTreeNode(dopingprofileBookInfo);
        testBook.add(dopingprofileBook);
        dopingprofileBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(dopingprofileBook);

        silverresistanceBookInfo = new BookInfo("Silver Resistance Test","SilverResistanceTest.html");
        silverresistanceBook = new DefaultMutableTreeNode(silverresistanceBookInfo);
        testBook.add(silverresistanceBook);
        silverresistanceBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(silverresistanceBook);

        voccontourBookInfo = new BookInfo("Voc Contour Test","VocContourMapping.html");
        voccontourBook = new DefaultMutableTreeNode(voccontourBookInfo);
        testBook.add(voccontourBook);
        voccontourBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(voccontourBook);

        ivcurveBookInfo = new BookInfo("IV Curves","IVCurves.html");
        ivcurveBook = new DefaultMutableTreeNode(ivcurveBookInfo);
        testBook.add(ivcurveBook);
        ivcurveBookInfo.treePath = testBookInfo.treePath.pathByAddingChild(ivcurveBook);

    }

}
