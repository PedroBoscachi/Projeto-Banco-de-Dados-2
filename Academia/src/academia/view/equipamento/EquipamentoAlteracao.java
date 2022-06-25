package academia.view.equipamento;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import academia.controller.EquipamentoController;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class EquipamentoAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbDescricao, lbValor, lbUnidade;
	private JTextField tfNome, tfDescricao;
	private JFormattedTextField tfValor;
	private JComboBox<Unidade> cbUnidade;
	private JButton btSalvar;
	private Container cp; 
	private int idEquipamento; 
	private int linhaSelecionada; 
	private EquipamentoModeloTabela mtTabela; 

	public EquipamentoAlteracao(int idEquipamento, String nome, String descricao, String valor, 
			                    String unidade, int linha, EquipamentoModeloTabela mtTabela) { 
		setTitle("Alteração de Equipamentos"); 
		setSize(500, 335); 
		setLocationRelativeTo(null); 
		setModal(true); 
		
		lbTitulo = new JLabel("Alteração de Equipamentos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); 
		
		lbNome = new JLabel("Nome");
		lbDescricao = new JLabel("Descrição");
		lbValor = new JLabel("Valor (R$)");
		lbUnidade = new JLabel("Unidade");
		
		tfNome = new JTextField();
		tfDescricao = new JTextField();
		
        tfValor = new JFormattedTextField(); 
		DecimalFormat df = new DecimalFormat("##,##0.00"); 
        NumberFormatter nf = new NumberFormatter();
        nf.setFormat(df); 
        nf.setAllowsInvalid(false); 
        tfValor.setFormatterFactory(new DefaultFormatterFactory(nf));
		
		cbUnidade = new JComboBox<>();		
		
		List<Unidade> unidades = new ArrayList<Unidade>();
		
		unidades = new EquipamentoController().recuperaUnidades();
		if (unidades != null) 
			for (Unidade u : unidades)
				cbUnidade.addItem(u);
		
		String erro = new EquipamentoController().getExcecao();
		
		if (erro != null) 
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos equipamentos:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idEquipamento = idEquipamento; 
		
		tfNome.setText(nome);
		tfDescricao.setText(descricao);
		
		tfValor.setText(valor.toString());
		
		for (int i = 0; i < cbUnidade.getItemCount(); i++)
			if (cbUnidade.getItemAt(i).getNome().equals(unidade))
				cbUnidade.setSelectedItem(cbUnidade.getItemAt(i));
		
		this.linhaSelecionada = linha; 
		this.mtTabela = mtTabela; 

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205)); 
		
		lbTitulo.setBounds(125, 10, 300, 25); 
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbDescricao.setBounds(20, 90, 100, 25);
		tfDescricao.setBounds(100, 90, 100, 25);
		lbValor.setBounds(20, 130, 100, 25);
		tfValor.setBounds(100, 130, 100, 25);
		lbUnidade.setBounds(20, 170, 100, 25);
		cbUnidade.setBounds(100, 170, 220, 25);
		btSalvar.setBounds(200, 250, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbDescricao);
		cp.add(tfDescricao);
		cp.add(lbValor);
		cp.add(tfValor);
		cp.add(lbUnidade);
		cp.add(cbUnidade);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	} 

	private void btSalvarAction() { 
		double valor = 0;
		
		if (!tfValor.getText().equals(""))
			valor =  Double.parseDouble(tfValor.getText().replace(".", "").replace(",", "."));
		
		List<String> erros = new ArrayList<String>();
		
		erros = new EquipamentoController().alteraEquipamento(this.idEquipamento,
															  tfNome.getText(),
															  tfDescricao.getText(),
															  valor,
											                  (Unidade) cbUnidade.getSelectedItem());	
		
		if (erros.get(0) == null) { 
			JOptionPane.showMessageDialog(this, "Equipamento alterado com sucesso.", 
                    					  "Informação", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(tfDescricao.getText(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(valor, this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(cbUnidade.getSelectedItem(), this.linhaSelecionada, 4);
			this.setVisible(false);
		} else { 
			String mensagem = "Não foi possível alterar o equipamento:\n";
			for (String e : erros) 
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
