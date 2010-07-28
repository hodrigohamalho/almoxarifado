import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;  

public class Alterar extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textId = new JTextField();
	private JTextField textNome = new JTextField();
	private JTextField textMatricula = new JTextField();
	private JTextField textTitulo = new JTextField();
	private JTextArea textDescricao = new JTextArea();
	private JButton butAlterar = new JButton("Alterar");
	private JButton butVerificar = new JButton("Verificar");
	static String titulo,nome,matricula,descricao;

	public Alterar() {  
		super("Almoxarifado - Alterar");  

		this.getContentPane().setLayout(new GridBagLayout());  

		JLabel msg = new JLabel("Informe os dados do cadastro que deseja alterar",JLabel.CENTER);
		msg.setBorder(BorderFactory.createLineBorder(Color.black));
		msg.setBackground(Color.lightGray);
		msg.setOpaque(true);
		this.add("",msg);
		this.add("Numero Reg", textId, "", butVerificar);  
		this.add("Nome", textNome);  
		this.add("Matricula", textMatricula);   
		this.add("Descricao", new JScrollPane(textDescricao));
		this.add("",butAlterar);
		this.setSize(600,600);         

		butAlterar.addActionListener(this);
		butVerificar.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean valida = true;
		int id=0,idAux=0;
		String sql;
		// Valida o campo ID obrigata³rio.
		if (textId.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Obrigataorio informar o Registro","RRS",JOptionPane.WARNING_MESSAGE);
			valida = false;
		}else{
			try{
				id = Integer.parseInt(textId.getText());
			}catch (Exception es) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "No campo Registro deve ser informado somente naºmeros","RSS",JOptionPane.WARNING_MESSAGE);
				valida = false;
			}
		}
		if (valida){
			if (e.getSource() == butVerificar){
				sql = "SELECT * FROM cadastro where id = "+id;
				idAux = Banco.validarID(sql);
				if (idAux != -1){
					Banco.pesquisar(sql,1);
					textNome.setText(nome);
					textTitulo.setText(titulo);
					textMatricula.setText(matricula);
					textDescricao.setText(descricao);
					textId.setEditable(false);
				}else
					JOptionPane.showMessageDialog(null, "Registro informado na£o existe!","RRS"
							,JOptionPane.WARNING_MESSAGE);
			} else if ((e.getSource() == butAlterar) && (idAux != -1)){
				if (!(textNome.getText().equalsIgnoreCase("") || textMatricula.getText().equalsIgnoreCase("")
						|| textTitulo.getText().equalsIgnoreCase("") || 
						textDescricao.getText().equalsIgnoreCase(""))){
					sql = "UPDATE cadastro set nome = '"+textNome.getText()+"', matricula = '"+
					textMatricula.getText()+"', titulo = '"+textTitulo.getText()+"', descricao = '"+
					textDescricao.getText()+"'  where id = "+id;
					if (Banco.alterar(sql)){
						textId.setEditable(true);
						textId.setText("");
						textNome.setText("");
						textMatricula.setText("");
						textTitulo.setText("");
						textDescricao.setText("");
						JOptionPane.showMessageDialog(null, "Cadastro alterado com sucesso!","RRS",
								JOptionPane.PLAIN_MESSAGE);
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro e o cadastro nao foi alterado!","RRS",
								JOptionPane.WARNING_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","RRS",
							JOptionPane.WARNING_MESSAGE);
			}

		}
	}      
}  