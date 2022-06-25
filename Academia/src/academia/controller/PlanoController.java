package academia.controller;

import java.util.ArrayList;
import java.util.List;

import academia.dao.PlanoDao;
import academia.model.plano.Plano;

public class PlanoController {
	private Plano plano;
	private List<String> erros;
	
	public List<String> inserePlano(String nome, Integer periodo, double valor) {
		recebeDadosPlano(null, nome, periodo, valor);
		
		if(erros.size() == 0)
			erros.add(new PlanoDao().inserePlano(plano));
		
		return erros;
	}
	
	public void recebeDadosPlano(Integer id, String nome, Integer periodo, double valor) {
		plano = new Plano();
		erros = new ArrayList<String>();
		
		plano.setId(id);
		plano.setNome(nome);
		plano.setPeriodo(periodo);
		plano.setValor(valor);
		
		//erros = PlanoValidacao.validaPlano(plano);
	}
	
	public String getExcecao() {
		return new PlanoDao().getExcecao();
	}
	
	public List<Plano> consultaPlanos() {
		return new PlanoDao().consultaPlanos();
	}
	
	public List<String> alteraPlano(Integer id, String nome, Integer periodo, double valor) {
    	recebeDadosPlano(id, nome, periodo, valor);
  
		if (erros.size() == 0)
			erros.add(new PlanoDao().alteraPlanos(plano));
		
		return erros;
    }
	
	public String excluiPlano(Integer id) {
    	String erro = new PlanoDao().excluiPlano(id);
        return erro;
    }

}
