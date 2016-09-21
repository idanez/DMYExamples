package my.smallProjects;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class HorarioPonto {
	
	private JFrame frame;
	private JTextField txtArrival;
	private JPanel panelSaida;
	private JLabel lblSaidaEntre;
	private JLabel lblDepartureInitial;
	private JLabel lblTimeToLeave;
	private JLabel lblDepartureFinal;
	private Date timeArrived;
	private Date timeToLeave;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private JLabel lblMessage;
	GregorianCalendar gc = new GregorianCalendar();
	private JLabel label;
	private JLabel label_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HorarioPonto window = new HorarioPonto();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	
	/**
	 * Create the application.
	 */
	public HorarioPonto() {
		initializeUI();
	}

	private void initializeUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 480, 199);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow][]", "[][][][grow]"));
		
		lblMessage = new JLabel("");		
		lblMessage.setForeground(Color.RED);
		frame.getContentPane().add(lblMessage, "cell 0 0 3 1");
		
		JLabel lblArrival = new JLabel("ENTRADA: ");
		lblArrival.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblArrival, "cell 0 1,alignx trailing");
		
		txtArrival = new JTextField();
		txtArrival.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtArrival.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				String text = txtArrival.getText();
				processUserInput(text);
			}
		});
		txtArrival.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				String text = txtArrival.getText();
				processUserInput(text);
			}
		});
		frame.getContentPane().add(txtArrival, "cell 1 1,grow");
		txtArrival.setColumns(10);
		
		JButton btnMark = new JButton("Marcar");
		btnMark.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				timeArrived = new Date();
				String nowAsString = sdf.format(timeArrived);
				txtArrival.setText(nowAsString);
				updateTimeToLeave(timeArrived);		
			}
		});
		frame.getContentPane().add(btnMark, "cell 2 1,growx,aligny center");
		
		panelSaida = new JPanel();
		frame.getContentPane().add(panelSaida, "cell 0 3 3 1,grow");
		panelSaida.setLayout(new MigLayout("", "[][grow][][][][grow]", "[]"));
		
		lblSaidaEntre = new JLabel("SAIDA:");
		lblSaidaEntre.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelSaida.add(lblSaidaEntre, "cell 0 0");
		
		lblDepartureInitial = new JLabel("00:00");
		lblDepartureInitial.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblDepartureInitial.setForeground(Color.BLUE);
		panelSaida.add(lblDepartureInitial, "cell 1 0,alignx center");
		
		label = new JLabel("-");
		label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSaida.add(label, "cell 2 0");
		
		lblTimeToLeave = new JLabel("00:00");
		lblTimeToLeave.setFont(new Font("Tahoma", Font.PLAIN, 46));
		panelSaida.add(lblTimeToLeave, "cell 3 0");
		
		label_1 = new JLabel("-");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSaida.add(label_1, "cell 4 0");
		
		lblDepartureFinal = new JLabel("00:00");
		lblDepartureFinal.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblDepartureFinal.setForeground(Color.BLUE);
		panelSaida.add(lblDepartureFinal, "cell 5 0,alignx center");
	}

	protected void processUserInput(final String text) {
		try {
			if(text != null && !text.isEmpty()) {
				timeArrived = sdf.parse(text);
				updateTimeToLeave(timeArrived);						
				lblMessage.setText("");
			}
		} catch (ParseException e1) {
			//e1.printStackTrace();
			lblMessage.setText("Formato de hora inválido. Deve ser: HH:mm");
		}
	}

	private void updateTimeToLeave(final Date timeArrived) {
		gc.setTime(timeArrived);
		gc.add(GregorianCalendar.HOUR, 9);
		gc.add(GregorianCalendar.MINUTE, 30);
		timeToLeave = gc.getTime();
		lblTimeToLeave.setText(sdf.format(timeToLeave));
		gc.clear();
		updateLabelDepartureInitial(timeArrived);
		updateLabelDepartureFinal(timeArrived);
	}
	
	protected void updateLabelDepartureFinal(final Date now) {
		gc.setTime(now);
		gc.add(GregorianCalendar.HOUR, 9);
		gc.add(GregorianCalendar.MINUTE, 20);
		Date time = gc.getTime();
		String initialAsString = sdf.format(time);
		lblDepartureInitial.setText(initialAsString);
		gc.clear();
		//setWarningTimer();
	}
	
	protected void updateLabelDepartureInitial(final Date now) {
		gc.setTime(now);
		gc.add(GregorianCalendar.HOUR, 9);
		gc.add(GregorianCalendar.MINUTE, 40);
		Date time = gc.getTime();
		String initialAsString = sdf.format(time);
		lblDepartureFinal.setText(initialAsString);
		gc.clear();
	}

	private void setWarningTimer() {
		Timer task = new Timer();
		task.schedule(new TimerTask() {					
			@Override
			public void run() {
				System.out.println("RODEI!!!");
			}
		}, timeToLeave);
	}
	
	public JLabel getLblDepartureInitial() {
		return lblDepartureInitial;
	}
	public JLabel getLblDepartureFinal() {
		return lblDepartureFinal;
	}
}
