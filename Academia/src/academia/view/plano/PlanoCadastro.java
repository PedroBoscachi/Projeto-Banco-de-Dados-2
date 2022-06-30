package academia.view.plano;

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

import academia.controller.PlanoController;

@SuppressWarnings("serial")
public class PlanoCadastro extends JDialog {
	Icon icon = new ImageIcon("C:\\Users\\lucas\\OneDrive\\Área de Trabalho\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
	private JLabel lbTitulo, lbNome, lbPeriodo, lbValor;
	private JTextField tfNome;
	private JFormattedTextField tfValor;
	private JComboBox<Integer> cbPeriodo;
	private JButton btCadastrar;
	private Container cp;
	
	public PlanoCadastro() {
		setTitle("Cadastro de Plano"); 
		setSize(550, 250);
		setLocationRelativeTo(null); 
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de plano",icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome:");
		lbPeriodo = new JLabel("Duração (meses):");
		lbValor = new JLabel("Valor (R$):");
		
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
		cp.setBackground(new Color(46, 46, 46)); 
		
		lbTitulo.setBounds(115, 10, 300, 20); 
		lbNome.setBounds(20, 50, 100, 20);
		tfNome.setBounds(150, 50, 360, 20);
		lbPeriodo.setBounds(20, 90, 120, 20);
		cbPeriodo.setBounds(150, 90, 100, 20);
		lbValor.setBounds(20, 130, 100, 20);
		tfValor.setBounds(150, 130, 100, 20);
		btCadastrar.setBounds(215, 180, 100, 20);
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbPeriodo.setForeground(new Color(255, 255, 255));
		lbValor.setForeground(new Color(255, 255, 255));
		
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
		
		JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso:\n", 
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
			tfNome.setText("");
			tfValor.setValue(0);
	}
}
