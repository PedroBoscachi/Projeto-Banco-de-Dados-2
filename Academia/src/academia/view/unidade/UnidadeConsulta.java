package academia.view.unidade;

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

import academia.controller.UnidadeController;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class UnidadeConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbUnidade; 
	private UnidadeModeloTabela mtTabela; 
	private JScrollPane spTabela; 
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;
	
	public UnidadeConsulta() {
		setTitle("Consulta de Unidades"); 
		setSize(700, 320); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		String excecaoUnidades = null;
		
		List<Unidade> unidades = new UnidadeController().consultaUnidades();
		excecaoUnidades = new UnidadeController().getExcecao();
		
		if(excecaoUnidades != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades:\n" + excecaoUnidades, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new UnidadeModeloTabela();
		} else 
			mtTabela = new UnidadeModeloTabela(unidades);
		
		lbTitulo = new JLabel("Consulta de Unidades");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		tbUnidade = new JTable(mtTabela);
		spTabela = new JScrollPane(tbUnidade);
		
		tbUnidade.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbUnidade.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbUnidade.getColumnModel().getColumn(1).setPreferredWidth(160);
		tbUnidade.getColumnModel().getColumn(2).setPreferredWidth(70);
		tbUnidade.getColumnModel().getColumn(3).setPreferredWidth(105);
		tbUnidade.getColumnModel().getColumn(4).setPreferredWidth(177);
		
		tbUnidade.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		
		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbUnidade.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		
		tbUnidade.getTableHeader().setReorderingAllowed(false);
		tbUnidade.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
		if(tbUnidade.getSelectedRow() != -1) {
			int linhaSelecionada = tbUnidade.getSelectedRow();
			
			int id = Integer.parseInt(tbUnidade.getModel().getValueAt(linhaSelecionada, 0).toString());
			String nome = tbUnidade.getModel().getValueAt(linhaSelecionada, 1).toString();
			String estado = tbUnidade.getModel().getValueAt(linhaSelecionada, 2).toString();
			String cidade = tbUnidade.getModel().getValueAt(linhaSelecionada, 3).toString();
			String rua = tbUnidade.getModel().getValueAt(linhaSelecionada, 4).toString();
			
			SwingUtilities.invokeLater(new Runnable(){ // 
				@Override
				public void run(){ new UnidadeAlteracao(id, nome, estado, cidade, rua, 
						                                    linhaSelecionada, mtTabela).setVisible(true); }});
		} else {
			JOptionPane.showMessageDialog(this, "Selecione uma unidade.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btExcluirAction() {
		if(tbUnidade.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", 
					 		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(resposta ==0) {
				int linhaSelecionada = tbUnidade.getSelectedRow();
				int id = Integer.parseInt(tbUnidade.getModel().getValueAt(linhaSelecionada, 0).toString());
				
				String erro = "";
				
				erro = new UnidadeController().excluiUnidade(id);
				
				if(erro == null) {
					JOptionPane.showMessageDialog(this, "Unidade excluída com sucesso.", 
                            "Informação", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeUnidade(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir a unidade:\n";
				    mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		} else  {
			JOptionPane.showMessageDialog(this, "Selecione uma unidade.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
