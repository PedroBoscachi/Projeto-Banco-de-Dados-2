package academia.controller;

import java.util.ArrayList;
import java.util.List;

import academia.dao.UnidadeDao;
import academia.model.unidade.Unidade;

public class UnidadeController {
	private Unidade unidade;
	private List<String> erros;
	
	public List<String> insereUnidade(String nome, String estado, String cidade, String rua) {
		recebeDadosUnidade(null, nome, estado, cidade, rua);
		
		if(erros.size() == 0)
			erros.add(new UnidadeDao().insereUnidade(unidade));
		
		return erros;
	}
	
	public void recebeDadosUnidade(Integer id, String nome, String estado, String cidade,
									String rua) {
		unidade = new Unidade();
		erros = new ArrayList<String>();
		
		unidade.setId(id);
		unidade.setNome(nome);
		unidade.setEstado(estado);
		unidade.setCidade(cidade);
		unidade.setRua(rua);
		
		//erros = UnidadeValidacao.validaUnidade(unidade);
	}
	
	public String getExcecao() {
		return new UnidadeDao().getExcecao();
	}
	
	public List<Unidade> consultaUnidades() {
		return new UnidadeDao().consultaUnidades();
	}
	
	public List<String> alteraUnidade(Integer id, String nome, String estado, String cidade, String rua) {
		recebeDadosUnidade(id, nome, estado, cidade, rua);
		
		if(erros.size() == 0)
			erros.add(new UnidadeDao().alteraUnidade(unidade));
		
		return erros;
	}
	
	public String excluiUnidade(Integer id) {
		String erro = new UnidadeDao().excluiUnidade(id);
		return erro;
	}
}
