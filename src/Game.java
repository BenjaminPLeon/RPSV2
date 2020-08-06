import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game {
	/*
	 * 1 Rock
	 * 2 Paper
	 * 3 Scissors
	 * 4 UNASSIGNED
	 */
	int c1 = 4;
	int c2 = 4;
	
	/*
	 * returns a 0 if the game is a tie, otherwise returns the
	 * int that is associated with the choice that won, if a
	 * 5 is returned, an error has occurred
	 */
	public int getWinner(){
		if(c1 == c2)
			return 0;
		else{
			switch(c1){
				case 1:
					if(c2 == 3)
						return c1;
					else
						return c2;
				case 2:
					if(c2 == 1)
						return c1;
					else
						return c2;
				case 3:
					if(c2 == 2)
						return c1;
					else
						return c2;
			}
		}
		return 5;
	}
	
	public void setC1(int c1) {
		this.c1 = c1;
	}
	
	public void setC2(int c2) {
		this.c2 = c2;
	}
	
	class Player extends Thread {
		int choice = 4;
		int num;
		Socket socket;
		Player opponent;
        BufferedReader input;
        PrintWriter output;
        
        public Player(Socket socket, int num) {
        	this.num = num;
        	this.socket = socket;
        	try {
        		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
        	} catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }
        
        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }
        
        
        public void run() {
            try {
                	String fromServer = input.readLine();
                	if(this.num == 1){
                		setC1(Integer.parseInt(fromServer));
                	} else {
                		setC2(Integer.parseInt(fromServer));
                	}
                	
                	this.choice = Integer.parseInt(fromServer);
                	
                	output.flush();
                	while(opponent.choice == 4)
                		Thread.sleep(1);
                	
                	if(getWinner() == 4 || (c1 == 4 || c2 == 4)){
                		output.println("Tie!");
                	} else if(getWinner() == this.choice) {
                		output.println("You Win!");
                	} else {
                		output.println("You Lost!");
                	}
                	if(this.num == 1){
                		System.out.println("\nClient 1: Finished\n");
                	} else {
                		System.out.println("Client 2: Finished");
                	}
                	output.flush();
            }catch (Exception e) {
            	System.out.println("Player died: " + e);
            } finally {
            	try {socket.close();} catch (IOException e) {}
            }
        }
	}
}
