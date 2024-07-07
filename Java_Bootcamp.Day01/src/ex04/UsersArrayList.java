package ex04;
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь с таким id отсутствует!");
    }
}
public class UsersArrayList implements UsersList {
    private User[] arr;
    private int amount;
    private int size;
    public UsersArrayList() {
        amount = 0;
        size = 10;
        arr = new User[size];
    }
    @Override
    public void addUser(User user) {
        if (size <= amount) {
            User[] tmp = new User[2 * size];
            for (int i = 0; i < arr.length; i++) {
                tmp[i] = arr[i];
            }
            size = tmp.length;
            arr = tmp;
        }
        arr[amount] = user;
        amount++;
    }
    @Override
    public User getUserById(int id) {
        for (int i = 0; i < arr.length; i++) {
            if (id == arr[i].getId()) return arr[i];
        }
        throw new UserNotFoundException();
    }
    @Override
    public User getUserByIndex(int index) {
        if (index >= 0 && index < arr.length && arr[index] != null) return arr[index];
        throw new UserNotFoundException();
    }
    @Override
    public int getUsersAmount() {
        return amount;
    }
}
