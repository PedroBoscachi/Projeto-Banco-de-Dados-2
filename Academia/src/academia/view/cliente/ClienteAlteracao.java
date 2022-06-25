package academia.view.cliente;

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

import academia.controller.ClienteController;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class ClienteAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbCep, lbIdade, lbUnidade, lbPlano;
	private JTextField tfNome, tfCep, tfIdade;
	private JComboBox<Unidade> cbUnidade;
	private JComboBox<Plano> cbPlano;
	private JButton btSalvar;
	private Container cp; 
	private int idCliente;
	private int linhaSelecionada; 
	private ClienteModeloTabela mtTabela; 

	public ClienteAlteracao(int idCliente, String nome, String cep, String idade, 
			                    String unidade, String plano, int linha, ClienteModeloTabela mtTabela) { 
		setTitle("Alteração de Clientes"); 
		setSize(500, 335); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		lbTitulo = new JLabel("Alteração de Clientes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); 
		
		lbNome = new JLabel("Nome");
		lbCep = new JLabel("CEP");
		lbIdade = new JLabel("Idade");
		lbUnidade = new JLabel("Unidade");
		lbPlano = new JLabel("Plano");
		
		tfNome = new JTextField();
		tfCep = new JTextField();
		tfIdade = new JTextField();
		
		cbUnidade = new JComboBox<>();		
		
		List<Unidade> unidades = new ArrayList<Unidade>();
		
		unidades = new ClienteController().recuperaUnidades();
		if (unidades != null) 
			for (Unidade u : unidades)
				cbUnidade.addItem(u);
		
		String erro = new ClienteController().getExcecao();
		
		if (erro != null)
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		cbPlano = new JComboBox<>();		
		
		List<Plano> planos = new ArrayList<Plano>();
		
		planos = new ClienteController().recuperaPlanos();
		if (planos != null) 
			for (Plano p : planos)
				cbPlano.addItem(p);
		
		String erro2 = new ClienteController().getExcecao();
		
		if (erro2 != null)
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos planos:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idCliente = idCliente; 
		
		tfNome.setText(nome);
		tfCep.setText(cep);
		tfIdade.setText(idade);;
		
		for (int i = 0; i < cbUnidade.getItemCount(); i++)
			if (cbUnidade.getItemAt(i).getNome().equals(unidade))
				cbUnidade.setSelectedItem(cbUnidade.getItemAt(i));
		
		for (int i = 0; i < cbPlano.getItemCount(); i++)
			if (cbPlano.getItemAt(i).getNome().equals(plano))
				cbPlano.setSelectedItem(cbPlano.getItemAt(i));
		
		this.linhaSelecionada = linha; 
		this.mtTabela = mtTabela; 

		cp = getContentPane();
		cp.setLayout(null); 
		cp.setBackground(new Color(180, 205, 205)); 

		lbTitulo.setBounds(125, 10, 300, 25); 
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbCep.setBounds(20, 90, 100, 25);
		tfCep.setBounds(100, 90, 100, 25);
		lbIdade.setBounds(20, 130, 100, 25);
		tfIdade.setBounds(100, 130, 100, 25);
		lbUnidade.setBounds(20, 170, 100, 25);
		cbUnidade.setBounds(100, 170, 220, 25);
		lbPlano.setBounds(16, 210, 250, 25);
		cbPlano.setBounds(100, 210, 300, 35);
		btSalvar.setBounds(200, 250, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbCep);
		cp.add(tfCep);
		cp.add(lbIdade);
		cp.add(tfIdade);
		cp.add(lbUnidade);
		cp.add(cbUnidade);
		cp.add(lbPlano);
		cp.add(cbPlano);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	} 

	private void btSalvarAction() { 
		List<String> erros = new ArrayList<String>();
		
		erros = new ClienteController().alteraCliente(this.idCliente,
															  tfNome.getText(),
															  tfCep.getText(),
															  Integer.parseInt(tfIdade.getText()),
															  (Unidade) cbUnidade.getSelectedItem(),
															  (Plano) cbPlano.getSelectedItem());	
		
		if (erros.get(0) == null) { 
			JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso.", 
                    					  "Informação", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(tfCep.getText(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfIdade.getText(), this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(cbUnidade.getSelectedItem(), this.linhaSelecionada, 4);
			this.mtTabela.setValueAt(cbPlano.getSelectedItem(), this.linhaSelecionada, 5);
			this.setVisible(false); 
		} else { 
			String mensagem = "Não foi possível alterar o cliente:\n";
			for (String e : erros) 
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
