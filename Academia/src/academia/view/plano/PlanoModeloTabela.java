package academia.view.plano;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

public class PlanoModeloTabela extends AbstractTableModel {
	private String[] colunas = {"Id", "Nome", "Periodo", "Valor"};
	private List<Plano> planos;

	public PlanoModeloTabela() { }
	
	public PlanoModeloTabela(List<Plano> planos) {
		this.planos = planos;
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
		if(planos != null)
			return planos.size();
		
		return 0;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Plano plano =  planos.get(linha);
		Object valor = null;
		DecimalFormat df = new DecimalFormat("##,##0.00");
		
		switch(coluna) {
		case 0:
			valor = plano.getId();
			break;
		case 1:
			valor = plano.getNome();
			break;
		case 2:
			valor = plano.getPeriodo();
			break;
		case 3:
			valor = df.format(plano.getValor());
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
		switch (coluna) { 
		case 1: 
			planos.get(linha).setNome(valor.toString()); 
			break;
		case 2: 
			planos.get(linha).setPeriodo(Integer.parseInt((String) valor)); 
			break;
		case 3: 
			planos.get(linha).setValor(Double.parseDouble((String) valor)); 
			break;
		}
		fireTableCellUpdated(linha, coluna);
	}
	
	public void removePlanoTabela(int linha) {
    	planos.remove(linha);
        fireTableRowsDeleted(linha, linha);
    }
}
