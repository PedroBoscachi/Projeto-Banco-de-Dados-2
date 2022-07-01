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
public class PlanoAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbPeriodo, lbValor;
	private JTextField tfNome;
	private JFormattedTextField tfValor;
	private JComboBox<Integer> cbPeriodo;
	private JButton btSalvar;
	private Container cp; 
	private int idPlano; 
	private int linhaSelecionada; 
	private PlanoModeloTabela mtTabela; 

	public PlanoAlteracao(int idPlano, String nome, Integer periodo, String valor, int linha, PlanoModeloTabela mtTabela) { 
		Icon icon = new ImageIcon("D:\\LIÇÕES IFSP\\3 SEMESTRE\\BD2A3\\Projeto\\Projeto-Academia\\Projeto-Banco-de-Dados-2\\Academia\\src\\academia\\view\\caneta.png");
		setTitle("Alteração de Planos"); 
		setSize(600, 335); 
		setLocationRelativeTo(null); 
		setModal(true); 
		
		lbTitulo = new JLabel("Alteração de Planos", icon, SwingConstants.CENTER);
		lbTitulo.setForeground(new Color(255, 255, 255));
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); 
		
		lbNome = new JLabel("Nome:");
		lbPeriodo = new JLabel("Duração (meses):");
		lbValor = new JLabel("Valor (R$):");
		
		tfNome = new JTextField();
		
		lbPeriodo.setForeground(new Color(255, 255, 255));
		lbNome.setForeground(new Color(255, 255, 255));
		lbValor.setForeground(new Color(255, 255, 255));
		
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
		
		String erro = new PlanoController().getExcecao();
		
		if (erro != null) 
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos planos:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idPlano = idPlano; 
		
		tfNome.setText(nome);
		
		tfValor.setText(valor.toString());
		
		for (int i = 0; i < cbPeriodo.getItemCount(); i++)
			if (cbPeriodo.getItemAt(i).equals(periodo))
				cbPeriodo.setSelectedItem(cbPeriodo.getItemAt(i));
		
		this.linhaSelecionada = linha; 
		this.mtTabela = mtTabela; 

		cp = getContentPane(); 
		cp.setLayout(null); 
		cp.setBackground(new Color(46, 46, 46)); 

		lbTitulo.setBounds(155, 10, 300, 25); 
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(150, 50, 360, 25);
		lbPeriodo.setBounds(20, 90, 120, 25);
		cbPeriodo.setBounds(150, 90, 100, 25);
		lbValor.setBounds(20, 130, 100, 25);
		tfValor.setBounds(150, 130, 100, 25);
		btSalvar.setBounds(240, 250, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbPeriodo);
		cp.add(cbPeriodo);
		cp.add(lbValor);
		cp.add(tfValor);
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
			valor = Double.parseDouble(tfValor.getText().replace(".", "").replace(",", "."));
		
		List<String> erros = new ArrayList<String>();
		
		erros = new PlanoController().alteraPlano(this.idPlano,
															  tfNome.getText(),
											                  (Integer) cbPeriodo.getSelectedItem(),
											                  valor);	
		
		if (erros.get(0) == null) { 
			JOptionPane.showMessageDialog(this, "Plano alterado com sucesso.", 
                    					  "Informação", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbPeriodo.getSelectedItem(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(valor, this.linhaSelecionada, 3);
			this.setVisible(false); 
		} else { 
			String mensagem = "Não foi possível alterar o plano:\n";
			for (String e : erros) 
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
