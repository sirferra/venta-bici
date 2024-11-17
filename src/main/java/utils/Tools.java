package utils;

import java.util.List;

public class Tools {
    public static <T> Object[][] fromListToObjectArray(List<T> list) {
        Object[][] array = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = new Object[]{list.get(i)};
        }
        return array;
    }
}
