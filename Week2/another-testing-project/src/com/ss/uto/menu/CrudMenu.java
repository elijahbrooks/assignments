package com.ss.uto.menu;

import com.ss.uto.Menu;

import java.util.function.Function;

public class CrudMenu {
    public Integer displayMenu(BaseMenu entityMenu){
        String[] options = {"Add", "Update", "Delete", "Read"};
        Menu menu = new Menu(options);

        Function<Object, Object> add = (a) -> entityMenu.add();
        Function<Object, Object> update = (a) -> entityMenu.update();
        Function<Object, Object> delete = (a) -> entityMenu.delete();
        Function<Object, Object> read = (a) -> entityMenu.read();

        Integer choice = menu.getInput();
        if(choice != null){
            Function[] functions = {add, update, delete, read};
            functions[choice].apply(new Object());
        }else {
            displayMenu(entityMenu);
        }
        return null;
    }
}
