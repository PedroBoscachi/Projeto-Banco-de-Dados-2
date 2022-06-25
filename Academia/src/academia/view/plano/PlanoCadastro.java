package academia.view.plano;

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
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import academia.controller.PlanoController;

@SuppressWarnings("serial")
public class PlanoCadastro extends JDialog {
	private JLabel lbTitulo, lbNome, lbPeriodo, lbValor;
	private JTextField tfNome;
	private JFormattedTextField tfValor;
	private JComboBox<Integer> cbPeriodo;
	private JButton btCadastrar;
	private Container cp;
	
	public PlanoCadastro() {
		setTitle("Cadastro de Plano"); 
		setSize(1000, 800); 
		setLocationRelativeTo(null); 
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de plano");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome");
		lbPeriodo = new JLabel("Período");
		lbValor = new JLabel("Valor");
		
		tfNome = new JTextField();
		
		tfValor = new JFormattedTextField();
		DecimalFormat df = new DecimalFormat("##,##0.00");
		NumberFormatter nf = new NumberFormatter();
        nf.setFormat(df); 
        nf.setAllowsInvalid(false);
        tfValor.setFormatterFactory(new DefaultFormatterFactory(nf));
        
        cbPeriodo = new JComboBox<>();
        for(int i = 0; i < 12; i++) {
        	cbPeriodo.addItem(i+1);
        }
        
        btCadastrar = new JButton("Cadastrar");
        
        cp = getContentPane(); 
		cp.setLayout(null); 
		cp.setBackground(new Color(180, 205, 205)); 
		
		lbTitulo.setBounds(125, 10, 300, 25); 
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbPeriodo.setBounds(20, 90, 100, 25);
		cbPeriodo.setBounds(50, 100, 100, 25);
		lbValor.setBounds(100, 150, 100, 25);
		tfValor.setBounds(100, 170, 100, 25);
		btCadastrar.setBounds(200, 250, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbPeriodo);
		cp.add(cbPeriodo);
		cp.add(lbValor);
		cp.add(tfValor);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btCadastrarAction();
			}
		});
	}
	
	private void btCadastrarAction() {
		double valor = 0;
		
		if(!tfValor.getText().equals(""))
			valor = Double.parseDouble(tfValor.getText().replace(".", "").replace(",", "."));
		
		List<String> erros = new ArrayList<String>();
		
		erros = new PlanoController().inserePlano(tfNome.getText(), (Integer)cbPeriodo.getSelectedItem(), valor);
	}
}
