package academia.view.cliente;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import academia.model.cliente.Cliente;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class ClienteModeloTabela extends AbstractTableModel {
		private String[] colunas = { "Matr�cula", "Nome", "CEP", "Idade", "Unidade", "Plano" };
		private List<Cliente> clientes; 
		private List<Unidade> unidades;
		private List<Plano> planos;
		
		public ClienteModeloTabela() { } 

		public ClienteModeloTabela(List<Cliente> clientes, List<Unidade> unidades, List<Plano> planos) { 
			this.clientes = clientes;
			this.unidades = unidades;
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
			if (clientes != null)
				return clientes.size();	
			return 0;
		}

		@Override
	    public Object getValueAt(int linha, int coluna) {
	        Cliente cliente = clientes.get(linha); 
	        Object valor = null;
			
	        switch (coluna) { 
	            case 0: 
	            	valor = cliente.getMatricula(); 
	            	break;
	            case 1:
	            	valor = cliente.getNome(); 
	            	break;
	            case 2: 
	            	valor = cliente.getCep();
	            	break;
	            case 3: 
	            	valor = cliente.getIdade();
	            	break;
	            case 4:
	            	if(unidades != null)
	            		for(Unidade u : unidades)
	            			if(u.getId() == cliente.getUnidade().getId())
	            				valor = u;
	            	break;
	            case 5: 
					if (planos != null) 
						for (Plano p : planos)
							if (p.getId() == cliente.getPlano().getId())
								valor = p;
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
				clientes.get(linha).setNome(valor.toString()); 
				break;
			case 2: 
				clientes.get(linha).setCep(valor.toString()); 
				break;
			case 3:
				clientes.get(linha).setIdade( Integer.parseInt(valor.toString())); 
				break;
			case 4:
				clientes.get(linha).setUnidade((Unidade) valor);
				break;
			case 5: 
				clientes.get(linha).setPlano((Plano) valor); 
				break;
			}
			fireTableCellUpdated(linha, coluna);
		}
		
	    public void removeClienteTabela(int linha) {
	    	clientes.remove(linha);
	        fireTableRowsDeleted(linha, linha);
	    }
}
