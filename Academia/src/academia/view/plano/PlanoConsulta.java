package academia.view.plano;

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

import academia.controller.PlanoController;
import academia.controller.UnidadeController;
import academia.model.plano.Plano;

@SuppressWarnings("serial")
public class PlanoConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbPlano; 
	private PlanoModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;
	
	public PlanoConsulta() {
		Icon icon = new ImageIcon("C:\\Users\\lucas\\OneDrive\\Área de Trabalho\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\agenda.png");
		setTitle("Consulta de Planos"); 
		setSize(660, 350); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		String excecaoPlanos = null;
		
		List<Plano> planos = new PlanoController().consultaPlanos();
		excecaoPlanos = new PlanoController().getExcecao();
		
		if(excecaoPlanos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos planos:\n" + excecaoPlanos, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new PlanoModeloTabela();
		} else 
			mtTabela = new PlanoModeloTabela(planos);
		
		lbTitulo = new JLabel("Consulta de Planos",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		tbPlano = new JTable(mtTabela);
		spTabela = new JScrollPane(tbPlano);
		
		tbPlano.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbPlano.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbPlano.getColumnModel().getColumn(1).setPreferredWidth(190);
		tbPlano.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbPlano.getColumnModel().getColumn(3).setPreferredWidth(135);
		
		tbPlano.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		
		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbPlano.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		
		tbPlano.getTableHeader().setReorderingAllowed(false);
		tbPlano.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");
		
		cp = getContentPane(); 
		cp.setLayout(null); 
		cp.setBackground(new Color(46, 46, 46));
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		
		lbTitulo.setBounds(175, 10, 300, 25); 
		spTabela.setBounds(60, 50, 509, 182);
		btAlterar.setBounds(215, 270, 100, 25);
		btExcluir.setBounds(355, 270, 100, 25);
		
		
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
		if (tbPlano.getSelectedRow() != -1){ 
			int linhaSelecionada = tbPlano.getSelectedRow(); 
			
			int id = Integer.parseInt(tbPlano.getModel().getValueAt(linhaSelecionada, 0).toString()); 
			String nome = tbPlano.getModel().getValueAt(linhaSelecionada, 1).toString(); 
			int periodo = Integer.parseInt(tbPlano.getModel().getValueAt(linhaSelecionada, 2).toString()); 
			String valor = tbPlano.getModel().getValueAt(linhaSelecionada, 3).toString(); 
			
			SwingUtilities.invokeLater(new Runnable(){ 
				@Override
				public void run(){ new PlanoAlteracao(id, nome, periodo, valor,
						                                    linhaSelecionada, mtTabela).setVisible(true); }});
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um plano.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btExcluirAction() { 
		if (tbPlano.getSelectedRow() != -1) { 
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", 
					 									 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				int linhaSelecionada = tbPlano.getSelectedRow(); 
				
				int id = Integer.parseInt(tbPlano.getModel().getValueAt(linhaSelecionada, 0).toString()); 
				
				String erro = "";
				
				erro = new PlanoController().excluiPlano(id);
				
				if (erro == null) { 
					JOptionPane.showMessageDialog(this, "Plano excluído com sucesso.", 
		                                          "Informação", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removePlanoTabela(linhaSelecionada); 
				} else { 
					String mensagem = "Não foi possível excluir o plano:\n";
				    mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1) 
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		} else { 
			JOptionPane.showMessageDialog(this, "Selecione um fplano.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
