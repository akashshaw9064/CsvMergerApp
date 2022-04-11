import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MergeCSVs {
    public static void main(String[] args) throws IOException, FileMergeException {
        final File folder  = new File("D:\\CSVs");
        List<File> listOfFilesToBeMerged = Arrays.asList(folder.listFiles());
        String[] headers = null;
        //String firstFile = "D:\\MergedCSV\\first.csv";

        File[] targetFolder = new File("D:\\MergedCSV").listFiles();
        File targetFile = targetFolder[0];
        Scanner scanner = new Scanner(targetFile);

        if(scanner.hasNextLine())
            headers =scanner.nextLine().split(",");
        scanner.close();
        Iterator<File> iterFiles = listOfFilesToBeMerged.iterator();
        BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile.getAbsolutePath(), true));
        System.out.println(targetFile.getAbsolutePath());
        System.out.println("Merging files...");
        while(iterFiles.hasNext())
        {
            File nextFile = iterFiles.next();
            BufferedReader reader = new BufferedReader(new FileReader(nextFile));

            String line = null;
            String[] firstLine = null;
            if ((line = reader.readLine()) != null)
                firstLine = line.split(",");

            if (!Arrays.equals(headers, firstLine))
                throw new FileMergeException("Header mis-match between CSV files: '" +
                        targetFile.getAbsolutePath() + "' and '" + nextFile.getAbsolutePath());

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            System.out.println(targetFile.getName()+" and "+nextFile.getName());
        }
        writer.close();
        System.out.println("SUCCESS");
    }
}
