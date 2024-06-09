package logic.reports;

import java.util.Set;

import logic.items.Item;

public class DefectiveReport {
    public static String createReport(Set<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("Defective/Expired Items Report:\n");
        for (Item item : items) {
                sb.append(item.toString().indent(1)).append('\n');
                sb.append("----------\n");
        }

        return sb.toString();
    }
}
