package academia.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

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
	
	public Index() {
		mbBarra = new  JMenuBar();
		mnCadastro = new JMenu("Cadastro");
		mnCadCliente =  new JMenu("Cliente");
		mnCadUnidade = new JMenu("Unidade");
		mnCadPlano = new JMenu("Plano");
		mnCadEquipamento = new JMenu("Equipamento");
		miCadCliente = new JMenuItem[2];
		miCadUnidade = new JMenuItem[2];
		miCadPlano = new JMenuItem[2];
		miCadEquipamento = new JMenuItem[2];
		miCadSair = new JMenuItem("Sair");
		
		setTitle("Sistema de Academia");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(180, 205, 205));
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
