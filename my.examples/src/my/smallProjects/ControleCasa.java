package my.smallProjects;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;


public class ControleCasa {

	private JFrame frame;
	private JTextField txtTotal;
	private JTextField txtSal1;
	private JTextField txtSal2;
	private JSlider slider;
	private JLabel valorSal1;
	private JLabel valorSal2;
	private JLabel valorTotal;
	private JLabel lblPorcentagem;
	
	Double multiplier = 0.01;
	int multiplierDecimals = 2;
	DecimalFormat df = new DecimalFormat("#.00");
	private JLabel lblNewLabel;
	private JTextField txtPorcentagem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControleCasa window = new ControleCasa();
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
	public ControleCasa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 654, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][][][][]"));

		JLabel lblValorTotalR = new JLabel("Valor Total: R$");
		frame.getContentPane().add(lblValorTotalR, "cell 0 0,alignx trailing");

		txtTotal = new JTextField();
		txtTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calcularValoresAutomaticamente();
			}
		});
		frame.getContentPane().add(txtTotal, "cell 1 0,growx");
		txtTotal.setColumns(10);

		JLabel lblSal = new JLabel("Sal 1:");
		frame.getContentPane().add(lblSal, "cell 0 2,alignx trailing");

		txtSal1 = new JTextField();
		frame.getContentPane().add(txtSal1, "cell 1 2,growx");
		txtSal1.setColumns(10);
		txtSal1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calcularValoresAutomaticamente();
			}
		});

		JLabel lblSal_1 = new JLabel("Sal 2:");
		frame.getContentPane().add(lblSal_1, "cell 0 4,alignx trailing");

		txtSal2 = new JTextField();
		frame.getContentPane().add(txtSal2, "cell 1 4,growx");
		txtSal2.setColumns(10);
		txtSal2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calcularValoresAutomaticamente();
			}
		});
		
		lblNewLabel = new JLabel("Porcentagem (%): ");
		frame.getContentPane().add(lblNewLabel, "cell 0 6,alignx trailing");
		
		txtPorcentagem = new JTextField();
		frame.getContentPane().add(txtPorcentagem, "cell 1 6,growx");
		txtPorcentagem.setColumns(10);
		txtPorcentagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				String text = txtPorcentagem.getText();
				
				double parsedValue = 0d;
				
				try {
					parsedValue = Double.parseDouble(text);										
				} catch (NumberFormatException e) {}
				
				Double withoutComma = parsedValue * 100;								
				slider.setValue(withoutComma.intValue());					
				calcularValoresPorPorcentagem(parsedValue, false);
			}			
		});

		lblPorcentagem = new JLabel("");
		frame.getContentPane().add(lblPorcentagem, "cell 0 8");
		slider = new JSlider();
		slider.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent arg0) {}
			
			public void mouseDragged(MouseEvent arg0) {
				JSlider sl = (JSlider)arg0.getSource();	
				
				int intValue = sl.getValue();
				Double doubleValue = intValue * multiplier;												
				//System.out.println(porcentagem + "%");
												
				calcularValoresPorPorcentagem(doubleValue, true);
				
			}
		});
	
		slider.setValue(0);
		slider.setMaximum(10000);
		slider.setExtent(1);
		
		frame.getContentPane().add(slider, "cell 0 7 2 1,grow");

		JLabel lblValSal = new JLabel("Val Sal1:");
		frame.getContentPane().add(lblValSal, "cell 0 10");

		valorSal1 = new JLabel("");
		frame.getContentPane().add(valorSal1, "cell 1 10");

		JLabel lblValSal_1 = new JLabel("Val Sal2:");
		frame.getContentPane().add(lblValSal_1, "cell 0 11");

		valorSal2 = new JLabel("");
		frame.getContentPane().add(valorSal2, "cell 1 11");

		JLabel lblTotal = new JLabel("Total: ");
		frame.getContentPane().add(lblTotal, "cell 0 12");

		valorTotal = new JLabel("");
		frame.getContentPane().add(valorTotal, "cell 1 12");
		
	}

	protected void calcularValoresAutomaticamente() {
			
		if(txtTotal.getText().trim().length() > 0 && txtSal1.getText().trim().length() > 0 && txtSal2.getText().trim().length() > 0)
		{						
			Double sal1 = 0d;
			Double sal2 = 0d;
			Double total = 0d;
			
			try{
				total = Double.parseDouble(txtTotal.getText());
			} catch(NumberFormatException e){}
						
			int loopCount = (int) Math.pow(100, multiplierDecimals);
			
			for(int count = 1; count <= loopCount; count++) {
								
				Double porc = count * multiplier;							
								
				//System.out.println(df.format(porc));
				
				try{
					sal1 = Double.parseDouble(txtSal1.getText());
					sal2 = Double.parseDouble(txtSal2.getText());
				} catch (NumberFormatException e) {}
				
				Double vlCalculadoSal1 = calcValue(sal1, porc);
				Double vlCalculadoSal2 = calcValue(sal2, porc);
				
				//limita o número de casas decimais     
				String sal1AsString = df.format(vlCalculadoSal1);
				String sal2AsString = df.format(vlCalculadoSal2);
				
				Double vlCalculadoTotal = vlCalculadoSal1 + vlCalculadoSal2;
								
				//System.out.println("V1: "+vlCalculadoSal1+" V2: "+vlCalculadoSal2+" VT: "+vlCalculadoTotal+" TOTAL: "+total);										
								
				if(vlCalculadoTotal.equals(total) || (vlCalculadoTotal > total && ( isInRange(total, vlCalculadoTotal,100) ))) {
					
					valorSal1.setText(sal1AsString);
					valorSal2.setText(sal2AsString);
					
					setLblValorTotalText(vlCalculadoTotal, total);
					
//					String totalAsString = df.format(vlCalculadoTotal);
//					
//					if(vlCalculadoTotal < total)
//						valorTotal.setText("<HTML><BODY COLOR=RED>"+totalAsString+"</BODY></HTML>");
//					else
//						valorTotal.setText("<HTML><BODY COLOR=GREEN>"+totalAsString+"</BODY></HTML>");
															
					slider.setValue(count);
					String formatted = df.format(porc);
					txtPorcentagem.setText(formatted);
					setLblPorcentagemText(porc);
					
					break;
					
				}
				
			}


		}

	}

	private boolean isInRange(Double total, Double vlCalculadoTotal, int range) {
		
		Double vlCalcMais = total + range;
						
		return vlCalculadoTotal <= vlCalcMais;
	}
	
	private void setLblValorTotalText(Double vlCalculadoTotal, Double total) {
		
		String totalAsString = df.format(vlCalculadoTotal);
		
		if (vlCalculadoTotal == total && total > -1) {
			valorTotal.setText(totalAsString);
		} else if(vlCalculadoTotal < total) {
			valorTotal.setText("<HTML><BODY COLOR=RED>"+totalAsString+"</BODY></HTML>");
		} else {
			valorTotal.setText("<HTML><BODY COLOR=GREEN>"+totalAsString+"</BODY></HTML>");
		}
	}

	private void setLblPorcentagemText(Double porcentagem) {
		String formatedValue = df.format(porcentagem);
		lblPorcentagem.setText(formatedValue+"%");	
	}
	
	protected void calcularValoresPorPorcentagem(Double porcentagem, boolean updatePorcentText) {

		if(txtTotal.getText().trim().length() > 0 && txtSal1.getText().trim().length() > 0 && txtSal2.getText().trim().length() > 0)
		{
			Double sal1 = 0d;
			Double sal2 = 0d;
			
			try{
				sal1 = Double.parseDouble(txtSal1.getText());
				sal2 = Double.parseDouble(txtSal2.getText());
			} catch (NumberFormatException e) {}

			sal1 = calcValue(sal1, porcentagem);//(sal1*porcentagem) / 100;
			sal2 = calcValue(sal2, porcentagem);

			String sal1AsString = df.format(sal1);
			String sal2AsString = df.format(sal2);
						
			valorSal1.setText(sal1AsString);
			valorSal2.setText(sal2AsString);

			Double soma = sal1+sal2;						
			Double total = -1d;
			
			try{
				total = Double.parseDouble(txtTotal.getText());
			} catch(NumberFormatException e) {}
			
			setLblValorTotalText(soma, total);
			//this.valorTotal.setText(somaAsString);
			
			setLblPorcentagemText(porcentagem);			
			
			if(updatePorcentText) {
				String formatedValue = df.format(porcentagem);
				txtPorcentagem.setText(String.valueOf(formatedValue));
			}

		}

	}

	private Double calcValue(Double valor, Double porc) {
		return (valor*porc) / 100;
	}
		
	public JSlider getSlider() {
		return slider;
	}
	public JLabel getLblLblvalorsal() {
		return valorSal1;
	}
	public JLabel getLblLblvalorsal_1() {
		return valorSal2;
	}
	public JLabel getLblLblvalortotal() {
		return valorTotal;
	}
	public JTextField getTextField() {
		return txtTotal;
	}
	public JTextField getTextField_1() {
		return txtSal1;
	}
	public JTextField getTextField_2() {
		return txtSal2;
	}
}
