import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class RPS2Client {

	private JFrame frame;
	private static String host = "localhost";
	private static Integer port = 8901;
	private String msg = "\nYou Chose ";
	private String P = msg + "Paper!";
	private String R = msg + "Rock!";
	private String S = msg + "Scissors!";
	ImageIcon rockIcon = new ImageIcon("Rock.png");
	ImageIcon paperIcon = new ImageIcon("paper.png");
	ImageIcon scissorsIcon = new ImageIcon("Scissors.png");
	
	private int ch;

	private Socket socket;
	private BufferedReader in;
    private PrintWriter out;
    
    JTextArea outputTA;
    JButton RButton;
    JButton PButton;
    JButton SButton;
    JButton sendButton;
    
    Font theFont = new Font(Font.SANS_SERIF, 1, 30);
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RPS2Client window = new RPS2Client();
					window.frame.setResizable(false);
					window.frame.setVisible(true);
					window.frame.setTitle("Rock-Paper-Scissors");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RPS2Client() throws Exception {
		socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.print("Client: Connected");
		initialize();
	}
	
	private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
            "Want to play again?",
            "Want to play again?",
            JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 655, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		outputTA = new JTextArea();
		outputTA.setFont(theFont);
		outputTA.setBounds(273, 11, 356, 364);
		frame.getContentPane().add(outputTA);
		
		RButton = new JButton("Rock");
		RButton.setIcon(rockIcon);
		RButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.append(R);
				ch = 1;
			}
		});
		RButton.setBounds(10, 11, 186, 89);
		frame.getContentPane().add(RButton);
		
		PButton = new JButton("Paper");
		PButton.setIcon(paperIcon);
		PButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					outputTA.append(P);
					ch = 2;
			}
		});
		PButton.setBounds(10, 111, 186, 89);
		frame.getContentPane().add(PButton);
		
		SButton = new JButton("Scissors");
		SButton.setIcon(scissorsIcon);
		SButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					outputTA.append(S);
					ch = 3;
			}
		});
		SButton.setBounds(10, 211, 186, 89);
		frame.getContentPane().add(SButton);
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.append("\nSent choice");
				outputTA.append("\nWaiting For Opponent");
				RButton.setEnabled(false);
				PButton.setEnabled(false);
				SButton.setEnabled(false);
				sendButton.setEnabled(false);
				out.println("" + ch);
				try {			
					String output = in.readLine();
					outputTA.append("\n" + output);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(wantsToPlayAgain()){
					frame.dispose();
					String[] args = {""};
					main(args);
				} else 
					System.exit(0);
			}
		});
		sendButton.setBounds(10, 322, 210, 53);
		frame.getContentPane().add(sendButton);
	}
}
