package main;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;

import data.City;
import data.Coordinates;
import data.Human;
import exceptions.StringConvertException;
import data.Government;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CsvConverter {
    private String filePath;

    public CsvConverter(String filePath) {
        this.filePath = filePath;
    }

    public List<City> csvFileToCities(CollectionManager collectionManager) throws IOException {
        List<City> cities = new ArrayList<>();

        try (FileReader reader = new FileReader(this.filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            
            Integer i = 1;
            for (CSVRecord record : csvParser) {
                i++;
                try {
                    String name = StringConverter.convertString(record.get("name"), String.class);
                    float x = StringConverter.convertString(record.get("x"), Float.class);
                    Long y = StringConverter.convertString(record.get("y"), Long.class);
                    int area = StringConverter.convertString(record.get("area"), Integer.class);
                    int population = StringConverter.convertString(record.get("population"), Integer.class);
                    double metersAboveSeaLevel = StringConverter.convertString(record.get("metersAboveSeaLevel"), Double.class);
                    float timezone = StringConverter.convertString(record.get("timezone"), Float.class);
                    long populationDensity = StringConverter.convertString(record.get("populationDensity"), Long.class);
                    String governmentType = StringConverter.convertString(record.get("government"), String.class);
                    String governorName = StringConverter.convertString(record.get("governorName"), String.class);
                    int governorAge = StringConverter.convertString(record.get("governorAge"), Integer.class);
                    int governorHeight = StringConverter.convertString(record.get("governorHeight"), Integer.class);
                    LocalDate governorBirthday = StringConverter.convertString(record.get("governorBirthday"), LocalDate.class);


                    Coordinates coordinates = new Coordinates(x, y);
                    Human governor = new Human(governorName, governorAge, governorHeight, governorBirthday);
                    Government government = Government.valueOf(governmentType.toUpperCase());

                    City city = new City(IdGenerator.generateId(), name, coordinates, area, population, metersAboveSeaLevel,
                            timezone, populationDensity, government, governor);

                    cities.add(city);
                } catch (StringConvertException e) {
                    System.out.println("Error: Incorrect data format in string " + i);
                }
            }
        }

        return cities;
    }

    public void CitiesToCsvFile(Hashtable<Integer, City> cities) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("id", "name", "x", "y", "area", "population",
                                "metersAboveSeaLevel", "timezone", "populationDensity", "government", "governorName",
                                "governorAge", "governorHeight", "governorBirthday"))) {

            for (City city : cities.values()) {
                csvPrinter.printRecord(
                        city.getId(),
                        city.getName(),
                        city.getCoordinates().getX(),
                        city.getCoordinates().getY(),
                        city.getArea(),
                        city.getPopulation(),
                        city.getMetersAboveSeaLevel(),
                        city.getTimezone(),
                        city.getPopulationDensity(),
                        city.getGovernment().name(),
                        city.getGovernor().getName(),
                        city.getGovernor().getAge(),
                        city.getGovernor().getHeight(),
                        city.getGovernor().getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
    }
}
