package data;

import java.util.LinkedList;
import java.util.Queue;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

import data.Client.MachineDispenser;

public class CoffeeMachine {
	
	private static State machineState;
	private static boolean nextClientDifferentBrewType;
	private static int sameBrewTypeToDo;
	private static int active;
	private static int time;


	private static int dispenserOne;
	private static int dispenserTwo;

	private static Queue<Client> allClients;
	private static Queue<Client> sameBrewTypeClients;

	
	
	public enum State {
		Hot,
		Cold;
	}

	static {
		dispenserOne = 0;
		dispenserTwo = 0;
		active=0;
		machineState=State.Hot;
		allClients = new LinkedList<>();
		sameBrewTypeClients = new LinkedList<>();
		sameBrewTypeToDo=0;
		time=0;
	}
	
	public CoffeeMachine() {
		
	}
	
	public static int getTime() {
		return time;
	}
	
	public static void setTime() {
		
	}
	

	//changes machine state (mode)
	public static void changeMachineState() {
	
		
		switch(machineState) {
		
		//if the machine state is hot
		case Hot:
			
			//change to cold
			machineState=State.Cold;
			break;
			
		//if the machine state is cold
		case Cold:
			
			//change to hot
			machineState=State.Hot;
			break;
		default:
			System.out.println("Error: No machine state, hence can't change it.");
			break;
		}
	}
	
	public static void brewWait(Client client) {
		//as long as same brewType, add to sameBrewTypeClients monitor queue
		allToSmallLoop:
		while(machineState.toString().equalsIgnoreCase(allClients.peek().getBrewType().toString())) {
			
			
			client=allClients.poll();
			sameBrewTypeClients.add(client);
			
			if(allClients.isEmpty()) {
				break allToSmallLoop;
			}
		}
	}
	
	//preconditions: active clients is <2 
	public static void brewSignal(Executor executor) {
		
		//if dispenser one is free, and the same brew type (monitor) queue is not empty
		if(dispenserOne==0&&!sameBrewTypeClients.isEmpty()) {
			
			//execute that client thread (brew coffee for that client)
			Client dispenserOneClient=sameBrewTypeClients.poll();
			dispenserOneClient.setDispenserUtilized(MachineDispenser.One);
			executor.execute(dispenserOneClient);
			
		} 
		
		//if dispenser two is free, and the same brew type (monitor) queue is not empty
		if(dispenserTwo==0&&!sameBrewTypeClients.isEmpty()) {
			
			//execute that client thread (brew coffee for that client)
			Client dispenserTwoClient=sameBrewTypeClients.poll();
			dispenserTwoClient.setDispenserUtilized(MachineDispenser.Two);
			executor.execute(dispenserTwoClient);
			
		} 
	}
	

	
	public static void brewCoffee() {
		
		Client client = new Client();
		
		//while all the clients haven't had their coffee served
		while(!allClients.isEmpty()) {

				//add to same brew type (monitor) queue
				brewWait(client);
			
				//get same brew monitor queue size
				sameBrewTypeToDo=sameBrewTypeClients.size();
				
				//initiate executor
				ExecutorService executor = Executors.newFixedThreadPool(sameBrewTypeClients.size());
				
				//as long as same brew type (monitor) queue is not empty, or the dispenser is in use
				while(!sameBrewTypeClients.isEmpty()||sameBrewTypeToDo>0) {
					
					//if there's at least one dispenser free
					if(active<2) {
						
						//signal same brew type (monitor) queue 
						brewSignal(executor);
						
					}
					
					
					//increment time
					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					time++;
				}
			
				//shut down executor for that machine state 
				//once all clients of a same brew type have been served
				executor.shutdown();
				try {
					executor.awaitTermination(1, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//change machine state to prepare for next group of same brew type clients
				changeMachineState();
				

		}
		//all clients in the waiting queue (hot and cold) have been served
		
	System.out.println("("+time+") DONE");
	}
	

	public static Queue<Client> getAllClients() {
		return allClients;
	}


	public static void setAllClients(Queue<Client> all) {
		allClients = all;
	}


	public static Queue<Client> getSameBrewTypeClients() {
		return sameBrewTypeClients;
	}


	public static void setSameBrewTypeClients(Queue<Client> sameBrewType) {
		sameBrewTypeClients = sameBrewType;
	}
	
	public static boolean isNextClientDifferentBrewType() {
		return nextClientDifferentBrewType;
	}


	public static void setNextClientDifferentBrewType(boolean different) {
		nextClientDifferentBrewType = different;
	}


	public static State getMachineState() {
		return machineState;
	}


	public static void setMachineState(State state) {
		machineState = state;
	}

	
	public static void incrementActive() {
		active++;
	}
	
	public static void decrementActive() {
		active--;
	}
	
	public static int getActive() {
		return active;
	}


	public static void setActive(int act) {
		active = act;
	}

	public static int getDispenserOne() {
		return dispenserOne;
	}


	public static void setDispenserOne(int one) {
		dispenserOne = one;
	}

	public static void incrementDispenserOne() {
		dispenserOne++;
	}
	
	public static void decrementDispenserOne() {
		dispenserOne--;
	}
	
	public static void incrementDispenserTwo() {
		dispenserTwo++;
	}
	
	public static void decrementDispenserTwo() {
		dispenserTwo--;
	}

	public static int getDispenserTwo() {
		return dispenserTwo;
	}


	public static void setDispenserTwo(int two) {
		dispenserTwo = two;
	}

	public static void incrementSameBrewTypeToDo() {
		sameBrewTypeToDo++;
	}
	
	public static void decrementSameBrewTypeToDo() {
		sameBrewTypeToDo--;
	}
	
	public static int getSameBrewTypeToDo() {
		return sameBrewTypeToDo;
	}

	
}
