package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import dominio.ManipulaExcel;

import dominio.JulimarException;

public class AtualizadorInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCeulaDestino;
	private JFrame mainFrame;
	private Connection conn;
	String fileName;
	Integer linhaInicio;
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
					AtualizadorInterface frame = new AtualizadorInterface();
					frame.setVisible(true);
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
	public AtualizadorInterface() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AtualizadorInterface.class.getResource("/assets/icon2.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 650, 231);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("SCRIPT SQL:");
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 630, 14);
		panel.add(lblNewLabel);

		JPanel cp = new JPanel();
		cp.setBounds(10, 36, 630, 160);
		panel.add(cp);

		RSyntaxTextArea textAreaSQL = new RSyntaxTextArea(9, 80);
		textAreaSQL.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String sql = textAreaSQL.getText().replaceAll(";", "");
				textAreaSQL.setText(sql);
			}
		});
		textAreaSQL.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
		textAreaSQL.setCodeFoldingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textAreaSQL);
		cp.add(sp);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 204, 0));
		separator.setBounds(10, 207, 630, 13);
		panel.add(separator);

		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		panel_1.setBounds(10, 241, 650, 80);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblCelulasDestino = new JLabel("C\u00C9LULA(S) DESTINO:");
		lblCelulasDestino.setFont(new Font("Courier New", Font.BOLD, 12));
		lblCelulasDestino.setBounds(10, 11, 630, 14);
		panel_1.add(lblCelulasDestino);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 36, 630, 36);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		textCeulaDestino = new JTextField();
		textCeulaDestino.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String texto = textCeulaDestino.getText();

				textCeulaDestino.setText(texto.toUpperCase());

			}
		});
		textCeulaDestino.setToolTipText("EXEMPLO: (A,B,C,D)");
		textCeulaDestino.setFont(new Font("Courier New", Font.PLAIN, 12));
		textCeulaDestino.setBounds(10, 5, 610, 25);
		panel_2.add(textCeulaDestino);
		textCeulaDestino.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setToolTipText("");
		panel_3.setBounds(10, 321, 650, 80);
		contentPane.add(panel_3);

		JLabel lblOpes = new JLabel("CONFIGURA\u00C7\u00D5ES BANCO:");
		lblOpes.setFont(new Font("Courier New", Font.BOLD, 12));
		lblOpes.setBounds(10, 11, 630, 14);
		panel_3.add(lblOpes);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(10, 36, 630, 36);
		panel_3.add(panel_4);

		JCheckBox checkUserExec = new JCheckBox("USER_EXEC");
		checkUserExec.setEnabled(false);
		checkUserExec.setFont(new Font("Courier New", Font.PLAIN, 12));
		checkUserExec.setBounds(383, 7, 89, 23);
		panel_4.add(checkUserExec);

		JCheckBox checkCopiaLinha = new JCheckBox("+1 LINHA");
		checkCopiaLinha.setEnabled(false);
		checkCopiaLinha.setFont(new Font("Courier New", Font.PLAIN, 12));
		checkCopiaLinha.setBounds(474, 7, 81, 23);
		panel_4.add(checkCopiaLinha);

		JCheckBox checkCommit = new JCheckBox("COMMIT");
		checkCommit.setEnabled(false);
		checkCommit.setFont(new Font("Courier New", Font.PLAIN, 12));
		checkCommit.setBounds(557, 7, 67, 23);
		panel_4.add(checkCommit);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setToolTipText("");
		panel_5.setBounds(10, 481, 650, 62);
		contentPane.add(panel_5);

		JLabel lblIniciarProcesso = new JLabel("INICIAR PROCESSO:");
		lblIniciarProcesso.setFont(new Font("Courier New", Font.BOLD, 12));
		lblIniciarProcesso.setBounds(10, 11, 630, 14);
		panel_5.add(lblIniciarProcesso);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBounds(10, 22, 630, 36);
		panel_5.add(panel_6);

		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setToolTipText("");
		panel_7.setBounds(10, 401, 650, 80);
		contentPane.add(panel_7);

		JLabel lblConfiguraesArquivo = new JLabel("ARQUIVO:");
		lblConfiguraesArquivo.setFont(new Font("Courier New", Font.BOLD, 12));
		lblConfiguraesArquivo.setBounds(10, 11, 630, 14);
		panel_7.add(lblConfiguraesArquivo);

		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		panel_8.setBounds(10, 36, 630, 36);
		panel_7.add(panel_8);

		final JFileChooser fc = new JFileChooser();

		JLabel lblArqSelec = new JLabel("");
		lblArqSelec.setFont(new Font("Courier New", Font.BOLD, 12));
		lblArqSelec.setBounds(10, 11, 481, 20);
		panel_8.add(lblArqSelec);

		JButton btnAbrir = new JButton("ABRIR");
		btnAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(mainFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fc.getSelectedFile();
					lblArqSelec.setText("Arquivo Selecionado :" + file.getName());
					fileName = fc.getSelectedFile().toString();
				} else {
					lblArqSelec.setText("Nenhum arquivo foi selecionado");
				}

				System.out.println(fileName);

			}
		});
		btnAbrir.setFont(new Font("Courier New", Font.BOLD, 12));
		btnAbrir.setBounds(501, 11, 119, 23);
		panel_8.add(btnAbrir);

		JButton btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				ManipulaExcel man = new ManipulaExcel();
				man.setConexao(conn);
				XSSFSheet folha = man.abreArquivo(fileName);
				try {
					man.montaConsulta(folha, textAreaSQL.getText(), textCeulaDestino.getText());

				} catch (JulimarException e) {
					alertas.setMessage(e.getMessage());
					alertas.setMessageType(JOptionPane.WARNING_MESSAGE);
					caixaDialogo.setSize(450, 135);
					caixaDialogo.setLocationRelativeTo(null);
					caixaDialogo.setIconImage(
							Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/assets/icon2.png")));
					caixaDialogo.setAlwaysOnTop(true);
					caixaDialogo.setVisible(true);

				}

			}
		});
		btnAtualizar.setFont(new Font("Courier New", Font.BOLD, 12));
		btnAtualizar.setBounds(501, 11, 119, 23);
		panel_6.add(btnAtualizar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setFont(new Font("Courier New", Font.BOLD, 12));
		btnCancelar.setBounds(377, 11, 119, 23);
		panel_6.add(btnCancelar);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

	}

	public Connection getconn() {
		return conn;
	}

	public void setconn(Connection conn) {
		this.conn = conn;
	}
}
