package inventorymanagementsystem.Models;

public class InhousePart extends Part {

    private int machineID;

    public InhousePart(int ID, String name, double price, int inStock, int min, int max, int machine) {

        super(ID, name, price, inStock, min, max);
        this.machineID = machine;

    }

    public int getMachineID() {

        return machineID;

    }

    public void setMachineID(int ID) {

        this.machineID = ID;

    }    

}
