package academia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import academia.model.plano.Plano;

public class PlanoDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;
	
	public String inserePlano(Plano plano) {
		instrucaoSql = "INSERT INTO PLANO (Nome, Periodo, Valor) VALUES (?,?,?)";
		return insereAlteraExclui(instrucaoSql, plano.getNome(), plano.getPeriodo(),
						plano.getValor());
	}
	
	public String getExcecao() {
		return excecao;
	}
	
	 public List<Plano> consultaPlanos() {
	    	Plano plano;
	        List<Plano> planos = new ArrayList<Plano>();
	        instrucaoSql = "SELECT * FROM PLANO";
	  
	        try {
	        	excecao = ConnectionDatabase.conectaBd(); 
	        	if (excecao == null) {
	                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
	                registros = comando.executeQuery(); 
	                
	                if (registros.next()) { 
	                    registros.beforeFirst(); 
	        	        while (registros.next()) {
	        	            plano = new Plano();
	        	            plano.setId(registros.getInt("Id"));
	        	            plano.setNome(registros.getString("Nome"));
	        	            plano.setPeriodo(registros.getInt("Periodo"));
	        	            plano.setValor(registros.getDouble("Valor"));
	        	            planos.add(plano);
	        	        }
	        	    }
	                registros.close(); 
	                comando.close(); 
	                ConnectionDatabase.getConexaoBd().close(); 
	            }
	        } catch (Exception e) {
	        	excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
	        	planos = null; 
	        }
	        return planos; 
	    }
	 
	 public String alteraPlanos(Plano plano) {
		 instrucaoSql = "UPDATE PLANO SET Nome = ?, Periodo = ?, Valor = ? " + "WHERE Id = ?";
		 return insereAlteraExclui(instrucaoSql, plano.getNome(), plano.getPeriodo(), plano.getValor(), plano.getId());
	 }
	 
	 public String excluiPlano(int id) {
		 instrucaoSql = "DELETE FROM PLANO WHERE Id = ?";
		 return insereAlteraExclui(instrucaoSql, id);
	 }
}
