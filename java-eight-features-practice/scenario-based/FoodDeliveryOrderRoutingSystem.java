import java.util.*;
public class FoodDeliveryOrderRoutingSystem {
	public static void main(String[] args) {
        DeliveryService service=new DeliveryService();

        service.addAgent(new Agent(1, "Sector 10"));
        service.addAgent(new Agent(2, "Sector 15"));
        System.out.println();

        service.addOrder(new Order(101, "Sector 12"));
        service.addOrder(new Order(102, "Sector 18"));
        System.out.println();

        try {
            service.assignOrder();
            service.assignOrder();
            service.assignOrder();
        } catch (NoAgentAvailableException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        service.viewActiveDeliveries();
        System.out.println();
        Order cancelOrder = new Order(101, "Sector 12");
        service.cancelDelivery(cancelOrder);
        System.out.println();

        service.viewActiveDeliveries();
    }
}

class Order{
	int id;
	String location;
	
	Order(int id, String location){
		this.id=id;
		this.location=location;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		return "Order{ id- "+id+", location- "+location+" }";
	}
}

class Agent{
	int id;
	String location;
	boolean isAvailable;
	
	Agent(int id, String location) {
        this.id =id;
        this.location =location;
        this.isAvailable =true;
    }
	
	public int getId() {
		return id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable=isAvailable;
	}
	
	@Override
    public String toString() {
        return "Agent{ id- "+id+ ", location- "+location+ ", available-"+isAvailable+" }";
    }
}

class NoAgentAvailableException extends Exception {
    public NoAgentAvailableException(String message) {
        super(message);
    }
}

class DeliveryService{
	Queue<Order> orders=new LinkedList<>();
	List<Agent> agents=new ArrayList<>();
	Map<Order, Agent> activeDelivery=new HashMap<>();
	
	public void addAgent(Agent agent) {
		agents.add(agent);
	}
	
	public void addOrder(Order order) {
        orders.offer(order);
    }

	//assign order FIFO to nearest available agent
    public void assignOrder() throws NoAgentAvailableException {
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        Order order =orders.poll();
        Agent nearest =findNearestAvailableAgent(order);

        if (nearest==null) {
            throw new NoAgentAvailableException("No agent available for order " + order.getId());
        }

        nearest.setIsAvailable(false);
        activeDelivery.put(order, nearest);
        System.out.println("Assigned " + order + " to " + nearest);
    }
    
    public void cancelDelivery(Order order) {
        Agent agent =activeDelivery.remove(order);
        if (agent!=null) {
            agent.setIsAvailable(true);
            System.out.println("Cancelled " + order + ". Agent " + agent.getId() + " is now free.");
        } else {
            System.out.println("Order not found in active deliveries.");
        }
    }

    public void viewActiveDeliveries() {
        if (activeDelivery.isEmpty()) {
            System.out.println("No active deliveries.");
        } else {
            activeDelivery.forEach((order, agent) -> System.out.println(order + " -> " + agent));
        }
    }

    //to find nearest available agent
    private Agent findNearestAvailableAgent(Order order) {
        Agent nearest=null;
        for (Agent agent :agents) {
            if (agent.isAvailable()) {
                nearest =agent;
                break;
            }
        }
        return nearest;
    }
}
