package ollamaStar.common.utils;

import ollamaStar.pojo.User;

public class UserUtils {
    static ThreadLocal<User> local = new ThreadLocal<>();

    public static void savaUser(User user){
        local.set(user);
    }

    public static User getUser(){
        return local.get();
    }

    public static void removeUser(){
        local.remove();
    }
}
