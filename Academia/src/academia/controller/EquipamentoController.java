package academia.controller;

import java.util.ArrayList;
import java.util.List;

import academia.dao.ClienteDao;
import academia.dao.EquipamentoDao;
import academia.model.equipamento.Equipamento;
import academia.model.unidade.Unidade;

public class EquipamentoController {
	private Equipamento equipamento;
	private List<String> erros;
	
	public List<String> insereEquipamento(String nome, String descricao, double valor, Unidade unidade) {
		recebeDadosEquipamento(null, nome, descricao, valor, unidade);
		
		if(erros.size() == 0)
			erros.add(new EquipamentoDao().insereEquipamento(equipamento));
		
		return erros;
	}
	
	public void recebeDadosEquipamento(Integer id, String nome, String descricao, double valor,
										Unidade unidade) {
		equipamento = new Equipamento();
		erros = new ArrayList<String>();
		
		equipamento.setId(id);
		equipamento.setNome(nome);
		equipamento.setDescricao(descricao);
		equipamento.setValor(valor);
		equipamento.setUnidade(unidade);
	}
	
	public List<Unidade> recuperaUnidades() {
		return new EquipamentoDao().recuperaUnidades();
	}
	
	public String getExcecao() {
		return new EquipamentoDao().getExcecao();
	}
	
	public List<Equipamento> consultaEquipamentos() {
		return new EquipamentoDao().consultaEquipamentos();
	}
	
	public List<String> alteraEquipamento(Integer id, String nome, String descricao, double valor, Unidade unidade) {
    	recebeDadosEquipamento(id, nome, descricao, valor, unidade);
    	
		if (erros.size() == 0)
			erros.add(new EquipamentoDao().alteraEquipamento(equipamento));

		return erros;
    }

    public String excluiEquipamento(Integer id) {
    	String erro = new EquipamentoDao().excluiEquipamento(id);
        return erro;
    }
}
