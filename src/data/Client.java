package data;

public class Client implements Runnable{

	private BrewChoice brewType;
	private int brewTime;
	private String clientID;
	private boolean done;
	private MachineDispenser dispenserUtilized;
	
	
	public MachineDispenser getDispenserUtilized() {
		return dispenserUtilized;
	}

	public void setDispenserUtilized(MachineDispenser utilized) {
		dispenserUtilized=utilized;
	}


	public enum MachineDispenser {
		One,
		Two;
	}

	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean d) {
		done = d;
	}

	public Client() {
		
	}
	
	public Client(String id, int time) {
		
		clientID = id;
		
		if(clientID.contains("H")) {
			brewType = BrewChoice.Hot;
		} 
		else if (clientID.contains("C")) {
			brewType = BrewChoice.Cold;
		} else {
			brewType = null;
		}
		
		brewTime=time;
		
		done=false;
	}
	
	
	public enum BrewChoice {
		Hot,
		Cold;
	}
	
	public void setClientID(String id) {
		
		clientID = id;
		
	}
	
	public String getClientID() {
		return clientID;
	}
	
	public void setBrewType(BrewChoice choice) {
		
		brewType=choice;
	}
	
	public BrewChoice getBrewType() {
		return brewType;
	}

	public void setBrewTime(int time) {
		brewTime = time;
	}
	
	public int getBrewTime() {
		return brewTime;
	}
	
	public void run() {
		
		//increment active counter in the CoffeeMachine class
		CoffeeMachine.incrementActive();
		
		
		switch(dispenserUtilized) {
		
		//if this client's dispenser has been set to one
		case One:
			
			//set the dispenser one timing based on client's brew time
			CoffeeMachine.setDispenserOne(brewTime);
			
			System.out.println("("+CoffeeMachine.getTime()+")"+" "+clientID+" uses dispenser 1 (time: "+brewTime+")");
			
			//critical section
			
			//while the dispenser one timing is not 0
			while(CoffeeMachine.getDispenserOne()!=0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//decrement dispenser one timing
				CoffeeMachine.decrementDispenserOne();
				}
			
			break;
		
		//if this client's dispenser has been set to two
		case Two:
			
			//set the dispenser two timing based on client's brew time
			CoffeeMachine.setDispenserTwo(brewTime);
			
			System.out.println("("+CoffeeMachine.getTime()+")"+" "+clientID+" uses dispenser 2 (time: "+brewTime+")");
			
			//critical section
			
			//while the dispenser two timing is not 0
			while(CoffeeMachine.getDispenserTwo()!=0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//decrement dispenser two timing
				CoffeeMachine.decrementDispenserTwo();
				}
			break;
			
		default:
			System.out.println("Error: No dispenser chosen for client "+clientID+". ");
			break;
		}
		

		//decrement the amount of same brew types left to brew
		CoffeeMachine.decrementSameBrewTypeToDo();
		
		//decrement active counter in the CoffeeMachine class
		CoffeeMachine.decrementActive();
	}
}
