import domain.Pair;
import domain.Purchase;
import services.FileService;
import services.PurchaseMappingService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static final String EX_2 = "./src/exported/ex2/";
    public static final String EX_3 = "./src/exported/ex3/";
    public static final String PURCHASES_OF = "purchases-of-";

    public static void main(String[] args) {
        Path path = Paths.get("./src/resources/client-car-purchase-spreadsheet.csv");
        List<Purchase> purchases = FileService.loadData(path);

        Map<String, List<Purchase>> mapByCompany = purchases.stream()
                .collect(Collectors.groupingBy(purchase -> purchase.getCar().getCompany()));

        Map<String, Map<String, List<Purchase>>> mapByCompanyAndModel = mapByCompany.entrySet().stream()
                .collect(Collectors.toMap(
                                entry -> entry.getKey(),
                                e -> e.getValue().stream()
                                        .collect(Collectors.groupingBy(p -> p.getCar().getModel()))
                        )

                );
        Map<String, Map<String, Pair<BigDecimal, Long>>> reportMap = mapByCompanyAndModel.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e1 -> buildPair(e1.getValue())

                                ))
                ));
        AtomicInteger counter = new AtomicInteger(1);
        List<String> reportMapData = reportMap.entrySet().stream()
                .flatMap(eExternal -> eExternal.getValue().entrySet().stream()
                        .map(
                                eInternal -> getRawRow(
                                counter.getAndIncrement(),
                                eExternal.getKey(),
                                eInternal.getKey(),
                                eInternal.getValue()))

                )

                .toList();

        generateEx3report(reportMapData);


        for (Map.Entry<String, List<Purchase>> entry : mapByCompany.entrySet()) {
            Path path1 = Paths.get(EX_2 + PURCHASES_OF + entry.getKey() + ".csv");
            List<String> data = entry.getValue().stream()
                    .map(PurchaseMappingService::toCsvRow)
                    .toList();
            FileService.saveToFile(path1, data);
        }

      try {
            TreeMap<Path, ? extends Number> mapSizeByCompany = Files.list(Paths.get(EX_2))
                    .collect(Collectors.toMap(
                            Path::getFileName,
                            Main::getFileSize,
                            (a, b) -> a,
                            () -> new TreeMap<>(Comparator.reverseOrder())));

            for (Path fileName : mapSizeByCompany.keySet()) {
                String substringOfNameFile = fileName.toString().substring(PURCHASES_OF.length(), fileName.toString().indexOf('.'));
                System.out.printf("%s:%s%n",
                        substringOfNameFile,
                        mapSizeByCompany.get(fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        purchases.forEach(System.out::println);
    }

    private static void generateEx3report(List<String> reportMapData) {
        Path path = Paths.get(EX_3 + "report.csv");
        FileService.saveToFile(path, reportMapData, "id,company,model,average_price,count");
    }

    private static String getRawRow(int counter, String company, String model, Pair<BigDecimal, Long> pair) {
        return  counter + ","+
                company + "," +
                model + "," +
                pair.t() + "," +
                pair.u();
    }

    private static Pair<BigDecimal, Long> buildPair(List<Purchase> purchases) {
        long count = purchases.size();
        BigDecimal averagePrice = purchases.stream()
                .map(purchase -> purchase.getCar().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

        return new Pair<>(averagePrice, count);
    }

    private static long getFileSize(Path path1) {
        try {
            return Files.size(path1);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}