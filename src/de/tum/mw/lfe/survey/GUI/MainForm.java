package de.tum.mw.lfe.survey.GUI;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.plaf.OptionPaneUI;

import de.tum.mw.lfe.survey.utils.ExcelFileFilter;
import de.tum.mw.lfe.survey.utils.ExcelFileHandler;
import de.tum.mw.lfe.survey.utils.SubTask;
import de.tum.mw.lfe.survey.utils.SurveyTask;
import de.tum.mw.lfe.survey.utils.Utilities;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import jxl.read.biff.BiffException;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;

public class MainForm extends JPanel{

	private JFrame frmSurveyPostProcessor;
	private JFileChooser fc;
	private String xmlFileName = "xml/ExcelColumns.xml";
	private ArrayList<SurveyTask> tasks = new ArrayList<SurveyTask>();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTabbedPane tabbedPane;
	private JPanel panel[];
	private JLabel lblTotalnumberofusers;
	private JLabel lblCurrentuser;
	private JButton btnNextUser;
	private JButton btnPrevUser;
	private int currentUserIndex=0;
	private int totalNumberOfUsers=0;
	private File file;
	private ExcelFileHandler handler;
	private JButton btnSaveFile;
	private JButton btnUpdateExistingFile;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmSurveyPostProcessor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSurveyPostProcessor = new JFrame();
		frmSurveyPostProcessor.setTitle("Survey post processor");
		frmSurveyPostProcessor.setResizable(false);
		frmSurveyPostProcessor.setBounds(100, 100, 401, 332);
		frmSurveyPostProcessor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		if(!de.tum.mw.lfe.survey.utils.Utilities.readXML(xmlFileName,tasks))
		{
			JOptionPane.showMessageDialog(frmSurveyPostProcessor, "Error, Can not read "+ xmlFileName);
			System.exit(ERROR);
		}
		
		System.out.println(tasks);
		
