package ex02;

public class Program {
    public static void main(String[] args) {
        try {
            UsersArrayList myArr = new UsersArrayList();
            for (int i = 0; i <= 20; i++) {
                User u = new User("User" + (i + 1), 1000 * (i + 1));
                myArr.addUser(u);
                System.out.println(myArr.getUserById(i + 1));
                System.out.println(myArr.getUserByIndex(i));
                System.out.println();
            }
            User us = new User("Userr", -9);
            myArr.addUser(us);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
