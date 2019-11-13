package test;

public class LamdaTestClient {
    public static void main(String[] args) {
        // System.out.println("hello,world");
        SaveService ss = new SaveService();
        System.out.println( ss.save( () -> {return "hehaibo";}));
    }
}

interface Test1{
    String save();
}

class SaveService{
    String save(Test1 t){
        return t.save();
    }
}
