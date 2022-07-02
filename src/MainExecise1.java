import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainExecise1 {

    public static void main(String[] args) {

        Map<String, Family> familyTree = new HashMap<>();
        Map<String, Set<String>> relationTree = new HashMap<>();
        Family family;
        String[] data;
        // try-with-resources, auto close
        String line;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8))) {
            line = br.readLine();
            // read line by line
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                if (familyTree.containsKey(data[0])) {
                    family = familyTree.get(data[0]);
                    family.parentId.add(data[1]);
                    familyTree.put(data[0], family);
                } else {
                    family = new Family();
                    family.parentId.add(data[1]);
                    family.gender = data[2];
                    family.name = data[3];
                    familyTree.put(data[0], family);
                }

                Set<String> set = relationTree.computeIfAbsent(data[1], s -> new HashSet());
                set.add(data[0]);
            }

            printFamily(familyTree, relationTree, "");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printFamily(Map<String, Family> family, Map<String, Set<String>> relation, String treeLevel) {

        Set<String> keys = relation.keySet();

        for(String key : keys) {
            printFamily(family, relation, key, treeLevel);
        }
    }

    public static void printFamily(Map<String, Family> family, Map<String, Set<String>> relation, String currentId, String treeLevel) {
        Family f = family.get(currentId);
        Set<String> child;

        if (f != null) {
            if (treeLevel.isEmpty()) {
                System.out.println(f.name);
            }

            child = relation.get(currentId);

            if(child != null) {
                for (String k : child) {
                    treeLevel += "\t";
                    System.out.println(treeLevel + family.get(k).name);
                    printFamily(family, relation, k, treeLevel);
                }
            }
        }
    }

}
