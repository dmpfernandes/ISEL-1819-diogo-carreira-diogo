package tp1.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ClientGUI {

	Client c;
	MessageReceiver mr;
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
	private JTextField inDuracao;
	
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
		c = new Client();
		mr = new MessageReceiver(this,c);
		Thread t = new Thread(mr);
		t.start();
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
		
		
		
		
//		showLogin();
//		showAdicionarPergunta();
//		showEnviarPergunta();
//		showAlunos();
//		showInterfaceAluno();
//		showMenu();
		showPerguntas();
	}
	
	//check
	public void showLogin() {
		limparPanel();
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = inNAluno.getText();
				String pass = new String(inPassword.getPassword());
				String xmlLogin = "<login numero='"+user+"' pass='"+pass+"'/>";
				c.enviarMsg(xmlLogin);
				System.out.println(xmlLogin);
			}
		});
		btnSubmit.setBounds(205, 184, 89, 23);
		panel.add(btnSubmit);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		textArea.setEditable(false);

		scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 11, 414, 81);
		scrollBar.setFocusable(false);
		panel.add(scrollBar);
		
		inNAluno = new JTextField();
		inNAluno.setBounds(205, 107, 89, 20);
		panel.add(inNAluno);
		inNAluno.setColumns(10);
		
		inPassword = new JPasswordField();
		inPassword.setBounds(205, 138, 89, 20);
		panel.add(inPassword);
		
		JLabel nAluno = new JLabel("N\u00BA Aluno:");
		nAluno.setBounds(92, 110, 103, 17);
		panel.add(nAluno);
		
		JLabel inPassword = new JLabel("Password:");
		inPassword.setBounds(92, 138, 103, 20);
		panel.add(inPassword);
		
		
		
		panel.revalidate();
		panel.repaint();
		
	}
	
	//menu do prof
	//check
	public void showMenu() {
		limparPanel();
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
		btnVerPerguntas = new JButton("Ver Perguntas");
		btnVerPerguntas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPerguntas();
			}
		});
		btnVerPerguntas.setBounds(166, 103, 133, 31);
		panel.add(btnVerPerguntas);
		
		btnAddPergunta = new JButton("Adicionar Pergunta");
		btnAddPergunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAdicionarPergunta();
			}
		});
		btnAddPergunta.setBounds(166, 145, 133, 31);
		panel.add(btnAddPergunta);
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEnviarPergunta();
			}
		});
		btnSeleccionarPergunta.setBounds(166, 187, 133, 31);
		panel.add(btnSeleccionarPergunta);
		
		panel.revalidate();
		panel.repaint();
	}
	//check
	public void showInterfaceAluno() {
		limparPanel();
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
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
		
		panel.revalidate();
		panel.repaint();
	}
	
	//check
	public void showAdicionarPergunta() {
		limparPanel();
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
		tituloPergunta = new JTextField();
		tituloPergunta.setBounds(66, 121, 122, 20);
		panel.add(tituloPergunta);
		tituloPergunta.setColumns(10);
		
		lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 124, 46, 14);
		panel.add(lblTitulo);
		
		lblAdicionarNovaPergunta = new JLabel("Adicionar nova Pergunta:");
		lblAdicionarNovaPergunta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdicionarNovaPergunta.setBounds(10, 95, 200, 20);
		panel.add(lblAdicionarNovaPergunta);
		
		inTema = new JTextField();
		inTema.setBounds(280, 121, 122, 20);
		panel.add(inTema);
		inTema.setColumns(10);
		
		lblTema = new JLabel("Tema:");
		lblTema.setBounds(224, 123, 46, 14);
		panel.add(lblTema);
		
		lblAdicioneAt = new JLabel("Adicione at\u00E9 4 respostas poss\u00EDveis:");
		lblAdicioneAt.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblAdicioneAt.setBounds(10, 144, 200, 20);
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
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* <adicionarPergunta>
				 * 	<pergunta  tema="basic" duracao="20">
				 * 		<texto>Quantos quadrados estao presentes na seguinte imagem?</texto>
				 * 		<anexoMultimedia>squares.jpeg</anexoMultimedia>
				 * 		<respPossiveis>
				 * 			<resp>8</resp>
				 * 			<resp>10</resp>
				 * 			<resp>11</resp>
				 * 			<resp>12</resp>
				 * 		</respPossiveis></pergunta>
				 * </adicionarPergunta>
				 * */
				ArrayList<String> resps = getRespostasPossiveis();
				if(!tituloPergunta.getText().isEmpty() && !inTema.getText().isEmpty() && resps.size() >= 1 ) {
					String msg = "<adicionarPergunta><pergunta  tema='" + inTema.getText() + "' duracao='" + inDuracao.getText() + "'><texto>"
				 + tituloPergunta.getText() + "</texto><anexoMultimedia></anexoMultimedia><respPossiveis>";
					
					for(int i = 0; i<resps.size(); i++) {
						msg = msg + "<resp>" + resps.get(i) + "</resp>";
					}
					msg = msg + "</respPossiveis></pergunta></adicionarPergunta>";
					c.enviarMsg(msg);
				}
				
			}

			private ArrayList<String> getRespostasPossiveis() {
				ArrayList<String> resp = new ArrayList<>();
				if(!respPossivel_1.getText().isEmpty()) resp.add(respPossivel_1.getText());
				if(!respPossivel_2.getText().isEmpty()) resp.add(respPossivel_2.getText());
				if(!respPossivel_3.getText().isEmpty()) resp.add(respPossivel_3.getText());
				if(!respPossivel_4.getText().isEmpty()) resp.add(respPossivel_4.getText());
				return resp;
			}
		});
		btnSubmit.setBounds(181, 228, 89, 23);
		panel.add(btnSubmit);
		
		JLabel lblDuracao = new JLabel("Duracao:");
		lblDuracao.setBounds(224, 146, 77, 14);
		panel.add(lblDuracao);
		
		inDuracao = new JTextField();
		inDuracao.setColumns(10);
		inDuracao.setBounds(319, 144, 83, 20);
		panel.add(inDuracao);
		
		panel.revalidate();
		panel.repaint();
	}
	//check
	public void showPerguntas() {
		limparPanel();
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEnviarPergunta();
			}
		});
		btnSeleccionarPergunta.setBounds(160, 103, 133, 34);
		panel.add(btnSeleccionarPergunta);
		
		btnAdicionarPergunta = new JButton("Adicionar pergunta");
		btnAdicionarPergunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAdicionarPergunta();
			}
		});
		btnAdicionarPergunta.setBounds(160, 148, 133, 34);
		panel.add(btnAdicionarPergunta);
		
		panel.revalidate();
		panel.repaint();
	}
	//check
	public void showAlunos() {
		limparPanel();
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
		btnSeleccionarPergunta = new JButton("Seleccionar Pergunta");
		btnSeleccionarPergunta.setBounds(160, 103, 133, 34);
		panel.add(btnSeleccionarPergunta);
		
		btnAdicionarPergunta = new JButton("Adicionar pergunta");
		btnAdicionarPergunta.setBounds(160, 148, 133, 34);
		panel.add(btnAdicionarPergunta);
		
		panel.revalidate();
		panel.repaint();

	}
	//check
	public void showEnviarPergunta() {
		limparPanel();
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 81);
		panel.add(textArea);
		
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
		
		panel.revalidate();
		panel.repaint();
	}
	
	public void limparPanel() {

		panel.removeAll();	
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}
