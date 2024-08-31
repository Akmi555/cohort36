import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class MetadataGenerator {

    private static String pathPrefix;

    private static final List<String> sortPriorityList = List.of(
            "/entity/",
            "/dto/",
            "/repository/",
            "/service/",
            "/controller/",
            "/constants/",
            "/config/",
            "/security/",
            "/logging/",
            "/exception_handling/",
            "Application.java",
            "/resources/",
            "/webapp/",
            "/test/",
            "/pom.",
            "/presentation/",
            "/presentations/"
    );

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {

        File file = new File("metadata.json");

        try (Writer writer = new FileWriter(file)) {
            file.createNewFile();

            writer.write("""
                    {
                      "plan": "plan.md",
                      "theory": "theory.md",
                      "homework": "homework.md",
                      "code": [
                    """);

            File srcDir = new File("src");

            setProjectName(srcDir);

            Set<String> fileNames = new TreeSet<>((x, y) -> {
                int priorityX = sortPriorityList.size();
                int priorityY = sortPriorityList.size();

                for (int i = 0; i < sortPriorityList.size(); i++) {
                    String pathPart = sortPriorityList.get(i);

                    if (x.contains(pathPart)) {
                        priorityX = i;
                    }

                    if (y.contains(pathPart)) {
                        priorityY = i;
                    }
                }

                if (priorityX != priorityY) {
                    return priorityX - priorityY;
                }

                return x.compareTo(y);
            });

            generateFileNamesList(fileNames, srcDir);
            addDockerfileAndPom(fileNames);
            writer.write(generateFilesBundle(fileNames));

            writer.write("""

                      ],
                      "video": ""
                    }""");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDockerfileAndPom(Set<String> fileNames) {
        File dockerfile = new File("Dockerfile");
        File pom = new File("pom.xml");

        if (dockerfile.exists()) {
            fileNames.add(pathPrefix + dockerfile.getName());
        }

        if (pom.exists()) {
            fileNames.add(pathPrefix + pom.getName());
        }
    }

    private static String generateFilesBundle(Set<String> fileNames) {
        StringBuilder builder = new StringBuilder();
        for (String fileName : fileNames) {
            builder.append("    \"").append(fileName).append("\",\n");
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    private static void generateFileNamesList(Set<String> fileNames, File parentDir) {
        File[] files = parentDir.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            String fileName = file.getPath();
            if (file.isFile() && !fileName.contains("MetadataGenerator.java")) {
                fileNames.add(pathPrefix + fileName.replace("\\", "/"));
            } else {
                generateFileNamesList(fileNames, file);
            }
        }
    }

    private static void setProjectName(File srcDir) {
        String path = srcDir.getAbsolutePath();
        String[] pathParts = path.split("\\\\");
        String projectName = pathParts[pathParts.length - 2];
        pathPrefix = String.format("code/%s/", projectName);
    }
}