import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;  

public class Pesquisar extends JFrame implements ActionListener{  
	private static final long serialVersionUID = 1L;
	private JButton butListar = new JButton("Listar");
	private JButton butPesquisar = new JButton("Pesquisar");
	private JTextField textNome = new JTextField();
	private JTextField textId = new JTextField();
	private JTextField textTitulo = new JTextField();
	private JTextArea textDescricao = new JTextArea();


	public Pesquisar() {  
		super("Almoxarifado - RRS");  
		this.getContentPane().setLayout(new GridBagLayout());
		// Adiciona Labels.
		this.add("Numero Reg.", textId,"Titulo", textTitulo);  
		this.add("Nome", textNome);
		this.add("",butPesquisar,"",butListar);
		this.add("",new JSeparator());
		this.add("", new JScrollPane(textDescricao));

		butListar.addActionListener(this);
		butPesquisar.addActionListener(this);
		this.setSize(600,600);         
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
		cons.gridheight = GridBagConstraints.REMAINDER;  

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
		if (e.getSource() == butListar){
			StringBuffer lista = new StringBuffer();
			lista.append(Banco.listar());
		     
			textDescricao.setText(lista.toString());
		}
		if (e.getSource() == butPesquisar){
			System.out.println("aqui");
			int id=0;
			boolean valida=true;
			String sql;
			StringBuffer listagem = new StringBuffer();
			
			// Busca por ID
			if (!textId.getText().equalsIgnoreCase("")){
				try{
					id = Integer.parseInt(textId.getText());
				}catch (Exception es) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "No campo Registro\n Deve ser informado apenas NUMEROS!","RRS",JOptionPane.WARNING_MESSAGE);
					valida = false;
				}
				if (valida){
					sql = "SELECT * FROM cadastro where id = "+id;
					listagem.append(Banco.pesquisar(sql));
					textDescricao.setText(listagem.toString());
				}
			}else if (!textTitulo.getText().equalsIgnoreCase("")){
				sql = "SELECT * FROM cadastro where titulo like '"+textTitulo.getText()+"%'";
				listagem.append(Banco.pesquisar(sql));
				textDescricao.setText(listagem.toString());
			} else if (!textNome.getText().equalsIgnoreCase("")){
				sql = "SELECT * FROM cadastro where nome like '"+textNome.getText()+"%'";
				listagem.append(Banco.pesquisar(sql));
				textDescricao.setText(listagem.toString());
			}else
				JOptionPane.showMessageDialog(
						null, "Algum dos campos deve ser informado\npara a pesquisa ser efetuada!",
						"RRS",JOptionPane.WARNING_MESSAGE);
		}

	}      

}  