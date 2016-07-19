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
		//Vamos fazer um teste de ipadx e ipady. Coloque esses valores ap�s o setLayout e execute o programa.
		//cons.ipadx = 50;  
		//cons.ipady = 200;  
		//Os dois bot�es ficar�o com o tamanho de width = 141 e height= 225. Sem isto o tamanho dele era width = 91, height = 25
		//Retire a linha adicionada para seguirmos para o pr�ximo teste.
		
		//Analizando o insets
		//Adicione a seguinte linha depois do setLayout;
		//cons.insets = new Insets(10,10,10,10);  
		//Isto far� com que cada componente adicionado crie um espa�o de 10 pixels para cada uma das dire��es: acima, abaixo, direita e esquerda. Portanto a dist�ncia entre o bot�o da direita e o da esquerda � 20 pixels.
		//Retire a linha adicionada para seguirmos para o pr�ximo teste.
		
		//Analizando o weight
		//Se voc� adicionar as linhas abaixo depois do setLayout, ir� alterar o tamanho das c�lulas, como foi explicado anteriormente.
		
		cons.weightx = 1;  
		cons.weighty = 1;
		

		//Esta altera��o far� com que o GridBagLayout ocupe todo o frame, e aumente tamb�m o tamanho das 
		//c�lulas. Portanto agora, o tamanho das c�lulas � 300 de largura x 600 de altura. 
		//Agora podemos perceber que os dois bot�es ficam centralizados nas suas c�lulas, 
		//porque est�o ancorados no centro. Como o fill deles � NONE, eles n�o se redimension�ram. 
		//Deixe esta linha para continuarmos os testes.
		//Analizando o anchor
		//Adicione a seguinte linha ap�s o setLayout;
		
		//cons.anchor = GridBagConstraints.SOUTHEAST;  

		//Isto far� com que os bot�es permane�am no sudeste de suas c�lulas. Portanto, aparecer�o no canto inferior direito. Fa�a testes com as outras constantes, lembrando que as relativas s�o mais complexas de analisar.

		//Analizando o fill
		//Vamos fazer nosso bot�o preencher toda a tela e se tornar redimension�vel.
		
		//cons.fill = GridBagConstraints.BOTH;  

		//Ir�o aparecer como se estivessem em um BorderLayout. S� que ao redimensionar, ambos estar�o recebendo a mesma quantidade de pixels para aumentar de tamanho. Assim, eles possuir�o sempre as mesmas alturas e larguras. Altere os valores do weightx e weighty para cada um dos bot�es. Ser� interessante ver como funciona o c�lculo do redimensionamento
		
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


