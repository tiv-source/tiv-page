package de.tivsource.page.converter;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.entity.contentitem.ContentItem;


public class ContentItemSortedSetConverter extends StrutsTypeConverter {

    private static final Logger LOGGER = LogManager.getLogger(ContentItemSortedSetConverter.class);

    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        LOGGER.info("convertFromString(Map context, String[] values, Class toClass) aufgerufen.");
        LOGGER.info("Anzahl der UUIDs: " + values.length);
        
        SortedSet<ContentItem> contentItemSortedSet = null;

        if(values.length<=0) {
            throw new TypeConversionException("Kein gültigen ContentItems angegeben");
        }
    	
    	for ( int i = 0; i < values.length; i++ ){
    		
    		if(i==0){
    			contentItemSortedSet = new TreeSet<ContentItem>();
    		}
    		
    		String contentItemUuid = values[i];
            try {
                Context initialContext = new InitialContext();
                ContentItemDaoLocal contentItemDaoLocal = (ContentItemDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/ContentItemDao");
                ContentItem contentItem = contentItemDaoLocal.findByUuid(contentItemUuid);
                if(contentItem.getVisible()) {
                    contentItemSortedSet.add(contentItem);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    	}

        if(contentItemSortedSet.size()<=0) {
            throw new TypeConversionException("Kein gültige ContentItem - UUID");
        }

        return contentItemSortedSet;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map context, Object o) {
        ContentItem contentItem = (ContentItem)o;
        return ""+contentItem.getUuid();
    }

}// Ende class
