package academia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import academia.model.unidade.Unidade;

public class UnidadeDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;
	
	public String insereUnidade(Unidade unidade) {
		instrucaoSql = "INSERT INTO UNIDADE (Nome, Estado, Cidade, Rua) VALUES (?,?,?,?)";
		return insereAlteraExclui(instrucaoSql, unidade.getNome(), unidade.getEstado(),
						unidade.getCidade(), unidade.getRua());
	}
	
	public String getExcecao() {
		return excecao;
	}
	
	public List<Unidade> consultaUnidades() {
		Unidade unidade;
		List<Unidade> unidades = new ArrayList<Unidade>();
		instrucaoSql = "SELECT * FROM UNIDADE";
		
		try {
			excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				registros = comando.executeQuery();
				
				if(registros.next()) {
					registros.beforeFirst();
					while(registros.next()) {
						unidade = new Unidade();
						unidade.setId(registros.getInt("Id"));
						unidade.setNome(registros.getString("Nome"));
						unidade.setEstado(registros.getString("Estado"));
						unidade.setCidade(registros.getString("Cidade"));
						unidade.setRua(registros.getString("Rua"));
						unidades.add(unidade);
					}
				}
				
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        	unidades = null;
		}
		
		return unidades;
	}
	
	public String alteraUnidade(Unidade unidade) {
		instrucaoSql = "UPDATE UNIDADE SET Nome = ?, Estado = ?, Cidade = ?, Rua = ? " +
						"WHERE Id = ?";
		return insereAlteraExclui(instrucaoSql, unidade.getNome(), unidade.getEstado(), unidade.getCidade(), unidade.getRua(),
									unidade.getId());	
	}
	
	public String excluiUnidade(int id) {
		instrucaoSql = "DELETE FROM UNIDADE WHERE ID = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
