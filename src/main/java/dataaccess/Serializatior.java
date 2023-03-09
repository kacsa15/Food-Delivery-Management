package dataaccess;

import businesslogic.DeliveryService;

import java.io.*;

public class Serializatior implements Serializable {

    public static void serialize(DeliveryService deliveryService) throws IOException, ClassNotFoundException{

            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\products.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(deliveryService);
            out.close();
            fileOut.close();
            System.out.printf("DONE -->Serialized");

    }

    public static DeliveryService deserialize() throws IOException, ClassNotFoundException {

            DeliveryService deliveryService;
            FileInputStream deserializableFile = new FileInputStream("src/main/resources/file.ser");
            ObjectInputStream deserializable = new ObjectInputStream(deserializableFile);
            deliveryService = (DeliveryService) deserializable.readObject();
            deserializable.close();
            deserializableFile.close();
            System.out.println("DONE --> Deserialized");

            return deliveryService;

    }

}
