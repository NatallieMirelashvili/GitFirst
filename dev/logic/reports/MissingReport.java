package logic.reports;

import java.util.Set;

import logic.items.ItemStock;

public class MissingReport {
    public static String createReport(Set<ItemStock> stocks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Missing Items Reports:\n");
        for (ItemStock stock : stocks) {
            sb.append("Item Type:").append('\n');
            sb.append(stock.getItemType().toString().indent(1)).append('\n');
            sb.append("Current Quantity: ").append(stock.getQuantity()).append('\n');
            sb.append("Min Quantity: ").append(stock.getItemType().getMinQuantity()).append('\n');
            sb.append("----------\n");
        }

        return sb.toString();
    }
}
