package ex05;


public class Program {

    public static void main(String[] args) {
        boolean dev = args.length > 0 && args[0].equals("--profile=dev");
        //  System.out.println(args[0]);
        Menu menu = new Menu(dev);
        try {
            while (true) {
//                menu.inviteUser(dev);
                if (menu.inputMenuNumber()) break;
            }
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());

        }
    }
}
