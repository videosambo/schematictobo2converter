package fi.videosambo.schemtobo2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;

public class GUI extends JFrame {

	private JPanel contentPane;

	private File schemFile;
	private File bo2FilePath;
	
	static JCheckBox chckbxCopyAir;
	
	/**
	 * Launch the application.
	 */
	public static void showWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Schematic to bo2 converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		JLabel lblSchematicFile = new JLabel("Schematic File:");
		
		JLabel lblBoFile = new JLabel("bo2 File:");
		
		JEditorPane editorPane = new JEditorPane();
		
		
		JLabel lblTiedostoaEiValittu = new JLabel("File not selected");
		lblTiedostoaEiValittu.setForeground(Color.RED);
		
		JButton btnMuunna = new JButton("Convert");
		btnMuunna.setEnabled(false);
		btnMuunna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Converter converter = null;
				try {
					converter.convertToBO2(schemFile, bo2FilePath);
				} catch (Exception e) {
					lblTiedostoaEiValittu.setText("Error occupied");
					lblTiedostoaEiValittu.setForeground(Color.RED);
					editorPane.setText(e.getStackTrace().toString());
					editorPane.setForeground(Color.RED);
					return;
				}
				try {
					@SuppressWarnings("resource")
					Scanner reader = new Scanner(bo2FilePath);
					String data = "";
					while (reader.hasNextLine()) {
						data = data + reader.nextLine()+"\n";
					}
					editorPane.setText(data);
					editorPane.setForeground(Color.BLACK);
				} catch (FileNotFoundException e) {
					lblTiedostoaEiValittu.setText("Error occupied");
					lblTiedostoaEiValittu.setForeground(Color.RED);
					editorPane.setText(e.getStackTrace().toString());
					editorPane.setForeground(Color.RED);
					return;
				}
				lblTiedostoaEiValittu.setText("Success");
				lblTiedostoaEiValittu.setForeground(Color.GREEN);
			}
		});
		
		JButton btnValitseTiedosto = new JButton("Choose schematic");
		btnValitseTiedosto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = null;
				schemFile = main.chooseFile();
				lblSchematicFile.setText("Schematic File: " + schemFile.getName());
				if (bo2FilePath == null) {
					lblTiedostoaEiValittu.setText("No file path");
					lblTiedostoaEiValittu.setForeground(Color.RED);
				} else {
					lblTiedostoaEiValittu.setText("File choosen");
					lblTiedostoaEiValittu.setForeground(Color.GREEN);
					btnMuunna.setEnabled(true);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnTallennusSijainti = new JButton("Save to file");
		btnTallennusSijainti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = null;
				bo2FilePath = main.fileSaver();
				if (schemFile == null) {
					lblTiedostoaEiValittu.setText("File not selected");
					lblTiedostoaEiValittu.setForeground(Color.RED);
				} else {
					lblTiedostoaEiValittu.setText("File path choosen");
					lblTiedostoaEiValittu.setForeground(Color.GREEN);
					btnMuunna.setEnabled(true);
				}
				lblBoFile.setText("bo2 File: " + bo2FilePath.getName());
			}
		});
		
		chckbxCopyAir = new JCheckBox("Copy air");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnTallennusSijainti, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblTiedostoaEiValittu, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
								.addComponent(btnValitseTiedosto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnMuunna, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSchematicFile, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
										.addComponent(lblBoFile, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(68)
									.addComponent(chckbxCopyAir))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnValitseTiedosto)
						.addComponent(lblSchematicFile))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBoFile)
						.addComponent(btnTallennusSijainti))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTiedostoaEiValittu)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMuunna))
						.addComponent(chckbxCopyAir))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
					.addGap(12))
		);
		
		scrollPane.setViewportView(editorPane);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static boolean copyAir() {
		return chckbxCopyAir.isSelected();
	}
}
