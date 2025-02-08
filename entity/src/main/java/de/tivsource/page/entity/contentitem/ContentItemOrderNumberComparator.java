package de.tivsource.page.entity.contentitem;

import java.util.Comparator;

public class ContentItemOrderNumberComparator implements Comparator<ContentItem> {

    @Override
    public int compare(ContentItem firstContentItem, ContentItem secondContentItem) {
        // TODO: Es muss nach dem Datum sortiert werden wenn die Nummern gleich sind.
       return Integer.compare(firstContentItem.getOrderNumber(), secondContentItem.getOrderNumber());
    }

}// Ende class
