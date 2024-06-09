package logic.items;

public class ItemType {
    // This class is used to represent item type. (Product)
    private int serialNumber;  // unique ID of the item type - serialNumber
    private String category;
    private String subCategory;
    private String sizeCategory;
    private String manufacturer;
    private double costPrice;  // The price at which the item was bought
    private double sellingPrice;  // The price at which the item is sold -
    // can be changed in correspondence to the store sales/discounts.
    private int minQuantity;  // the minimum quantity of the item that should be in stock, below which a notification is sent.

    public ItemType(
        int id,
        String category,
        String subCategory,
        String sizeCategory,
        String manufacturer,
        double costPrice,
        double sellingPrice,
        int minQuantity
    ) {
        this.serialNumber = id;
        this.category = category;
        this.subCategory = subCategory;
        this.sizeCategory = sizeCategory;
        this.manufacturer = manufacturer;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minQuantity = minQuantity;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getSizeCategory() {
        return sizeCategory;
    }
    public String getManufacturer(){
        return manufacturer;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serial number: ").append(this.serialNumber).append('\n');
        sb.append("Category: ").append(this.category).append('\n');
        sb.append("Sub Category: ").append(this.subCategory).append('\n');
        sb.append("Size Category: ").append(this.sizeCategory).append('\n');
        sb.append("Manufacturer: ").append(this.manufacturer).append('\n');
        sb.append("Cost Price: ").append(this.costPrice).append('\n');
        sb.append("Selling Price: ").append(this.sellingPrice).append('\n');
    return sb.toString();
    }
}
