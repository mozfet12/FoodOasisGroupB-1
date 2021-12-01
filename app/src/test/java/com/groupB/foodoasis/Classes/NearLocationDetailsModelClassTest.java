package com.groupB.foodoasis.Classes;

import org.junit.Before;

public class NearLocationDetailsModelClassTest {

    private NearLocationDetailsModelClass nearLocationDetailsModelClassUnderTest;

    @Before
    public void setUp() {
        nearLocationDetailsModelClassUnderTest = new NearLocationDetailsModelClass("name", "icon", "place_id", 0.0, 0.0, 0);
    }
}
