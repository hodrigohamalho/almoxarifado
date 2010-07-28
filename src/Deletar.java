import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;  

public class Deletar extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textId = new JTextField();
	private JTextField textNome = new JTextField();
	private JTextField textMatricula = new JTextField();
	private JTextField textTitulo = new JTextField();
	private JTextArea textDescricao = new JTextArea();
	private JButton butDeletar = new JButton("Deletar");
	private JButton butVerificar = new JButton("Verificar");
	static String titulo,nome,matricula,descricao;

	public Deletar() {  
		super("Almoxarifado - Deletar");  

		this.getContentPane().setLayout(new GridBagLayout());  

		JLabel msg = new JLabel("Informe o nº do Registro que deseja DELETAR",JLabel.CENTER);
		msg.setBorder(BorderFactory.createLineBorder(Color.black));
		msg.setBackground(Color.lightGray);
		msg.setOpaque(true);
		this.add("",msg);
		this.add("N�mero Reg", textId, "", butVerificar);  
		this.add("Nome", textNome);  
		this.add("Matr�cula", textMatricula);   
		this.add("Descri��o", new JScrollPane(textDescricao));
		this.add("",butDeletar);
		this.setSize(600,600);         

		butDeletar.addActionListener(this);
		butVerificar.addActionListener(this);
	}  

	/** 
	 * Adiciona um label e um componente horizontalmente 
	 * @param label String que irá aparecer no label 
	 * @param componente Componente de edição 
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
	 * Adiciona um label e um componente horizontalmente. O componente ocupará todo o reto da tela 
	 * @param label String que irá aparecer no label 
	 * @param componente Componente de edição 
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
	 * Adiciona um label, um componente de edição, mais um label e outro componente de edição. Todos  
	 * na mesma linha 
	 * @param label Label 1  
	 * @param componente Componente de edição 
	 * @param label2 Label 2 
	 * @param componente2 Componente de edição 2 
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
		// Valida o campo ID obrigatório.
		if (textId.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Obrigatório informar o Registro","RRS",JOptionPane.WARNING_MESSAGE);
			valida = false;
		}else{
			try{
				id = Integer.parseInt(textId.getText());
			}catch (Exception es) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "No campo Registro deve ser informado somente números","RSS",JOptionPane.WARNING_MESSAGE);
				valida = false;
			}
		}
		if (valida){
			if (e.getSource() == butVerificar){
				sql = "SELECT * FROM cadastro where id = "+id;
				idAux = Banco.validarID(sql);
				if (idAux != -1){
					Banco.pesquisar(sql,2);
					textNome.setText(nome);
					textTitulo.setText(titulo);
					textMatricula.setText(matricula);
					textDescricao.setText(descricao);
					textId.setEditable(false);
					textNome.setEditable(false);
					textTitulo.setEditable(false);
					textMatricula.setEditable(false);
					textDescricao.setEditable(false);
				}else
					JOptionPane.showMessageDialog(null, "Registro informado não existe!","RRS"
							,JOptionPane.WARNING_MESSAGE);
			} else if ((e.getSource() == butDeletar) && (idAux != -1)){
				if (!(textNome.getText().equalsIgnoreCase("") || textMatricula.getText().equalsIgnoreCase("")
						|| textTitulo.getText().equalsIgnoreCase("") || 
						textDescricao.getText().equalsIgnoreCase(""))){
					sql = "DELETE from cadastro where id = "+id;
					if (Banco.alterar(sql)){
						textId.setEditable(true);
						textNome.setEditable(true);
						textTitulo.setEditable(true);
						textMatricula.setEditable(true);
						textDescricao.setEditable(true);
						textId.setText("");
						textNome.setText("");
						textMatricula.setText("");
						textTitulo.setText("");
						textDescricao.setText("");
						JOptionPane.showMessageDialog(null, "Cadastro deletado com sucesso!","RRS",
								JOptionPane.PLAIN_MESSAGE);
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro e o cadastro nao foi Deletado!","RRS",
								JOptionPane.WARNING_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","RRS",
							JOptionPane.WARNING_MESSAGE);
			}

		}
	}      
}  