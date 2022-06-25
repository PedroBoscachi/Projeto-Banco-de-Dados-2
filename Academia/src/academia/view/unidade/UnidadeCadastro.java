package academia.view.unidade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import academia.controller.UnidadeController;

public class UnidadeCadastro extends JDialog {
	private JLabel lbTitulo, lbNome, lbEstado, lbCidade, lbRua;
	private JTextField tfNome, tfCidade, tfRua;
	private static final String valEstado[] = {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
												"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"};
	private JRadioButton rbEstado[];
	private ButtonGroup bgEstado;
	private JButton btCadastrar;
	private Container cp;
	
	public UnidadeCadastro() {
		setTitle("Cadastro de Unidade");
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Unidade");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome");
		lbEstado = new JLabel("Estado");
		lbCidade = new JLabel("Cidade");
		lbRua = new JLabel("Rua");
		
		tfNome = new JTextField();
		tfCidade = new JTextField();
		tfRua = new JTextField();
		
		rbEstado = new JRadioButton[27];
		bgEstado = new ButtonGroup();
		for(int i = 0; i < rbEstado.length; i++) {
			rbEstado[i] = new JRadioButton(valEstado[i]);
			rbEstado[i].setBackground(new Color(180, 205, 205));
			bgEstado.add(rbEstado[i]);
		}
		rbEstado[0].setSelected(true);
		
		btCadastrar = new JButton("Cadastrar");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbEstado.setBounds(20, 90, 100, 25);
		rbEstado[0].setBounds(100, 100, 100, 25);
		rbEstado[1].setBounds(100, 120, 100, 25);
		rbEstado[2].setBounds(100, 140, 100, 25);
		rbEstado[3].setBounds(100, 160, 100, 25);
		rbEstado[4].setBounds(100, 180, 100, 25);
		rbEstado[5].setBounds(100, 200, 100, 25);
		rbEstado[6].setBounds(100, 220, 100, 25);
		rbEstado[7].setBounds(100, 240, 100, 25);
		rbEstado[8].setBounds(100, 260, 100, 25);
		rbEstado[9].setBounds(100, 280, 100, 25);
		rbEstado[10].setBounds(100, 300, 100, 25);
		rbEstado[11].setBounds(100, 320, 100, 25);
		rbEstado[12].setBounds(100, 340, 100, 25);
		rbEstado[13].setBounds(100, 360, 100, 25);
		rbEstado[14].setBounds(100, 380, 100, 25);
		rbEstado[15].setBounds(100, 400, 100, 25);
		rbEstado[16].setBounds(100, 420, 100, 25);
		rbEstado[17].setBounds(100, 440, 100, 25);
		rbEstado[18].setBounds(100, 460, 100, 25);
		rbEstado[19].setBounds(100, 480, 100, 25);
		rbEstado[20].setBounds(100, 500, 100, 25);
		rbEstado[21].setBounds(100, 520, 100, 25);
		rbEstado[22].setBounds(100, 540, 100, 25);
		rbEstado[23].setBounds(100, 560, 100, 25);
		rbEstado[24].setBounds(100, 580, 100, 25);
		rbEstado[25].setBounds(100, 600, 100, 25);
		rbEstado[26].setBounds(100, 620, 100, 25);
		lbCidade.setBounds(200, 100, 100, 25);
		tfCidade.setBounds(200, 120, 100, 25);
		lbRua.setBounds(400, 100, 100, 25);
		tfRua.setBounds(400, 120, 100, 25);
		btCadastrar.setBounds(400, 400, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbEstado);
		cp.add(rbEstado[0]);
		cp.add(rbEstado[1]);
		cp.add(rbEstado[2]);
		cp.add(rbEstado[3]);
		cp.add(rbEstado[4]);
		cp.add(rbEstado[5]);
		cp.add(rbEstado[6]);
		cp.add(rbEstado[7]);
		cp.add(rbEstado[8]);
		cp.add(rbEstado[9]);
		cp.add(rbEstado[10]);
		cp.add(rbEstado[11]);
		cp.add(rbEstado[12]);
		cp.add(rbEstado[13]);
		cp.add(rbEstado[14]);
		cp.add(rbEstado[15]);
		cp.add(rbEstado[16]);
		cp.add(rbEstado[17]);
		cp.add(rbEstado[18]);
		cp.add(rbEstado[19]);
		cp.add(rbEstado[20]);
		cp.add(rbEstado[21]);
		cp.add(rbEstado[22]);
		cp.add(rbEstado[23]);
		cp.add(rbEstado[24]);
		cp.add(rbEstado[25]);
		cp.add(rbEstado[26]);
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
		
		for(JRadioButton rb : rbEstado)
			if(rb.isSelected())
				estado = rb.getText();
		
		List<String> erros = new ArrayList<String>();
		erros = new UnidadeController().insereUnidade(tfNome.getText(), estado, tfCidade.getText(), tfRua.getText());
	}
}
