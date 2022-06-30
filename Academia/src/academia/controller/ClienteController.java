package academia.controller;

import java.util.ArrayList;
import java.util.List;

import academia.dao.ClienteDao;
import academia.dao.UnidadeDao;
import academia.model.cliente.Cliente;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

public class ClienteController {
	private Cliente cliente;
	private List<String> erros;
	
	public List<String> insereCliente(String nome, String cep, String idade, Unidade unidade, Plano plano) {
		recebeDadosCliente(null, nome, cep, idade, unidade, plano);
		
		if(erros.size() == 0)
			erros.add(new ClienteDao().insereCliente(cliente));
		
		return erros;
	}
	
	public void recebeDadosCliente(Integer matricula, String nome, String cep, String idade, Unidade unidade, Plano plano) {
		cliente = new Cliente();
		erros = new ArrayList<String>();
		
		cliente.setMatricula(matricula);
		cliente.setNome(nome);
		cliente.setCep(cep);
		cliente.setIdade(idade);
		cliente.setUnidade(unidade);
		cliente.setPlano(plano);
	}
	
	public List<Unidade> recuperaUnidades() {
		return new ClienteDao().recuperaUnidades();
	}
	
	public List<Plano> recuperaPlanos() {
		return new ClienteDao().recuperaPlanos();
	}
	
	public String getExcecao() {
		return new UnidadeDao().getExcecao();
	}
	
	public List<Cliente> consultaClientes() {
		return new ClienteDao().consultaClientes();
	}
	
	public List<String> alteraCliente(Integer matricula, String nome, String cep, String idade, Unidade unidade, Plano plano) {
    	recebeDadosCliente(matricula, nome, cep, idade, unidade, plano);
    	
		if (erros.size() == 0)
			erros.add(new ClienteDao().alteraCliente(cliente));
		
		return erros;
    }

    public String excluiCliente(Integer id) {
    	String erro = new ClienteDao().excluiCliente(id);
        return erro;
    }
}
