package academia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import academia.model.cliente.Cliente;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

public class ClienteDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;
	
	public String insereCliente(Cliente cliente) {
		instrucaoSql = "INSERT INTO CLIENTE (Nome, Cep, Dat_nas, Unidade, Plano) VALUES (?,?,?,?,?)";
		String ano = cliente.getIdade().substring(6,10)+"-";
		String mes = cliente.getIdade().substring(3,5)+"-";
		String dia = cliente.getIdade().substring(0,2);
	
		String FormattedDate = ano.concat(mes).concat(dia);
	
		
		return insereAlteraExclui(instrucaoSql, cliente.getNome(), cliente.getCep(), FormattedDate,
						cliente.getUnidade().getId(), cliente.getPlano().getId());
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
	
	public List<Plano> recuperaPlanos() {
		Plano plano;
		List<Plano> planos = new ArrayList<Plano>();
		instrucaoSql = "SELECT * FROM PLANO";
		
		try {
			excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				registros = comando.executeQuery();
				
				if(registros.next()) {
					registros.beforeFirst();
					while(registros.next()) {
						plano = new Plano();
						plano.setId(registros.getInt("Id"));
						plano.setNome(registros.getString("Nome"));
						plano.setPeriodo(registros.getInt("Duracao"));
						plano.setValor(registros.getDouble("Valor"));
						planos.add(plano);
					}
				}
				
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch(Exception e) {
			excecao = "Exceção " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			planos = null;
		}
		
		return planos;
	}
	
	public String getExcecao() {
		return excecao;
	}
	
	public List<Cliente> consultaClientes() {
    	Cliente cliente;
        Unidade unidade;
        Plano plano;
        List<Cliente> clientes = new ArrayList<Cliente>();
        instrucaoSql = "SELECT * FROM CLIENTE";
  
        try {
        	excecao = ConnectionDatabase.conectaBd(); 
        	if (excecao == null) {
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
                registros = comando.executeQuery(); 
                
                if (registros.next()) { 
                    registros.beforeFirst(); 
        	        while (registros.next()) {
        	            cliente = new Cliente();
        	            cliente.setMatricula(registros.getInt("Matricula"));
        	            cliente.setNome(registros.getString("Nome"));
        	            cliente.setCep(registros.getString("Cep"));
        	            cliente.setIdade(registros.getString("Dat_nas"));
        	            unidade = new Unidade();
        	            unidade.setId(registros.getInt("Unidade"));
        	            plano = new Plano();
        	            plano.setId(registros.getInt("Plano"));
        	            cliente.setUnidade(unidade);
        	            cliente.setPlano(plano);
        	            clientes.add(cliente);
        	        }
        	    }
                registros.close(); 
                comando.close(); 
                ConnectionDatabase.getConexaoBd().close(); 
            }
        } catch (Exception e) {
        	excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
        }
        return clientes;
    }
	
	 public String alteraCliente(Cliente cliente) {
	    	instrucaoSql = "UPDATE CLIENTE SET Nome = ?, Cep = ?, Dat_nas = ?, Unidade = ?, Plano = ? " +
	                       "WHERE Matricula = ?";
	    	
	    	String ano = cliente.getIdade().substring(6,10)+"-";
			String mes = cliente.getIdade().substring(3,5)+"-";
			String dia = cliente.getIdade().substring(0,2);
		
			String FormattedDate = ano.concat(mes).concat(dia);
		
	    	return insereAlteraExclui(instrucaoSql, cliente.getNome(), cliente.getCep(), FormattedDate, 
	        		                                cliente.getUnidade().getId(), cliente.getPlano().getId(), cliente.getMatricula());
	    }
	    
	    public String excluiCliente(int matricula) {
	    	instrucaoSql = "DELETE FROM CLIENTE WHERE Matricula = ?";
	    	return insereAlteraExclui(instrucaoSql, matricula);
	    }
}
