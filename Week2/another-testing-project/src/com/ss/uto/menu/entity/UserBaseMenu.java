package com.ss.uto.menu.entity;

import com.ss.uto.Helper;
import com.ss.uto.Menu;
import com.ss.uto.entity.User;
import com.ss.uto.service.UserService;

import java.util.function.Function;

public class UserBaseMenu extends Helper  {
    UserService userService = new UserService();

    public Object add(boolean employee) {
        String firstName = new Menu(new String[]{"Input First Name: "}).inputMenu();
        String lastName = new Menu(new String[]{"Input Last Name: "}).inputMenu();
        String username = new Menu(new String[]{"Input Username: "}).inputMenu();
        String email = new Menu(new String[]{"Input email: "}).inputMenu();
        String password = new Menu(new String[]{"Input password: "}).inputMenu();
        String phone = new Menu(new String[]{"Input phone number: "}).inputMenu();

        User user = new User(firstName, lastName, username, email, password, phone);
        userService.addUser(user, employee);
        return null;
    }

    public Object update(boolean employee){
        String[] users = userService.getAllUsers(employee);
        Integer id = Integer.parseInt(new Menu(users).getInputAsString().split(" ")[0]);

        User traveler = userService.getUser(id);
        if (traveler != null)
            updateMenu(traveler);

        return null;
    }

    public Object delete(boolean employee) {
        String[] users = userService.getAllUsers(employee);
        Integer id = Integer.parseInt(new Menu(users).getInputAsString().split(" ")[0]);

        User traveler = userService.getUser(id);
        if (traveler != null)
            userService.deleteUser(traveler);

        return null;
    }

    public Object read(boolean employee) {
        System.out.println("Press enter to continue. ");
        String[] users = userService.getAllUsers(employee);
        new Menu(users).inputMenu();

        return null;
    }

    public User chooseUserRole(){
        String[] options = {"Choose from Travelers", "Choose from Employees", "Proceed as Guest"};
        Integer choice = new Menu(options).getInput() + 1;
        User user = null;
        if(choice != null){
            switch(choice){
                case 1:
                    user = chooseUser(false);
                    break;
                case 2:
                    user = chooseUser(true);
                    break;
                default:
            }
        }else
            return chooseUserRole();
        return user;
    }

    public User chooseUser(Boolean employees){
        String[] users = userService.getAllUsers(employees);
        String input = new Menu(users).getInputAsString();

        if(input != null){
            Integer userId = Integer.parseInt(input.split(" ")[0]);
            return userService.getUser(userId);
        }else {
            return chooseUser(employees);
        }
    }


    private void updateMenu(User user) {
        String[] options = {"First Name", "Last Name", "Username", "Email", "Password", "Phone"};
        System.out.println("What do you wish to update?: ");
        Integer input = new Menu(options).getInput();

        if (input != null) {
            Function<Object, Object> firstName = value -> {
                user.setGivenName(new Menu(new String[]{"New First Name: "}).inputMenu());
                return null;
            };
            Function<Object, Object> lastName = value -> {
                user.setFamilyName(new Menu(new String[]{"New Last Name: "}).inputMenu());
                return null;
            };
            Function<Object, Object> username = value -> {
                user.setUsername(new Menu(new String[]{"New Username: "}).inputMenu());
                return null;
            };
            Function<Object, Object> password = value -> {
                user.setPassword(new Menu(new String[]{"New Password: "}).inputMenu());
                return null;
            };
            Function<Object, Object> email = value -> {
                user.setEmail(new Menu(new String[]{"New Email: "}).inputMenu());
                return null;
            };
            Function<Object, Object> phone = value -> {
                user.setEmail(new Menu(new String[]{"New Phone: "}).inputMenu());
                return null;
            };

            Function[] methods = {firstName, lastName, username, email, password, phone};
            methods[input].apply(new Object());

            userService.updateUser(user);
        }
    }
}
