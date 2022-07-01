package academia.view.cliente;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import academia.controller.ClienteController;
import academia.controller.PlanoController;
import academia.model.plano.Plano;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class ClienteCadastro extends JDialog {
	Icon icon = new ImageIcon("D:\\LIÇÕES IFSP\\3 SEMESTRE\\BD2A3\\Projeto\\Projeto-Academia\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
	private JLabel lbTitulo, lbNome, lbMatricula, lbCep, lbIdade, lbPlano, lbUnidade;
	private JTextField tfNome;
	//tfCep, tfIdade; 
	private JComboBox<Plano> cbPlano;
	private JComboBox<Unidade> cbUnidade;
	private JButton btCadastrar;
	private Container cp;
	private JFormattedTextField tfCep, tfIdade;
	
	public ClienteCadastro() {
		setTitle("Cadastro de Cliente");
		setSize(550, 400);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Cliente",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		lbNome = new JLabel("Nome:");
		lbMatricula = new JLabel("Matrícula:");
		lbCep = new JLabel("CEP:");
		lbIdade = new JLabel("Data de nascimento:");
		lbPlano = new JLabel("Plano:");
		lbUnidade = new JLabel("Unidade:");
		
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbMatricula.setForeground(new Color(255, 255, 255));
		lbCep.setForeground(new Color(255, 255, 255));
		lbIdade.setForeground(new Color(255, 255, 255));
		lbPlano.setForeground(new Color(255, 255, 255));
		lbUnidade.setForeground(new Color(255, 255, 255));
		MaskFormatter mascaraCep = null;
		 MaskFormatter mascaraData = null;
		try {
			mascaraCep = new MaskFormatter("#####-###");
			 mascaraData = new MaskFormatter("##/##/####");
			 mascaraCep.setPlaceholderCharacter('_');
			 mascaraData.setPlaceholderCharacter('_');
		} catch (Exception e) {
			System.out.print("erro");
		}
		
	  tfCep = new JFormattedTextField(mascaraCep);
	  tfIdade = new JFormattedTextField(mascaraData);
	// tfCep = new JTextField();
	 
		tfNome = new JTextField();
		
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
		cp.setBackground(new Color(46, 46, 46));
	
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(150, 50, 360, 25);
		lbCep.setBounds(20, 100, 100, 25);
		tfCep.setBounds(150, 100, 100, 25);
		lbIdade.setBounds(20, 150, 140, 25);
		tfIdade.setBounds(150, 150, 100, 25);
		lbPlano.setBounds(20, 200, 100, 25);
		cbPlano.setBounds(150, 200, 100, 25);
		lbUnidade.setBounds(20, 250, 100, 25);		
		cbUnidade.setBounds(150, 250, 150, 25);
		btCadastrar.setBounds(190, 310, 150, 30);
		
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
		
		erros = new ClienteController().insereCliente(tfNome.getText(), tfCep.getText(), tfIdade.getText(), 
														(Unidade) cbUnidade.getSelectedItem(), (Plano) cbPlano.getSelectedItem());

			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso:\n", 
					                      "Cadastro", JOptionPane.INFORMATION_MESSAGE);
			
			tfCep.setText("");
			tfIdade.setText("");
			tfNome.setText("");
			
	}
	
}
