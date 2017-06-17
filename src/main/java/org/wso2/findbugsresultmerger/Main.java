package org.wso2.findbugsresultmerger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jayanga on 6/16/17.
 */
public class Main {
    public static void main(String[] args) {
        Utils.log("Starting Findbugs Result Merger");

        if (args.length != 2) {
            Utils.log("Usage: java -jar findbugs-result-merger-<version>.jar [MainFile] [NewFile]");
            return;
        }

        Path mainFilePath = Paths.get(args[0]);
        Path newFilePath = Paths.get(args[1]);

        if (!mainFilePath.toFile().exists()) {
            Utils.log("Main file doesn't exist");
            return;
        }

        if (!newFilePath.toFile().exists()) {
            Utils.log("New file doesn't exist");
            return;
        }

        FindbugResultMerger findbugResultMerger = new FindbugResultMerger();
        try {
            findbugResultMerger.loadMainResult(mainFilePath);
            findbugResultMerger.loadNewResult(newFilePath);
        } catch (IOException e) {
            Utils.log("FindbugResultMerger failed to merge files. " + e);
        }
//        findbugResultMerger.print();
        List<String> vl = findbugResultMerger.getVulnerabilityList();
        for (String line : vl) {
            Utils.log(line);
        }
    }
}
