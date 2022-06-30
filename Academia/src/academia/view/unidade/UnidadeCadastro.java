package academia.view.unidade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import academia.controller.UnidadeController;

public class UnidadeCadastro extends JDialog {
	Icon icon = new ImageIcon("C:\\Users\\lucas\\OneDrive\\Área de Trabalho\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
	private JLabel lbTitulo, lbNome, lbEstado, lbCidade, lbRua;
	private JTextField tfNome, tfCidade, tfRua;
	private static final String valEstado[] = {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
												"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"};
	private JRadioButton rbEstado[];
	private ButtonGroup bgEstado;
	private JButton btCadastrar;
	private Container cp;
	private JComboBox<String> cbEstado;
	
	public UnidadeCadastro() {
		setTitle("Cadastro de Unidade");
		setSize(500, 270);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Unidade",icon,SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome:");
		lbEstado = new JLabel("Estado:");
		lbCidade = new JLabel("Cidade:");
		lbRua = new JLabel("Rua:");
		
		tfNome = new JTextField();
		tfCidade = new JTextField();
		tfRua = new JTextField();
	
		bgEstado = new ButtonGroup();
		
		cbEstado = new JComboBox<>();
        for(int i = 0; i < 27; i++) {
        	cbEstado.addItem(valEstado[i]);
        }
		
		
		btCadastrar = new JButton("Cadastrar");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(46, 46, 46));
		
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbEstado.setForeground(new Color(255, 255, 255));
		lbCidade.setForeground(new Color(255, 255, 255));
		lbRua.setForeground(new Color(255, 255, 255));
		
		lbTitulo.setBounds(90, 10, 300, 20);
		lbNome.setBounds(20, 50, 100, 20);
		tfNome.setBounds(100, 50, 360, 20);
		lbEstado.setBounds(20, 90, 50, 20);
		lbCidade.setBounds(220, 90, 100, 20);
		tfCidade.setBounds(280, 90, 180, 20);
		lbRua.setBounds(20, 130, 100, 20);
		tfRua.setBounds(100, 130, 360, 20);
		cbEstado.setBounds(100,90,80,20);
		btCadastrar.setBounds(190, 180, 100, 20);
		
		
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbEstado);
		cp.add(cbEstado);
		cp.add(lbCidade);
		cp.add(tfCidade);
		cp.add(lbRua);
		cp.add(tfRua);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btCadastrarAction();
			}
		});
	}
	
	private void btCadastrarAction() {
		String estado = null;
	
		List<String> erros = new ArrayList<String>();
		erros = new UnidadeController().insereUnidade(tfNome.getText(), cbEstado.getSelectedItem()+"", tfCidade.getText(), tfRua.getText());
		
		JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso:\n", 
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
			tfNome.setText("");
			tfRua.setText("");
			tfCidade.setText("");
			tfNome.setText("");
	}
}
