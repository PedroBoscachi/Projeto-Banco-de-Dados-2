package academia.view.unidade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import academia.controller.UnidadeController;

@SuppressWarnings("serial")
public class UnidadeAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbEstado, lbCidade, lbRua;
	private JTextField tfNome, tfCidade, tfRua;
	private JComboBox<String> cbEstado;
	private JButton btSalvar;
	private Container cp; 
	private int idUnidade; 
	private int linhaSelecionada; 
	private UnidadeModeloTabela mtTabela; 

	public UnidadeAlteracao(int idUnidade, String nome, String estado, String cidade, 
			                    String rua, int linha, UnidadeModeloTabela mtTabela) { 
		setTitle("Alteração de Unidades"); 
		setSize(500, 335); 
		setLocationRelativeTo(null); 
		setModal(true); 
		
		lbTitulo = new JLabel("Alteração de Unidades");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); 
		
		lbNome = new JLabel("Nome");
		lbEstado = new JLabel("Estado");
		lbCidade = new JLabel("Cidade");
		lbRua = new JLabel("Rua");
		
		tfNome = new JTextField();
		tfCidade = new JTextField();
		tfRua = new JTextField();
		
		cbEstado = new JComboBox<>();
		
		String estados[] = { "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
				    "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF" };
		
		if (estados != null) 
			for (String e : estados)
				cbEstado.addItem(e);
		
		String erro = new UnidadeController().getExcecao();
		
		if (erro != null) 
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos estados:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idUnidade = idUnidade; 
		
		tfNome.setText(nome);
		tfCidade.setText(cidade);
		tfRua.setText(rua);
		
		for (int i = 0; i < cbEstado.getItemCount(); i++)
			if (cbEstado.getItemAt(i).equals(estado))
				cbEstado.setSelectedItem(cbEstado.getItemAt(i));
		
		this.linhaSelecionada = linha; 
		this.mtTabela = mtTabela; 

		cp = getContentPane(); 
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205)); 

		lbTitulo.setBounds(125, 10, 300, 25); 
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbEstado.setBounds(20, 90, 100, 25);
		cbEstado.setBounds(100, 90, 100, 25);
		lbCidade.setBounds(20, 130, 100, 25);
		tfCidade.setBounds(100, 130, 100, 25);
		lbRua.setBounds(20, 170, 100, 25);
		tfRua.setBounds(100, 170, 220, 25);
		btSalvar.setBounds(200, 250, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbEstado);
		cp.add(cbEstado);
		cp.add(lbCidade);
		cp.add(tfCidade);
		cp.add(lbRua);
		cp.add(tfRua);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	} 

	private void btSalvarAction() { 
		
		List<String> erros = new ArrayList<String>();
		
		erros = new UnidadeController().alteraUnidade(this.idUnidade,
															  tfNome.getText(),
															  (String) cbEstado.getSelectedItem(),
															  tfCidade.getText(),
											                  tfRua.getText());	
		
		if (erros.get(0) == null) { 
			JOptionPane.showMessageDialog(this, "Unidade alterada com sucesso.", 
                    					  "Informação", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbEstado.getSelectedItem(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfCidade.getText(), this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(tfRua.getText(), this.linhaSelecionada, 4);
			this.setVisible(false);
		} else { 
			String mensagem = "Não foi possível alterar a unidade:\n";
			for (String e : erros) 
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
