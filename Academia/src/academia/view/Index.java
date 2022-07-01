package academia.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import academia.view.cliente.ClienteCadastro;
import academia.view.cliente.ClienteConsulta;
import academia.view.equipamento.EquipamentoCadastro;
import academia.view.equipamento.EquipamentoConsulta;
import academia.view.plano.PlanoCadastro;
import academia.view.plano.PlanoConsulta;
import academia.view.unidade.UnidadeCadastro;
import academia.view.unidade.UnidadeConsulta;

@SuppressWarnings("serial")
public class Index extends JFrame {
	private JMenuBar mbBarra;
	private JMenu mnCadastro;
	
	private JMenu mnCadCliente;
	private JMenu mnCadUnidade;
	private JMenu mnCadPlano;
	private JMenu mnCadEquipamento;
	private JMenuItem miCadCliente[];
	private JMenuItem miCadUnidade[];
	private JMenuItem miCadPlano[];
	private JMenuItem miCadEquipamento[];
	private static final String cadItens[] = { "Cadastrar", "Consultar" };
	private JMenuItem miCadSair;
	private JLabel lbTitulo;

	
	public Index() {
		Icon icon = new ImageIcon("D:\\\\LIÇÕES IFSP\\\\3 SEMESTRE\\\\BD2A3\\\\Projeto\\\\Projeto-Academia\\\\Projeto-Banco-de-Dados-2\\\\Academia\\\\src\\\\academia\\\\view\\\\icon.png");
		mbBarra = new  JMenuBar();
		mnCadastro = new JMenu("Cadastro");
		mnCadastro.setFont(new Font("Roboto", Font.BOLD, 15)); 
	
		mnCadCliente =  new JMenu("Aluno");
		mnCadUnidade = new JMenu("Unidade");
		mnCadPlano = new JMenu("Plano");
		mnCadEquipamento = new JMenu("Equipamento");
		miCadCliente = new JMenuItem[2];
		miCadUnidade = new JMenuItem[2];
		miCadPlano = new JMenuItem[2];
		miCadEquipamento = new JMenuItem[2];
		miCadSair = new JMenuItem("Sair");
		lbTitulo = new JLabel("GYM Solutions", icon, SwingConstants.CENTER);
		lbTitulo.setFont(new Font("Roboto", Font.BOLD, 55)); 
		lbTitulo.setIcon(icon);
		lbTitulo.setForeground(new Color(212, 212, 212));
		  Border blackline = BorderFactory.createLineBorder(Color.black);
		mbBarra.setBorder(blackline);
	
		
		setTitle("GYM Solutions");
		mnCadCliente.setFont(new Font("Roboto", Font.BOLD, 15)); 
		mnCadUnidade.setFont(new Font("Roboto", Font.BOLD, 15)); 
		mnCadPlano.setFont(new Font("Roboto", Font.BOLD, 15)); 
		mnCadEquipamento.setFont(new Font("Roboto", Font.BOLD, 15)); 
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(46, 46, 46));
		getContentPane().add(lbTitulo);
		for(int i = 0; i < 2; i++) {
			miCadCliente[i] = new JMenuItem(cadItens[i]);
			mnCadCliente.add(miCadCliente[i]);
			miCadUnidade[i] = new JMenuItem(cadItens[i]);
			mnCadUnidade.add(miCadUnidade[i]);
			miCadPlano[i] = new JMenuItem(cadItens[i]);
			mnCadPlano.add(miCadPlano[i]);
			miCadEquipamento[i] = new JMenuItem(cadItens[i]);
			mnCadEquipamento.add(miCadEquipamento[i]);
		}
		lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		mnCadastro.add(mnCadCliente);
		mnCadastro.add(mnCadUnidade);
		mnCadastro.add(mnCadPlano);
		mnCadastro.add(mnCadEquipamento);
		mnCadastro.addSeparator();
		mnCadastro.add(miCadSair);
		mbBarra.add(mnCadastro);
		setJMenuBar(mbBarra);
		
		
		miCadCliente[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miCadClienteAction(); }
		});
		miCadCliente[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miConsClienteAction(); }
		});
		
		miCadUnidade[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miCadUnidadeAction(); }
		});
		miCadUnidade[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miConsUnidadeAction(); }
		});
		
		miCadPlano[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miCadPlanoAction(); }
		});
		miCadPlano[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miConsPlanoAction(); }
		});
		
		miCadEquipamento[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miCadEquipamentoAction(); }
		});
		miCadEquipamento[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { miConsEquipamentoAction(); }
		});
		
		miCadSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { System.exit(0);}
		});
	}
	
	public void miCadClienteAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new ClienteCadastro().setVisible(true); }
		});
	}
	private void miConsClienteAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new ClienteConsulta().setVisible(true); }
		});
	}
	
	public void miCadUnidadeAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new UnidadeCadastro().setVisible(true); }
		});
	}
	private void miConsUnidadeAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new UnidadeConsulta().setVisible(true); }
		});
	}
	
	public void miCadPlanoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new PlanoCadastro().setVisible(true); }
		});
	}
	private void miConsPlanoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new PlanoConsulta().setVisible(true); }
		});
	}
	
	public void miCadEquipamentoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new EquipamentoCadastro().setVisible(true); }
		});
	}
	private void miConsEquipamentoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() { new EquipamentoConsulta().setVisible(true); }
		});
	}
	
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){ new Index().setVisible(true); }});
	}
}
