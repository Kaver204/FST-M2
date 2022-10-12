package activities;


import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Activity1 {

    static ArrayList list;
    @BeforeEach
    public  void  setUp()
    {
        // Initialize a new ArrayList
        list = new ArrayList<String>();
        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1

    }

    @Test
    @Order(1)
    void insertTest()
    {
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");
        list.add("gamma");
        assertEquals(3, list.size(), "Wrong size");

        final List<String> expected = Arrays.asList("alpha", "beta", "gamma");
        // Sort array
        Collections.sort(expected);
        Collections.sort(list);

        // Assertion
        assertIterableEquals(expected, list,"Items did not match"); //Pass
    }


    @Test
    @Order(2)
    void replaceTest()
    {
        assertEquals(2, list.size(), "Wrong size");
        list.add("gamma");
        assertEquals(3, list.size(), "Wrong size");
        // Replace element in ArrayList
        list.set(1, "nu");
        // Assert individual elements
        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("nu", list.get(1), "Wrong element");
        assertEquals("gamma", list.get(2), "Wrong element");

    }

}
