import main.*;

public class Main {
    public static void main(String[] args) {
        CsvConverter csvConverter = new CsvConverter(System.getenv("LAB5_PATH"));
        CollectionManager collectionManager = new CollectionManager(csvConverter);
        Console console = new Console();
        CommandManager commandManager = new CommandManager(collectionManager, console);

        console.startConsole(commandManager);
    }
}
