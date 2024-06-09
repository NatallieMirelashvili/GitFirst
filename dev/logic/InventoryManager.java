package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import logic.items.Item;
import logic.items.ItemLocation;
import logic.items.ItemStatus;
import logic.items.ItemStock;
import logic.items.ItemType;

public class InventoryManager {
    private Map<ItemType, ItemStock> stocks;
    private Map<Integer, ItemType> itemTypes;
    private Set<ItemType> lowStockNotifications;

    public InventoryManager() {
        this.stocks = new HashMap<>();
        this.itemTypes = new HashMap<Integer, ItemType>();
        this.lowStockNotifications = new LinkedHashSet<>();
    }

    public InventoryManager(String filename) {
        this();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Value formate: 0 -itemTypeID, 1- category 2-subCategory 3- sizeCategory 4- manufacturer
                // 5- ninquantity 6- costPrice 7- sellingPrice, 8- itemID, 9-itemStatus, 10-primaryLocation, 11-section,
                // 12-shelfNum, 13-expirationDate
                if (values.length != 14) {
                    throw new IOException();
                }

                // ItemType params
                int itemTypeID = Integer.parseInt(values[0]);
                ItemType itemType;
                ItemStock stock;
                if (this.itemTypes.containsKey(itemTypeID)) {
                    itemType = this.itemTypes.get(itemTypeID);
                    stock = this.stocks.get(itemType);
                } else {
                    String category = values[1];
                    String subCategory = values[2];
                    String sizeCategory = values[3];
                    String manufacturer = values[4];
                    double costPrice = Double.parseDouble(values[5]);
                    double sellingPrice = Double.parseDouble(values[6]);

                    // Stock params
                    int minQuantity = Integer.parseInt(values[7]);
                    itemType = new ItemType(itemTypeID, category, subCategory, sizeCategory, manufacturer, costPrice, sellingPrice, minQuantity);

                    stock = new ItemStock(itemType);

                    this.itemTypes.put(itemTypeID, itemType);
                    this.stocks.put(itemType, stock);
                }
                
                // Location params
                String primaryLocation = values[8];
                String section = values[9];
                int shelfNum = Integer.parseInt(values[10]);
                ItemLocation location = new ItemLocation(primaryLocation, section, shelfNum);
                
                // Item params
                int itemID = Integer.parseInt(values[11]);
                ItemStatus itemStatus = ItemStatus.valueOf(values[12]);
                Date expirationDate = new Date(values[13]);
                Item item = new Item(itemID, expirationDate, itemStatus, location, itemType);

                stock.addItem(item);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<ItemType> getLowStockNotifications() {
        Set<ItemType> notifications = new HashSet<>(this.lowStockNotifications);
        this.lowStockNotifications.clear();
        return notifications;
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        }

        ItemType itemType = item.getType();
        if (itemType == null) {
            return;
        }

        this.stocks.get(itemType).addItem(item);
    }

    public void addItemType(ItemType itemType) {
        if (this.itemTypes.putIfAbsent(itemType.getSerialNumber(), itemType) == null) {
            this.stocks.put(itemType, new ItemStock(itemType));
        }
    }

    public void updateItemStatus(Item item, ItemStatus newStatus) {
        if (item == null) {
            return;
        }

        this.stocks.get(item.getType()).updateItemStatus(item, newStatus);
        if (this.stocks.get(item.getType()).isRunninglow()) {
            this.lowStockNotifications.add(item.getType());
        }
    }

    public void updateExpiredItems() {
        for (ItemStock stock : this.stocks.values()) {
            stock.updateExpiredItems();
        }
    }

    public Set<ItemType> getItemTypes() {
        return new HashSet<>(this.stocks.keySet());
    }

    public ItemType getItemTypeById(int itemTypeId) {
        return this.getItemTypeById(itemTypeId);
    }

    public Set<ItemStock> getStocks() {
        return new HashSet<>(this.stocks.values());
    }

    public ItemStock getItemTypeStock(ItemType itemType) {
        if (!this.stocks.containsKey(itemType)) {
            return null;
        }
        
        return this.stocks.get(itemType);
    }

    public Item getItemById(int itemId) {
        for (ItemStock stock : this.stocks.values()) {
            Item item = stock.getItemById(itemId);
            if (item != null) {
                return item;
            }
        }

        return null;
    }
    
    public Set<Item> getAvailableItems() {
        Set<Item> items = new HashSet<>();
        for (ItemStock stock : this.stocks.values()) {
            items.addAll(stock.getAvailableItems());
        }

        return items;
    }

    public Set<Item> getDefectiveItems() {
        Set<Item> items = new HashSet<>();
        for (ItemStock stock : this.stocks.values()) {
            items.addAll(stock.getDefectiveItems());
        }

        return items;
    }

    public Set<Item> getExpiredItems() {
        Set<Item> items = new HashSet<>();
        for (ItemStock stock : this.stocks.values()) {
            items.addAll(stock.getExpiredItems());
        }

        return items;
    }

    public Set<ItemType> getMissingItemTypes() {
        Set<ItemType> types = new HashSet<>();
        for (Entry<ItemType, ItemStock> entry : this.stocks.entrySet()) {
            if (entry.getValue().isRunninglow()) {
                types.add(entry.getKey());
            }
        }

        return types;
    }
}
