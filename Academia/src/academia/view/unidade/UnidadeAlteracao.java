package academia.view.unidade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
		Icon icon = new ImageIcon("D:\\LI??ES IFSP\\3 SEMESTRE\\BD2A3\\Projeto\\Projeto-Academia\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
		setTitle("Altera??o de Unidades"); 
		setSize(500, 315); 
		setLocationRelativeTo(null); 
		setModal(true); 
		
		lbTitulo = new JLabel("Altera??o de Unidades",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); 
		
		lbNome = new JLabel("Nome:");
		lbEstado = new JLabel("Estado:");
		lbCidade = new JLabel("Cidade:");
		lbRua = new JLabel("Rua:");
		
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
			JOptionPane.showMessageDialog(null, "N?o foi poss?vel recuperar os dados dos estados:\n" + erro, 
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
		cp.setBackground(new Color(46, 46, 46)); 

		lbTitulo.setBounds(90, 10, 300, 20); 
		lbNome.setBounds(20, 50, 100, 20);
		tfNome.setBounds(100, 50, 360, 20);
		lbEstado.setBounds(20, 90, 100, 20);
		cbEstado.setBounds(100, 90, 100, 20);
		lbCidade.setBounds(20, 130, 100, 20);
		tfCidade.setBounds(100, 130, 180, 20);
		lbRua.setBounds(20, 170, 100, 20);
		tfRua.setBounds(100, 170, 360, 20);
		btSalvar.setBounds(190, 220, 100, 25);
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbCidade.setForeground(new Color(255, 255, 255));
		lbRua.setForeground(new Color(255, 255, 255));
		
		lbEstado.setForeground(new Color(255, 255, 255));
		
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
                    					  "Informa??o", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbEstado.getSelectedItem(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfCidade.getText(), this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(tfRua.getText(), this.linhaSelecionada, 4);
			this.setVisible(false);
		} else { 
			String mensagem = "N?o foi poss?vel alterar a unidade:\n";
			for (String e : erros) 
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
