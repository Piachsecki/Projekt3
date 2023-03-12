package services;

import domain.Purchase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    public static List<Purchase> loadData(Path path){
        try {
            List<Purchase> purchases = Files.lines(path, Charset.defaultCharset())
                    .skip(1)
                    .map(PurchaseMappingService::mapPurchase)
                    .collect(Collectors.toList());
            return purchases;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public static void saveToFile(Path path, List<String> data){
        String header ="id,first_name,last_name,email,ip_address,color,car_vin,car_company," +
                "car_model,car_model_year,car_price,country,city,date\n";

        saveToFile(path, data, header);
    }


    public static void saveToFile(Path path, List<String> data, String header){
        try {
            Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())){
            writer.write(header);
            for (String row : data) {
                writer.write(row);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
