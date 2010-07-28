import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/*
 * Author: Rodrigo Ramalho da Silva
 * Data: 18/02/2009
 * email: hodrigohamalho@gmail.com
 */

public class Principal extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int idAtual=0;
	static String lastTitulo;
	private JTextField textNome = new JTextField();
	private JTextField textMatricula = new JTextField();
	private JTextField textTitulo = new JTextField();
	private JButton enviar = new JButton("Enviar");
	private JTextArea textDescricao = new JTextArea();
	private JScrollPane scrol = new JScrollPane(textDescricao);
	private JMenuBar barramenu = new JMenuBar();
	private JMenu arquivo = new JMenu("Arquivo");
	private JMenuItem itemPesquisar = new JMenuItem("Pesquisar");
	private JMenuItem itemAlterar = new JMenuItem("Alterar");
	private JMenuItem itemSair = new JMenuItem("Sair");
	private JMenuItem itemAbout = new JMenuItem("Sobre");
	private JMenuItem itemDeletar = new JMenuItem("Deletar");
	private JLabel lUltimoReg;
	private JLabel lUltimoTit;

	public Principal() {  
		super("Almoxarifado - RRS");  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 

		this.getContentPane().setLayout(new GridBagLayout());

		// Conecta com o Banco e pega o Ultimo Registro
		Banco.start();
		Banco.banco();

		arquivo.add(itemPesquisar);
		arquivo.add(itemAlterar);
		arquivo.add(itemDeletar);
		arquivo.addSeparator();
		arquivo.add(itemAbout);
		arquivo.add(itemSair);

		// Define um atalho pra Sair (ALT + S).
		itemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
		itemSair.setMnemonic(KeyEvent.VK_S);
		
		itemPesquisar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.ALT_MASK));
		itemPesquisar.setMnemonic(KeyEvent.VK_P);
		
		itemAlterar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK));
		itemAlterar.setMnemonic(KeyEvent.VK_A);
		
		itemDeletar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK));
		itemDeletar.setMnemonic(KeyEvent.VK_A);
		
		barramenu.add(arquivo);
		teste(barramenu);

		// Labels contendo os dados do Ãºltimo registro
		lUltimoReg = new JLabel(Integer.toString(idAtual),JLabel.CENTER);
		lUltimoTit = new JLabel(lastTitulo,JLabel.CENTER);
		Labels(lUltimoReg,lUltimoTit); // Edita JLabels

		this.add("Último Registro", lUltimoReg, "Título", lUltimoTit);  
		this.add("Nome", textNome);  
		this.add("Matrícula", textMatricula); 
		this.add("Título Atual", textTitulo);  
		this.add("Descricao", scrol);
		this.add("",enviar);

		enviar.addActionListener(this);
		itemSair.addActionListener(this);
		itemPesquisar.addActionListener(this);
		itemAlterar.addActionListener(this);
		itemAbout.addActionListener(this);
		itemDeletar.addActionListener(this);
		this.setSize(600,600);         
	}  

	/** 
	 * Faz tabela e Muda cor do fundo dos Labels
	 * @param Label 
	 * @param Label 
	 */  
	public void Labels(JLabel lUltimoReg, JLabel lUltimoTit){
		lUltimoReg.setBorder(BorderFactory.createLineBorder(Color.black));
		lUltimoReg.setBackground(Color.cyan);
		lUltimoReg.setOpaque(true);
		lUltimoTit.setBorder(BorderFactory.createLineBorder(Color.black));
		lUltimoTit.setBackground(Color.cyan);
		lUltimoTit.setOpaque(true);
	}

	/** 
	 * Adiciona um label e um componente horizontalmente 
	 * @param label String que ira¡ aparecer no label 
	 * @param componente Componente de edia§a£o 
	 */  
	public void add(String label, JComponent componente ) {  
		GridBagConstraints cons = new GridBagConstraints();  
		cons.fill = GridBagConstraints.NONE;  
		cons.anchor = GridBagConstraints.NORTHWEST;  
		cons.insets = new Insets(4,4,4,4);  

		cons.weightx = 0;  
		cons.gridwidth = 1;  
		this.getContentPane().add(new JLabel(label), cons);  

		cons.fill = GridBagConstraints.BOTH;  
		cons.weightx = 1;  
		cons.gridwidth = GridBagConstraints.REMAINDER;  
		this.getContentPane().add(componente, cons);  
	}  
	
	public void teste (JComponent componente){
		GridBagConstraints cons = new GridBagConstraints();  
		cons.fill = GridBagConstraints.NONE;  
		cons.anchor = GridBagConstraints.NORTHWEST;  
		cons.insets = new Insets(4,4,4,4);  

		cons.fill = GridBagConstraints.BOTH;  
		cons.weightx = 1;  
		cons.gridwidth = GridBagConstraints.REMAINDER;  
		this.getContentPane().add(componente, cons);  
	}

	/** 
	 * Adiciona um label e um componente horizontalmente. O componente ocupara¡ todo o reto da tela 
	 * @param label String que ira¡ aparecer no label 
	 * @param componente Componente de edia§a£o 
	 */  
	public void add(String label, JScrollPane componente ) {  
		GridBagConstraints cons = new GridBagConstraints();  
		cons.fill = GridBagConstraints.NONE;  
		cons.anchor = GridBagConstraints.NORTHWEST;  
		cons.insets = new Insets(4,4,4,4);  
		cons.weighty = 1;  
		cons.gridheight = GridBagConstraints.RELATIVE;  

		cons.weightx = 0;  
		cons.gridwidth = 1;  
		this.getContentPane().add(new JLabel(label), cons);  

		cons.fill = GridBagConstraints.BOTH;  
		cons.weightx = 1;  
		cons.gridwidth = GridBagConstraints.REMAINDER;  
		this.getContentPane().add(componente, cons);  
	}      

	/** 
	 * Adiciona um label, um componente de edia§a£o, mais um label e outro componente de edia§a£o. Todos  
	 * na mesma linha 
	 * @param label Label 1  
	 * @param componente Componente de edia§a£o 
	 * @param label2 Label 2 
	 * @param componente2 Componente de edia§a£o 2 
	 */  
	public void add(String label, JComponent componente, String label2, JComponent componente2) {  
		GridBagConstraints cons = new GridBagConstraints();  
		cons.fill = GridBagConstraints.BOTH;  
		cons.insets = new Insets(4,4,4,4);  

		cons.fill = GridBagConstraints.NONE;  
		cons.anchor = GridBagConstraints.NORTHWEST;  
		cons.weightx = 0;  
		cons.gridwidth = 1;  
		this.getContentPane().add(new JLabel(label), cons);  

		cons.weightx = 1;  
		cons.gridwidth = 1;  
		cons.fill = GridBagConstraints.BOTH;  
		this.getContentPane().add(componente, cons);  

		cons.fill = GridBagConstraints.NONE;  
		cons.weightx = 0;  
		cons.gridwidth = 1;  
		this.getContentPane().add(new JLabel(label2), cons);  

		cons.weightx = 1;  
		cons.fill = GridBagConstraints.BOTH;  
		cons.gridwidth = GridBagConstraints.REMAINDER;  
		this.getContentPane().add(componente2, cons);  
	}  

	public static void main(String[] args ) {  
		Principal exe = new Principal();  
		exe.show();        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == enviar){
			if (!(textNome.getText().equalsIgnoreCase("") || textTitulo.getText().equalsIgnoreCase("") ||
					textMatricula.getText().equalsIgnoreCase("") || textDescricao.getText().equalsIgnoreCase(""))){
				boolean validaBanco = Banco.cadastrar(textNome.getText(),textMatricula.getText(),textTitulo.getText(),textDescricao.getText());
				if (validaBanco){
					JOptionPane.showMessageDialog(null,"Dados Cadastrados com Sucesso","RRS",JOptionPane.PLAIN_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null,"Dados na£o pode ser cadastrado!","RRS",JOptionPane.WARNING_MESSAGE);
				textNome.setText("");
				textMatricula.setText("");
				textTitulo.setText("");
				textDescricao.setText("");
				Banco.banco();
				lUltimoReg.setText(Integer.toString(idAtual));
				lUltimoTit.setText(lastTitulo);
			}else
				JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","RRS",JOptionPane.WARNING_MESSAGE);
		}
			
		if (e.getSource() == itemSair){
			System.exit(0);
		}else if (e.getSource() == itemPesquisar){
			Pesquisar pesq = new Pesquisar();
			pesq.show();
		}else if (e.getSource() == itemAlterar){
			Alterar alterar = new Alterar();
			alterar.show();
		}else if (e.getSource() == itemAbout){
			JOptionPane.showMessageDialog(null, "Todos os direitos desse programa sa£o reservados\n" +
					"a Rodrigo Ramalho da Silva.\nContato: hodrigohamalho@gmail.com");
		}else if (e.getSource() == itemDeletar){
			Deletar del = new Deletar();
			del.show();
		}

	}  
}  