package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.Conexao;
import dao.Host;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usuarioTxt;
	private JPasswordField senhaTxt;
	public JOptionPane alertas = new JOptionPane();
	public JDialog caixaDialogo = alertas.createDialog("ALERTA!");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/assets/icon2.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 278, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 162, 246, 180);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblBase = new JLabel("USU\u00C1RIO:");
		lblBase.setFont(new Font("Courier New", Font.BOLD, 12));
		lblBase.setBounds(10, 11, 444, 14);
		panel.add(lblBase);

		usuarioTxt = new JTextField();
		usuarioTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				usuarioTxt.setText(usuarioTxt.getText().toUpperCase());
			}
		});

		usuarioTxt.setFont(new Font("Courier New", Font.PLAIN, 12));
		usuarioTxt.setBounds(10, 25, 225, 20);
		panel.add(usuarioTxt);
		usuarioTxt.setColumns(10);

		JLabel lblSenha = new JLabel("SENHA:");
		lblSenha.setFont(new Font("Courier New", Font.BOLD, 12));
		lblSenha.setBounds(10, 53, 444, 14);
		panel.add(lblSenha);

		JLabel lblBase_1 = new JLabel("BASE:");
		lblBase_1.setFont(new Font("Courier New", Font.BOLD, 12));
		lblBase_1.setBounds(10, 98, 444, 14);
		panel.add(lblBase_1);

		senhaTxt = new JPasswordField();
		senhaTxt.setFont(new Font("Courier New", Font.PLAIN, 12));
		senhaTxt.setBounds(10, 67, 225, 20);
		panel.add(senhaTxt);

		JComboBox<String> baseTxt = new JComboBox<String>();
		baseTxt.setFont(new Font("Courier New", Font.BOLD, 12));
		baseTxt.setModel(new DefaultComboBoxModel<String>(new String[] { "FXPRD", "EBILPRD", "FXPRDE", "FARM11G" , "MGE_CS" }));
		baseTxt.setBounds(10, 111, 225, 20);
		panel.add(baseTxt);

		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// JMJR
				Conexao conexao = new Conexao();
				Host hosts = new Host();
				
				 //JMJR BYPASS 
				 usuarioTxt.setText("SANKHYA");
				 senhaTxt.setText("j1u2n3c4o5"); //JMJR BYPASS
				 
				String usuario = usuarioTxt.getText();
				@SuppressWarnings("deprecation")
				String senha = senhaTxt.getText();
				String base = baseTxt.getSelectedItem().toString();

				conexao.setUser(usuario);
				conexao.setPassword(senha);
				conexao.setStrHost(hosts.selecionarHost(base));

				try {

					AtualizadorInterface atualizador = new AtualizadorInterface();
					atualizador.setconn(conexao.conectar());
					atualizador.setVisible(true);
					dispose();

				} catch (SQLException e) {
					if (e.getErrorCode() == 1017) {
						alertas.setMessage("Usuário e/ou senhas inválidos");
						alertas.setMessageType(JOptionPane.WARNING_MESSAGE);
			
						caixaDialogo.setLocationRelativeTo(null);
						caixaDialogo.setAlwaysOnTop(true);
						caixaDialogo.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/assets/icon2.png")));
						caixaDialogo.setVisible(true);

					}
					e.printStackTrace();
				}
			}
		});
		btnConectar.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnConectar.setBounds(10, 143, 107, 23);
		panel.add(btnConectar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JMJR
				System.exit(0);

			}
		});
		btnCancelar.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnCancelar.setBounds(125, 142, 107, 23);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 47, 242, 103);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/assets/logoKyros.png")));
		lblNewLabel.setBounds(35, 11, 180, 87);
		panel_1.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 246, 25);
		contentPane.add(panel_2);

		JLabel lblAtualizadorExcel = new JLabel("ATUALIZADOR EXCEL - 2.0");
		lblAtualizadorExcel.setFont(new Font("Courier New", Font.BOLD, 12));
		panel_2.add(lblAtualizadorExcel);
	}
}
