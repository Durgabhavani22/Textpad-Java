import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Textpad extends JFrame {
  	JTextArea textArea=  new JTextArea();
            JTextArea lineNumbers;
	JFileChooser chooser = new JFileChooser();
	JFrame frame=new JFrame();

    public Textpad() {

        setSize(600, 600);
        setTitle("TEXTPAD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        lineNumbers = new JTextArea("1");
        lineNumbers.setEditable(false);
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane lineNumberScrollPane = new JScrollPane(lineNumbers);                   lineNumberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(lineNumberScrollPane, BorderLayout.WEST);

         scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent e ) {
         lineNumberScrollPane.getVerticalScrollBar().setValue(e.getValue());
        }
        });

        createMenuBar();

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout()); // Adjust the layout manager as needed


//start of word count (total south panel)

        JLabel l2 = new JLabel();
        JLabel l1 = new JLabel();

        southPanel.add(l1,BorderLayout.EAST);
        l1.setLocation(0,0);
        southPanel.add(l2,BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
        textArea.addCaretListener(new CaretListener() {   
        public void caretUpdate(CaretEvent e) {
        int pos = e.getDot();
        int  s = textArea.getText().split("\\s+").length;
        int c = textArea.getText().length();
        try {
                    int line = textArea.getLineOfOffset(pos) + 1;
                    int col = pos - textArea.getLineStartOffset(line - 1) + 1;
                   l1. setText("Line: " + line + "          Column: " + col  +"                        ");
                   l2.setText("\n\n\n\n"+"                  Words: "+s+"\n"+"          Characters:"+c);
                } 
       catch (Exception ex)
               {
                    ex.printStackTrace();
                }
            }
        });
        updateLineNumbers();
        pack();
        setLocationRelativeTo(null);
    }

//end of word count


//start of line numbers
    private void updateLineNumbers() {
        String[] lines = textArea.getText().split("\n");
        int lineCount = lines.length;
        StringBuilder numbers = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            numbers.append(i).append("\n");
        }
        lineNumbers.setText(numbers.toString());
    }

//end of line numbers


    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu view = new JMenu("View");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem SaveAs=new JMenuItem("SaveAs");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem font=new JMenuItem("Font");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem color=new JMenuItem("FontColor");
       JMenuItem New=new JMenuItem("New");
       JMenuItem zoomInItem=new JMenuItem("Zoom--");
       JMenuItem zoomOutItem=new JMenuItem("Zoom++");
       JMenuItem About=new JMenuItem("About");


//file menu
	fileMenu.add(New);
            fileMenu.add(openItem);
            fileMenu.add(saveItem);
	fileMenu.add(SaveAs);
            fileMenu.add(exitItem);

//viewMenu

	view.add(zoomInItem);
	view.add(zoomOutItem);
	view.add(About);

//edit menu
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(font);
        editMenu.add(color);

        openItem.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               saveFile();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });

        New.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
                    setTitle("New Document.txt");
		 textArea.setText(""); 
            }
         });

	zoomInItem.addActionListener(
	new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
			int fontSize = textArea.getFont ().getSize ();
    			// Decrease the font size by 2
    			fontSize -= 2;
   		 	// Set the new font size to the text area
   			 textArea.setFont (new Font (textArea.getFont ().getName (), textArea.getFont ().getStyle (), fontSize));
	lineNumbers.setFont (new Font (textArea.getFont ().getName (), textArea.getFont ().getStyle (), fontSize));
	}
	});

	zoomOutItem.addActionListener(
	new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	  int fontSize = textArea.getFont ().getSize ();
	    // Increase the font size by 2
	     fontSize += 2;
	    // Set the new font size to the text area
	    textArea.setFont (new Font (textArea.getFont ().getName (), textArea.getFont ().getStyle (), fontSize));
	lineNumbers.setFont (new Font (textArea.getFont ().getName (), textArea.getFont ().getStyle (), fontSize));
	}
	});

	About.addActionListener(
	new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
		 JPanel message = new JPanel(new GridLayout(4, 1));
  		 message.add(new JLabel("Notepad - A simple text editor"));
        		message.add(new JLabel("Version: 1.0"));
        		message.add(new JLabel("DurgaBhavani,Meghana,Madhuri,Yashwanth,Rahul"));
      		message.add(new JLabel("License: MIT"));
		JOptionPane.showMessageDialog(frame, message, "About TEXTPAD", JOptionPane.INFORMATION_MESSAGE); 
	}
	});

        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
          });
	color.addActionListener(new ActionListener()
	{
            public void actionPerformed(ActionEvent e) {
	Color selectedColor = JColorChooser.showDialog(new Textpad(), "Choose Font Color", textArea.getForeground());
            if(selectedColor!= null) {
            textArea.setForeground(selectedColor);
     	   }
	}
	});
	
	SaveAs.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {			 
			String path = null;
			String data = null;

                                    chooser = new JFileChooser();
        			int choose = chooser.showSaveDialog(SaveAs);
        			if (choose == JFileChooser.APPROVE_OPTION) {
            		File file = chooser.getSelectedFile();
            		path = file.getAbsolutePath();

           			 if (file.exists()) {
            		    int result = JOptionPane.showConfirmDialog(
            		            SaveAs,
            		            "The file already exists. Do you want to overwrite it?",
            		            "File Exists",
            		            JOptionPane.YES_NO_OPTION
                		);

                		if (result != JOptionPane.YES_OPTION) {
                    		// User chose not to overwrite, return without saving
                    		return;
               	 }}
            	data = textArea.getText();
            	try {
            	    FileOutputStream fos = new FileOutputStream(path);
            	    byte[] b = data.getBytes();
            	    fos.write(b);
            	    fos.close();
		    }
            	 catch (Exception e1) {
                          e1.printStackTrace();
          		  }
		}
       	 }});

	font.addActionListener(new  ActionListener(){
	            public void actionPerformed(ActionEvent e) {
	 Font selectedFont = JFontChooser.showDialog(new Textpad(), "Choose Font ", textArea.getFont() );
             if(selectedFont!= null) {
             textArea.setFont(selectedFont);
	 lineNumbers.setFont(selectedFont);
	        }
	}});

        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

