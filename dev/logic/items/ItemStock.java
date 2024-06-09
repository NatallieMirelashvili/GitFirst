package logic.items;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemStock {
    private ItemType itemType;
    private Set<Item> availableItems;
    private Set<Item> defectiveItems;
    private Set<Item> expiredItems;
    private Set<Item> soldItems;
    private Map<Integer, Item> itemIdToItemMap;

    public ItemStock(ItemType itemType) {
        this.itemType = itemType;
        this.availableItems = new HashSet<>();
        this.defectiveItems = new HashSet<>();
        this.expiredItems = new HashSet<>();
        this.soldItems = new HashSet<>();
        this.itemIdToItemMap = new HashMap<>();
    }

    public void addItem(Item item) {
        if (item.getType().getSerialNumber() == this.itemType.getSerialNumber() && this.availableItems.add(item)) {
            this.itemIdToItemMap.put(item.getItemID(), item);
            this.updateExpiredItem(item);
            this.updateItemLists(item);
        }
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return this.availableItems.size();
    }

    public Set<Item> getItems() {
        Set<Item> items = new HashSet<>();
        items.addAll(this.availableItems);
        items.addAll(this.defectiveItems);
        items.addAll(this.expiredItems);
        items.addAll(this.soldItems);
        return items;
    }

    public Item getItemById(int id) {
        Item item = this.itemIdToItemMap.get(id);
        if (item == null) {
            return null;
        }

        this.updateExpiredItem(item);
        this.updateItemLists(item);
        return null;
    }

    public Set<Item> getAvailableItems() {
        return new HashSet<>(this.availableItems);
    }

    public Set<Item> getDefectiveItems() {
        return new HashSet<>(this.defectiveItems);
    }

    public Set<Item> getExpiredItems() {
        this.updateExpiredItems();
        return this.expiredItems;
    }

    public void updateItemStatus(Item item, ItemStatus newStatus) {
        if (item == null) {
            return;
        }

        item.setItemStatus(newStatus);
        this.updateExpiredItem(item);
        this.updateItemLists(item);
    }

    public void updateExpiredItems() {
        Date currentDate = new Date();
        for (Item item : this.availableItems) {
            this.updateExpiredItem(item, currentDate);
        }
    }

    private void updateExpiredItem(Item item) {
        this.updateExpiredItem(item, new Date());
    }

    private void updateExpiredItem(Item item, Date currentDate) {
        if (item.getItemStatus() == ItemStatus.AVAILABLE && item.getExpirationDate().before(currentDate)) {
            item.setItemStatus(ItemStatus.EXPIRED);
            this.updateItemLists(item);
        }
    }

    private void updateItemLists(Item item) {
        switch (item.getItemStatus()) {
            case EXPIRED -> {
                availableItems.remove(item);
                expiredItems.add(item);
                break;
            }
            case DEFECTED -> {
                availableItems.remove(item);
                defectiveItems.add(item);
                break;
            }
            case SOLD -> {
                availableItems.remove(item);
                soldItems.add(item);
                break;
            }
            case AVAILABLE -> {
                // Do nothing
            }
        }
    }

    public boolean isRunninglow() {
        return this.getQuantity() <= this.itemType.getMinQuantity();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Items Type:").append('\n');
        sb.append(this.itemType.toString().indent(1)).append('\n');
        sb.append("Items:").append('\n');
        for (Item item : this.getItems()) {
            sb.append(item.toString().indent(1)).append('\n');
            sb.append("---\n");
        }

        return sb.toString();
    }
}
