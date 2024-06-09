package logic.items;

public class ItemLocation {
    private String primaryLocation;
    private String section;
    private int shelfNum;

    public ItemLocation(String primaryLocation, String section, int shelfNum) {
        this.primaryLocation = primaryLocation;
        this.section = section;
        this.shelfNum = shelfNum;
    }

    public String getPrimaryLocation() {
        return primaryLocation;
    }

    public void setPrimaryLocation(String primaryLocation) {
        this.primaryLocation = primaryLocation;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getShelfNum() {
        return shelfNum;
    }

    public void setShelfNum(int shelfNum) {
        this.shelfNum = shelfNum;
    }

    // TODO: add validation

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Primary Location: ").append(this.primaryLocation).append('\n');
        sb.append("Section: ").append(this.section).append('\n');
        sb.append("Shelf: ").append(this.shelfNum).append('\n');
        return sb.toString();
    }
}