// Edit menu
cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

// View menu
zoomInItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
zoomOutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

// File menu
New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
SaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK)); // Ctrl + Shift + A
exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(view);
        setJMenuBar(menuBar);
    }



    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                textArea.read(reader, null);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }}}

    private void saveFile() {
        String path = null;
        String data = null;
        chooser = new JFileChooser();
        int choose = chooser.showSaveDialog(this);
        if (choose == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
            if (file.exists()) {
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "The file already exists. Do you want to overwrite it?",
                        "File Exists",
                        JOptionPane.YES_NO_OPTION
                );
                if (result != JOptionPane.YES_OPTION) {
                    // User chose not to overwrite, return without saving
                    return;
                }}
            data = textArea.getText();
            try {
                FileOutputStream fos = new FileOutputStream(path);
                byte[] b = data.getBytes();
                fos.write(b);
                fos.close();
                } catch (Exception e1) {
                e1.printStackTrace();
            }}}
    	public static void main(String[] args) {
                new Textpad().setVisible(true); 
            }}


class JFontChooser extends JDialog {

    private Font selectedFont;
    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> sizeComboBox;
    private JCheckBox boldCheckBox, italicCheckBox;
    private JButton okButton, cancelButton;
    private static String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private static Integer[] fontSizes = { 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 72 };
    private boolean okButtonClicked = false;
    public JFontChooser(Frame parent, String title, Font initialFont) {
        super(parent, title, true);
        selectedFont = initialFont;
        fontComboBox = new JComboBox<>(fontNames);
        sizeComboBox = new JComboBox<>(fontSizes);
        boldCheckBox = new JCheckBox("Bold");
        italicCheckBox = new JCheckBox("Italic");
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Font:"));
        add(fontComboBox);
        add(new JLabel("Size:"));
        add(sizeComboBox);
        add(new JLabel("Style:"));
        add(boldCheckBox);
        add(new JLabel(""));
        add(italicCheckBox);
        add(okButton);
        add(cancelButton);

        // Set initial values based on the initialFont
        fontComboBox.setSelectedItem(initialFont.getFamily());
        sizeComboBox.setSelectedItem(initialFont.getSize());
        boldCheckBox.setSelected(initialFont.isBold());
        italicCheckBox.setSelected(initialFont.isItalic());

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            okButtonClicked = true;
	Textpad ob=new Textpad();
            ob.textArea.setFont(getSelectedFont());
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public Font getSelectedFont() {
        if (okButtonClicked) {
        String fontName = (String) fontComboBox.getSelectedItem();
        int fontSize = (int) sizeComboBox.getSelectedItem();
        int fontStyle = (boldCheckBox.isSelected() ? Font.BOLD : 0) | (italicCheckBox.isSelected() ? Font.ITALIC : 0);
            selectedFont = new Font(fontName, fontStyle, fontSize);
        }
        return selectedFont;
    }
    public static Font showDialog(Frame parent, String title, Font initialFont) {
        JFontChooser fontChooser = new JFontChooser(parent, title, initialFont);
        return fontChooser.getSelectedFont();
    }
}