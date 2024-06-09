package logic.reports;
import java.util.Set;

import logic.items.Item;

public class InStockReport {
    public static String createReport(Set<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("In Stock Items Report:\n");
        for (Item item : items) {
            sb.append(item.toString().indent(1)).append('\n');
            sb.append("----------\n");
        }

        return sb.toString();
    }
}
