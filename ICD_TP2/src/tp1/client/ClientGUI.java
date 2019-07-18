package tp1.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JCheckBox;

public class ClientGUI {

	private JFrame frame;
	public JTextArea textArea;
	private JTextField inputResposta;
	private JLabel lblResposta;
	private JPanel panel;
	private JTextField inNAluno;
	private JPasswordField inPassword;
	private JButton btnVerPerguntas;
	private JButton btnAddPergunta;
	private JButton btnSeleccionarPergunta;
	private JScrollPane scrollBar; 
	private JTextField tituloPergunta;
	private JLabel lblTitulo;
	private JLabel lblAdicionarNovaPergunta;
	private JTextField inTema;
	private JLabel lblTema;
	private JLabel lblAdicioneAt;
	private JTextField respPossivel_1;
	private JTextField respPossivel_3;
	private JTextField respPossivel_2;
	private JTextField respPossivel_4;
	private JButton btnSubmit;
	private JButton btnAdicionarPergunta;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
		scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 11, 414, 81);
		
		panel.add(scrollBar);
		showLogin();
		
	}
	
	//check
	public void showLogin() {
		limparPanel();
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(201, 163, 89, 23);
		panel.add(btnSubmit);
		
		inNAluno = new JTextField();
		inNAluno.setBounds(201, 86, 89, 20);
		panel.add(inNAluno);
		inNAluno.setColumns(10);
		
		inPassword = new JPasswordField();
		inPassword.setBounds(201, 117, 89, 20);
		panel.add(inPassword);
		
		JLabel nAluno = new JLabel("N\u00BA Aluno:");
		nAluno.setBounds(127, 89, 64, 17);
		panel.add(nAluno);
		
		JLabel inPassword = new JLabel("Password:");
		inPassword.setBounds(127, 117, 64, 20);
		panel.add(inPassword);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panel.repaint();
		
	}
	
	//menu do prof
	//check
	public void showMenu() {
		limparPanel();
		btnVerPerguntas = new JButton("Ver Perguntas");
		btnVerPerguntas.setBounds(166, 103, 133, 31);
		panel.add(btnVerPerguntas);
		
		btnAddPergunta = new JButton("Adicionar Pergunta");
		btnAddPergunta.setBounds(166, 145, 133, 31);
		panel.add(btnAddPergunta);
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.setBounds(166, 187, 133, 31);
		panel.add(btnSeleccionarPergunta);
		panel.repaint();
	}
	//check
	public void showInterfaceAluno() {
		limparPanel();
		textArea.setBounds(10, 23, 414, 98);
		panel.add(textArea);
		
		inputResposta = new JTextField();
		inputResposta.setBounds(76, 132, 86, 20);
		panel.add(inputResposta);
		inputResposta.setColumns(10);
		
		lblResposta = new JLabel("Resposta:");
		lblResposta.setBounds(10, 135, 61, 14);
		panel.add(lblResposta);
		
		JButton submitResposta = new JButton("Submeter");
		submitResposta.setBounds(200, 215, 89, 23);
		panel.add(submitResposta);
		panel.repaint();
	}
	
	//check
	public void showAdicionarPergunta() {
		limparPanel();
		tituloPergunta = new JTextField();
		tituloPergunta.setBounds(66, 121, 122, 20);
		panel.add(tituloPergunta);
		tituloPergunta.setColumns(10);
		
		lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 124, 46, 14);
		panel.add(lblTitulo);
		
		lblAdicionarNovaPergunta = new JLabel("Adicionar nova Pergunta:");
		lblAdicionarNovaPergunta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdicionarNovaPergunta.setBounds(10, 95, 155, 20);
		panel.add(lblAdicionarNovaPergunta);
		
		inTema = new JTextField();
		inTema.setBounds(280, 121, 122, 20);
		panel.add(inTema);
		inTema.setColumns(10);
		
		lblTema = new JLabel("Tema:");
		lblTema.setBounds(224, 124, 46, 14);
		panel.add(lblTema);
		
		lblAdicioneAt = new JLabel("Adicione at\u00E9 4 respostas poss\u00EDveis:");
		lblAdicioneAt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdicioneAt.setBounds(10, 144, 209, 20);
		panel.add(lblAdicioneAt);
		
		respPossivel_1 = new JTextField();
		respPossivel_1.setBounds(10, 167, 209, 20);
		panel.add(respPossivel_1);
		respPossivel_1.setColumns(10);
		
		respPossivel_3 = new JTextField();
		respPossivel_3.setBounds(10, 198, 209, 20);
		panel.add(respPossivel_3);
		respPossivel_3.setColumns(10);
		
		respPossivel_2 = new JTextField();
		respPossivel_2.setBounds(224, 167, 200, 20);
		panel.add(respPossivel_2);
		respPossivel_2.setColumns(10);
		
		respPossivel_4 = new JTextField();
		respPossivel_4.setBounds(224, 198, 200, 20);
		panel.add(respPossivel_4);
		respPossivel_4.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(181, 228, 89, 23);
		panel.add(btnSubmit);
		
		
		panel.repaint();
	}
	//check
	public void showPerguntas() {
		limparPanel();
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.setBounds(160, 103, 133, 34);
		panel.add(btnSeleccionarPergunta);
		
		btnAdicionarPergunta = new JButton("Adicionar pergunta");
		btnAdicionarPergunta.setBounds(160, 148, 133, 34);
		panel.add(btnAdicionarPergunta);
		panel.repaint();
	}
	//check
	public void showAlunos() {
		limparPanel();
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.setBounds(160, 103, 133, 34);
		panel.add(btnSeleccionarPergunta);
		
		btnAdicionarPergunta = new JButton("Adicionar pergunta");
		btnAdicionarPergunta.setBounds(160, 148, 133, 34);
		panel.add(btnAdicionarPergunta);
		panel.repaint();

	}
	//check
	public void showEnviarPergunta() {
		limparPanel();
		JCheckBox chckbxTodos = new JCheckBox("Todos");
		chckbxTodos.setBounds(10, 114, 66, 31);
		panel.add(chckbxTodos);
		
		JLabel lblNaluno = new JLabel("N\u00BAAluno:");
		lblNaluno.setBounds(82, 122, 46, 14);
		panel.add(lblNaluno);
		
		textField = new JTextField();
		textField.setBounds(138, 119, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(138, 150, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblIdDaPergunta = new JLabel("ID da Pergunta:");
		lblIdDaPergunta.setBounds(51, 153, 77, 14);
		panel.add(lblIdDaPergunta);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(135, 211, 89, 23);
		panel.add(btnSubmit);
		
		panel.repaint();
	}
	
	public void limparPanel() {
		for (int i = 0; i < panel.getComponents().length; i++) {
			if(!(panel.getComponent(i) instanceof JTextArea) || !(panel.getComponent(i) instanceof JScrollPane)) {
				panel.remove(panel.getComponent(i));
			}
		}
	}
}
