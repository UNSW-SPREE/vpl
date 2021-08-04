package vpl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.TreePath;
import javax.swing.*;
import javax.swing.JToolBar.Separator;
import javax.swing.ImageIcon;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.NumberFormat;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce 
 * @version 1.0
 */

public class VirtualProductionLine extends javax.swing.JFrame
{
//**********OBJECTS**********
    public String urlPrefix = "file:" + System.getProperty("user.dir")
        + System.getProperty("file.separator");
    public String directoryPrefix = System.getProperty("user.dir")
        + System.getProperty("file.separator");
    public String userPrefix = System.getProperty("user.home") 
	+ System.getProperty("file.separator");

  //demo version
  boolean demo = false;

/**
 * batch status panel (left-hand panel)
 */
    BatchStatusPanel batchStatusPanel;
    JScrollPane bsPanel;

/**
 * tests and notes panel (bottom panel)
 */
    TestResultsPanel testResultsPanel;
    GraphListPanel graphListPanel;
//    VisualListPanel visualListPanel;
    JTextPane noteBook = new JTextPane();
    JPanel noteBookPanel;
    JTabbedPane testsNotesPane;
    LimitedStyledDocument lsd;

/**
 *  big panel contains all except process control panel
 */
    javax.swing.JPanel bigPanel = new javax.swing.JPanel();
    PicPanel coolpic;
    JPanel middlePanel;
    JPanel picturePanel;
    JScrollPane picturePane;

/**
 * process control panel (right-hand panel) - contains process panel
 */
    JScrollPane pane;

/**
 * current batch and settings data
 */
    int cbIndex = -1;
    int noOfBatchesCreated = 1;
    Vector batches = new Vector();
    JPanel batchPanels[] = new JPanel[10];
    Settings previousSettings;
    Settings preferredSettings;
    Settings defaultSettings;
    Settings mySettings;
    String noteBookString;
    TestArrays testArrays = new TestArrays();

    //object streams for opening & saving batches and settings
    private ObjectOutputStream output;
    private ObjectInputStream input;
    String filename;
    File directory;
    File userDirectory;

    //audio/video/graph/picture stuff
    PC1DPlot currentGraphPlot;
//    PicPanel currentVisualInspection;
    Video currentVideo;
//    AlScreenGraphic alGraphic;

    ImageIcon currentImages[];
//    VPLCanvas canvas;
    JLabel label;
    //tool bar components
    JFileChooser fileDialog;
    JFileChooser textfileDialog;
    JPanel toolBarPanel = new JPanel();
    JToolBar vplToolBar = new JToolBar();
    JButton newButton = new JButton();
    JButton openButton = new JButton();
    JButton saveButton = new JButton();
    JButton closeButton = new JButton();
    Separator Separator1 = new JToolBar.Separator();
    JButton preferredButton = new JButton();
    JButton previousButton = new JButton();
    JButton defaultButton = new JButton();
    Separator Separator2 = new JToolBar.Separator();
    JButton exportBatchButton = new JButton();

    //MENUS
    JMenuBar MenuBar = new JMenuBar();
    JMenu batchMenu = new JMenu();
    JMenuItem newItem = new JMenuItem();
    JMenuItem openItem = new JMenuItem();
    JMenuItem saveItem = new JMenuItem();
    JMenuItem saveAsItem = new JMenuItem();
    JMenuItem closeItem = new JMenuItem();
//    JMenuItem splitItem = new JMenuItem();
    JSeparator JSeparator1 = new JSeparator();
    JMenu batchesMenu = new JMenu();
    JRadioButtonMenuItem batchItems[];
    JMenu testsMenu = new JMenu();
    JMenuItem testItems[];
    Vector testNames;
    Vector testTypes;
    ButtonGroup batchesGroup = new ButtonGroup();
    JSeparator JSeparator2 = new JSeparator();
    JMenuItem exitItem = new JMenuItem();
    JMenu settingsMenu = new JMenu();
    JMenuItem editSettingsMenuItem = new JMenuItem();
    JMenu settingsUsedMenu = new JMenu();
    JRadioButtonMenuItem settingsItems[];
    ButtonGroup settingsGroup = new ButtonGroup();
    JMenu toolsMenu = new JMenu();
    JMenuItem setHomeDirectoryItem = new JMenuItem();
    JMenu maintenanceMenu = new JMenu();
    JMenuItem refreshSSBatchesItem = new JMenuItem();
    JMenuItem refreshTSSBatchesItem = new JMenuItem();

    // add refreshAcid Texture Menu item Bobh

    JMenuItem refreshAcidBatchesItem = new JMenuItem();

    // end add Bobh

    JMenuItem autoProcessItem = new JMenuItem();

    JMenu helpMenu = new JMenu();
    JMenuItem overviewMenuItem = new JMenuItem();
    JMenuItem helpIndexMenuItem = new JMenuItem();
    JMenuItem currentProcessHelpItem = new JMenuItem();
    JMenuItem aboutItem = new JMenuItem();
    //******USEFUL VARIABLES*****
    Font boldFont = new Font("Dialog", Font.BOLD, 12);
    Font buttonFont = new Font("Dialog", Font.PLAIN, 12);
    Font smallButtonFont = new Font("Dialog", Font.PLAIN, 10);
    Color buttonTextColour = new Color(16,9,115);
//    Color backgroundColour = new Color(153,153,153);
    Color yellowColour = new java.awt.Color(251,231,170);
    //Color mauveColour = new Color(146,146,146);
    //Color lightMauveColour = new Color(176,176,176);
    SymAction lSymAction = new SymAction();
    TNPChangeListener tnpChangeListener = new TNPChangeListener();
    ItemHandler itemHandler = new ItemHandler();
    TestsItemHandler testsItemHandler = new TestsItemHandler();
    SettingsItemHandler settingsItemHandler = new SettingsItemHandler();

    //*******VPL Maintenance Variables***********
    // add varible to hold the number of acid texture batches Bobh
    public int acidBatches;
        // add end here Bobh

    //sodium silicate batches through etch solution
    public int SSBatches;
    //no of cells textured in the texture solution
    public int TSSBatches;
    //probability of breakage of silver screen
    public int PSBreakage;

    public static void main(String[] args)
    {
        boolean d = false;
        try {
            if (args.length > 0){
              if (args[0].equals("true")){
                d = true;
                VirtualProductionLine vpl = new VirtualProductionLine(d);
                vpl.setTitle("Virtual Production Line Demo");
                vpl.pack();
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                Dimension selfBounds = vpl.getSize();
                vpl.setLocation((screenSize.width - selfBounds.width) / 2, (screenSize.height - selfBounds.height) / 2);
                vpl.setVisible(true);
              }
            }
            else{
                d = false;
                VirtualProductionLine vpl = new VirtualProductionLine(d);
                vpl.setTitle("Virtual Production Line");
                vpl.pack();
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                Dimension selfBounds = vpl.getSize();
                vpl.setLocation((screenSize.width - selfBounds.width) / 2, (screenSize.height - selfBounds.height) / 2);
                vpl.setVisible(true);
            }
	}
	catch (Throwable t) {
	    t.printStackTrace();
	    //Ensure the application exits with an error condition.
	    System.exit(1);
	}
    }