	    //Create a file chooser
        fc = new JFileChooser();
        fc.setFileFilter(new ExcelFileFilter());
 
		
		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpenFile.setBounds(188, 11, 89, 23);
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		           int returnVal = fc.showOpenDialog(MainForm.this);
		           
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                file = fc.getSelectedFile();
		                //This is where a real application would open the file.
		                JOptionPane.showMessageDialog(frmSurveyPostProcessor, "Opening: " + file.getName());
		                btnSaveFile.setEnabled(true);
		             //   btnUpdateExistingFile.setEnabled(true);
		                try {
		                	initData(file);
							populateData(file);
						} catch (BiffException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
			}
		});
		frmSurveyPostProcessor.getContentPane().setLayout(null);
		frmSurveyPostProcessor.getContentPane().add(btnOpenFile);
		
		JLabel lblPleaseChooseExcel = new JLabel("Please choose source file: (*.xls)");
		lblPleaseChooseExcel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPleaseChooseExcel.setBounds(10, 15, 267, 14);
		frmSurveyPostProcessor.getContentPane().add(lblPleaseChooseExcel);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					populateData(file);
				} catch (BiffException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		tabbedPane.setBounds(10, 45, 375, 206);
		frmSurveyPostProcessor.getContentPane().add(tabbedPane);
		tabbedPane.setEnabled(false);
		
		btnNextUser = new JButton("Next user");
		btnNextUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnPrevUser.setEnabled(true);
				currentUserIndex++;
				lblCurrentuser.setText(Integer.toString(currentUserIndex));
				if(currentUserIndex>=totalNumberOfUsers)
					btnNextUser.setEnabled(false);
				try {
					populateData(file);
				} catch (BiffException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNextUser.setHorizontalAlignment(SwingConstants.LEFT);
		btnNextUser.setEnabled(false);
		btnNextUser.setBounds(286, 262, 99, 36);
		frmSurveyPostProcessor.getContentPane().add(btnNextUser);
		
		btnPrevUser = new JButton("Prev user");
		btnPrevUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNextUser.setEnabled(true);
				currentUserIndex--;
				lblCurrentuser.setText(Integer.toString(currentUserIndex));
				if(currentUserIndex<=1)
					btnPrevUser.setEnabled(false);
				try {
					populateData(file);
				} catch (BiffException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPrevUser.setEnabled(false);
		btnPrevUser.setBounds(10, 262, 107, 36);
		frmSurveyPostProcessor.getContentPane().add(btnPrevUser);
		
		//lblCurrentuser = new JLabel("currentUser");
		lblCurrentuser = new JLabel("0");
		lblCurrentuser.setBounds(130, 273, 79, 14);
		frmSurveyPostProcessor.getContentPane().add(lblCurrentuser);
		
		JLabel label = new JLabel("/");
		label.setBounds(188, 273, 58, 14);
		frmSurveyPostProcessor.getContentPane().add(label);
		
		//lblTotalnumberofusers = new JLabel("totalNumberOfUsers");
		lblTotalnumberofusers = new JLabel("0");
		lblTotalnumberofusers.setBounds(219, 273, 58, 14);
		frmSurveyPostProcessor.getContentPane().add(lblTotalnumberofusers);
		
		btnSaveFile = new JButton("Save New file");
		btnSaveFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSaveFile.setEnabled(false);
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showSaveDialog(MainForm.this);
		           
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File newfile = fc.getSelectedFile();
	                //This is where a real application would open the file.
	                JOptionPane.showMessageDialog(frmSurveyPostProcessor, newfile.getName() + " saved");
	                try {
						handler.saveNewFile(newfile,tasks);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	               
	            }
			}
		});
		btnSaveFile.setBounds(278, 11, 107, 23);
		frmSurveyPostProcessor.getContentPane().add(btnSaveFile);
		
		btnUpdateExistingFile = new JButton("Update Exisitng file");
		btnUpdateExistingFile.setEnabled(false);
		btnUpdateExistingFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdateExistingFile.setBounds(314, 11, 51, 23);
		frmSurveyPostProcessor.getContentPane().add(btnUpdateExistingFile);
		
		panel = new JPanel[tasks.size()];
				
		for (int i = 0;i<tasks.size();i++)
		{
			panel[i] = new JPanel();
			
			//JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab(tasks.get(i).getName(), null, panel[i], null);
			panel[i].setLayout(null);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setEnabled(false);
			comboBox.setBounds(10, 11, 322, 20);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						UpdateTextFields(file);
					} catch (BiffException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}});
			panel[i].add(comboBox);
			
			JLabel lblOfCharacters = new JLabel("# of characters");
			lblOfCharacters.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblOfCharacters.setBounds(10, 58, 107, 14);
			panel[i].add(lblOfCharacters);
			
			JLabel lblTime = new JLabel("Time In seconds");
			lblTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblTime.setBounds(10, 88, 107, 14);
			panel[i].add(lblTime);
			
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(113, 55, 86, 20);
			panel[i].add(textField);
			textField.setColumns(10);
			
			textField_1 = new JTextField();
			textField_1.setEditable(false);
			textField_1.setBounds(113, 85, 86, 20);
			panel[i].add(textField_1);
			textField_1.setColumns(10);
			
			JLabel lblOfErrors = new JLabel("# of errors");
			lblOfErrors.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblOfErrors.setBounds(10, 118, 75, 14);
			panel[i].add(lblOfErrors);
			
			textField_2 = new JTextField();
			textField_2.setEditable(false);
			textField_2.setBounds(113, 115, 86, 20);
			panel[i].add(textField_2);
			textField_2.setColumns(10);

		}
	}

	protected void UpdateTextFields(File file2) throws BiffException, IOException {
		// TODO Auto-generated method stub
		if(handler!=null&&handler.openFile(file))
		{
			ArrayList<SubTask> subTasksList = tasks.get(tabbedPane.getSelectedIndex()).getSubTasks();
			UpdateTextFields(subTasksList);
			
			
		}

	}

	protected void initData(File file2) throws BiffException, IOException {
		// TODO Auto-generated method stub
		handler = new ExcelFileHandler();
		if(handler.openFile(file))
		{
			totalNumberOfUsers = handler.getNumberOfUsers(file);
			if(totalNumberOfUsers>0)
				currentUserIndex=1;
			lblTotalnumberofusers.setText(new Integer(totalNumberOfUsers).toString());
			lblCurrentuser.setText("1");
			if(totalNumberOfUsers>1)
				btnNextUser.setEnabled(true);
			tabbedPane.setEnabled(true);
		}

		ArrayList<SubTask> subTasksList;
		for(int i=0;i<tasks.size();i++)
		{
			subTasksList = tasks.get(i).getSubTasks();
			handler.FillSubTasksData(file,subTasksList,currentUserIndex);
		}
		
	}

	private void populateData(File file) throws BiffException, IOException {
		// TODO Auto-generated method stub
		if(handler!=null&&handler.openFile(file))
		{
			ArrayList<SubTask> subTasksList = tasks.get(tabbedPane.getSelectedIndex()).getSubTasks();
			String taskStrings[] = new String[subTasksList.size()];
			handler.FillSubTasksData(file,subTasksList,currentUserIndex);
			for(int i=0; i<subTasksList.size();i++)
			{
				//taskStrings[i] = "Sub task "+ i+1;
				taskStrings[i] = subTasksList.get(i).getName();
			}
			
			DefaultComboBoxModel aModel =new DefaultComboBoxModel(taskStrings);
			JComboBox combo = ((JComboBox)panel[tabbedPane.getSelectedIndex()].getComponent(0));
			combo.setModel(aModel);
			combo.setEnabled(true);
			
			UpdateTextFields(subTasksList);
			
			
		}
	}

	private void UpdateTextFields(ArrayList<SubTask> subTasksList) {
		// TODO Auto-generated method stub
		JComboBox combo = ((JComboBox)panel[tabbedPane.getSelectedIndex()].getComponent(0));

		((JTextField)panel[tabbedPane.getSelectedIndex()].getComponent(3)).setText(new Integer(subTasksList.get(combo.getSelectedIndex()).getSubTaskAnswer().length()).toString());
		((JTextField)panel[tabbedPane.getSelectedIndex()].getComponent(4)).setText(new Double(subTasksList.get(combo.getSelectedIndex()).getTime()).toString());
		//calculate number of errors
		int NumOfErrors = Utilities.StringMismatchCounter(subTasksList.get(combo.getSelectedIndex()).getOriginalSubTaskText(),subTasksList.get(combo.getSelectedIndex()).getSubTaskAnswer());
		((JTextField)panel[tabbedPane.getSelectedIndex()].getComponent(6)).setText(new Integer(NumOfErrors).toString());
		
	}
}
