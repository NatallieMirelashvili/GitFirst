package logic.reports;

import java.util.Set;

import logic.items.ItemStock;

public class StockReport {
    public static String createReport(Set<ItemStock> stocks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Stock Report:").append('\n');
        for (ItemStock stock : stocks) {
            sb.append(stock.toString().indent(1)).append('\n');
            sb.append("---------\n");
        }

        return sb.toString();
    }
}
