package my.snippets.swing;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ExemploGridBagLayout extends JFrame{


	public ExemploGridBagLayout() {  
		super("Exemplo GridBagLayout");  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);  

		Container c = this.getContentPane();  

		GridBagConstraints cons = new GridBagConstraints();  
		GridBagLayout layout = new GridBagLayout();  
		c.setLayout(layout);  

		//Analizando o ipad
		//Vamos fazer um teste de ipadx e ipady. Coloque esses valores após o setLayout e execute o programa.
		//cons.ipadx = 50;  
		//cons.ipady = 200;  
		//Os dois botões ficarão com o tamanho de width = 141 e height= 225. Sem isto o tamanho dele era width = 91, height = 25
		//Retire a linha adicionada para seguirmos para o próximo teste.
		
		//Analizando o insets
		//Adicione a seguinte linha depois do setLayout;
		//cons.insets = new Insets(10,10,10,10);  
		//Isto fará com que cada componente adicionado crie um espaço de 10 pixels para cada uma das direções: acima, abaixo, direita e esquerda. Portanto a distância entre o botão da direita e o da esquerda é 20 pixels.
		//Retire a linha adicionada para seguirmos para o próximo teste.
		
		//Analizando o weight
		//Se você adicionar as linhas abaixo depois do setLayout, irá alterar o tamanho das células, como foi explicado anteriormente.
		
		cons.weightx = 1;  
		cons.weighty = 1;
		

		//Esta alteração fará com que o GridBagLayout ocupe todo o frame, e aumente também o tamanho das 
		//células. Portanto agora, o tamanho das células é 300 de largura x 600 de altura. 
		//Agora podemos perceber que os dois botões ficam centralizados nas suas células, 
		//porque estão ancorados no centro. Como o fill deles é NONE, eles não se redimensionáram. 
		//Deixe esta linha para continuarmos os testes.
		//Analizando o anchor
		//Adicione a seguinte linha após o setLayout;
		
		//cons.anchor = GridBagConstraints.SOUTHEAST;  

		//Isto fará com que os botões permaneçam no sudeste de suas células. Portanto, aparecerão no canto inferior direito. Faça testes com as outras constantes, lembrando que as relativas são mais complexas de analisar.

		//Analizando o fill
		//Vamos fazer nosso botão preencher toda a tela e se tornar redimensionável.
		
		//cons.fill = GridBagConstraints.BOTH;  

		//Irão aparecer como se estivessem em um BorderLayout. Só que ao redimensionar, ambos estarão recebendo a mesma quantidade de pixels para aumentar de tamanho. Assim, eles possuirão sempre as mesmas alturas e larguras. Altere os valores do weightx e weighty para cada um dos botões. Será interessante ver como funciona o cálculo do redimensionamento
		
		JButton esq = new JButton("Esquerda");
		//esq.setBackground(new Color(125,23,15));
		JButton dir = new JButton("Direita");
		//dir.setBackground(new Color(125,251,96));
		
		c.add(esq, cons);  
		c.add(dir, cons);  

		this.setSize(600,600);         
	}  

	public static void main(String[] args ) {  
		ExemploGridBagLayout exe = new ExemploGridBagLayout(); 
		exe.show();        
	}  
}  


