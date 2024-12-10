import java.util.concurrent.atomic.AtomicReference;

record UserProfile (String name , int edad){

}
class UserProfileUpdater{
    AtomicReference<UserProfile> userProfile=
            new AtomicReference<>(new UserProfile("Alvaro",54));

    void updateProfile(String newName, int newAge){
        userProfile.set(new UserProfile(newName,newAge));
    }

    void updateProfile(String expectedName, String newName, int newAge){
        userProfile.getAndUpdate(current ->{
            if (current.name().equals(expectedName)){
                return new UserProfile(newName,newAge);
            }
            return current;
        });


    }
    void updateProfile(UserProfile expected , UserProfile nuevo){
        userProfile.compareAndSet(expected,nuevo);
    }
}




public class Repaso3 {
    public static void main(String[] args) {

    }
}
