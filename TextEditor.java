import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
class TextEditor extends JFrame implements ActionListener
{
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;//JSpinner is used to set the currentSize of the font
	JLabel fontLabel;
	JButton fontColorButton;
	JComboBox fontStyleBox;
	JMenuBar menuBar;//JMenuBar contains many JMenu's
	JMenu filMenu;//JMenu contains many JMenuItems's
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	TextEditor()
	{
		//setting things for frame
		this.setSize(800,600);
		this.setTitle("Text Editor By Surya");
		this.setLocationRelativeTo(null);//sets this to open in centre of screen
		this.setLayout(new FlowLayout());
		this.setForeground(Color.BLACK);//setting fontColor to black
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		menuBar=new JMenuBar();
		filMenu=new JMenu("File");
		openItem=new JMenuItem("Open");
		saveItem=new JMenuItem("Save");
		exitItem=new JMenuItem("Exit");
		filMenu.add(openItem);//adding openMenuItem to fileMenu
		filMenu.add(saveItem);//adding saveMenuItem to fileMenu
		filMenu.add(exitItem);//adding exitMenuItem to fileMenu
		menuBar.add(filMenu);//adding fileMenu(after setting options of fileMenu only) to menuBar.
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);


		//setting things for TextArea
		textArea=new JTextArea();
		textArea.setLineWrap(true);//text does not goes horizontally infinitely.It comes to next Line when meets the rightEnd.
		textArea.setWrapStyleWord(true);//when word is in middle and we got hit to the rightEnd, then it makes the semi-finished word to form fully in nextLine.
		Font defaultFont=new Font("Arial", Font.PLAIN, 20); 
		textArea.setFont(defaultFont);//in new Font(#1, #2, #3)-->#1.FontStyle(ARIAL), #2,FontType(BOLD, ITALIC) #3.fontSize
		

		//fontLabel
		fontLabel=new JLabel();
		//we can set Images as well.But not set here because not able to see image.
		//ImageIcon fontSizeImageIcon = new ImageIcon(new ImageIcon("fontSize-logo.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		//fontLabel.setIcon(fontSizeImageIcon);
		fontLabel.setText("FontSize:");


		//setting things for scrollPane(more or less its a ScrollBar)
		scrollPane=new JScrollPane(textArea);
		Dimension d=this.getSize();//getting frameSize
		d.width=d.width-30;
		d.height=d.height-100;
		scrollPane.setPreferredSize(d);//new Dimension(750, 450)
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		


