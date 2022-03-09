package de.tivsource.page.entity.contentitem;

import java.util.Comparator;

public class ContentItemOrderNumberComparator implements Comparator<ContentItem> {

    @Override
    public int compare(ContentItem firstContentItem, ContentItem secondContentItem) {
       return Integer.compare(firstContentItem.getOrderNumber(), secondContentItem.getOrderNumber());
    }

}// Ende class
