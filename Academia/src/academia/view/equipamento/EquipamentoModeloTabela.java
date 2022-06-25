package academia.view.equipamento;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import academia.model.equipamento.Equipamento;
import academia.model.unidade.Unidade;


@SuppressWarnings("serial")
public class EquipamentoModeloTabela extends AbstractTableModel {
	private String[] colunas = {"Id", "Nome", "Descricao", "Valor", "Unidade"};
	private List<Equipamento> equipamentos;
	private List<Unidade> unidades;

	public EquipamentoModeloTabela() { }
	
	public EquipamentoModeloTabela(List<Equipamento> equipamentos, List<Unidade> unidades) {
		this.equipamentos = equipamentos;
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
		if(equipamentos != null)
			return equipamentos.size();
		
		return 0;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Equipamento equipamento =  equipamentos.get(linha);
		Object valor = null;
		DecimalFormat df = new DecimalFormat("##,##0.00");
		
		switch(coluna) {
		case 0:
			valor = equipamento.getId();
			break;
		case 1:
			valor = equipamento.getNome();
			break;
		case 2:
			valor = equipamento.getDescricao();
			break;
		case 3:
			valor = df.format(equipamento.getValor());
			break;
		case 4:
			if(unidades != null)
				for(Unidade u : unidades)
					if(u.getId() == equipamento.getUnidade().getId())
						valor = u;
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
			equipamentos.get(linha).setNome(valor.toString()); 
			break;
		case 2:
			equipamentos.get(linha).setDescricao(valor.toString()); 
			break;
		case 3: 
			equipamentos.get(linha).setValor( Double.parseDouble(valor.toString())); 
			break;
		case 4: 
			equipamentos.get(linha).setUnidade((Unidade) valor); 
			break;
		}
	
		fireTableCellUpdated(linha, coluna);
	}
		
	public void removeEquipamentoTabela(int linha) {
		equipamentos.remove(linha);
		fireTableRowsDeleted(linha, linha);
	}
}
