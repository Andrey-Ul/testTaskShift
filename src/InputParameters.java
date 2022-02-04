import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class InputParameters {

    private boolean isAsc = true;
    private String dataType;
    private String outputFile;
    private ArrayList<String> inputFiles = new ArrayList<String>();

    public InputParameters(String[] args) {

        int numberItem = 1;

        for (String arg : args) {

            String parameter = arg.trim();

            if (numberItem < 3) {

                switch (parameter) {
                    case ("-a"):
                        isAsc = true;
                        break;
                    case ("-d"):
                        isAsc = false;
                        break;
                    case ("-s"):
                        dataType = "s";
                        break;
                    case ("-i"):
                        dataType = "i";
                        break;
                }

                if (numberItem == 2 && parameter.length() > 2) {
                    outputFile = parameter;
                    numberItem++;
                }

            } else if (numberItem == 3) {
                outputFile = parameter;
            } else if (numberItem > 3) {
                if (!inputFiles.contains(parameter)) {
                    inputFiles.add(parameter);
                }
            }

            numberItem++;

        }

    }

    public boolean isCorrectInputParameters() {

        String errorMessage = "";

        if (dataType == null) {
            errorMessage = errorMessage + "Sort data type not specified!\n";
        }
        if (outputFile == null) {
            errorMessage = errorMessage + "No output file specified!\n";
        }
        if (inputFiles.size() == 0) {
            errorMessage = errorMessage + "No input files specified!\n";
        }
        if (!outputFile.endsWith(".txt")) {
            errorMessage = errorMessage + "Type output file is not txt!\n";
        }
        for (int i = 0; i < inputFiles.size(); i++) {

            String fileName = inputFiles.get(i);
            boolean removeFileFromList = false;

            if (!(fileName.endsWith(".txt"))) {
                removeFileFromList = true;
                errorMessage = errorMessage + "Type input file " + fileName + " is not txt!\n";
            }
            if (!Files.exists(Path.of(fileName))) {
                removeFileFromList = true;
                errorMessage = errorMessage + "Input file " + fileName + " do not exist!\n";
            }

            if (removeFileFromList) {
                inputFiles.remove(fileName);
                i--;
            }

        }
        if (inputFiles.size() == 0) {
            errorMessage = errorMessage + "No valid input files specified!\n";
        }

        if (!errorMessage.isEmpty()) {
            System.out.print(errorMessage);
        }

        return errorMessage.isEmpty();

    }

    public boolean isAsc() {
        return isAsc;
    }

    public String getDataType() {
        return dataType;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public ArrayList<String> getInputFiles() {
        return inputFiles;
    }
}
