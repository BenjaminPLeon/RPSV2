import java.awt.EventQueue;
import java.net.ServerSocket;

import javax.swing.JFrame;

public class RPS2Server {
	
	private JFrame frame;
	
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RPS2Server window = new RPS2Server();
					window.frame.setResizable(false);
					window.frame.setVisible(true);
					window.frame.setTitle("Server");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
        ServerSocket listener = new ServerSocket(8901);
        System.out.println("RPS Server(version 2) is Running");
        try {
            while (true) {
                Game game = new Game();
                Game.Player player1 = game.new Player(listener.accept(), 1);
                System.out.println("Player 1 Connected\n");
                Game.Player player2 = game.new Player(listener.accept(), 2);
                System.out.println("Player 2 connected, Starting\n");
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                player1.start();
                player2.start();
            }
        } finally {
            listener.close();
        }
    }
	
	public RPS2Server() throws Exception {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public boolean isUserAGoat(){
		return false;
	}
}
