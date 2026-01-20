import java.util.*;
public class SmartWarehouseManagement {
	public static void main(String[] args) {
        //creating storages for different categories
        Storage<Electronics> electronicsStorage =new Storage<>();
        Storage<Groceries> groceriesStorage =new Storage<>();
        Storage<Furniture> furnitureStorage =new Storage<>();

        //adding items
        electronicsStorage.addItem(new Electronics("Buds"));
        electronicsStorage.addItem(new Electronics("Phones"));

        groceriesStorage.addItem(new Groceries("Rice"));
        groceriesStorage.addItem(new Groceries("Milk"));

        furnitureStorage.addItem(new Furniture("Chair"));
        furnitureStorage.addItem(new Furniture("Table"));

        //displaying items using wild card method
        System.out.println("Electronics Storage---");
        WarehouseUtils.displayAllItems(electronicsStorage.getItems());
        System.out.println();

        System.out.println("Groceries Storage---");
        WarehouseUtils.displayAllItems(groceriesStorage.getItems());
        System.out.println();

        System.out.println("Furniture Storage---");
        WarehouseUtils.displayAllItems(furnitureStorage.getItems());
        System.out.println();
        
        electronicsStorage.removeItem("Buds");
        System.out.println("After removing buds- ");
        WarehouseUtils.displayAllItems(electronicsStorage.getItems());

    }
}

//Create an abstract class WarehouseItem that all items extend (Electronics, Groceries, Furniture).
abstract class WarehouseItem {
    private String name;

    public WarehouseItem(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    //each item will describe itself
    public abstract void displayInfo();
}

class Electronics extends WarehouseItem {
    public Electronics(String name) {
        super(name);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Electronics item is- "+ getName());
    }
}

class Groceries extends WarehouseItem {
    public Groceries(String name) {
        super(name);
    }

    @Override
    public void displayInfo() {
        System.out.println("Grocery item is- "+ getName());
    }
}

class Furniture extends WarehouseItem {
    public Furniture(String name) {
        super(name);
    }

    @Override
    public void displayInfo() {
        System.out.println("Furniture item is- "+ getName());
    }
}

//Implement a generic class Storage<T extends WarehouseItem> to store items safely.
class WarehouseUtils {
    public static void displayAllItems(java.util.List<? extends WarehouseItem> items) {
        for (WarehouseItem item :items) {
            item.displayInfo();
        }
    }
}

class Storage<T extends WarehouseItem> {
    private List<T> items =new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }
    public java.util.List<T> getItems() {
        return items;
    }
    
    public boolean removeItem(String name) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                return true;
            }
        }
        return false; //not found
    }
}
