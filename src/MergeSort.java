import java.io.*;
import java.util.HashMap;

import java.util.Map;

public class MergeSort {

    public static void main(String[] args) {

        InputParameters inputParameters = new InputParameters(args);
        if (!inputParameters.isCorrectInputParameters()) System.exit(0);

        Map<BufferedReader, String> inputBufferedReaders = new HashMap<>();

        for (int i = 0; i < inputParameters.getInputFiles().size(); i++) {
            BufferedReader bufferedReader = getReaderFromFile(inputParameters.getInputFiles().get(i));
            if (bufferedReader != null) {
                String firstLine = getLineFromBufferedReader(bufferedReader);
                if (firstLine != null) {
                    inputBufferedReaders.put(bufferedReader, firstLine);
                }
            }
        }

        if (inputBufferedReaders.size() == 0) {
            System.out.println("Input files are empty!");
            System.exit(0);
        }

        boolean isAsc = inputParameters.isAsc();
        boolean writeOutputFile = false;
        String dataType = inputParameters.getDataType();
        String valueToWrite = null;
        String wroteValue = null;
        BufferedReader nextReader = null;

        try (FileWriter writer = new FileWriter(inputParameters.getOutputFile(), false)) {

            while (inputBufferedReaders.size() > 0) {

                for (Map.Entry<BufferedReader, String> entry : inputBufferedReaders.entrySet()) {

                    BufferedReader bufferedReader = entry.getKey();
                    String line = entry.getValue();

                    if (isCorrectItemArray(line, dataType)) {
                        if (valueToWrite == null) {
                            valueToWrite = line;
                            nextReader = bufferedReader;
                        } else {
                            int comparisonResult  = compareValue(valueToWrite, line, dataType);
                            if ((comparisonResult > 0 && isAsc) || (comparisonResult < 0 && !isAsc)) {
                                valueToWrite = line;
                                nextReader = bufferedReader;
                            }
                        }
                    } else {
                        nextReader = bufferedReader;
                        valueToWrite = null;
                        break;
                    }
                }

                if (nextReader != null) {
                    String nextLine = getLineFromBufferedReader(nextReader);

                    if (nextLine == null) {
                        inputBufferedReaders.remove(nextReader);
                        nextReader.close();
                    } else {
                        inputBufferedReaders.put(nextReader, nextLine);
                    }
                }

                if (valueToWrite != null) {
                    if (wroteValue == null) {
                        writer.write(valueToWrite + "\n");
                        wroteValue = valueToWrite;
                        writeOutputFile = true;
                    } else {
                        int comparisonResult  = compareValue(valueToWrite, wroteValue, dataType);
                        if (comparisonResult == 0 ||
                                (comparisonResult > 0 && isAsc) || (comparisonResult < 0 && !isAsc)) {
                            writer.write(valueToWrite + "\n");
                            wroteValue = valueToWrite;
                            writeOutputFile = true;
                        }
                    }
                }
                
                valueToWrite = null;
                
            }

        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(writeOutputFile ? "Sorting complete!" : "Input file data does not match the specified!");

    }

    private static BufferedReader getReaderFromFile(String fileName) {

        BufferedReader bufferedReader = null;

        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bufferedReader;

    }

    private static boolean isCorrectItemArray(String line, String dataType) {

        if (dataType.equals("i")) {
            return !line.isEmpty() && line.matches("[-+]?\\d+");
        } else {
            return !line.isEmpty() && line.matches("[\\S]{0,}");
        }

    }

    private static String getLineFromBufferedReader(BufferedReader bufferedReader) {

        if (bufferedReader == null) {
            return null;
        }

        String line = null;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;

    }

    private static int compareValue(String valueOne, String valueTwo, String dataType) {

        if (dataType.equals("i")) {
            return  Integer.parseInt(valueOne) - Integer.parseInt(valueTwo);
        } else {
            return valueOne.compareTo(valueTwo);
        }

    }
}