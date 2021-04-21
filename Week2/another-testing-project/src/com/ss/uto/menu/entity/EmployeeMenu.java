package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.menu.BaseMenu;

public class EmployeeMenu extends Helper implements BaseMenu {

    UserBaseMenu userBaseMenu = new UserBaseMenu();

    @Override
    public Object add() {
        userBaseMenu.add(true);
        return null;
    }

    @Override
    public Object update() {
        userBaseMenu.update(true);
        return null;
    }

    @Override
    public Object delete() {
        userBaseMenu.delete(true);
        return null;
    }

    @Override
    public Object read() {
        userBaseMenu.read(true);
        return null;
    }
}
