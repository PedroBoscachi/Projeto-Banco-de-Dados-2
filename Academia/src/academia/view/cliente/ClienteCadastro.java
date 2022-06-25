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
import academia.controller.PlanoController;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class ClienteCadastro extends JDialog {
	private JLabel lbTitulo, lbNome, lbMatricula, lbCep, lbIdade, lbPlano, lbUnidade;
	private JTextField tfNome, tfCep, tfIdade; 
	private JComboBox<Plano> cbPlano;
	private JComboBox<Unidade> cbUnidade;
	private JButton btCadastrar;
	private Container cp;
	
	public ClienteCadastro() {
		setTitle("Cadastro de Cliente");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Cliente");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		lbNome = new JLabel("Nome");
		lbMatricula = new JLabel("Matrícula");
		lbCep = new JLabel("CEP");
		lbIdade = new JLabel("Idade");
		lbPlano = new JLabel("Plano");
		lbUnidade = new JLabel("Unidade");
		
		tfNome = new JTextField();
		tfCep = new JTextField();
		tfIdade = new JTextField();
		
		cbPlano = new JComboBox<>();
		cbUnidade = new JComboBox<>();
		
		List<Plano> planos = new ArrayList<Plano>();
		List<Unidade> unidades = new ArrayList<Unidade>();
		
		planos = new ClienteController().recuperaPlanos();
		unidades = new ClienteController().recuperaUnidades();
		
		if(planos != null)
			for(Plano p : planos)
				cbPlano.addItem(p);
		
		String erro = new PlanoController().getExcecao();
		
		if(erro != null)
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos planos:\n" + erro,
											"Erro", JOptionPane.ERROR_MESSAGE);
		
		if(unidades != null)
			for(Unidade u : unidades)
				cbUnidade.addItem(u);
		
		String erro2 = new PlanoController().getExcecao();
		
		if(erro2 != null)
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades:\n" + erro,
					"Erro", JOptionPane.ERROR_MESSAGE);
		
		btCadastrar = new JButton("Cadastrar");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbCep.setBounds(20, 100, 100, 25);
		tfCep.setBounds(100, 100, 100, 25);
		lbIdade.setBounds(20, 150, 100, 25);
		tfIdade.setBounds(100, 150, 100, 25);
		lbPlano.setBounds(20, 200, 100, 25);
		cbPlano.setBounds(100, 200, 100, 25);
		lbUnidade.setBounds(20, 250, 100, 25);		
		cbUnidade.setBounds(100, 250, 100, 25);
		btCadastrar.setBounds(330, 300, 300, 30);
		
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(lbMatricula);
		cp.add(lbCep);
		cp.add(lbIdade);
		cp.add(lbPlano);
		cp.add(lbUnidade);
		cp.add(tfNome);
		cp.add(tfCep);
		cp.add(tfIdade);
		cp.add(cbPlano);
		cp.add(cbUnidade);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btCadastrarAction();
			}
		});
	}
	
	private void btCadastrarAction() {
		List<String> erros = new ArrayList<String>();
		
		erros = new ClienteController().insereCliente(tfNome.getText(), tfCep.getText(), Integer.parseInt(tfIdade.getText()), 
														(Unidade) cbUnidade.getSelectedItem(), (Plano) cbPlano.getSelectedItem());
	}
	
}
