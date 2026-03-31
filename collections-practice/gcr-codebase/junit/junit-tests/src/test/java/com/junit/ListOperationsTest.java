package com.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
public class ListOperationsTest {
	List<Integer>list=new ArrayList<>();

	@Test
	public void addElementTest() {
		ListOperations.addElement(list, 10);
		ListOperations.addElement(list, 20);
		assertTrue(list.contains(10));
	}
	
	@Test
    public void removeElementTest() {
		ListOperations.addElement(list, 10);
		ListOperations.addElement(list, 20);
		ListOperations.removeElement(list, 10);
        assertFalse(list.contains(10));
    }

    @Test
    public void getSizeTest() {
    	ListOperations.addElement(list, 10);
        ListOperations.addElement(list, 20);
        assertEquals(2, ListOperations.getSize(list));
    }
}
