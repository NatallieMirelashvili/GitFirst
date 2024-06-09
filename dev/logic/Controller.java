package logic;
import java.util.HashSet;
import java.util.Set;

import logic.items.Item;
import logic.items.ItemStatus;
import logic.items.ItemStock;
import logic.items.ItemType;
import logic.reports.DefectiveReport;
import logic.reports.InStockReport;
import logic.reports.MissingReport;
import logic.reports.StockReport;

public class Controller {
    private InventoryManager inventoryManager;
    private PasswordAuthenticator passwordAuthenticator;

    public Controller() {
        this.inventoryManager = new InventoryManager();
        this.passwordAuthenticator = new PasswordAuthenticator();
    }

    public Controller(String configurationFile) {
        this.inventoryManager = new InventoryManager(configurationFile);
        this.passwordAuthenticator = new PasswordAuthenticator();
    }

    public boolean authenticatePassword(String password) {
        return this.passwordAuthenticator.authenticate(password);
    }

    public String getInventoryReport(String categories) {
        return StockReport.createReport(this.inventoryManager.getStocks());
    }

    public String getExpiredDefectiveReport() {
        Set<Item> defectiveItems = this.inventoryManager.getDefectiveItems();
        Set<Item> expiredItems = this.inventoryManager.getExpiredItems();
        Set<Item> items = new HashSet<>();
        items.addAll(defectiveItems);
        items.addAll(expiredItems);
        return DefectiveReport.createReport(items);
    }

//    public String getExpiredItems() {
//        // TODO: do we want this option?
//        Set<Item> expiredItems = this.inventoryManager.getExpiredItems();
//        StringBuilder sb = new StringBuilder();
//        for (Item item : expiredItems) {
//            sb.append(item.toString()).append("\n");
//        }
//        return sb.toString();
//    }

    public String getMissingReport() {
        Set<ItemType> missingItems = this.inventoryManager.getMissingItemTypes();
        Set<ItemStock> stocks = new HashSet<>();
        for (ItemType type : missingItems) {
            stocks.add(this.inventoryManager.getItemTypeStock(type));
        }

        return MissingReport.createReport(stocks);
    }

    public String getItemInfo(int itemId) {
        Item item = this.inventoryManager.getItemById(itemId);
        if (item != null) {
            return item.toString();
        }

        return "Item not found";
    }

    public String getItemsInStock() {
        Set<Item> availableItems = this.inventoryManager.getAvailableItems();
        return InStockReport.createReport(availableItems);
    }

    public String reportDefective(int itemId) {
        Item item = this.inventoryManager.getItemById(itemId);
        if (item == null) {
            return "Failed to report defective item";
        }

        this.inventoryManager.updateItemStatus(item, ItemStatus.DEFECTED);
        return "Item reported as defective successfully";
    }

    public String getLowStockNotifications() {
        Set<ItemType> itemTypes = this.inventoryManager.getLowStockNotifications();
        if (itemTypes == null || itemTypes.size() == 0) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("The following item types have low stock:");
        for (ItemType itemType : itemTypes) {
            sb.append(itemType.toString()).append('\n');
            sb.append("-----------\n");
        }

        return sb.toString();
    }

    public String updateItemStatus(int itemId, String status) {
        Item item = this.inventoryManager.getItemById(itemId);
        if (item == null) {
            return "Item does not exist";
        }

        ItemStatus itemStatus = ItemStatus.valueOf(status);
        this.inventoryManager.updateItemStatus(item, itemStatus);
        return "Item status updated";
    }
}
