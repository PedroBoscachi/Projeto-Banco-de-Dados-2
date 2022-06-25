package academia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import academia.model.equipamento.Equipamento;
import academia.model.unidade.Unidade;

public class EquipamentoDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;
	
	public String insereEquipamento(Equipamento equipamento) {
		instrucaoSql = "INSERT INTO EQUIPAMENTO (Nome, Descricao, Valor, Unidade) VALUES (?,?,?,?)";
		return insereAlteraExclui(instrucaoSql, equipamento.getNome(), equipamento.getDescricao(),
						equipamento.getValor(), equipamento.getUnidade().getId());
	}
	
	public List<Unidade> recuperaUnidades() {
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
		} catch(Exception e) {
			excecao = "Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			unidades = null;
		}
		
		return unidades; 
	}
	
	public String getExcecao() {
		return excecao;
	}
	
	public List<Equipamento> consultaEquipamentos() {
		Equipamento equipamento;
		Unidade unidade;
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		instrucaoSql = "SELECT * FROM EQUIPAMENTO";
		
		try {
			excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				registros = comando.executeQuery();
				
				if(registros.next()) {
					registros.beforeFirst();
					while(registros.next()) {
						equipamento = new Equipamento();
						equipamento.setId(registros.getInt("Id"));
						equipamento.setNome(registros.getString("Nome"));
						equipamento.setDescricao(registros.getString("Descricao"));
						equipamento.setValor(registros.getDouble("Valor"));
						unidade = new Unidade();
						unidade.setId(registros.getInt("Unidade"));
						equipamento.setUnidade(unidade);
						equipamentos.add(equipamento);
					}
				}
				
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch(Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        	equipamentos = null;
		}
		
		return equipamentos;
	}
	
	public String alteraEquipamento(Equipamento equipamento) {
    	instrucaoSql = "UPDATE EQUIPAMENTO SET Nome = ?, Descricao = ?, Valor = ?, Unidade = ? " +
                       "WHERE Id = ?";
    	return insereAlteraExclui(instrucaoSql, equipamento.getNome(), equipamento.getDescricao(), equipamento.getValor(), 
        		                                equipamento.getUnidade().getId(), equipamento.getId());
    }
    
    public String excluiEquipamento(int id) {
    	instrucaoSql = "DELETE FROM EQUIPAMENTO WHERE Id = ?";
    	return insereAlteraExclui(instrucaoSql, id);
    }
	
}
