package model;

/**
 *
 * @author Nicole Mau
 */
public class OutSourced extends Part {

    private String companyName;



    public OutSourced(int id, String name, double price, int stock,  int min, int max, String company) {
        super(id, name, price, stock , min, max);
        companyName = company;
    }

    public  String getCompanyName() {
        return companyName;
    }

    public  void setCompanyName(String companyName) {
        this.companyName= companyName;
    }


}
