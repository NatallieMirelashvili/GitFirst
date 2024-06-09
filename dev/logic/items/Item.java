package logic.items;

import java.util.Date;

public class Item {
    // This is a class of a concrete item appearance.
    private ItemType type;  // type of the item, has a unique ID of its own,
    // unifying items of the same logical categories ( main category, subCategory, sizeCategory, manufacturer)
    private final int itemID; // each item has a unique ID - for easy locating and management
    private ItemStatus itemStatus;  // status of the item - available, sold, expired, defective
    private ItemLocation location;  // location of the item.
    private final Date expirationDate;  // finalized once the item is initialized.

    // Constructors, getters and setters:
    public Item(int itemID, Date expirationDate, ItemStatus itemStatus, ItemLocation location, ItemType itemType) {
        this.itemID = itemID;
        this.expirationDate = expirationDate;
        this.itemStatus = itemStatus;
        this.location = location;
        this.type = itemType;
    }

    public ItemType getType() {
        return type;
    }

    public int getItemID() {
        return itemID;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public ItemLocation getLocation() {
        return location;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item ID: ").append(this.itemID).append("\n");
        sb.append("Item Status: ").append(this.itemStatus).append("\n");
        sb.append("Location: ").append(this.location.toString().indent(1)).append("\n");
        sb.append("Expiration Date: ").append(this.expirationDate).append("\n");
        return sb.toString();
    }
}