    public VirtualProductionLine(boolean d)
    {
        demo = d;
        //user interface
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPopupMenu.setDefaultLightWeightPopupEnabled( false );
        JPanel contentPane = new JPanel();
        //contentPane.setBackground(backgroundColour);
        setJMenuBar(MenuBar);
	contentPane.setLayout(new BorderLayout(1,1));
        bigPanel.setLayout(new BorderLayout(1,1));
        //bigPanel.setBackground(backgroundColour);
        toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        toolBarPanel.setBorder(BorderFactory.createEtchedBorder());
        contentPane.add(BorderLayout.NORTH, toolBarPanel);
	vplToolBar.setAlignmentY(0.222222F);
	toolBarPanel.add(vplToolBar);
	newButton.setDefaultCapable(false);
	newButton.setToolTipText("Create a new batch");
	newButton.setText("New Batch");
        URL newIconURL = null;
        String filename = "icons" + System.getProperty("file.separator") + "new.gif";
        try {
            newIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                          + "with a bad URL: " + newIconURL);
            newIconURL = null;
        }
        ImageIcon newIcon = new ImageIcon(newIconURL);
        newButton.setIcon(newIcon);
	newButton.setFont(smallButtonFont);
        //newButton.setBackground(lightMauveColour);
	vplToolBar.add(newButton);
	openButton.setDefaultCapable(false);
	openButton.setToolTipText("Open an existing batch");
	openButton.setText("Open Batch");
        URL openIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "open.gif";
        try {
            openIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + openIconURL);
            openIconURL = null;
        }
        ImageIcon openIcon = new ImageIcon(openIconURL);
        openButton.setIcon(openIcon);
	openButton.setFont(smallButtonFont);
        //openButton.setBackground(lightMauveColour);
	vplToolBar.add(openButton);
	saveButton.setDefaultCapable(false);
	saveButton.setToolTipText("Save the current batch");
	saveButton.setText("Save Batch");
        URL saveIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "save.gif";
        try {
            saveIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + saveIconURL);
            saveIconURL = null;
        }
        ImageIcon saveIcon = new ImageIcon(saveIconURL);
        saveButton.setIcon(saveIcon);
	saveButton.setFont(smallButtonFont);
        //saveButton.setBackground(lightMauveColour);
	vplToolBar.add(saveButton);
	closeButton.setDefaultCapable(false);
	closeButton.setToolTipText("Close the current batch");
	closeButton.setText("Close Batch");
        URL closeIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "close.gif";
        try {
            closeIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + closeIconURL);
            closeIconURL = null;
        }
        ImageIcon closeIcon = new ImageIcon(closeIconURL);
        closeButton.setIcon(closeIcon);
	closeButton.setFont(smallButtonFont);
        //closeButton.setBackground(lightMauveColour);
	vplToolBar.add(closeButton);
	vplToolBar.add(Separator1);
	preferredButton.setDefaultCapable(false);
	preferredButton.setToolTipText("Use My Preferred Settings for this Process");
	preferredButton.setText("Use Preferred Settings");
	preferredButton.setFont(smallButtonFont);
        //preferredButton.setBackground(lightMauveColour);
	vplToolBar.add(preferredButton);
	previousButton.setDefaultCapable(false);
	previousButton.setToolTipText("Use Last Used Settings for this Process");
	previousButton.setText("Use previous Settings");
	previousButton.setFont(smallButtonFont);
        //previousButton.setBackground(lightMauveColour);
	vplToolBar.add(previousButton);
	defaultButton.setDefaultCapable(false);
	defaultButton.setToolTipText("Use Default Settings for this Process");
	defaultButton.setText("Use default Settings");
	defaultButton.setFont(smallButtonFont);
        //defaultButton.setBackground(lightMauveColour);
	vplToolBar.add(defaultButton);
	vplToolBar.add(Separator1);
	exportBatchButton.setDefaultCapable(false);
	exportBatchButton.setToolTipText("Export the Current Batch to Text File");
	exportBatchButton.setText("Export Batch");
	exportBatchButton.setFont(smallButtonFont);
    ClassLoader classLoader = getClass().getClassLoader();
        //exportBatchButton.setBackground(lightMauveColour);
	vplToolBar.add(exportBatchButton);
        picturePanel = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout());
        picturePanel.setLayout(gb);
        //picturePanel.setBackground(lightMauveColour);
        picturePanel.setBorder(BorderFactory.createBevelBorder(0));
        picturePane = new JScrollPane();
        picturePane.setBorder(BorderFactory.createBevelBorder(1));
        String picString = "images" + System.getProperty("file.separator") + "VPL4_Intro.jpg"; test
        coolpic = new PicPanel(classLoader.getResource("images/VPL4_Intro.jpg").toString());
        coolpic.validate();
        picturePane.getViewport().add(coolpic);
        Dimension picturePaneSize = new Dimension(300,230);
	picturePane.setPreferredSize(picturePaneSize);
        picturePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        picturePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbConstraints.anchor = GridBagConstraints.CENTER;
        picturePanel.add(picturePane);
        middlePanel.add(picturePanel);
        bigPanel.add(BorderLayout.CENTER, middlePanel);
        testsNotesPane = new JTabbedPane();
        Dimension bottomPanelSize = new Dimension(600,210);
	testsNotesPane.setPreferredSize(bottomPanelSize);
    	bigPanel.add(BorderLayout.SOUTH, testsNotesPane);
        bsPanel = new JScrollPane();
        //bsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //bsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        bsPanel.setBorder(BorderFactory.createBevelBorder(0));
        Dimension bsPanelSize = new Dimension(220,200);
	bsPanel.setPreferredSize(bsPanelSize);

        bigPanel.add(BorderLayout.WEST, bsPanel);
	pane = new JScrollPane();
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension paneSize = new Dimension(240,450);
	pane.setPreferredSize(paneSize);
	pane.setBorder(BorderFactory.createEtchedBorder());

        lsd = new LimitedStyledDocument(10000);
	contentPane.add(BorderLayout.EAST, pane);
        contentPane.add(BorderLayout.CENTER, bigPanel);

        setContentPane(contentPane);
	this.getContentPane().doLayout();

	validate();

        //MENUS
        //MenuBar.setBackground(mauveColour);
        batchMenu.setText("Batch");
	batchMenu.setMnemonic((int)'B');
        batchMenu.setFont(boldFont);
        //batchMenu.setBackground(mauveColour);
	MenuBar.add(batchMenu);
	newItem.setText("New Batch");
	newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
	newItem.setMnemonic((int)'N');
        newItem.setIcon(newIcon);
	batchMenu.add(newItem);
	openItem.setText("Open Batch");
	openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
	openItem.setMnemonic((int)'O');
        openItem.setIcon(openIcon);
	batchMenu.add(openItem);
	saveItem.setText("Save Batch");
	saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
	saveItem.setMnemonic((int)'S');
        saveItem.setIcon(saveIcon);
	batchMenu.add(saveItem);
	saveAsItem.setText("Save Batch As...");
	saveAsItem.setMnemonic((int)'A');
	batchMenu.add(saveAsItem);
	closeItem.setText("Close Batch");
	closeItem.setMnemonic((int)'C');
	batchMenu.add(closeItem);
//	splitItem.setText("Split Batch");
//	splitItem.setMnemonic((int)'P');
//	batchMenu.add(splitItem);
        batchMenu.add(JSeparator1);
        batchesMenu.setText("Current Batches");
        batchesMenu.setMnemonic('B');
        updateBatchMenu();
        batchMenu.add(batchesMenu);
        batchMenu.add(JSeparator2);
	exitItem.setText("Exit Virtual Production Line");
	exitItem.setMnemonic((int)'X');
	batchMenu.add(exitItem);
	settingsMenu.setText("Settings");
	settingsMenu.setMnemonic((int)'S');
        settingsMenu.setFont(boldFont);
        //settingsMenu.setBackground(mauveColour);
	MenuBar.add(settingsMenu);
	editSettingsMenuItem.setText("Edit/View settings");
	editSettingsMenuItem.setMnemonic((int)'E');
	settingsMenu.add(editSettingsMenuItem);
        settingsUsedMenu.setText("Settings to be used by default");
        settingsItems = new JRadioButtonMenuItem[3];
        settingsItems[0] = new JRadioButtonMenuItem("VPL Default Settings");
        settingsItems[1] = new JRadioButtonMenuItem("Last Used Settings");
        settingsItems[2] = new JRadioButtonMenuItem("Preferred Settings");
        for (int i=0; i<3;i++){
            settingsUsedMenu.add(settingsItems[i]);
            settingsGroup.add(settingsItems[i]);
            settingsItems[i].addActionListener(settingsItemHandler);
        }
        settingsItems[1].setSelected(true);
        settingsUsedMenu.validate();
        settingsMenu.add(settingsUsedMenu);
        if (demo)
            settingsUsedMenu.setEnabled(false);
	toolsMenu.setText("Tools");
	toolsMenu.setMnemonic((int)'T');
        toolsMenu.setFont(boldFont);
        //toolsMenu.setBackground(mauveColour);
	MenuBar.add(toolsMenu);
	setHomeDirectoryItem.setText("Set User Home Directory");
	setHomeDirectoryItem.setMnemonic((int)'S');
        URL setHomeDirectoryIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "folder.gif";
        try {
            setHomeDirectoryIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + setHomeDirectoryIconURL);
            setHomeDirectoryIconURL = null;
        }
        ImageIcon setHomeDirectoryIcon = new ImageIcon(setHomeDirectoryIconURL);
        setHomeDirectoryItem.setIcon(setHomeDirectoryIcon);
	//toolsMenu.add(setHomeDirectoryItem);
	maintenanceMenu.setText("VPL Maintenance");
        URL maintenanceIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "tools.gif";
        try {
            maintenanceIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + maintenanceIconURL);
            maintenanceIconURL = null;
        }
        ImageIcon maintenanceIcon = new ImageIcon(maintenanceIconURL);
        refreshSSBatchesItem.setText("Refresh NaOH Etch solution");
        refreshTSSBatchesItem.setText("Refresh NaOH Texturing solution");

        // add refreshacidTextureBatchItem BobH
        refreshAcidBatchesItem.setText("Refresh Acidic Texturing solution");
        // end add Bobh

        maintenanceMenu.add(refreshSSBatchesItem);
        maintenanceMenu.add(refreshTSSBatchesItem);
        // added Bobh
        maintenanceMenu.add(refreshAcidBatchesItem);
        // end add

        maintenanceMenu.setIcon(maintenanceIcon);
	toolsMenu.add(maintenanceMenu);
	testsMenu.setText("Tests");
        URL testsIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "tests.gif";
        try {
            testsIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + testsIconURL);
            testsIconURL = null;
        }
        ImageIcon testsIcon = new ImageIcon(testsIconURL);
        testsMenu.setIcon(testsIcon);
	toolsMenu.add(testsMenu);
        autoProcessItem.setText("Auto Processing");
        toolsMenu.add(autoProcessItem);
	helpMenu.setText("Help");
	helpMenu.setMnemonic((int)'H');
        helpMenu.setFont(boldFont);
        //helpMenu.setBackground(mauveColour);
	MenuBar.add(helpMenu);
	overviewMenuItem.setText("Overview");
	overviewMenuItem.setMnemonic((int)'O');
	helpMenu.add(overviewMenuItem);
	helpIndexMenuItem.setText("Index");
	helpIndexMenuItem.setMnemonic((int)'I');
        URL helpIndexIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "help.gif";
        try {
            helpIndexIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                          + "with a bad URL: " + helpIndexIconURL);
            helpIndexIconURL = null;
        }
        ImageIcon helpIndexIcon = new ImageIcon(helpIndexIconURL);
        helpIndexMenuItem.setIcon(helpIndexIcon);
	helpMenu.add(helpIndexMenuItem);
	currentProcessHelpItem.setText("Current Process");
	currentProcessHelpItem.setMnemonic((int)'C');
        URL currentProcessHelpIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "contextHelp.gif";
        try {
            currentProcessHelpIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                          + "with a bad URL: " + currentProcessHelpIconURL);
            currentProcessHelpIconURL = null;
        }
        ImageIcon currentProcessHelpIcon = new ImageIcon(currentProcessHelpIconURL);
        currentProcessHelpItem.setIcon(currentProcessHelpIcon);
	helpMenu.add(currentProcessHelpItem);
        URL aboutIconURL = null;
        filename = "icons" + System.getProperty("file.separator") + "about.gif";
        try {
            aboutIconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                           + "with a bad URL: " + aboutIconURL);
            aboutIconURL = null;
        }
        ImageIcon aboutIcon = new ImageIcon(aboutIconURL);
	aboutItem.setText("About...");
	aboutItem.setMnemonic((int)'A');
        aboutItem.setIcon(aboutIcon);
	helpMenu.add(aboutItem);

        //VPL variables
        if (demo)
            defaultSettings = new Settings("demo");
        else
            defaultSettings = new Settings("default");
        getPreviousSettings();
        if (demo)
            mySettings = defaultSettings;
        else
            mySettings = previousSettings;
        getPreferredSettings();
        userDirectory = preferredSettings.userDirectory;

        SSBatches = preferredSettings.SSB;
        TSSBatches = preferredSettings.TSSB;
        PSBreakage = preferredSettings.PSB;
        fileDialog = new JFileChooser(userDirectory);
        fileDialog.addChoosableFileFilter(new VPLFileFilter());
        textfileDialog = new JFileChooser(userDirectory);

	//IMAGES FOR ALL PROCESSES
        
 
    //LISTENERS
	SymWindow aSymWindow = new SymWindow();
        SymComponent aSymComponent = new SymComponent();
	this.addWindowListener(aSymWindow);
        this.addComponentListener(aSymComponent);
	openItem.addActionListener(lSymAction);
	saveItem.addActionListener(lSymAction);
	saveAsItem.addActionListener(lSymAction);
        closeItem.addActionListener(lSymAction);
	exitItem.addActionListener(lSymAction);
	aboutItem.addActionListener(lSymAction);
	openButton.addActionListener(lSymAction);
	saveButton.addActionListener(lSymAction);
	closeButton.addActionListener(lSymAction);
	SymContainer aSymContainer = new SymContainer();
	this.addContainerListener(aSymContainer);
	setHomeDirectoryItem.addActionListener(lSymAction);
