import java.sql.*;


public class Banco {
	public static String database = "jdbc:mysql://localhost/almoxarifado";
	public static String usuario = "root";
	public static String senha = "root";
	
	/*
	 * Inicia a database
	 */
	public static void start(){
		Connection con = null;
		String query;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/",usuario,senha);
			try{
				Statement st = con.createStatement();
				st.executeUpdate("CREATE DATABASE if not exists almoxarifado");
				try{
					query = "CREATE table if not exists cadastro(" +
					" id int(7) unsigned not null primary key auto_increment, " +
					"nome varchar(55) not null, matricula varchar(25), " +
					"titulo varchar(55) not null, " +
					"descricao varchar(100), " +
					"date datetime)";
					st.executeUpdate("use almoxarifado");
					st.executeUpdate(query);
				}catch (Exception es) {
					// TODO: handle exception
					System.out.println("Erro ao criar a tabela");
				}
			}
			catch (SQLException s){
				System.out.println("SQL statement is not executed!");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * Pega o ID e o Titulo do ultimo cadastro do Banco
	 */
	public static void banco(){
		Connection conn = null;
		String query = "SELECT MAX(ID) FROM cadastro";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);  
			Statement stm = conn.createStatement();
			try{
				ResultSet rs = stm.executeQuery(query);
				if (rs.next()) {
					Principal.idAtual = rs.getInt(1);
				}
				try{
					String sql = "SELECT * FROM cadastro where id = "+Principal.idAtual;
					rs = stm.executeQuery(sql);
					if (rs.next()) {
						Principal.lastTitulo = rs.getString("titulo");
					}
				}catch (Exception ex) {
					System.out.println("ERRO quando busca o titulo no BD");
				}
			}catch (Exception ex) {
				System.out.println("Erro no REsult Set!");
			}
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
			es.getStackTrace();
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro na consulta:");
			es.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
			}
		}
	}

	public static boolean cadastrar(String nome, String matricula, String titulo, String descricao){
		Connection conn = null;
		String query = "INSERT INTO cadastro VALUES(0,'"+nome+"','"+matricula+"','"+titulo+"','"+descricao+"',now())";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);
			System.out.println("A conexao foi um sucesso\n");  
			Statement stm = conn.createStatement();
			stm.executeUpdate(query);
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
			return false;
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro no Cadastro");
			return false;
		} finally {
			try {
				conn.close();
				System.out.println("\n\nFechamendo a conexao");
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
				return false;
			}
		}
		return true;
	}

	/*
	 * Pesquisa no banco de dados.
	 * @return StringBuffer com os dados concatenados. 
	 */
	public static StringBuffer listar(){
		StringBuffer listagem = new StringBuffer();
		String sql;
		Connection conn = null;
		String query = "SELECT * FROM cadastro";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);
			System.out.println("A conexao foi um sucesso\n");  
			Statement stm = conn.createStatement();
			try{
				ResultSet rs = stm.executeQuery(query);
				while (rs.next()) {
					listagem.append("Registro         -   ");
					listagem.append(rs.getString("id"));
					listagem.append("\nTitulo            -   ");
					listagem.append(rs.getString("titulo"));
					listagem.append("\nDescricao      -   ");
					listagem.append(rs.getString("descricao"));
					listagem.append("\nNome            -   ");
					listagem.append(rs.getString("nome"));
					listagem.append("\nMatricula       -   ");
					listagem.append(rs.getString("matricula"));
					listagem.append("\nData / Hora   -   ");
					listagem.append(rs.getString("date"));
					listagem.append("\n\n");
				}
				try{
					int contId=0;
					sql = "SELECT count(id) from cadastro";
					rs = stm.executeQuery(sql);
					if (rs.next()){
						contId = rs.getInt(1);
					}
					listagem.append("Numero de cadastros: "+contId);
				}catch (Exception ex) {
					System.out.println("Erro ao tentar concatenar o COUNT(ID)");
				}
			}catch (Exception ex) {
				System.out.println("Erro no REsult Set!");
			}
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro na consulta:");
		} finally {
			try {
				conn.close();
				System.out.println("\n\nFechamendo a conexao");
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
			}
		}
		return listagem;
	}

	/*
	 * Pesquisa no banco de dados.
	 * @param String contendo o comando a ser executado pelo banco (query).
	 * @return StrinBuffer contendo os dados da pesquisa.
	 */
	public static StringBuffer pesquisar(String sql){
		StringBuffer listagem = new StringBuffer();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);
			System.out.println("A conexao foi um sucesso\n");  
			Statement stm = conn.createStatement();
			try{
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					listagem.append("Registro         -   ");
					listagem.append(rs.getString("id"));
					listagem.append("\nTitulo            -   ");
					listagem.append(rs.getString("titulo"));
					listagem.append("\nDescricao      -   ");
					listagem.append(rs.getString("descricao"));
					listagem.append("\nNome            -   ");
					listagem.append(rs.getString("nome"));
					listagem.append("\nMatricula       -   ");
					listagem.append(rs.getString("matricula"));
					listagem.append("\nData / Hora   -   ");
					listagem.append(rs.getString("date"));
					listagem.append("\n\n");
				}
			}catch (Exception ex) {
				System.out.println("Erro no REsult Set!");
			}
		} catch(ClassNotFoundException es) {
			System.out.println("Exception Classe nao encontrada");
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro na consulta:");
		} finally {
			try {
				conn.close();
				System.out.println("\n\nFechamendo a conexao");
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
			}
		}
		return listagem;
	}

	// Type = 1 -> Verificar
	// Type = 2 -> Deletar
	public static StringBuffer pesquisar(String sql,int type){
		StringBuffer listagem = new StringBuffer();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);
			System.out.println("A conexao foi um sucesso\n");  
			Statement stm = conn.createStatement();
			try{
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					if (type == 1){
						Alterar.titulo = rs.getString("titulo");
						Alterar.nome = rs.getString("nome");
						Alterar.matricula = rs.getString("matricula");
						Alterar.descricao = rs.getString("descricao");
					}else{
						Deletar.titulo = rs.getString("titulo");
						Deletar.nome = rs.getString("nome");
						Deletar.matricula = rs.getString("matricula");
						Deletar.descricao = rs.getString("descricao");
					}
				}
			}catch (Exception ex) {
				System.out.println("Erro no REsult Set!");
			}
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro na consulta:");
		} finally {
			try {
				conn.close();
				System.out.println("\n\nFechamendo a conexao");
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
			}
		}
		return listagem;
	}

	/*
	 * Verifica se o ID informado existe no Banco de dados.
	 */
	public static int validarID(String sql){
		int id = -1;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);  
			Statement stm = conn.createStatement();
			try{
				ResultSet rs = stm.executeQuery(sql);
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}catch (Exception ex) {
				System.out.println("Erro no REsult Set!");
			}
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro na consulta:");
		} finally {
			try {
				conn.close();
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
			}
		}
		return id;
	}

	public static boolean alterar(String sql){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(database,usuario,senha);
			System.out.println("A conexao foi um sucesso\n");  
			Statement stm = conn.createStatement();
			stm.executeUpdate(sql);
		} catch(ClassNotFoundException es) {
			System.out.println("excessao Classe nao encontrada");
			return false;
		} catch(SQLException es) {
			System.out.println("SQL Exception... Erro no Cadastro");
			return false;
		} finally {
			try {
				conn.close();
				System.out.println("\n\nFechamendo a conexao");
			} catch(SQLException erro) {
				System.out.println("Erro no fechamento");
				return false;
			}
		}
		return true;
	}

}
