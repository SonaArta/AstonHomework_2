package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Main {

    public static List<Person> ageFilterList(List<Person> currentListPerson){
        return  currentListPerson.stream()
                .filter(person -> person.getAge() > 20)
                .collect(Collectors.toList());
    }

    public static void ageFilterAndWriteToFile(List<Person> list, String fileName) {
        List<Person> filteredList = ageFilterList(list);
        System.out.println(filteredList);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("По итогам фильтрации на " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) + " обнаружено " + filteredList.size() + " элементов\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeObjectListToFile(List<Person> list, String fileName ){

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))){

                objectOutputStream.writeObject(list);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Person> readObjectListFromFile(String fileName) {

        List<Person> newList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {

            newList = (List<Person>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newList;
    }


    public static void writeElementObjectListToFile(List<Person> list, String fileName ){

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))){
            for(Person person: list)
                objectOutputStream.writeObject(person);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Person> readElementObjectListFromFile(String fileName) {

        List<Person> newList = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(file);

            try {
                while(true)
                    newList.add((Person) objectInputStream.readObject());

            } catch (EOFException ignored) {
                ignored.printStackTrace();
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newList;

    }


    public static void writeString(List<Person> list, String delimeter, String fileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(Person person: list){
                writer.write(person.toString() + "\n" + delimeter + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

   public static List<Person> readString(String delimeter, String fileName){
        List<Person> newReadList = new LinkedList<>();
       try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
           String line;
           while((line = reader.readLine()) != null){
               if(!line.equals(delimeter)) {
                   String[] dat = line.split(" ");
                   newReadList.add(new Person(dat[0].split(":")[1], Integer.parseInt(dat[1].split(":")[1])));
               }
           }
       }catch (IOException e){
           e.printStackTrace();
       }
       return newReadList;
    }


    public static void main(String[] args) throws InterruptedException {
        String delimeter = "_________________________";
        String adress = "./src/main/resources/";
        String nameFileSer = "StartDatSer.txt";
        String nameFileWriteRead = "StartDat.txt";
        String nameFileReport = "Report.txt";


       /* Person person1 = new Person("Ryzhik", 40);
        Person person2 = new Person("Kuzma", 29);
        Person person3 = new Person("Maria", 3);
        Person person4 = new Person("Niva", 10);
        Person person5 = new Person("Сourage", 30);
        Person person6 = new Person("Leo", 60);
        Person person7 = new Person("Ernest", 98);

        List<Person> currentList = List.of(person1, person2, person3, person4, person5, person6, person7);
        writeString(currentList, delimeter, adress+nameFileWriteRead);
*/

        List<Person> currentList = readString(delimeter, adress+nameFileWriteRead);
        System.out.println(currentList);

        writeObjectListToFile(currentList, adress+nameFileSer);
        List<Person> serList = readObjectListFromFile(adress+nameFileSer);
        System.out.println(serList);

        ageFilterAndWriteToFile(currentList, adress + nameFileReport);

        TimeUnit.SECONDS.sleep(5);
        currentList.add(new Person("PsychrolutesMarcidus", 23));
        ageFilterAndWriteToFile(currentList, adress + nameFileReport);
        writeString(currentList,delimeter, adress+"resFile.txt");


    }

}
