import java.util.ArrayList;
import java.util.Iterator;

public class Catalog implements Iterable<CatalogItem> {

    private ArrayList<CatalogItem> catalogItems = new ArrayList<CatalogItem>();

    public Catalog() {
    }

    public Catalog(ArrayList<CatalogItem> catalogItems) {
        catalogItems.forEach(item -> {
            this.catalogItems.add(item);
        });
    }

    public void addItem(CatalogItem item) {
        catalogItems.add(item);
    }

    public void removeItem(CatalogItem item) {
        Iterator<CatalogItem> catalogIterator = this.iterator();
        while (catalogIterator.hasNext()) {
            CatalogItem catalogItem = catalogIterator.next();
            if (catalogItem.getCode().equals(item.getCode())) {
                catalogIterator.remove();
            }
        }
    }

    public CatalogItem getItem(String title){
        Iterator<CatalogItem> catalogItemIterator = this.iterator();
        while(catalogItemIterator.hasNext()){
            CatalogItem catalogItem = catalogItemIterator.next();
            if(catalogItem.getTitle().equals(title)){
                return catalogItem;
            }
        }
        return null;
    }

    public CatalogItem getItem(int index) {
        int length = catalogItems.size();
        if (index < length) {
            return catalogItems.get(index);
        }
        return null;
    }

    public int getNumberOfItems() {
        return catalogItems.size();
    }


    @Override
    public Iterator<CatalogItem> iterator() {
        return catalogItems.iterator();
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "catalogItems=" + catalogItems +
                '}';
    }

}
