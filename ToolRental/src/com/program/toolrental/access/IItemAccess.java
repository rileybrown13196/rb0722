package com.program.toolrental.access;

import com.program.toolrental.Item;

import java.util.ArrayList;

public interface IItemAccess {
    ArrayList<String> getValidToolCodes();
    Item getItem(String toolCode);
}
