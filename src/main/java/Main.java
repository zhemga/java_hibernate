import models.User;
import utils.UserManager;

public class Main {
    public static void main(String[] args) {
        UserManager um = new UserManager();
        User user = new User();
        user.setName("oleksiy");

        System.out.println("\n---Read Users------------------\n");
        um.readUsers();

        System.out.println("\n---Read User 9------------------\n");
        um.readUser(9);

        System.out.println("\n---Add User------------------\n");
        um.addUser(user);

        System.out.println("\n---Delete User------------------\n");
        //um.deleteUser(4);

        System.out.println("\n---Update User------------------\n");
        User userToUpdate = um.getUser(7);
        System.out.println(userToUpdate.toString());
        userToUpdate.setName("petro");
        um.editUser(userToUpdate);

        System.out.println("\n---Read Users------------------\n");
        um.readUsers();

        um.shutDown();
    }
}
