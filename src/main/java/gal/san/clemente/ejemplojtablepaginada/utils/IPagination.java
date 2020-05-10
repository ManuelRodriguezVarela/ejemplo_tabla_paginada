package gal.san.clemente.ejemplojtablepaginada.utils;

import java.util.List;

public interface IPagination<T> {
    
    int getTotalRowCount();
    List<T> getRows(int startIndex, int endIndex);
    
}
