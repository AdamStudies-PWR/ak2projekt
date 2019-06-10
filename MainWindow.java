package lock;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private GroupofLocks glock = new GroupofLocks();
	
	private static final String ABOUT_SCREEN =
			"Program - obsługa zdalnego zamka\n"+
			"Autor - Adam Krizar & Katarzyna Czajkowska\n"+
			"Data - marzec 2019\n"+
			"Wersja alpha 0.2";
	
	Color color = new Color(20, 60, 80);
	Font font = new Font("Impact", Font.PLAIN, 12);
	Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	
	JMenuBar bar = new JMenuBar();
	JMenu mainmenu = new JMenu("Menu");
	JMenuItem aboutItem = new JMenuItem("O aplikacji");
	JMenuItem exitItem = new JMenuItem("Wyjdź");
	JMenu lockmenu = new JMenu("Opcje");
	JMenuItem passwordItem = new JMenuItem("Zmień hasło");
	JMenuItem closeItem = new JMenuItem("Zamknij zamek");
	JMenuItem showItem = new JMenuItem("Pokaż hasło");
	JMenuItem connectionItem = new JMenuItem("Zakończ połączenie");
	JMenuItem addItem = new JMenuItem("Dodaj zamek");
	JMenuItem saveItem = new JMenuItem("Zapisz");
	JMenuItem loadItem = new JMenuItem("Wczytaj");
	JMenuItem deleteItem = new JMenuItem("Usuń zamek");
	
	JLabel selectLabel = new JLabel("Zamek:");		
	JComboBox<String> lockBox;
	JLabel passLabel = new JLabel("Hasło:");
	JTextField passField = new JTextField();
	JLabel openLabel = new JLabel("Status:");
	JTextField openField = new JTextField("Nie znany");
	JLabel connectionLabel = new JLabel("Połączenie:");
	JTextField connectionField = new JTextField();
	
	JButton refreshButton = new JButton("Odśwież");
	JButton showButton = new JButton("Pokaż hasło");
	JButton connectionButton = new JButton("Połącz");
	JButton openButton = new JButton("Otwórz");
	
	private void placeContent()
	{
		bar.setBounds(0, 0, 380, 20);
		selectLabel.setBounds(10, 30, 50, 20);
		lockBox.setBounds(90, 30, 150, 20);
		refreshButton.setBounds(250, 30, 120, 20);
		passLabel.setBounds(10, 60, 50, 20);
		passField.setBounds(90, 60, 150, 20);
		showButton.setBounds(250, 60, 120, 20);
		connectionLabel.setBounds(10, 90, 70, 20);
		openLabel.setBounds(10, 120, 50, 20);
		connectionField.setBounds(90, 90, 150, 20);
		openField.setBounds(90, 120, 150, 20);
		connectionButton.setBounds(250, 90, 120, 20);
		openButton.setBounds(250, 120, 120, 20);
	}
	
	private void updateBox()
	{
		lockBox = new JComboBox<String>();
		String tab[] = glock.toStringAr();
		for(String str: tab)
		{
			lockBox.addItem(str);
		}
	}
	
	MainWindow()
	{
		try 
		{
			glock.readObject();
		} 
		catch (Exception error) 
		{
			JOptionPane.showMessageDialog(this, "Nie znaleziono zamków do wczytania");
		}
		updateBox();
		mainmenu.setCursor(cursor);
		aboutItem.setCursor(cursor);
		exitItem.setCursor(cursor);
		lockmenu.setCursor(cursor);
		passwordItem.setCursor(cursor);
		closeItem.setCursor(cursor);
		showItem.setCursor(cursor);
		connectionItem.setCursor(cursor);
		addItem.setCursor(cursor);
		saveItem.setCursor(cursor);
		loadItem.setCursor(cursor);
		lockBox.setCursor(cursor);
		refreshButton.setCursor(cursor);
		deleteItem.setCursor(cursor);
		showButton.setCursor(cursor);
		openButton.setCursor(cursor);
		connectionButton.setCursor(cursor);
		
		mainmenu.setFont(font);
		aboutItem.setFont(font);
		exitItem.setFont(font);
		lockmenu.setFont(font);
		passwordItem.setFont(font);
		closeItem.setFont(font);
		showItem.setFont(font);
		connectionItem.setFont(font);
		addItem.setFont(font);
		saveItem.setFont(font);
		loadItem.setFont(font);
		selectLabel.setFont(font);
		lockBox.setFont(font);
		refreshButton.setFont(font);
		deleteItem.setFont(font);
		passLabel.setFont(font);
		showButton.setFont(font);
		openLabel.setFont(font);
		connectionLabel.setFont(font);
		connectionButton.setFont(font);
		openButton.setFont(font);
		
		setTitle("Lock Manager 2k19");
		setFont(font);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380,180);
		setLocationRelativeTo(null);
		setResizable(false);
		
		selectLabel.setForeground(Color.WHITE);
		passLabel.setForeground(Color.WHITE);
		openLabel.setForeground(Color.WHITE);
		connectionLabel.setForeground(Color.WHITE);
		connectionField.setBackground(Color.RED);
		openField.setBackground(Color.YELLOW);
		
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		passwordItem.addActionListener(this);
		closeItem.addActionListener(this);
		showItem.addActionListener(this);
		connectionItem.addActionListener(this);
		addItem.addActionListener(this);
		saveItem.addActionListener(this);
		loadItem.addActionListener(this);
		refreshButton.addActionListener(this);
		deleteItem.addActionListener(this);
		showButton.addActionListener(this);
		connectionButton.addActionListener(this);
		openButton.addActionListener(this);
		
		connectionField.setEnabled(false);
		openField.setEnabled(false);
		JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setLayout(null);
		
		panel.add(showButton);
		panel.add(connectionLabel);
		panel.add(connectionField);
		panel.add(openLabel);
		panel.add(openField);
		panel.add(bar);
		panel.add(selectLabel);
		panel.add(lockBox);
		panel.add(refreshButton);
		panel.add(passLabel);
		panel.add(passField);
		panel.add(connectionButton);
		panel.add(openButton);
		bar.add(mainmenu);
		bar.add(lockmenu);
		mainmenu.add(aboutItem);
		mainmenu.add(exitItem);
		lockmenu.add(addItem);
		lockmenu.add(deleteItem);
		lockmenu.addSeparator();
		lockmenu.add(passwordItem);
		lockmenu.add(closeItem);
		lockmenu.add(showItem);
		lockmenu.addSeparator();
		lockmenu.add(saveItem);
		lockmenu.add(loadItem);
		lockmenu.addSeparator();
		lockmenu.add(connectionItem);
		
		placeContent();
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent eventSource) 
	{
		Object event = eventSource.getSource();
		if(event == exitItem) System.exit(0);
		if(event == aboutItem) JOptionPane.showMessageDialog(this, ABOUT_SCREEN);
		if(event == saveItem) 
		{
				try 
				{
					glock.writeObject();
				} 
				catch (Exception error) 
				{
					JOptionPane.showMessageDialog(this, "Wystąpił błąd");
				}
		}
		if(event == loadItem) 
		{
				try 
				{
					glock.readObject();
				} 
				catch (Exception error) 
				{
					JOptionPane.showMessageDialog(this, "Wystąpił błąd");
				}
		}
		if(event == refreshButton) updateBox();
		if(event == deleteItem)
		{
			int i =  lockBox.getSelectedIndex();
			glock.removeLock(i);
		}
	}
}
