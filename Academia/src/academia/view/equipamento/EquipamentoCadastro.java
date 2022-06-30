package academia.view.equipamento;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import academia.controller.EquipamentoController;
import academia.model.unidade.Unidade;

@SuppressWarnings("serial")
public class EquipamentoCadastro extends JDialog {
	Icon icon = new ImageIcon("C:\\Users\\lucas\\OneDrive\\Área de Trabalho\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
	private JLabel lbTitulo, lbNome, lbDescricao, lbValor, lbUnidade;
	private JTextField tfNome, tfDescricao;
	private JFormattedTextField tfValor;
	private JComboBox<Unidade> cbUnidade;
	private JButton btCadastrar;
	private Container cp;
	
	public EquipamentoCadastro() {
		setTitle("Cadastro de Funcionários"); 
		setSize(500, 300); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Equipamento",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome:");
		lbDescricao = new JLabel("Descrição:");
		lbValor = new JLabel("Valor (R$):");
		lbUnidade = new JLabel("Unidades:");
		
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
        
        if(unidades != null)
        	for(Unidade u : unidades)
        		cbUnidade.addItem(u);
        
        String erro = new EquipamentoController().getExcecao();
        
        if(erro != null)
        	JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados das unidades:\n" + erro, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        
        btCadastrar = new JButton("Cadastrar");
        
        cp = getContentPane(); 
		cp.setLayout(null); 
		cp.setBackground(new Color(46, 46, 46));
		
		lbTitulo.setBounds(90, 10, 300, 20); 
		lbNome.setBounds(20, 50, 100, 20);
		tfNome.setBounds(100, 50, 360, 20);
		lbValor.setBounds(20, 90, 100, 20);
		tfValor.setBounds(100, 90, 100, 20);
		lbDescricao.setBounds(20, 130, 100, 20);
		tfDescricao.setBounds(100, 130, 220, 20);
		lbUnidade.setBounds(20, 170, 220, 20);
		cbUnidade.setBounds(100, 170, 220, 20);
		btCadastrar.setBounds(170, 220, 150, 20);
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbValor.setForeground(new Color(255, 255, 255));
		lbUnidade.setForeground(new Color(255, 255, 255));
		lbDescricao.setForeground(new Color(255, 255, 255));
		
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbValor);
		cp.add(tfValor);
		cp.add(lbDescricao);
		cp.add(tfDescricao);
		cp.add(lbUnidade);
		cp.add(cbUnidade);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btCadastrarAction();
			}
		});
	}
	
	private void btCadastrarAction() {
		double valor = 0;
		
		if (!tfValor.getText().equals(""))
			valor = Double.parseDouble(tfValor.getText().replace(".", "").replace(",", "."));
		
		List<String> erros = new ArrayList<String>();
		
		erros = new EquipamentoController().insereEquipamento(tfNome.getText(), tfDescricao.getText(), valor, (Unidade) cbUnidade.getSelectedItem());
		
		JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso:\n", 
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);

			tfDescricao.setText("");
			tfNome.setText("");
			tfValor.setValue(0);
	}
}
