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


public class ControlePorPorcentagemDeSalario {

	private JFrame frame;
	private JTextField txtTotal;
	private JTextField txtSalCa;
	private JTextField txtSalDan;
	private JTextField txtContas;
	private JTextField txtPrestAp;
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
	private JLabel depositarCa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlePorPorcentagemDeSalario window = new ControlePorPorcentagemDeSalario();
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
	public ControlePorPorcentagemDeSalario() {
		initializeUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeUI() {
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
				try{
					Double total = Double.parseDouble(txtTotal.getText());
					calcular(total);
				} catch(NumberFormatException e){}				
			}
		});
		frame.getContentPane().add(txtTotal, "cell 1 0,growx");
		txtTotal.setColumns(10);

		JLabel lblSalCa = new JLabel("Sal Ca:");
		frame.getContentPane().add(lblSalCa, "cell 0 2,alignx trailing");

		txtSalCa = new JTextField();
		frame.getContentPane().add(txtSalCa, "cell 1 2,growx");
		txtSalCa.setColumns(10);
		txtSalCa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					Double total = Double.parseDouble(txtTotal.getText());
					calcular(total);
				} catch(NumberFormatException e){}
			}
		});

		JLabel lblSal_1 = new JLabel("Sal Dan:");
		frame.getContentPane().add(lblSal_1, "cell 0 4,alignx trailing");

		txtSalDan = new JTextField();
		frame.getContentPane().add(txtSalDan, "cell 1 4,growx");
		txtSalDan.setColumns(10);
		txtSalDan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					Double total = Double.parseDouble(txtTotal.getText());
					calcular(total);
				} catch(NumberFormatException e){}
			}
		});
		
		JLabel lblContas = new JLabel("Total das contas:");
		frame.getContentPane().add(lblContas, "cell 0 6,alignx trailing");

		txtContas = new JTextField();
		frame.getContentPane().add(txtContas, "cell 1 6,growx");
		txtContas.setColumns(10);
		txtContas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calcularDeposito();
			}
		});
		
		JLabel lblPrestAp = new JLabel("Prestação Ap:");
		frame.getContentPane().add(lblPrestAp, "cell 0 8,alignx trailing");

		txtPrestAp = new JTextField();
		frame.getContentPane().add(txtPrestAp, "cell 1 8,growx");
		txtPrestAp.setColumns(10);
		txtPrestAp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calcularDeposito();
			}
		});
		
		lblNewLabel = new JLabel("Porcentagem (%): ");
		frame.getContentPane().add(lblNewLabel, "cell 0 10,alignx trailing");
		
		txtPorcentagem = new JTextField();
		frame.getContentPane().add(txtPorcentagem, "cell 1 10,growx");
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
		frame.getContentPane().add(lblPorcentagem, "cell 0 12");
		
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
		
		frame.getContentPane().add(slider, "cell 0 11 2 1,grow");

		JLabel lblValSal = new JLabel("Val Sal1:");
		frame.getContentPane().add(lblValSal, "cell 0 12");

		valorSal1 = new JLabel("");
		frame.getContentPane().add(valorSal1, "cell 1 12");

		JLabel lblValSal_1 = new JLabel("Val Sal2:");
		frame.getContentPane().add(lblValSal_1, "cell 0 13");

		valorSal2 = new JLabel("");
		frame.getContentPane().add(valorSal2, "cell 1 13");

		JLabel lblTotal = new JLabel("Total: ");
		frame.getContentPane().add(lblTotal, "cell 0 14");

		valorTotal = new JLabel("");
		frame.getContentPane().add(valorTotal, "cell 1 14");
		
		JLabel lblDepositarCa = new JLabel("Depositar para Ca: ");
		frame.getContentPane().add(lblDepositarCa, "cell 0 16");
		depositarCa = new JLabel("");
		frame.getContentPane().add(depositarCa, "cell 1 16");
		
	}

	protected void calcularDeposito() {
		Contas contasDoMes = null;
		Contas prestAp = null;		
		try	{
			Double totalContasDoMes = Double.parseDouble(txtContas.getText());
			contasDoMes = calcular(totalContasDoMes);
		} catch (NumberFormatException e) {}
		try {
			Double totalPrestAp = Double.parseDouble(txtPrestAp.getText());
			prestAp = calcular(totalPrestAp);
		}catch (NumberFormatException e) {}
		
		if(contasDoMes != null && prestAp != null) {
			Double parteDanAp = prestAp.getParteDan();
			Double parteCaContas = contasDoMes.getParteCa();
			Double depositar = parteDanAp - parteCaContas;
			depositarCa.setText(df.format(depositar));
		} else {
			depositarCa.setText("");
		}
	}
	
	protected Contas calcular(Double total) {			
		Double salCa = 0d;
		Double salDan = 0d;						
		Contas conta = null;
			int loopCount = (int) Math.pow(100, multiplierDecimals);			
			for(int count = 1; count <= loopCount; count++) {								
				Double porc = count * multiplier;								
				try{
					salCa = Double.parseDouble(txtSalCa.getText());
					salDan = Double.parseDouble(txtSalDan.getText());
				} catch (NumberFormatException e) {}				
				Double vlCalculadoSalCa = calcValue(salCa, porc);
				Double vlCalculadoSalDan = calcValue(salDan, porc);				
				//limita o número de casas decimais     
				Double vlCalculadoTotal = vlCalculadoSalCa + vlCalculadoSalDan;								
				if(vlCalculadoTotal.equals(total) || (vlCalculadoTotal > total && ( isInRange(total, vlCalculadoTotal,100) ))) {
					conta = new Contas();
					conta.setParteCa(vlCalculadoSalCa);
					conta.setParteDan(vlCalculadoSalDan);
					conta.setPorcentagem(porc);
					String formatted = df.format(porc);
					conta.setPorcentagemTextForamted(formatted);
					conta.setTotal(total);
					conta.setTotalCalculado(vlCalculadoTotal);
					break;					
				}				
			}	
			return conta;
	}	

	protected void calcularValoresAutomaticamente(Double total) {			
		if(txtTotal.getText().trim().length() > 0 && txtSalCa.getText().trim().length() > 0 && txtSalDan.getText().trim().length() > 0)
		{						
			Double sal1 = 0d;
			Double sal2 = 0d;
						
			int loopCount = (int) Math.pow(100, multiplierDecimals);
			
			for(int count = 1; count <= loopCount; count++) {
								
				Double porc = count * multiplier;						
								
				try{
					sal1 = Double.parseDouble(txtSalCa.getText());
					sal2 = Double.parseDouble(txtSalDan.getText());
				} catch (NumberFormatException e) {}
				
				Double vlCalculadoSal1 = calcValue(sal1, porc);
				Double vlCalculadoSal2 = calcValue(sal2, porc);
				
				//limita o número de casas decimais     
				String sal1AsString = df.format(vlCalculadoSal1);
				String sal2AsString = df.format(vlCalculadoSal2);
				
				Double vlCalculadoTotal = vlCalculadoSal1 + vlCalculadoSal2;
								
				if(vlCalculadoTotal.equals(total) || (vlCalculadoTotal > total && ( isInRange(total, vlCalculadoTotal,100) ))) {
					
					valorSal1.setText(sal1AsString);
					valorSal2.setText(sal2AsString);
					
					setLblValorTotalText(vlCalculadoTotal, total);
					
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

		if(txtTotal.getText().trim().length() > 0 && txtSalCa.getText().trim().length() > 0 && txtSalDan.getText().trim().length() > 0)
		{
			Double sal1 = 0d;
			Double sal2 = 0d;
			
			try{
				sal1 = Double.parseDouble(txtSalCa.getText());
				sal2 = Double.parseDouble(txtSalDan.getText());
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
		return txtSalCa;
	}
	public JTextField getTextField_2() {
		return txtSalDan;
	}
	
	private class Contas {
		private Double parteDan;
		private Double parteCa;
		private Double porcentagem;
		private String porcentagemTextForamted;
		private int count;
		private Double total;
		private Double totalCalculado;
		
		public Double getParteDan() {
			return parteDan;
		}
		public void setParteDan(Double parteDan) {
			this.parteDan = parteDan;
		}
		public Double getParteCa() {
			return parteCa;
		}
		public void setParteCa(Double parteCa) {
			this.parteCa = parteCa;
		}
		public Double getPorcentagem() {
			return porcentagem;
		}
		public void setPorcentagem(Double porcentagem) {
			this.porcentagem = porcentagem;
		}
		public String getPorcentagemTextForamted() {
			return porcentagemTextForamted;
		}
		public void setPorcentagemTextForamted(String porcentagemTextForamted) {
			this.porcentagemTextForamted = porcentagemTextForamted;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public Double getTotal() {
			return total;
		}
		public void setTotal(Double total) {
			this.total = total;
		}
		public Double getTotalCalculado() {
			return totalCalculado;
		}
		public void setTotalCalculado(Double totalCalculado) {
			this.totalCalculado = totalCalculado;
		}
		
		
		
	}
}
