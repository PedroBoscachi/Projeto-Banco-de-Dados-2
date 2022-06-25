package academia.view.unidade;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class UnidadeModeloTabela extends AbstractTableModel {
	private String[] colunas = {"Id", "Nome", "Estado", "Cidade", "Rua"};
	private List<Unidade> unidades;

	public UnidadeModeloTabela() { }
	
	public UnidadeModeloTabela(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}
	
	@Override
	public int getRowCount() {
		if(unidades != null)
			return unidades.size();
		
		return 0;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Unidade unidade =  unidades.get(linha);
		Object valor = null;
		
		switch(coluna) {
		case 0:
			valor = unidade.getId();
			break;
		case 1:
			valor = unidade.getNome();
			break;
		case 2:
			valor = unidade.getEstado();
			break;
		case 3:
			valor = unidade.getCidade();
			break;
		case 4:
			valor = unidade.getRua();
			break;
		}
		
		return valor;
	}
	
	@Override 
	public boolean isCellEditable(int linha, int coluna) {
		return false;
	}
	
	@Override 
	public void setValueAt(Object valor, int linha, int coluna) {
		switch(coluna) {
			case 1:
				unidades.get(linha).setNome(valor.toString());
				break;
			case 2:
				unidades.get(linha).setEstado((String) valor);
				break;
			case 3:
				unidades.get(linha).setCidade(valor.toString());
				break;
			case 4:
				unidades.get(linha).setRua(valor.toString());
				break;
		}
		
		fireTableCellUpdated(linha, coluna);
	}
	
	public void removeUnidade(int linha) {
		unidades.remove(linha);
		
		fireTableRowsDeleted(linha, linha);
	}
}