//	maintenanceMenu.addActionListener(lSymAction);
        refreshSSBatchesItem.addActionListener(lSymAction);
        refreshTSSBatchesItem.addActionListener(lSymAction);

        // added by Bobh to refresh acid texture
        refreshAcidBatchesItem.addActionListener(lSymAction);
        // end add Bobh

        autoProcessItem.addActionListener(lSymAction);
//	splitItem.addActionListener(lSymAction);
	newItem.addActionListener(lSymAction);
	newButton.addActionListener(lSymAction);
        preferredButton.addActionListener(lSymAction);
        previousButton.addActionListener(lSymAction);
        defaultButton.addActionListener(lSymAction);
        exportBatchButton.addActionListener(lSymAction);
	editSettingsMenuItem.addActionListener(lSymAction);
	helpIndexMenuItem.addActionListener(lSymAction);
	currentProcessHelpItem.addActionListener(lSymAction);
        overviewMenuItem.addActionListener(lSymAction);
        testsNotesPane.addChangeListener(tnpChangeListener);
        try {
          jbInit();
        }
        catch(Exception e) {
          e.printStackTrace();
        }
    }


    public VirtualProductionLine(String sTitle)
    {
  	this(true);
	setTitle(sTitle);
    }


    //sets the size of the application
    Dimension minimumSize = new Dimension(780, 570);
    public Dimension getMinimumSize() {
        return new Dimension(780, 570);
    }
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    /**
     * Notifies this component that it has been added to a container
     * This method should be called by <code>Container.add</code>, and
     * not by user code directly.
     * Overridden here to adjust the size of the frame if needed.
     * @see java.awt.Container#removeNotify
     */
    public void addNotify()
    {
	// Record the size of the window prior to calling parents addNotify.
	Dimension size = getSize();
	super.addNotify();
	if (frameSizeAdjusted)
    	    return;
	frameSizeAdjusted = true;

	// Adjust size of frame according to the insets and menu bar
	JMenuBar menuBar = getRootPane().getJMenuBar();
	int menuBarHeight = 0;
	if (menuBar != null)
	    menuBarHeight = menuBar.getPreferredSize().height;
	Insets insets = getInsets();
	setSize(insets.left + insets.right + size.width, insets.top + insets.bottom + size.height + menuBarHeight);
    }

    // Used by addNotify
    boolean frameSizeAdjusted = false;


    void exitApplication()
    {
    	try {
	    // Beep
	    Toolkit.getDefaultToolkit().beep();
	    // Show a confirmation dialog
	    int reply = JOptionPane.showConfirmDialog(this,"Do you really want to exit?",
	  	                                          "Virtual Production Line - Exit" ,
	    	                                          JOptionPane.YES_NO_OPTION,
	    	                                          JOptionPane.QUESTION_MESSAGE);
    	    // If the confirmation was affirmative, handle exiting.
	    if (reply == JOptionPane.YES_OPTION){
                closeAllBatches();
                savePreviousSettings();
                savePreferredSettings();
                this.setVisible(false);    // hide the Frame
		this.dispose();            // free the system resources
		System.exit(0);            // close the application
	    }

	} catch (Exception e) {
	}
    }

    class SymWindow extends WindowAdapter
    {
	public void windowClosing(WindowEvent event)
	{
    	    Object object = event.getSource();
	    if (object == VirtualProductionLine.this)
		VirtualProductionLine_windowClosing(event);
        }

    }

    void VirtualProductionLine_windowClosing(WindowEvent event)
    {
	try {
    	    this.exitApplication();
	} catch (Exception e) {
        }
    }

    class SymComponent extends ComponentAdapter
    {
	public void componentResized(ComponentEvent event)
	{
    	    Object object = event.getSource();
	    if (object == VirtualProductionLine.this)
		VirtualProductionLine_componentResized(event);
        }

    }

    void VirtualProductionLine_componentResized(ComponentEvent event)
    {
        if (this.getSize().height < 570 || this.getSize().width < 780){
            Dimension minimumSize = new Dimension(780, 570);
            this.setSize(minimumSize);
        }
    }

    class TNPChangeListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e){
            JPanel p = (JPanel)testsNotesPane.getSelectedComponent();
            if (p == testResultsPanel || p == noteBookPanel){
                if (clearPicturePane() == true){
                    refreshPane();
                }
            }
            else if (p == graphListPanel){
                if (clearPicturePane() == true){
                    graphListPanel.redo();
                }
            }
        }
    }

    class SymAction implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
    	    Object object = event.getSource();
	    if (object == openItem)
		open_actionPerformed(event);
	    else if (object == saveItem)
		save_actionPerformed(event);
	    else if (object == saveAsItem)
		saveAs_actionPerformed(event);
            else if (object == closeItem)
                close_actionPerformed(event);
	    else if (object == exitItem)
		exitItem_actionPerformed(event);
	    else if (object == aboutItem)
		about_actionPerformed(event);
	    else if (object == openButton)
		open_actionPerformed(event);
	    else if (object == newButton)
		new_actionPerformed(event);
	    else if (object == saveButton)
		save_actionPerformed(event);
	    else if (object == closeButton)
		close_actionPerformed(event);
	    else if (object == preferredButton)
		preferredSettings_actionPerformed(event);
            else if (object == previousButton)
                previousSettings_actionPerformed(event);
            else if (object == defaultButton)
                defaultSettings_actionPerformed(event);
            else if (object == exportBatchButton)
                exportBatch_actionPerformed(event);
	    else if (object == setHomeDirectoryItem)
		setHomeDirectoryItem_actionPerformed(event);
	    else if (object == refreshSSBatchesItem)
		refreshSSBatchesItem_actionPerformed(event);

            // added  by Bobh
             else if (object == refreshAcidBatchesItem)
		refreshAcidBatchesItem_actionPerformed(event);
            // end add Bobh

	    else if (object == refreshTSSBatchesItem)
		refreshTSSBatchesItem_actionPerformed(event);
            else if (object == autoProcessItem)
                autoProcessItem_actionPerformed(event);
//	    else if (object == splitItem)
//		splitItem_actionPerformed(event);
	    else if (object == newItem)
		new_actionPerformed(event);
            else if (object == editSettingsMenuItem)
                editSettingsMenuItem_actionPerformed(event);
            else if (object == helpIndexMenuItem)
                helpIndexMenuItem_actionPerformed(event);
            else if (object == currentProcessHelpItem)
                currentProcessHelpItem_actionPerformed(event);
            else if (object == overviewMenuItem)
                overviewMenuItem_actionPerformed(event);
    	}
    }

    class ItemHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for (int i=0; i<batchItems.length; i++){
                if (batchItems[i].isSelected()){
                    if (cbIndex >=0){
                        batchPanels[cbIndex].setVisible(true);
                    }
                    cbIndex = i;
                    refreshPane();
//                    refreshProcess();
                    updateTestsMenu();
                    pane.revalidate();
                    refreshBatchStatusPanel();
                    refreshTestResultsPanel();
                    clearPicturePane();
	            validate();
                 }
            }
        }
    }

    class SettingsItemHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for (int i=0; i<settingsItems.length; i++){
                if (settingsItems[0].isSelected()){
                    mySettings = defaultSettings;
                    defaultSettings_actionPerformed(e);
                }
                else if (settingsItems[1].isSelected()){
                    mySettings = previousSettings;
                    previousSettings_actionPerformed(e);
                }
                else if (settingsItems[2].isSelected()){
                    mySettings = preferredSettings;
                    preferredSettings_actionPerformed(e);
                }
            }
        }
    }

    class TestsItemHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
    	    Object object = e.getSource();
            for (int i=0; i<testItems.length; i++){
                if (object == testItems[i]){
                    if (testTypes.elementAt(i).equals("t")){
                        runTest((String)testNames.elementAt(i));
                    }
                    else if (testTypes.elementAt(i).equals("g")){
                        runGraph((String)testNames.elementAt(i));
                    }
                    else if (testTypes.elementAt(i).equals("v")){
                        runVocTest((String)testNames.elementAt(i));
                    }
                }
            }
        }
    }
