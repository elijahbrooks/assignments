package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.menu.BaseMenu;



public class TravelerMenu extends Helper implements BaseMenu {
    UserBaseMenu userBaseMenu = new UserBaseMenu();

    @Override
    public Object add() {
        userBaseMenu.add(false);
        return null;
    }

    @Override
    public Object update() {
        userBaseMenu.update(false);
        return null;
    }

    @Override
    public Object delete() {
        userBaseMenu.delete(false);
        return null;
    }

    @Override
    public Object read() {
        userBaseMenu.read(false);
        return null;
    }

}
