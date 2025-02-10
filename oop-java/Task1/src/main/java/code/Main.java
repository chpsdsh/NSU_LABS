package code;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Needs two arguments input and output");
            return;
        }
        String inputFileName = args[0];
        String outPutFileName = args[1];
        FileData data = new FileData();
        data.createMap(inputFileName);
        data.sortMap();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.inputDataToCsv(outPutFileName, data);
    }
}