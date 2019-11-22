package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class A2C {

	public static void main(String[] args) {
		
		//adds all clients to a queue
		Client clientOne = new Client("H1",5);
		Client clientTwo = new Client("H2",2);
		Client clientThree = new Client("H3",3);
		Client clientFour = new Client("C1",3);
		Client clientFive = new Client("C2",4);
		Client clientSix = new Client("C3",1);
		Client clientSeven = new Client("C4",2);
		Client clientEight = new Client("H4",4);
		Client clientNine = new Client("H5",2);
		
		Queue<Client> clients = new LinkedList<>();
		
		clients.add(clientOne);
		clients.add(clientTwo);
		clients.add(clientThree);
		clients.add(clientFour);
		clients.add(clientFive);
		clients.add(clientSix);
		clients.add(clientSeven);
		clients.add(clientEight);
		clients.add(clientNine);
		
		CoffeeMachine.setAllClients(clients);
		
		//brews coffee for clients
		CoffeeMachine.brewCoffee();
		

	}

}
