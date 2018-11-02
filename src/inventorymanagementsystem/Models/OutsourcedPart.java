package inventorymanagementsystem.Models;

public class OutsourcedPart extends Part {

    private String companyName;

    public OutsourcedPart(int ID, String name, double price, int inStock, int min, int max, String company) {

        super(ID, name, price, inStock, min, max);
        this.companyName = company;

    }

    public String getCompanyName() {

        return companyName;

    }

    public void setCompanyName(String company) {

        this.companyName = company;

    }

}
