package model;
/**
 *
 * @author Nicole Mau
 */

public class InHouse extends Part {

    private int machineID;


    public InHouse(int id, String name, double price, int stock, int min, int max, int machine) {
        super(id, name, price, stock, min, max);
    this.machineID=machine;
    }

    public int getMachineID() {

        return machineID;
    }


    public void setMachineID(int machineID) {
        this.machineID= machineID;
    }

}
