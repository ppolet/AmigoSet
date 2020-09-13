
package amigoset;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String FILE_PATH = "c:\\TEMP\\1\\serialized_AmigoSet.ser";

    public static void main(String[] args) {
        AmigoSet<Integer> a = new AmigoSet<>();
        a.add(10);
        a.add(20);
        a.add(10);
        System.out.println("AmigoSet: " + a + " size: " + a.size());
        
        Object aCloneObject = a.clone();
        AmigoSet<Integer> aClone = (AmigoSet<Integer>) a.clone();
        System.out.println("aCloneObject (Object type): " + aCloneObject);
        System.out.println("aClone (AmigoSet type): " + aClone + " size: " + aClone.size());
        
        System.out.println("--- a.add(30) ---");
        a.add(30);
        
        System.out.println("AmigoSet: " + a + " size: " + a.size());
        System.out.println("aCloneObject (Object type): " + aCloneObject);
        System.out.println("aClone (AmigoSet type): " + aClone + " size: " + aClone.size());
        
        /////////////////////////////
        // Serialized
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(a);
            }
        } catch (IOException ex) {
            Logger.getLogger(AmigoSet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception at serialized: " + ex);
        }
        
        // DeSerialized
        AmigoSet<Integer> aDeser = new AmigoSet<>();
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                aDeser = (AmigoSet<Integer>)ois.readObject();
            }
        } catch (IOException ex){
            Logger.getLogger(AmigoSet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception at deserialized: " + ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception at deserialized (ClassNotFoundException): " + ex);
        }
        
        System.out.println("--- Deserialized ---");
        System.out.println("Deserialized AmigoSet aDeser: " + aDeser + " size: " + aDeser.size());
    }
}