//**********MENU & BUTTON OPERATIONS

    void open_actionPerformed(ActionEvent event)
    {
	try {
	    // openFileDialog Show the FileDialog
            fileDialog.rescanCurrentDirectory();
            fileDialog.setSelectedFile(null);
	    int result = fileDialog.showOpenDialog(this);
            if (result == JFileChooser.CANCEL_OPTION)
	        return;
            File fileName = fileDialog.getSelectedFile();
            directory = fileName;
    	    if (fileName == null || fileName.getName().equals(""))
  	        JOptionPane.showMessageDialog(this,"Invalid File Name","Invalid File Name",
                  			    JOptionPane.ERROR_MESSAGE);
  	    else {
	        try{
		    input = new ObjectInputStream(new FileInputStream(fileName));
  	        }
		catch(IOException e){
		    JOptionPane.showMessageDialog(this,"Error Creating Object Input Stream", "Error",
                			        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
	}
	getBatchFromFile(directory);
        input = null;
    }

    void close_actionPerformed(ActionEvent event){
        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to close",
                "Close Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        close();
    }

    void closeBatch(){
        if (cbIndex == batches.size()-1){
            batches.remove(cbIndex);
            batchPanels[cbIndex] = null;
            cbIndex = cbIndex -1;
        }
        else{
            for (int i=cbIndex; i<batches.size()-1; i++){
                batchPanels[i] = batchPanels[i+1];
            }
            batchPanels[batches.size()-1] = null;
            batches.remove(cbIndex);
            cbIndex = 0;
        }
        updateBatchMenu();
        refreshProcess();
    }


    void close(){
        Batch myBatch = (Batch)batches.elementAt(cbIndex);
        if (myBatch.batchChanged == true){
            int n = JOptionPane.showConfirmDialog(this,
            "Do you wish to save the changes to " + myBatch.batchName, "Save Changes?",
            JOptionPane.YES_NO_CANCEL_OPTION);
            if (n == JOptionPane.CANCEL_OPTION){
                return;
            }
            else if (n == JOptionPane.NO_OPTION) {
                closeBatch();
            }
            else if (n == JOptionPane.YES_OPTION){
                saveBatch();
                closeBatch();
            }
        }
        else closeBatch();
    }

    void closeAllBatches(){
        cbIndex = batches.size()-1;
        while (cbIndex>-1){
            Batch myBatch = (Batch)batches.elementAt(cbIndex);
            if (myBatch.batchChanged == true){
                int n = JOptionPane.showConfirmDialog(this,
                "Do you wish to save the changes to " + myBatch.batchName, "Save Changes?",
                JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.NO_OPTION) {
                    closeBatch();
                }
                else if (n == JOptionPane.YES_OPTION){
                    saveBatch();
                    closeBatch();
                }
            }
            else {
                closeBatch();
            }
        }
    }

    void save_actionPerformed(ActionEvent event)
    {
        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to save",
                "Save Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveBatch();
    }

    void saveAs_actionPerformed(ActionEvent event)
    {
        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to save",
                "Save Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveBatchAs();
    }

    void saveBatch(){
        Batch myBatch = (Batch)batches.elementAt(cbIndex);
        if (myBatch.directory != null){
            File fileName = myBatch.directory;
            if (fileName.exists()){
                try{
                    output = new ObjectOutputStream(new FileOutputStream(fileName));
                    updateBatchMenu();
                }
                catch(IOException e){
	            JOptionPane.showMessageDialog(this,"Error Saving File", "Error",
	    	        JOptionPane.ERROR_MESSAGE);
                }
                addBatchToFile();
                myBatch.batchChanged = false;
            }
            else{
                JOptionPane.showMessageDialog(this,"The directory or file no longer exists",
                    "Save Error",JOptionPane.ERROR_MESSAGE);
                saveBatchAs();
            }
        }
        else {
            saveBatchAs();
        }
    }

    void saveBatchAs(){
	try {
    	    // saveFileDialog Show the FileDialog
            fileDialog.rescanCurrentDirectory();
            Batch myBatch = (Batch)batches.elementAt(cbIndex);
            String fname = (myBatch).batchName;
            if (myBatch.directory != null){
                fileDialog.setSelectedFile(new File(fileDialog.getCurrentDirectory(), fname));
            }
            else{
                fileDialog.setSelectedFile(null);
            }
	    int result = fileDialog.showSaveDialog(this);
	    if (result == JFileChooser.CANCEL_OPTION)
	        return;
	    File fileName = fileDialog.getSelectedFile();
            String nm = fileName.toString();
            if (nm.substring(nm.length()-4, nm.length()).equals(".vpl")){
            }
            else{
//                fileName = new File(fileName.toString() + ".vpl");
                fileName = new File(fileName.toString() + ".vpl");
            }
            if (fileDialog.getSelectedFile().exists()){
                int selectedValue = JOptionPane.showConfirmDialog(this,"Do you want to overwrite the existing " + fileName.getName() + "?",
                    "Overwrite File?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selectedValue == JOptionPane.CANCEL_OPTION)
                    saveBatchAs();
            }
  	    if (fileName == null || fileName.getName().equals(""))
	        JOptionPane.showMessageDialog(this,"Invalid File Name",
			    "Invalid File Name",JOptionPane.ERROR_MESSAGE);
	    else {
	        try{
		    output = new ObjectOutputStream(new FileOutputStream(fileName));
		    ((Batch)batches.elementAt(cbIndex)).batchName = fileName.getName();
		    ((Batch)batches.elementAt(cbIndex)).directory = fileName;
		    updateBatchMenu();
		}
		catch(IOException e){
		    JOptionPane.showMessageDialog(this,"Error Opening File", "Error",
			        JOptionPane.ERROR_MESSAGE);
	        }
            }
	} catch (Exception e) {
	}
	addBatchToFile();
        ((Batch)batches.elementAt(cbIndex)).batchChanged = false;
    }

    void exportBatch_actionPerformed(ActionEvent event)
    {
        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to export",
                "Export Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        ((Batch)batches.elementAt(cbIndex)).createTextFile(this);
    }

    void exitItem_actionPerformed(ActionEvent event)
    {
        try {
	    this.exitApplication();
	} catch (Exception e) {
	}
    }

    void about_actionPerformed(ActionEvent event)
    {
        try {
	    // JAboutDialog Create with owner and show as modal
  	    JAboutDialog JAboutDialog1 = new JAboutDialog(this);
	    JAboutDialog1.setModal(true);
	    JAboutDialog1.setVisible(true);
	} catch (Exception e) {
	}
    }

    void setHomeDirectoryItem_actionPerformed(ActionEvent event)
    {
        JFileChooser dirDialog = new JFileChooser();
        dirDialog.setCurrentDirectory(userDirectory);
        dirDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = dirDialog.showDialog(this, "Save User Directory");
        if (result == JFileChooser.CANCEL_OPTION)
	    return;
        File dir = dirDialog.getSelectedFile();
        if (dir == null)
  	        JOptionPane.showMessageDialog(this,"Invalid File Name","Invalid File Name",
                  			    JOptionPane.ERROR_MESSAGE);
        else {
            userDirectory = dir;
            fileDialog.setCurrentDirectory(dir);
            preferredSettings.userDirectory = dir;
        }
    }

/*    void maintenanceItem_actionPerformed(ActionEvent event)
    {
        SparePartsDialog spd = new SparePartsDialog(this);
	spd.setVisible(true);
    }*/

/*    void splitItem_actionPerformed(ActionEvent event)
    {
        if (demo){
            JOptionPane.showMessageDialog(this,"Split batch disabled",
		    "Demo Version",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to split",
                "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else{
            SplitBatchDialog sbd = new SplitBatchDialog(this);
            sbd.setVisible(true);
        }
    }*/

    void new_actionPerformed(ActionEvent event)
    {

	if (mySettings == null) {
		mySettings = new Settings("default");
	}
        NewBatchDialog newBatchDialog 
		= new NewBatchDialog(
			this,
		       	mySettings.getIncomingSettings(), -1, false);
	newBatchDialog.setVisible(true);
    }

    void newAssignment_actionPerformed(ActionEvent event)
    {
        if (demo){
            JOptionPane.showMessageDialog(this,"Assignment batches disabled",
		    "Demo Version",JOptionPane.ERROR_MESSAGE);
        }
        else{
            AssignmentNoDialog assignmentNoDialog = new AssignmentNoDialog(this);
            assignmentNoDialog.setVisible(true);
        }
    }

    void newAssignment (int assignmentNo){
        Object[] possibleValues = { "normal assignment batch (10 wafers)", "large optimisation batch (100 wafers)"};
        Object selectedValue = JOptionPane.showInputDialog(this,
        "Choose One", "Special Optimisation Option",
        JOptionPane.INFORMATION_MESSAGE, null,
        possibleValues, possibleValues[0]);
        boolean special;
        if (selectedValue == null)
            return;
        else if (selectedValue.equals("large optimisation batch (100 wafers)"))
            special = true;
        else
            special = false;

    	NewBatchDialog newBatchDialog = new NewBatchDialog(this, defaultSettings.getIncomingSettings(), assignmentNo, special);
	newBatchDialog.setVisible(true);
    }

    void editSettingsMenuItem_actionPerformed(ActionEvent event)
    {
        if (demo){
            JOptionPane.showMessageDialog(this,
            "It is not possible to edit preferred settings in demo mode - you can only view default and previous settings", "Information",
            JOptionPane.INFORMATION_MESSAGE);
        }
        EditSettingsDialog esd = new EditSettingsDialog(this);
	esd.setVisible(true);
    }

    void preferredSettings_actionPerformed(ActionEvent event)
    {
        if (demo){
            JOptionPane.showMessageDialog(this,"Preferred settings disabled",
		    "Demo Version",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cbIndex>=0){
            Batch batch = (Batch)batches.elementAt(cbIndex);
            JPanel panel;
            if (batch.batchStatus == 16){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else if (batch.batchStatus == -2){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else{
                panel = new ProcessPanel(this,"Hello",preferredSettings);
            }
            batchPanels[cbIndex] = panel;
            pane.getViewport().removeAll();
            pane.getViewport().add(panel);
            panel.setVisible(true);
            panel.validate();
        }
    }

    void previousSettings_actionPerformed(ActionEvent event)
    {
        if (cbIndex>=0){
            Batch batch = (Batch)batches.elementAt(cbIndex);
            JPanel panel;
            if (batch.batchStatus == 16){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else if (batch.batchStatus == -2){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else{
                panel = new ProcessPanel(this,"Hello",previousSettings);
            }
            batchPanels[cbIndex] = panel;
            pane.getViewport().removeAll();
            pane.getViewport().add(panel);
            panel.setVisible(true);
            panel.validate();
        }
    }

    void defaultSettings_actionPerformed(ActionEvent event)
    {
        if (cbIndex>=0){
            Batch batch = (Batch)batches.elementAt(cbIndex);
            JPanel panel;
            if (batch.batchStatus == 16){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else if (batch.batchStatus == -2){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else{
                panel = new ProcessPanel(this,"Hello",defaultSettings);
            }
            batchPanels[cbIndex] = panel;
            pane.getViewport().removeAll();
            pane.getViewport().add(panel);
            panel.setVisible(true);
            panel.validate();
        }
    }

    void helpIndexMenuItem_actionPerformed(ActionEvent event)
    {
        String Url = "help" + System.getProperty("file.separator") + "VPLHelp.html";;
        BareBonesBrowserLaunch.openURL(Url);
        //HelpIndexDialog hid = new HelpIndexDialog(this);
        //hid.setVisible(true);
    }

    void currentProcessHelpItem_actionPerformed(ActionEvent event)
    {
        String loadurl = "VPLHelp.html";
        if (batches.size()>0){
        	Batch batch = (Batch)batches.elementAt(cbIndex);
	        switch(batch.batchStatus){
	            // need to add in an if block
                    case 1: loadurl="SAWDAMAGEREMOVALETCH.html"; break;
                    case 2: loadurl="SAWDAMAGEREMOVALETCH.html"; break;
	            case 3: loadurl="TEXTURING.html"; break;
                    // added by Bobh for acid texture
                    case 21: loadurl="ACIDICTEXTURE.html"; break;
                    case 22: loadurl="ACIDICTEXTURE.html"; break;
	            case 23: loadurl="PhosphorusDiffusion.html"; break;
                    // add end by Bobh

	            case 4: loadurl="TEXTURING.html"; break;
	            case 5: loadurl="PhosphorusDiffusion.html"; break;
	            case 6: loadurl="ALUMINIUMSCREENPRINTING.html"; break;
	            case 7: loadurl="ALUMINIUMSCREENPRINTING.html"; break;
	            case 8: loadurl="ALUMINIUMFIRING.html"; break;
	            case 9: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 10: loadurl="PLASMAETCHINGOFEDGES.html"; break;
	            case 11: loadurl="AntireflectionCoating.html"; break;
	            case 12: loadurl="AntireflectionCoating.html"; break;
	            case 13: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 14: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 15: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
                   
	            case 26: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 27: loadurl="PLASMAETCHINGOFEDGES.html"; break;
	            case 28: loadurl="AntireflectionCoating.html"; break;
	            case 29: loadurl="AntireflectionCoating.html"; break;
	            case 30: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 31: loadurl="SILVERSCREENPRINTINGANDFIRING.html"; break;
	            case 32: loadurl="ALUMINIUMSCREENPRINTING.html"; break;
	            case 33: loadurl="ALUMINIUMSCREENPRINTING.html"; break;
	            case 34: loadurl="ALUMINIUMFIRING.html"; break;
	        }
        }
        String Url = "help" + System.getProperty("file.separator") + loadurl;
        BareBonesBrowserLaunch.openURL(Url);
//        HelpIndexDialog hid = new HelpIndexDialog(this);
//        if (cbIndex<0){
//            hid.setVisible(true);
//        }
//        else {
//            Batch batch = (Batch)batches.elementAt(cbIndex);
//            hid.setSelectedPage(batch.batchStatus);
//            hid.setVisible(true);
//        }
    }

    
    void overviewMenuItem_actionPerformed(ActionEvent event)
    {
        if (clearPicturePane() == true){
            filename = "help" + System.getProperty("file.separator") + "audiovideo" + System.getProperty("file.separator") + "OVERVIEW1.avi";
            playVideo(directoryPrefix + filename);
            disableMenus();
            OverviewPanel ovpanel = new OverviewPanel(this);
            pane.getViewport().removeAll();
            pane.getViewport().add(ovpanel);
            ovpanel.setVisible(true);
            ovpanel.validate();
        }
        else{
            JOptionPane.showMessageDialog(this,"Processing Wafers, please wait","VPL Busy",
         			    JOptionPane.ERROR_MESSAGE);
        }
    }

//**********BATCH FILE OPERATIONS**********

    void createNewBatch(String type, int noOfCells, int thickness, double resistivity, String surfaceFinish, int assignmentNo, String qual){
        if (batches.size() < 9){
    	    Batch batch = new Batch(type, qual, noOfCells, thickness, resistivity, surfaceFinish, assignmentNo);
            batch.batchName = "Batch " + noOfBatchesCreated;
            noOfBatchesCreated ++;
            batches.addElement(batch);
            if (cbIndex >=0){
                batchPanels[cbIndex].setVisible(true);
            }
            cbIndex = batches.size()-1;
            updateBatchMenu();
    	    refreshProcess();
        }
        else{
            JOptionPane.showMessageDialog(this,
            "You have 10 batches open.\nThis is the maximum allowable.\nPlease close some batches before opening any more.", "Error",
            JOptionPane.ERROR_MESSAGE);
            return;
        }
            previousSettings.setIncomingSettings(type,qual,thickness,resistivity,surfaceFinish);
    }

    public void addBatchToFile(){
        Batch batch = (Batch)batches.elementAt(cbIndex);
        try{
        output.writeObject(batch);
        output.flush();
        }
        catch (IOException io){
            closeFile();
        }
    }

    public void closeFile(){
        try{
            output.close();
            //System.exit(0);
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(this,
            "Error closing file",
            "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void getBatchFromFile(File directory){
        if (batches.size() > 9){
            JOptionPane.showMessageDialog(this,
            "You have 10 batches open.\nThis is the maximum allowable.\nPlease close some batches before opening any more.", "Error",
            JOptionPane.ERROR_MESSAGE);
            return;
        }
        else{
            try{
            Object o = input.readObject();
            Batch batch = (Batch)o;
            String n = directory.getName();
            if (batch.batchName != n){
                batch.batchName = n;
            }
            batch.directory = directory;
            batch.batchChanged = false;
            batches.addElement(batch);
            if (cbIndex >=0){
                batchPanels[cbIndex].setVisible(true);
            }
            cbIndex = batches.size()-1;
            updateBatchMenu();
            }
            catch (java.lang.ClassNotFoundException cnf){
                JOptionPane.showMessageDialog(this,
                "Error reading file - Not a *.vpl Batch",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (OptionalDataException od){
                JOptionPane.showMessageDialog(this,
                "Error reading file - Optional Data Exception",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (EOFException eof){
                JOptionPane.showMessageDialog(this,
                "Error reading file - EOF Exception",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (StreamCorruptedException sc){
                JOptionPane.showMessageDialog(this,
                "Error reading file - Stream Corrupted Exception",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException io){
                JOptionPane.showMessageDialog(this,
                "Error reading file - IO Exception",
                "Error", JOptionPane.ERROR_MESSAGE);
            }

            refreshProcess();
        }
    }

//**********SETTINGS FILE OPERATIONS**********
    void savePreviousSettings(){
      if (demo){
        filename = "previousSettingsD.vps";
	try {
		File fileName = new File(userPrefix + filename);
		    try{
		        output = new ObjectOutputStream(new FileOutputStream(fileName));
		    }
		    catch(IOException e){
		        JOptionPane.showMessageDialog(this,
		        "Error creating/opening Previous Settings File", "Error",
		        JOptionPane.ERROR_MESSAGE);
		    }

	} catch (Exception e) {
            JOptionPane.showMessageDialog(this,
            "Error Saving Previous Settings", "Error",
            JOptionPane.ERROR_MESSAGE);
	}

        try{
        output.writeObject(previousSettings);
        output.flush();
        }
        catch (IOException io){
            closeFile();
        }
      }
      else{
        filename = "previousSettings.vps";
	try {
		File fileName = new File(userPrefix + filename);
		    try{
		        output = new ObjectOutputStream(new FileOutputStream(fileName));
		    }
		    catch(IOException e){
		        JOptionPane.showMessageDialog(this,
		        "Error creating/opening Previous Settings File", "Error",
		        JOptionPane.ERROR_MESSAGE);
		    }

	} catch (Exception e) {
            JOptionPane.showMessageDialog(this,
            "Error Saving Previous Settings", "Error",
            JOptionPane.ERROR_MESSAGE);
	}

        try{
        output.writeObject(previousSettings);
        output.flush();
        }
        catch (IOException io){
            closeFile();
        }
      }
    }

    void savePreferredSettings(){
        preferredSettings.SSB = SSBatches;
        preferredSettings.TSSB = TSSBatches;
        preferredSettings.PSB = PSBreakage;
        filename = "preferredSettings.vps";
	try {
		File fileName = new File(userPrefix + filename);
		    try{
		        output = new ObjectOutputStream(new FileOutputStream(fileName));
		    }
		    catch(IOException e){
		        JOptionPane.showMessageDialog(this,
		        "Error creating/opening Preferred Settings File", "Error",
		        JOptionPane.ERROR_MESSAGE);
		    }

	} catch (Exception e) {
            JOptionPane.showMessageDialog(this,
            "Error Saving Preferred Settings", "Error",
            JOptionPane.ERROR_MESSAGE);
	}

        try{
        output.writeObject(preferredSettings);
        output.flush();
        }
        catch (IOException io){
            closeFile();
        }
    }


    void getPreviousSettings(){
      if (demo){
        filename = "previousSettingsD.vps";
      } else { 
        filename = "previousSettings.vps";
      }
	try{
	input = new ObjectInputStream(new FileInputStream(userPrefix + filename));
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this,
            "No previous settings exist, previous settings are reverting to default settings", "Information",
            JOptionPane.INFORMATION_MESSAGE);
            if (demo){
                previousSettings = new Settings("demo");
                }
            else{
                previousSettings = new Settings("default");
            }
         }

        if (input != null){
            try{
            Settings settings = (Settings)input.readObject();
            this.previousSettings = settings;
            }
            catch (IOException io){
		if (demo) 
			previousSettings = new Settings("demo");
		else
	                previousSettings = new Settings("default");
                JOptionPane.showMessageDialog(this,
                ("Previous Settings File Corrupt, replacing previous settings with default settings"),
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (java.lang.ClassCastException c){
                if (demo) 
			previousSettings = new Settings("demo");
		else
	                previousSettings = new Settings("default");
                System.out.println("can not be casted to Settings Class");
            }
            catch (java.lang.ClassNotFoundException cnf){
                JOptionPane.showMessageDialog(this,
                "Error opening file - Not of type *.vps Production Line Settings, replacing file with default settings",
                "Error", JOptionPane.ERROR_MESSAGE);
		if (demo) 
			previousSettings = new Settings("demo");
		else
	                previousSettings = new Settings("default");
		savePreviousSettings();
            }
        }
    }

    void getPreferredSettings(){
        filename = "preferredSettings.vps";
	try{
	input = new ObjectInputStream(new FileInputStream(userPrefix + filename));
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this,
            "No preferred settings exist, preferred settings are reverting to default settings", "Information",
            JOptionPane.INFORMATION_MESSAGE);
            preferredSettings = new Settings("default");
         }

        if (input != null){
            try{
            	Settings settings = (Settings)input.readObject();
	        this.preferredSettings = settings;
            }
            catch (IOException io){
                preferredSettings = new Settings("default");
                JOptionPane.showMessageDialog(this,
                "Preferred Settings File Corrupt, replacing preferred settings with default settings",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (java.lang.ClassCastException c){
                preferredSettings = new Settings("default");
                System.out.println("can not be casted to Settings Class");
            }
            catch (java.lang.ClassNotFoundException cnf){
                JOptionPane.showMessageDialog(this,
                "Error opening file - Not of type *.vps Production Line Settings, replacing file with default settings",
                "Error", JOptionPane.ERROR_MESSAGE);
                preferredSettings = new Settings("default");
		savePreferredSettings();
            }
        }
    }

//**********MENU ACTIONS**********

    void refreshSSBatchesItem_actionPerformed(ActionEvent event)
    {
        int reply = JOptionPane.showConfirmDialog(this,"Do you wish to refresh the NaOH etch solution?",
	                                        "Refresh NaOH solution" ,
	                                          JOptionPane.YES_NO_OPTION,
	                                          JOptionPane.QUESTION_MESSAGE);
	if (reply == JOptionPane.YES_OPTION){
            SSBatches = 0;
        }
    }

    // added to refresh acid batch Bobh


       void refreshAcidBatchesItem_actionPerformed(ActionEvent event)
    {
        int reply = JOptionPane.showConfirmDialog(this,"Do you wish to refresh the HNA solution?",
	                                        "Refresh HNA solution" ,
	                                          JOptionPane.YES_NO_OPTION,
	                                          JOptionPane.QUESTION_MESSAGE);
	if (reply == JOptionPane.YES_OPTION){
            acidBatches = 0;
           // System.out.println("when click yes, acidBatches: " + acidBatches);
        }
        //else {
        //  System.out.println("when click no , acidBatches: " + acidBatches);
        //}
        }

    // add end Bobh

    void refreshTSSBatchesItem_actionPerformed(ActionEvent event)
    {
        int reply = JOptionPane.showConfirmDialog(this,"Do you wish to refresh the texturing solution?",
	                                        "Refresh texturing solution" ,
	                                          JOptionPane.YES_NO_OPTION,
	                                          JOptionPane.QUESTION_MESSAGE);
	if (reply == JOptionPane.YES_OPTION){
            TSSBatches = 0;
        }
    }

    void autoProcessItem_actionPerformed(ActionEvent event)
    {
        if (cbIndex<0){
            JOptionPane.showMessageDialog(this,"There is no batch to process",
                "Auto Process Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (((Batch)batches.elementAt(cbIndex)).batchStatus == 16 || ((Batch)batches.elementAt(cbIndex)).batchStatus == -2){
            JOptionPane.showMessageDialog(this,"Processing has been completed for this batch",
                "Auto Process Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        AutoProcessDialog apd = new AutoProcessDialog(this);
        apd.setVisible(true);
    }

    class SymContainer extends ContainerAdapter
    {
	public void componentAdded(ContainerEvent event)
	{
	    Object object = event.getSource();
	    if (object == VirtualProductionLine.this)
	        VirtualProductionLine_componentAdded(event);
	}
    }


    void VirtualProductionLine_componentAdded(ContainerEvent event)
    {
	// to do: code goes here.
    }

//**********INITIALIZE PANEL & MENUS**********

    void updateBatchMenu(){
        batchesMenu.removeAll();
        if (batches.size() > 0){
            batchItems = new JRadioButtonMenuItem[batches.size()];
            for (int i=0; i< batches.size(); i++){
               Batch b = (Batch)batches.elementAt(i);
               batchItems[i] = new JRadioButtonMenuItem(b.batchName);
               batchesMenu.add(batchItems[i]);
               batchesGroup.add(batchItems[i]);
               batchItems[i].addActionListener(itemHandler);
              }
            batchItems[cbIndex].setSelected(true);
            batchesMenu.validate();
        }
    }

    void updateTestsMenu(){
	testsMenu.removeAll();
        if (cbIndex < 0)
            return;
        Batch b = (Batch)batches.elementAt(cbIndex);
        testNames = testArrays.getTestVector(b.batchStatus, b.cofiringBatch, b.arc, b.texture);
        testTypes = testArrays.getTestTypeVector();
        if (testNames.size() > 0){
            testItems = new JMenuItem[testNames.size()];
            for (int i=0; i<testNames.size(); i++){
	        testItems[i] = new JMenuItem((String)testNames.elementAt(i));
                testsMenu.add(testItems[i]);
                testItems[i].addActionListener(testsItemHandler);
            }
            testsMenu.validate();
        }
    }
    
    void disableMenus(){
        this.batchMenu.setEnabled(false);
        this.settingsMenu.setEnabled(false);
        this.toolsMenu.setEnabled(false);
//        this.helpMenu.setEnabled(false);
        Dimension d = vplToolBar.getSize();
        toolBarPanel.remove(vplToolBar);
        String filename = "images" + System.getProperty("file.separator") + "processing1.jpg";
        ProcessingPicPanel myPic = new ProcessingPicPanel(directoryPrefix + filename, d);
        myPic.validate();
        toolBarPanel.add(myPic);
        toolBarPanel.validate();
	this.getContentPane().doLayout();
	repaint();
    }

    void refreshProcess(){
        if (currentGraphPlot != null){
            if (currentGraphPlot.isVisible() == true){
                middlePanel.remove(currentGraphPlot);
                middlePanel.add(picturePanel);
            }
        }

        if (cbIndex<0){
            pane.getViewport().removeAll();
            pane.repaint();
            updateTestsMenu();
            clearPicturePane();
            refreshBatchStatusPanel();
            refreshTestResultsPanel();
        }
        else{
            JPanel p = null;
            if (((Batch)batches.elementAt(cbIndex)).batchStatus == 16){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                p = fbp;
            }
            else if (((Batch)batches.elementAt(cbIndex)).batchStatus == -2){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                p = fbp;
            }
            else{
                p = new ProcessPanel(this,"Hello",mySettings);
            }
            batchPanels[cbIndex] = p;
	    newProcess(p);
        }
	this.getContentPane().doLayout();
	validate();
    }

    void refreshPane(){
        JPanel panel=null;
        if (batchPanels.length >= cbIndex){
            if (batchPanels[cbIndex] != null){
                panel = batchPanels[cbIndex];
            }
        }
        else{
            if (((Batch)batches.elementAt(cbIndex)).batchStatus == 16){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else if (((Batch)batches.elementAt(cbIndex)).batchStatus == -2){
                FinishedBatchPanel fbp = new FinishedBatchPanel((Batch)batches.elementAt(cbIndex));
                panel = fbp;
            }
            else{
                panel = new ProcessPanel(this,"Hello",mySettings);
            }
        }
        batchPanels[cbIndex] = panel;
        pane.getViewport().removeAll();
        pane.getViewport().add(panel);
        panel.doLayout();
	panel.setVisible(true);
	panel.validate();
	pane.revalidate();
    }

    void newProcess(JPanel panel){
        batchPanels[cbIndex] = panel;
        pane.getViewport().removeAll();
        pane.getViewport().add(panel);
	panel.setVisible(true);
	panel.validate();
        updateTestsMenu();
	pane.revalidate();
	refreshBatchStatusPanel();
	refreshTestResultsPanel();
        clearPicturePane();
	this.getContentPane().doLayout();
	validate();
    }

    void refreshBatchStatusPanel(){
        if (cbIndex<0){
            bsPanel.getViewport().removeAll();
            bsPanel.repaint();
        }
        else{
            Batch batch = (Batch)batches.elementAt(cbIndex);
    		if (batch != null){
		    batchStatusPanel = new BatchStatusPanel(this);
		    bsPanel.getViewport().removeAll();
		    bsPanel.getViewport().add(batchStatusPanel);
		    batchStatusPanel.validate();
		}
        }
	this.getContentPane().doLayout();
	validate();
    }

    void refreshTestResultsPanel(){
       testsNotesPane.removeAll();
        if (cbIndex>=0){
            Batch batch = (Batch)batches.elementAt(cbIndex);
    	    if (batch != null){
    	        testResultsPanel = new TestResultsPanel((Batch)batches.elementAt(cbIndex));
//                visualListPanel = new VisualListPanel(this, (Batch)batches.elementAt(cbIndex));
                graphListPanel = new GraphListPanel(this, (Batch)batches.elementAt(cbIndex));
                noteBookString = ((Batch)batches.elementAt(cbIndex)).batchName + " Note Book";
    	        noteBookPanel = new JPanel();
//                lsd = batch.batchNotes;
                noteBook.setDocument(lsd);
                noteBook.setCaretPosition(0);
                noteBook.setMargin(new Insets(5,5,5,5));
   	        noteBookPanel.add(new JScrollPane(noteBook));
       	        noteBookPanel.setLayout(new GridLayout());
                noteBookPanel.setBorder(BorderFactory.createBevelBorder(1));
	        testsNotesPane.add("Test Results", testResultsPanel);
//                testsNotesPane.add("Visual Inspections", visualListPanel);
                testsNotesPane.add("Graphed Results", graphListPanel);
                testsNotesPane.add(noteBookString, noteBookPanel);
	        testResultsPanel.validate();
            }
        }
	this.getContentPane().doLayout();
	validate();
    }

    void noWafers(){
        JPanel panel = new JPanel();
        JLabel finishedLabel = new JLabel("No Wafers Remaining in " + ((Batch)batches.elementAt(cbIndex)).batchName);
        panel.add(finishedLabel);
	newProcess(panel);
    }
    //**********PROCESSING**********
    void playVideo(String URLString){

        clearPicturePane();
        Video video = new Video(URLString, this);
        video.setBorder(BorderFactory.createEtchedBorder());
        currentVideo = video;
        picturePane.getViewport().add(currentVideo);
        currentVideo.setVisible(true);
        currentVideo.validate();
    	this.getContentPane().doLayout();
	validate();
    }

    @SuppressWarnings("deprecation")
	void finishProcess(){
        clearPicturePane();

        if (cbIndex >-1){
            Batch b = (Batch)batches.elementAt(cbIndex);
            if ((b.batchStatus == 6 && b.assignmentNo == -1) && (!demo) && !b.cofiringBatch){
                boolean cf = false;
                JOptionPane opane = new JOptionPane();
                String[] options = {"Separate Firing", "Cofiring"};
                opane.setOptions(options);
                opane.setInitialSelectionValue("Separate Firing");
                opane.setMessage("Do you wish to proceed with processing including cofiring of metal contacts?");
                JDialog dialog = opane.createDialog(this, "Firing of Metal Contacts");
                dialog.setVisible(true);
                //handle seperate firing or cofiring
                Object selectedValue = opane.getValue();
                if(selectedValue == null){
                   cf = false;
                   b.batchStatus = 6;
                }
                if (selectedValue.equals("Separate Firing")){
                    cf = false;
                   b.batchStatus = 6;
                }
                else{
                    cf = true;
                    b.batchStatus = 26;
                }
                b.cofiringBatch = cf;
            }
            else if ((b.batchStatus == 6 && b.assignmentNo == -1) && (demo)){
                b.cofiringBatch = false;
                b.batchStatus = 6;
            }
            if ((b.batchStatus == 3 && b.assignmentNo == -1) && (!demo) && !b.texture){
                int reply = JOptionPane.showConfirmDialog(this,"Do you wish to texture the wafers?",
                                                              "Texturing" ,
                                                              JOptionPane.YES_NO_OPTION,
                                                              JOptionPane.QUESTION_MESSAGE);
                // If the confirmation was affirmative, handle exiting.
                if (reply == JOptionPane.YES_OPTION){
                    b.texture = true;
                }
                else{
                    b.texture = false;
                    b.batchStatus = b.batchStatus + 1;
                }
            }
            else if ((b.batchStatus == 3 && b.assignmentNo == -1) && (demo)){
                b.texture = false;
                b.batchStatus = b.batchStatus + 1;
            }

            if ((b.batchStatus == 11 || b.batchStatus == 28) && b.assignmentNo == 8){
                    b.arc = true;
            }
            else if ((b.batchStatus == 11 || b.batchStatus == 28) && b.assignmentNo == -1 && !b.arc){
                //System.out.print("b.arc 1 = " + b.arc);
                Object[] possibleValues = { "Do not apply an AR coating", "Apply a Silicon Nitride AR coating", "Apply a Titanium Dioxide AR coating"};
                Object selectedValue = JOptionPane.showInputDialog(this,
                "Choose One", "Anti-Reflection Coating Options",
                JOptionPane.QUESTION_MESSAGE, null,
                possibleValues, possibleValues[0]);
                if (selectedValue == null){
                  b.arc = false;
                  b.batchStatus = b.batchStatus + 2;
    //              return;
                }
                else if (selectedValue.equals("Apply a Silicon Nitride AR coating")){
                  b.arc = true;
                  if (b.cofiringBatch)
                    b.batchStatus = 29;
                  else
                    b.batchStatus = 12;
                }
                else if (selectedValue.equals("Apply a Titanium Dioxide AR coating")){
                  b.arc = true;
                  if (b.cofiringBatch)
                    b.batchStatus = 28;
                  else
                    b.batchStatus = 11;
                }
                else{
                  b.arc = false;
                  b.batchStatus = b.batchStatus + 2;
                }

            }
            else if ((b.batchStatus == 11 || b.batchStatus == 28)  && !(b.assignmentNo == -1)) {
                //System.out.print("b.arc 2 = " + b.arc);
                b.arc = false;
                b.batchStatus = b.batchStatus + 2;
            }
            if (b.batchStatus == 16){
                b.finish(this);
            }

        }
        this.batchMenu.setEnabled(true);
        this.settingsMenu.setEnabled(true);
        this.toolsMenu.setEnabled(true);
        toolBarPanel.removeAll();
        toolBarPanel.add(vplToolBar);
        toolBarPanel.repaint();
        refreshProcess();
    }


/*    void showVisual(String visualURLString){
            clearPicturePane();
            String filename = "images" + System.getProperty("file.separator") + visualURLString;
            PicPanel myPic = new PicPanel(directoryPrefix + filename);
            myPic.validate();
            picturePane.getViewport().add(myPic);
            this.getContentPane().doLayout();
            repaint();
    }*/

    void runTest(String s){
        TestDialog td = new TestDialog(this, s);
	td.setVisible(true);
    }

    void runGraph(String s){
        GraphDialog gd = new GraphDialog(this, s);
	gd.setVisible(true);
    }

    void runVocTest(String s){
        int bs = ((Batch)batches.elementAt(cbIndex)).batchStatus;
        if ((bs > 14 && bs < 17) || (bs > 31)){
	    // Show a confirmation dialog
	    int reply = JOptionPane.showConfirmDialog(this,"If you perform a Voc test at this point,\n you will destroy your wafer.\nDo you wish to proceed?",
	  	                                          "Voc Test with Front Contact" ,
	    	                                          JOptionPane.YES_NO_OPTION,
	    	                                          JOptionPane.QUESTION_MESSAGE);
    	    // If the confirmation was affirmative, proceed.
	    if (reply == JOptionPane.NO_OPTION)
	        return;
        }

        GraphDialog gd = new GraphDialog(this, s);
	gd.setVisible(true);
    }

    void showGraph(GraphData graphData){
        if (clearPicturePane() == true){

            if (graphData.graphName.equals("Open Circuit Voltage Contour Map")){
                String filename = "";
                Vector labels = new Vector();
                Vector labels1 = new Vector();
                NumberFormat format = NumberFormat.getNumberInstance();
	        format.setMaximumFractionDigits(0);
                double Voc1 = ((Double)graphData.xpoints.elementAt(0)).doubleValue()*1000;
                double Voc2 = ((Double)graphData.xpoints.elementAt(1)).doubleValue();
                double Voc3 = 0.0;
                if (graphData.xpoints.size()>2)
                    Voc3 = ((Double)graphData.xpoints.elementAt(2)).doubleValue()*1000;
                if (Voc2!=0.0){
                    Voc2 = Voc2*1000;
                    double intVoc1 = 0.0;
                    double intVoc2 = 0.0;
                    double intVoc3 = 0.0;
                    double intVoc4 = 0.0;
                    double intVoc5 = 0.0;
                    double intVoc6 = 0.0;
                    double intVoc7 = 0.0;
                    double intVoc8 = 0.0;
                    double diff = (Voc1 - Voc2);
                    if (diff <=50){
                        labels1.addElement(format.format(Voc2) + " - " + format.format(Voc1));
                    }
                    else if (diff >=50 && diff < 150){
                        intVoc1 = Voc2;
                        intVoc2 = Voc2 + (diff/2);
                        intVoc3 = Voc1;
                        labels1.addElement(format.format(intVoc1) + " - " + format.format(intVoc2));
                        labels1.addElement(format.format(intVoc2) + " - " + format.format(intVoc3));
                    }
                    else if (diff >= 150){
                        intVoc1 = Voc2;
                        intVoc2 = Voc2 + (diff/3);
                        intVoc3 = Voc2 + (2*diff/3);
                        intVoc4 = Voc1;
                        labels1.addElement(format.format(intVoc1) + " - " + format.format(intVoc2));
                        labels1.addElement(format.format(intVoc2) + " - " + format.format(intVoc3));
                        labels1.addElement(format.format(intVoc3) + " - " + format.format(intVoc4));
                    }
                }

                else if (Voc2 == 0.0){
                    Voc1 = Voc1 + 3;
                    Voc2 = Voc2 - 3;
                    labels1.addElement(format.format(Voc2) + " - " + format.format(Voc1));
                }


                double bigdiff = (Voc2 - Voc3);
                if (graphData.xpoints.size() > 2){
                    if (bigdiff <200){
                        labels.addElement(format.format(Voc3) + " - " + format.format(Voc2 - (bigdiff/2)));
                        labels.addElement(format.format(Voc2 - (bigdiff/2)) + " - " + format.format(Voc2));
                    }
                    else if (bigdiff >= 200 && bigdiff <300){
                        labels.addElement(format.format(Voc3) + " - " + format.format(Voc2 - (2*bigdiff/3)));
                        labels.addElement(format.format(Voc2 - (2*bigdiff/3)) + " - " + format.format(Voc2 - (bigdiff/3)));
                        labels.addElement(format.format(Voc2 - (bigdiff/3)) + " - " + format.format(Voc2));
                    }
                    else if (bigdiff >= 300 && bigdiff <400){
                        labels.addElement(format.format(Voc3) + " - " + format.format(Voc2 - (3*bigdiff/4)));
                        labels.addElement(format.format(Voc2 - (3*bigdiff/4)) + " - " + format.format(Voc2 - (bigdiff/2)));
                        labels.addElement(format.format(Voc2 - (bigdiff/2)) + " - " + format.format(Voc2 - (bigdiff/4)));
                        labels.addElement(format.format(Voc2 - (bigdiff/4)) + " - " + format.format(Voc2));
                    }
                    else if (bigdiff >= 400){
                        labels.addElement(format.format(Voc3) + " - " + format.format(Voc2 - (4*bigdiff/5)));
                        labels.addElement(format.format(Voc2 - (4*bigdiff/5)) + " - " + format.format(Voc2 - (3*bigdiff/5)));
                        labels.addElement(format.format(Voc2 - (3*bigdiff/5)) + " - " + format.format(Voc2 - (2*bigdiff/5)));
                        labels.addElement(format.format(Voc2 - (2*bigdiff/5)) + " - " + format.format(Voc2 - (bigdiff/5)));
                        labels.addElement(format.format(Voc2 - (bigdiff/5)) + " - " + format.format(Voc2));
                    }
                }

                if (labels1.size() == 1){
                    if (labels.size() == 0)
                        filename = "solarcellVOCa.jpg";
                    else if (labels.size() == 2)
                        filename = "solarcellVOC2a.jpg";
                    else if (labels.size() == 3)
                        filename = "solarcellVOC3a.jpg";
                    else if (labels.size() == 4)
                        filename = "solarcellVOC4a.jpg";
                    else if (labels.size() == 5)
                        filename = "solarcellVOC5a.jpg";
                }
                else if (labels1.size() == 2){
                    if (labels.size() == 0)
                        filename = "solarcellVOCb.jpg";
                    else if (labels.size() == 2)
                        filename = "solarcellVOC2b.jpg";
                    else if (labels.size() == 3)
                        filename = "solarcellVOC3b.jpg";
                    else if (labels.size() == 4)
                        filename = "solarcellVOC4b.jpg";
                    else if (labels.size() == 5)
                        filename = "solarcellVOC5b.jpg";
                }
                else if (labels1.size() == 3){
                    if (labels.size() == 0)
                        filename = "solarcellVOCc.jpg";
                    else if (labels.size() == 2)
                        filename = "solarcellVOC2c.jpg";
                    else if (labels.size() == 3)
                        filename = "solarcellVOC3c.jpg";
                    else if (labels.size() == 4)
                        filename = "solarcellVOC4c.jpg";
                    else if (labels.size() == 5)
                        filename = "solarcellVOC5c.jpg";
                }

                VocPlotLegend legendPanel = new VocPlotLegend("Voc Contour Map",labels, labels1);
                pane.getViewport().removeAll();
                pane.getViewport().add(legendPanel);
                legendPanel.setVisible(true);
                legendPanel.validate();
                String fn = directoryPrefix + "images" + System.getProperty("file.separator") + filename;
                PicPanel myPic = new PicPanel(fn);
                myPic.validate();
                picturePane.getViewport().add(myPic);
                this.getContentPane().doLayout();
                repaint();
            }
            else{
                Vector graphs = new Vector();
                graphs.addElement(graphData);
                middlePanel.removeAll();
                currentGraphPlot = new PC1DPlot(graphs);
                middlePanel.add(currentGraphPlot);
                middlePanel.validate();
                String[] labels = {graphData.xaxisname, graphData.yaxisname, graphData.zaxisname,
                    graphData.waxisname, graphData.vaxisname};
                double[] values = {graphData.voc, graphData.isc, graphData.maxPower};
                double unitsTest = 0;
                if (graphData.graphName.equals("Doping Profile"))
                    unitsTest = ((Double)graphData.ypoints.elementAt(10)).doubleValue();
                String title = graphData.graphName;
                PlotLegend legendPanel = new PlotLegend(title,labels,values,unitsTest);
                pane.getViewport().removeAll();
                pane.getViewport().add(legendPanel);
	        legendPanel.setVisible(true);
                legendPanel.validate();
            }
            this.getContentPane().doLayout();
            repaint();
        }
    }

    boolean clearPicturePane(){
        if (currentGraphPlot != null){
            if (currentGraphPlot.isVisible() == true){
                middlePanel.remove(currentGraphPlot);
                middlePanel.add(picturePanel);
            }
        }
        if (currentVideo != null){
            if (currentVideo.playerBean.getState() == javax.media.bean.playerbean.MediaPlayer.Started){
                return false;
            }
            if (currentVideo.playerBean != null) {
                currentVideo.playerBean.stopAndDeallocate();
                currentVideo.playerBean.close();
                picturePane.getViewport().remove(currentVideo);
                currentVideo = null;
            }
        }
        if (batches.size() > 0){
        int batchStatus = ((Batch)batches.elementAt(cbIndex)).batchStatus;
        ClassLoader classLoader = getClass().getClassLoader();
    	String fn = classLoader.getResource("images/VPL4_Intro.jpg").toString();
        switch(batchStatus){
        	case -2:
       	 	fn = classLoader.getResource("images/VPL4_Intro.jpg").toString(); break;
	        case -1:
	       	 	fn = classLoader.getResource("images/VPL4_Intro.jpg").toString(); break;
	        case 0:
	        	 fn = classLoader.getResource("images/VPL4_Intro.jpg").toString(); break;
	        case 1:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "epic.JPG"; break;
	        case 2:
	        	 fn = directoryPrefix + "images" + System.getProperty("file.separator") + "rpic.JPG"; break;
	        case 3:
	        	 fn = directoryPrefix + "images" + System.getProperty("file.separator") + "tpic.JPG"; break;
	        case 4:
	        	 fn = directoryPrefix + "images" + System.getProperty("file.separator") + "racpic.JPG"; break;
	        case 5:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "dpic.JPG"; break;
	        case 6:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "alspic.JPG"; break;
	        case 7:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "alppic.JPG"; break; 
	        case 8:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "alfpic.JPG"; break;
	        case 9:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "dorpic.JPG"; break;
	        case 10:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "pepic.JPG"; break;
	        case 11:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "arcpic.JPG"; break;
	        case 12:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "arcpic.JPG"; break;
	        case 13:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "ssspic.JPG"; break;
	        case 14:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "ssppic.JPG"; break;
	        case 15:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "ssfpic.JPG"; break;
	        case 16:
	        	fn = classLoader.getResource("images/VPL4_Intro.jpg").toString(); break;

                 // add image on acid texture BobH

                case 21:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "atpic.jpg"; break;

                case 22:
                        fn = directoryPrefix + "images" + System.getProperty("file.separator") + "racpic.jpg"; break;

                case 23:
                        fn = directoryPrefix + "images" + System.getProperty("file.separator") + "racpic.jpg"; break;
                 // add end Bobh

                case 26:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "dorpic.JPG"; break;
	        case 27:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "pepic.JPG"; break;
	        case 28:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "arcpic.JPG"; break;
	        case 29:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "arcpic.JPG"; break;
	        case 30:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "ssspic.JPG"; break;
	        case 31:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "ssppic.JPG"; break;
	        case 32:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "alspic.JPG"; break;
	        case 33:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "alppic.JPG"; break;
	        case 34:
	        	fn = directoryPrefix + "images" + System.getProperty("file.separator") + "cofpic.jpg"; break;
	        }
	        PicPanel myPic = new PicPanel(fn);
	        myPic.validate();
	        picturePane.getViewport().add(myPic);
	        this.getContentPane().doLayout();
        }
        repaint();
        validate();
        return true;
    }

    void etch(Vector v, boolean disabled){
    	
        if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).etch(SSBatches, v);
            SSBatches = SSBatches + 1;
        }
        previousSettings.setEtchSettings(v);
        finishProcess();
    }
// to add acid texturing and acid rinse / clean method Bobh

    void acidTexture(Vector v, boolean disabled){

        if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).acidTexture(acidBatches, v);
            acidBatches = acidBatches + 1;
        }
        previousSettings.setAcidTextureSettings(v);
        finishProcess();
    }
    

    void acidRinseAcidClean(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).acidRinseAcidClean(v, this);
        }
            previousSettings.setAcidRinseAcidCleanSettings(v);
            finishProcess();
    }

    // to end here Bobh

    void postWaferEtchRinse(Vector v, boolean disabled){

        if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).postWaferEtchRinse(v);
        }
        if (!disabled || demo)
            previousSettings.setRinseSettings(v);
        disableMenus();
        finishProcess();
    }

    void texture(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).texture(TSSBatches, v);
            TSSBatches = TSSBatches + 1;
        }
            previousSettings.setTextureSettings(v);
            finishProcess();
    }

    void rinseAcidClean(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).rinseAcidClean(v, this);
        }
            previousSettings.setRinseAcidCleanSettings(v);
            finishProcess();
    }

    void spinOnDiffusion(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).spinOnDiffusion(v, this);
        }
            previousSettings.setDiffusionSettings(v);
            finishProcess();
    }

    void diffusionOxideRemoval(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).diffusionOxideRemoval(v, this);
        }
            previousSettings.setDiffusionOxideRemovalSettings(v);
            finishProcess();
    }

    void plasmaEtch(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).plasmaEtch(v, this);
        }
        if (!disabled || demo)
            previousSettings.setPlasmaEtchSettings(v);
        disableMenus();
        finishProcess();
    }

    void tarCoating(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).tarCoating(v, this);
        }
        if (!disabled || demo)
            previousSettings.setTArCoatingSettings(v);
        disableMenus();
        finishProcess();
    }

    void sarCoating(Vector v, boolean disabled){

        if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).sarCoating(v, this);
        }
        if (!disabled || demo)
            previousSettings.setSArCoatingSettings(v);
        disableMenus();
        finishProcess();
    }

   void alScreenSetup(Vector v, boolean disabled){
//        alGraphic = null;
	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).alScreenSetup(v, this);
        }
        if (!disabled || demo)
            previousSettings.setAlScreenSetupSettings(v);
        disableMenus();
        finishProcess();
    }

    void screenPrint(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).screenPrint(v, this);
        }
        if (!disabled || demo)
            previousSettings.setAlScreenPrintSettings(v);
        disableMenus();
        finishProcess();
    }

    void alFiring(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).alFiring(v, this);
        }
        if (!disabled || demo)
            previousSettings.setAlFiringSettings(v);
        disableMenus();
        finishProcess();
    }

    void silverScreenSetup(Vector v, boolean disabled){
	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).silverScreenSetup(v);
        }
	if (previousSettings == null) {
		//System.out.println(previousSettings);
		System.exit(-1);
	}
        if (!disabled || demo)
            previousSettings.setSilverScreenSetupSettings(v);
        disableMenus();
        finishProcess();
    }

    void silverScreenPrint(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).silverScreenPrint(v, this);
        }
        if (!disabled || demo)
            previousSettings.setSilverScreenPrintSettings(v);
        disableMenus();
        finishProcess();
    }

    void silverFiring(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).silverFiring(v, this);
        }
        if (!disabled || demo)
            previousSettings.setSilverFiringSettings(v);
        disableMenus();
        finishProcess();
    }

    void cofiring(Vector v, boolean disabled){

	if (batches.elementAt(cbIndex) != null){
            ((Batch)batches.elementAt(cbIndex)).cofiring(v, this);
        }
        if (!disabled || demo)
            previousSettings.setCofiringSettings(v);
        disableMenus();
        finishProcess();
    }

    private void jbInit() throws Exception {
        this.setForeground(Color.black);
    }

}
