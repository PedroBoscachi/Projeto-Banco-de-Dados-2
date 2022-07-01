package academia.view.equipamento;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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

import academia.controller.EquipamentoController;
import academia.controller.PlanoController;
import academia.model.equipamento.Equipamento;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class EquipamentoConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbEquipamento; 
	private EquipamentoModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;
	
	public EquipamentoConsulta() {
		Icon icon = new ImageIcon("D:\\\\LIÇÕES IFSP\\\\3 SEMESTRE\\\\BD2A3\\\\Projeto\\\\Projeto-Academia\\\\Projeto-Banco-de-Dados-2\\\\Academia\\\\src\\\\academia\\\\view\\\\agenda.png");
		setTitle("Consulta de Equipamentos"); 
		setSize(700, 320); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		String excecaoEquipamentos = null;
		String excecaoUnidades = null;
		
		List<Equipamento> equipamentos = new EquipamentoController().consultaEquipamentos();
		excecaoEquipamentos = new PlanoController().getExcecao();
		
		List<Unidade> unidades = new EquipamentoController().recuperaUnidades();
		excecaoUnidades = new EquipamentoController().getExcecao();
		
		if(excecaoEquipamentos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos equipamentos:\n" + excecaoEquipamentos, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new EquipamentoModeloTabela();
		} else if (excecaoUnidades != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades dos equipamento:\n" + excecaoUnidades, 
	                  "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new EquipamentoModeloTabela();
		} else
			mtTabela = new EquipamentoModeloTabela(equipamentos, unidades);
		
		lbTitulo = new JLabel("Consulta de Equipamentos",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		tbEquipamento = new JTable(mtTabela);
		spTabela = new JScrollPane(tbEquipamento);
		
		tbEquipamento.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbEquipamento.getColumnModel().getColumn(0).setPreferredWidth(70);
		tbEquipamento.getColumnModel().getColumn(1).setPreferredWidth(150);
		tbEquipamento.getColumnModel().getColumn(2).setPreferredWidth(150);
		tbEquipamento.getColumnModel().getColumn(3).setPreferredWidth(115);
		tbEquipamento.getColumnModel().getColumn(4).setPreferredWidth(155);
		tbEquipamento.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		
		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbEquipamento.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		
		tbEquipamento.getTableHeader().setReorderingAllowed(false);
		tbEquipamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");
		lbTitulo.setForeground(new Color(255, 255, 255));
		cp = getContentPane(); 
		cp.setLayout(null); 
		cp.setBackground(new Color(46, 46, 46));
		
		lbTitulo.setBounds(180, 10, 300, 25); 
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
		if (tbEquipamento.getSelectedRow() != -1){ 
			int linhaSelecionada = tbEquipamento.getSelectedRow();
			
			int id = Integer.parseInt(tbEquipamento.getModel().getValueAt(linhaSelecionada, 0).toString()); 
			String nome = tbEquipamento.getModel().getValueAt(linhaSelecionada, 1).toString(); 
			String descricao = tbEquipamento.getModel().getValueAt(linhaSelecionada, 2).toString(); 
			String valor = tbEquipamento.getModel().getValueAt(linhaSelecionada, 3).toString(); 
			String unidade = tbEquipamento.getModel().getValueAt(linhaSelecionada, 4).toString();
			
			SwingUtilities.invokeLater(new Runnable(){ 
				@Override
				public void run(){ new EquipamentoAlteracao(id, nome, descricao, valor, unidade, 
						                                    linhaSelecionada, mtTabela).setVisible(true); }});
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um equipamento.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btExcluirAction() {
		if (tbEquipamento.getSelectedRow() != -1) { 		
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", 
					 									 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) { 
				int linhaSelecionada = tbEquipamento.getSelectedRow(); 
				
				int id = Integer.parseInt(tbEquipamento.getModel().getValueAt(linhaSelecionada, 0).toString()); 
				
				String erro = "";
				
				erro = new EquipamentoController().excluiEquipamento(id);
				
				if (erro == null) { 
					JOptionPane.showMessageDialog(this, "Equipamento excluído com sucesso.", 
		                                          "Informação", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeEquipamentoTabela(linhaSelecionada); 
				} else { 
					String mensagem = "Não foi possível excluir o equipamento:\n";
				    mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1) 
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um equipamento.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