		//Below is the fontSizeSpinner visual prototype.
		//	___________
		//	|100	|^|		--->upArrow
		//	|		|.| 	--->downArrow
		//	___________
		//setting things for fontSizeSpinner
		fontSizeSpinner=new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);//setting default font size to 20.
		fontSizeSpinner.addChangeListener(//when value is changed in fontSizeSpinner, we perform method that's within the ChangeListener constructor.
			new ChangeListener() 
			{
				//implementing the unimplemented method stateChanged().
				public  void stateChanged(ChangeEvent e)
				{
					String fontStyle=textArea.getFont().getFamily();//getting the previous fontStyle
					int fontType=Font.PLAIN;
					int fontSize=(Integer)fontSizeSpinner.getValue();//getting the previos fontSize.
					textArea.setFont(new Font(fontStyle, fontType, fontSize));//setting font to previousFontStyle, previousFontSize, PlainText.
					return;
				}	
			}
		);


		//setting a JButton for choosing fontColor
		fontColorButton=new JButton("Choose Color");
		fontColorButton.setForeground(Color.white);//setting button textColor
		fontColorButton.setBackground(Color.ORANGE);//setting button BackgroundColor
		fontColorButton.addActionListener(this);//action to the choosed Color is written in actionPerformed()


		//creating a JLabel for fontStyle
		JLabel fontStyleLabel=new JLabel("FontStyle:");
		//creating JComboBox to choose font style. JComboBox is more or less same as dropdown menu in html.
		String fonts[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//getting all availableFonts.
		fontStyleBox=new JComboBox(fonts);//passing availableFonts into constructor.
		fontStyleBox.addActionListener(this);//setting actionListener.
		fontStyleBox.setSelectedItem(defaultFont.getFontName());//default selected option will be defaultFontColor


		this.setJMenuBar(menuBar);//there wont be add() for adding menuBar, Only setting()
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontStyleLabel);
		this.add(fontStyleBox);
		this.add(scrollPane);
		this.setVisible(true);//setVisible() should always be atlast because the things that got added before this line cant be seen in output.
	}
	public void actionPerformed(ActionEvent e) 
	{
		//If fontColorButton is clicked, we show a colorChooser to set other Colors.
		if(e.getSource()==fontColorButton)
		{
			JColorChooser colorChooser=new JColorChooser();
			String title="Hello Human,Choose a Color:)";
			Color prevColor=Color.BLACK;
			//getting the color choosed from Dialog Window
			Color newColor=colorChooser.showDialog(null, title, prevColor);//showDialog(#1,#2,#3) -->#1.parentComponent, #2.title of colorChooser DialogBox, #3.defaultColor
			//setting the choosed color to fonts of TextArea
			textArea.setForeground(newColor);
			System.out.println("changed fontColor");
		}
		if(e.getSource()==fontStyleBox)
		{
			String prevFontStyle=(String)fontStyleBox.getSelectedItem();//as font will be same as selected option.
			int previousFontSize=textArea.getFont().getSize();
			textArea.setFont(new Font(prevFontStyle,Font.PLAIN,previousFontSize));
			System.out.println("changed the FontStyle");
		}
		if(e.getSource()==openItem)
		{
			System.out.println("opening file...");
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter=new FileNameExtensionFilter(".txt (or) .java files only,Dude:)", "txt", "java");//i.e, Only this type of files are allowed to Open.
			fileChooser.setFileFilter(filter);//adding this filter to fileChooser
			int response=fileChooser.showOpenDialog(null);
			if(response==JFileChooser.APPROVE_OPTION)
			{
				//response=0 means we opened a file succesfully.
				String pathOfOpenedFile=fileChooser.getSelectedFile().getAbsolutePath();
				File f=new File(pathOfOpenedFile);
				Scanner fileInput=null;
				try
				{
					fileInput=new Scanner(f);//Scanner reads from "f" file.
					if(f.isFile())//if "f" is legitimate File
					{
						while(fileInput.hasNextLine())
						{
							String currentLine=fileInput.nextLine()+"\n";//after each line, we are making cursor to point to start of nextLine.
							textArea.append(currentLine);//eachtime appending currentLine to textArea
						}
					}
				}
				catch(Exception exep)
				{
					exep.printStackTrace();
				}
				finally
				{
					fileInput.close();
				}
			}
		}
		if(e.getSource()==saveItem)
		{
			System.out.println("saving file...");
			String currentDirectory = System.getProperty("user.dir");//"user.dir" is key that has to be passed to getProperty().
			JFileChooser fileChooser=new JFileChooser();
			//fileChooser.setCurrentDirectory(currentDirectory);//this line and below line are equivalent.
			fileChooser.setCurrentDirectory(new File("."));//
			int response=fileChooser.showSaveDialog(null);	
			if(response==JFileChooser.APPROVE_OPTION)//final value of JFileChooser.APPROVE_OPTION=0
			{
				//0 means we found valid file location
				String pathForSavingFile=fileChooser.getSelectedFile().getAbsolutePath();//gives path selected by user from save Dialog Window
				File f=new File(pathForSavingFile);
				PrintWriter fileOut=null;
				try
				{
					fileOut=new PrintWriter(f);//we are accessing "f" file for writing into it.
					String textInTextArea=textArea.getText();
					fileOut.println(textInTextArea);//Anyways,fontStyle and fontColor wont be saved in resulted SavedFile.
				}
				catch(Exception excep)
				{
					excep.printStackTrace();
				}
				finally
				{
					fileOut.close();//closing PrintWriter Object
				}
			}

		}if(e.getSource()==exitItem)
		{
			//exiting the application
			System.exit(0);
		}
	}
}