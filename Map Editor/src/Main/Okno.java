package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Panely.MapPanel;
import Panely.TilePanel;
import Panely.updateMap.UpdateMap;


/**
 * trieda hlavneho okna v ktorom a vsetko zobrazuje
 * @author Daniel
 *
 */
@SuppressWarnings("serial")
public class Okno extends JFrame{
	
	private JMenuBar menu;
	private JMenu file;
	private JMenu help;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem show;
	private JMenuItem clear;
	private JButton plusWidth;
	private JButton minusWidth;
	private JButton plusHeight;
	private JButton minusHeight;
	private JTextField mapWidth;
	private JTextField mapHeight;
	
	
	private JLabel tileSetLabel;
	private JLabel mapLabel;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	
	private JPanel mainPanel;
	private MapPanel mapPanel;
	private TilePanel tilePanel;
	
	public Okno() {
		super("Map Editor");
		
		setContentPane(getMainPanel());
		setMenu();
		setWindow();
		updateMapLabels();
	}
	
	
	private void setMenu() {
		menu = new JMenuBar();
		file = new JMenu(" File ");
		help = new JMenu(" Help ");
		open = new JMenuItem(" Open map ");
		save = new JMenuItem(" Save map ");
		clear = new JMenuItem(" Clear map ");
		show = new JMenuItem(" Show help ");
		
		
		setJMenuBar(menu);
		file.add(open);
		file.add(save);
		file.add(clear);
		help.add(show);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = JOptionPane.showInputDialog(null, "Save file name", "Save Map", 1);
					if(name == null) return;
					BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("Resources/" + name + ".map")));
					bw.write(mapPanel.getMapWidth() + "\n");
					bw.write(mapPanel.getMapHeight() + "\n");
					for(int row = 0; row < mapPanel.getMapHeight(); row++) {
						for(int col = 0; col < mapPanel.getMapWidth(); col++) {
							bw.write(mapPanel.getMap()[row][col] + " ");
						}
						bw.write("\n");
					}
					bw.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String name = JOptionPane.showInputDialog(null, "Map name", "Open Map", 1);
					if(name == null) return;
					BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(("/" + name))));
					mapPanel.setMapWidth(Integer.parseInt(br.readLine()));
					mapPanel.setMapHeight(Integer.parseInt(br.readLine()));
					for(int i=0;i<mapPanel.getMapHeight();i++) {
						String[] temp = br.readLine().split(" ");
						for(int j=0;j<mapPanel.getMapWidth();j++) {
							mapPanel.getMap()[i][j] = Integer.parseInt(temp[j]);
						}
					}
					br.close();
				}catch (Exception ex) {
					
				}
			}
		});
		
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear map?");
				if(i == 0)
					mapPanel.clearMap();
			}
			
		});
		
		menu.add(file);
		menu.add(help);
	}
	
	private void setWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1350, 900));
		setVisible(true);
	}
	
	
	private JPanel getMainPanel() {
		
		mainPanel = new JPanel(new GridBagLayout());
		mapPanel = new MapPanel();
		tilePanel = new TilePanel();
		
		mainPanel.setBackground(new Color(236,236,236));
		GridBagConstraints pos = new GridBagConstraints();
		
		tileSetLabel = new JLabel("Tile Set");
		tileSetLabel.setFont(new Font("Serif", Font.PLAIN, 32));
		mapLabel = new JLabel("MAPA");
		mapLabel.setFont(new Font("Serif", Font.BOLD, 32));
		mapWidthLabel = new JLabel("Map width: ");
		tileSetLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		mapHeightLabel = new JLabel("Map height: ");
		tileSetLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		
		plusWidth = new JButton("+");
		minusWidth= new JButton("-");
		plusHeight = new JButton("+");
		minusHeight = new JButton("-");
		mapWidth = new JTextField(3);
		mapHeight = new JTextField(3);
		
		
		
		pos.gridx = 0;
		pos.gridy = 0;
		pos.weightx = 0.2;
		pos.weighty = 0.1;
		pos.insets = new Insets(15,0,0,0);
		pos.anchor = GridBagConstraints.NORTH;
		mainPanel.add(tileSetLabel,pos);
		pos.gridx = 1;
		pos.weightx = 0.8;
		mainPanel.add(mapLabel,pos);
		pos.gridx = 0;
		pos.gridy = 1;
		pos.weightx = 0.4;
		pos.weighty = 0.7;
		pos.insets = new Insets(0,0,0,0);
		mainPanel.add(tilePanel,pos);
		pos.gridx = 1;
		pos.weightx = 0.6;
		mainPanel.add(mapPanel,pos);
		pos.gridx = 1;
		pos.gridy = 2;
		pos.weighty = 0.4;
		pos.insets = new Insets(0,0,50,0);
		JPanel p = new JPanel(new FlowLayout());
		p.setBackground(new Color(236,236,236));
		p.add(mapWidthLabel);
		p.add(minusWidth);
		p.add(mapWidth);
		p.add(plusWidth);
		p.add(mapHeightLabel);
		p.add(minusHeight);
		p.add(mapHeight);
		p.add(plusHeight);
		mainPanel.add(p, pos);
		mainPanel.add(p, pos);
		
		
		minusWidth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent Ae) {
				if(mapPanel.getMapWidth() == 0) return;
				mapPanel.updateMap(UpdateMap.MINUS_WIDTH);
				updateMapLabels();
			}
			
		});
		plusWidth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent Ae) {
				mapPanel.updateMap(UpdateMap.PLUS_WIDTH);
				updateMapLabels();
			}
		});
		minusHeight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent Ae) {
				if(mapPanel.getMapHeight() == 0) return;
				mapPanel.updateMap(UpdateMap.MINUS_HEIGHT);
				updateMapLabels();
			}
		});
		plusHeight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent Ae) {
				mapPanel.updateMap(UpdateMap.PLUS_HEIGHT);
				updateMapLabels();
			}
		});
		return mainPanel;
	}
	
	private void updateMapLabels() {
		mapWidth.setText(Integer.toString(mapPanel.getMapWidth()));
		mapHeight.setText(Integer.toString(mapPanel.getMapHeight()));
	}
	
}