package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Client;

public class Tela2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAccessKey;
	private JTextField txtSecretKey;
	private JTextField txtRegiao;
	private JTextField txtBucket;
	private JTextField txtConvenio;

	public static void main(String[] args) {
		Client cli = null;
		try {
			cli = ClientHelper.carregaClient();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(cli.getName());
		cli.setAccessKey("keeeey");
		cli.setBucket("bucket");
		cli.setName("NOMEEEEE");
		cli.setRegion("REGIAO");
		cli.setSecretKey("psiu");
		try {
			ClientHelper.salvaClient(cli);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela2 frame = new Tela2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Tela2() throws MalformedURLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 400, 407, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel panel = new JPanel();
		panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblAccessKey = new JLabel("Access Key");
		lblAccessKey.setBounds(12, 33, 114, 28);
		panel.add(lblAccessKey);

		JLabel lblSecretKey = new JLabel("Secret Key");
		lblSecretKey.setBounds(12, 87, 102, 17);
		panel.add(lblSecretKey);

		JLabel lblRegiao = new JLabel("Região");
		lblRegiao.setBounds(12, 129, 70, 15);
		panel.add(lblRegiao);

		JLabel lblBucket = new JLabel("Bucket");
		lblBucket.setBounds(12, 176, 70, 15);
		panel.add(lblBucket);

		JLabel lblConvnio = new JLabel("Convênio");
		lblConvnio.setBounds(12, 220, 70, 15);
		panel.add(lblConvnio);

		txtAccessKey = new JTextField();
		txtAccessKey.setBounds(167, 34, 193, 28);
		panel.add(txtAccessKey);
		txtAccessKey.setColumns(10);

		txtSecretKey = new JTextField();
		txtSecretKey.setBounds(167, 82, 193, 28);
		panel.add(txtSecretKey);
		txtSecretKey.setColumns(10);

		txtRegiao = new JTextField();
		txtRegiao.setBounds(167, 127, 193, 28);
		panel.add(txtRegiao);
		txtRegiao.setColumns(10);

		txtBucket = new JTextField();
		txtBucket.setBounds(167, 174, 193, 28);
		panel.add(txtBucket);
		txtBucket.setColumns(10);

		txtConvenio = new JTextField();
		txtConvenio.setBounds(167, 214, 193, 28);
		panel.add(txtConvenio);
		txtConvenio.setColumns(10);

		JButton btnBotao = new JButton("Botao");
		btnBotao.setBounds(145, 267, 117, 25);
		panel.add(btnBotao);

		final JPanel panelLoader = new JPanel();
		panelLoader.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(panelLoader);
		URL url = new URL("https://loading.io/spinners/flat-ball/lg.flat-bouncing-circle-loading-icon.gif");
		ImageIcon icon = new ImageIcon(url);
		JLabel lblLoader = new JLabel(icon);
		panelLoader.add(lblLoader);
		panelLoader.setVisible(false);

		btnBotao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				panelLoader.setVisible(true);
			}
		});
	}
}
