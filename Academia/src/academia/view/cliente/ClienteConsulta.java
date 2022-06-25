package academia.view.cliente;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import academia.controller.ClienteController;
import academia.model.cliente.Cliente;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class ClienteConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbCliente; 
	private ClienteModeloTabela mtTabela;
	private JScrollPane spTabela; 
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp; 
	
	public ClienteConsulta() { 
		setTitle("Consulta de Clientes"); 
		setSize(700, 320); 
		setLocationRelativeTo(null); 
		setModal(true); 
		
		String excecaoClientes = null;
		String excecaoUnidades = null;
		String excecaoPlanos = null;
		
		List<Cliente> clientes = new ClienteController().consultaClientes();
		excecaoClientes = new ClienteController().getExcecao();
		
		List<Unidade> unidades = new ClienteController().recuperaUnidades();
		excecaoUnidades = new ClienteController().getExcecao();
		
		List<Plano> planos = new ClienteController().recuperaPlanos();
		excecaoPlanos = new ClienteController().getExcecao();
		
		if (excecaoClientes != null) { 
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos clientes:\n" + excecaoClientes, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new ClienteModeloTabela(); 
		} else if (excecaoUnidades != null) { 
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades dos clientes:\n" + excecaoUnidades, 
						                  "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new ClienteModeloTabela(); 
		} else if (excecaoPlanos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos planos dos clientes:\n" + excecaoPlanos, 
	                  "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new ClienteModeloTabela(); 
		} else
			mtTabela = new ClienteModeloTabela(clientes, unidades, planos);
		
		lbTitulo = new JLabel("Consulta de Funcionários");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		tbCliente = new JTable(mtTabela);
		spTabela = new JScrollPane(tbCliente); 
		
		tbCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		tbCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbCliente.getColumnModel().getColumn(1).setPreferredWidth(160);
		tbCliente.getColumnModel().getColumn(2).setPreferredWidth(70);
		tbCliente.getColumnModel().getColumn(3).setPreferredWidth(80);
		tbCliente.getColumnModel().getColumn(4).setPreferredWidth(105);
		tbCliente.getColumnModel().getColumn(5).setPreferredWidth(177);
		
		tbCliente.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		
		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbCliente.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		
		DefaultTableCellRenderer dtcrDireita = new DefaultTableCellRenderer();
		dtcrDireita.setHorizontalAlignment(SwingConstants.RIGHT);
		tbCliente.getColumnModel().getColumn(3).setCellRenderer(dtcrDireita);
		
		tbCliente.getTableHeader().setReorderingAllowed(false);
		tbCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null); 
		cp.setBackground(new Color(180, 205, 205)); 

		lbTitulo.setBounds(215, 10, 300, 25); 
		spTabela.setBounds(20, 40, 645, 182);
		btAlterar.setBounds(215, 240, 100, 25);
		btExcluir.setBounds(355, 240, 100, 25);

		cp.add(lbTitulo);
		cp.add(spTabela);
		cp.add(btAlterar);
		cp.add(btExcluir);
		
		btAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAlterarAction();
			}
		});
				
		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btExcluirAction();
			}
		});
	} 
	
	private void btAlterarAction() { 	
		if (tbCliente.getSelectedRow() != -1){ 
			int linhaSelecionada = tbCliente.getSelectedRow(); 
			
			int matricula = Integer.parseInt(tbCliente.getModel().getValueAt(linhaSelecionada, 0).toString()); 
			String nome = tbCliente.getModel().getValueAt(linhaSelecionada, 1).toString(); 
			String cep = tbCliente.getModel().getValueAt(linhaSelecionada, 2).toString(); 
			String idade = tbCliente.getModel().getValueAt(linhaSelecionada, 3).toString(); 
			String unidade = tbCliente.getModel().getValueAt(linhaSelecionada, 4).toString();
			String plano = tbCliente.getModel().getValueAt(linhaSelecionada, 5).toString(); 
			
			SwingUtilities.invokeLater(new Runnable(){ 
				@Override
				public void run(){ new ClienteAlteracao(matricula, nome, cep, idade, unidade, plano, 
						                                    linhaSelecionada, mtTabela).setVisible(true); }});
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btExcluirAction() { 
		if (tbCliente.getSelectedRow() != -1) { 		
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", 
					 									 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) { 
				int linhaSelecionada = tbCliente.getSelectedRow(); 
				
				int matricula = Integer.parseInt(tbCliente.getModel().getValueAt(linhaSelecionada, 0).toString()); 
				
				String erro = "";
				
				erro = new ClienteController().excluiCliente(matricula);
				
				if (erro == null) { 
					JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.", 
		                                          "Informação", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeClienteTabela(linhaSelecionada); 
				} else { 
					String mensagem = "Não foi possível excluir o cliente:\n";
				    mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1) 
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
