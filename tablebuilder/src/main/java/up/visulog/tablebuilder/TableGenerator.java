/*
	22015094 - Idil Saglam
*/
package up.visulog.tablebuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.jgit.revwalk.RevCommit;
import up.visulog.analyzer.GroupBy;

class TableGenerator {

    private final int PADDING_SIZE = 2;
    private final String NEW_LINE = "\n";
    private final String TABLE_JOINT_SYMBOL = "+";
    private final String TABLE_V_SPLIT_SYMBOL = "|";
    private final String TABLE_H_SPLIT_SYMBOL = "-";
    private final Map<ZonedDateTime, Map<String, Set<RevCommit>>> commits;
    private final List<String> headersList;
    private final GroupBy groupBy;
    private final List<List<String>> rowsList;

    TableGenerator(Map<ZonedDateTime, Map<String, Set<RevCommit>>> commits, GroupBy groupBy) {
        this.commits = commits;
        this.headersList = new ArrayList<>();
        this.groupBy = groupBy;

        this.headersList.add("Periods");
        this.rowsList = new ArrayList<>();
        List<String> row;
        for (Entry<ZonedDateTime, Map<String, Set<RevCommit>>> commitEntry :
                this.commits.entrySet()) {
            row = new ArrayList<>();
            row.add(this.groupBy.formatDateTime(commitEntry.getKey()));
            for (Entry<String, Set<RevCommit>> userCommitsEntry :
                    commitEntry.getValue().entrySet()) {
                if (!this.headersList.contains(userCommitsEntry.getKey())) {
                    this.headersList.add(userCommitsEntry.getKey());
                }
                row.add(String.valueOf(userCommitsEntry.getValue().size()));
            }
            this.rowsList.add(row);
        }
    }

    /** Prints the generated table */
    void generate() {
        Map<Integer, Integer> columnMaxWidthMapping = getMaximumWidthOfTable();
        String title = String.format("Commits per %s", this.groupBy);
        int totalW = columnMaxWidthMapping.values().stream().reduce(0, (a, b) -> a + b + 4);
        totalW = totalW + columnMaxWidthMapping.keySet().size() - 1;
        int nbSpaces = (totalW - title.length()) / 2;
        System.out.printf(
                "%s%s%s%n%s%s%s%s%s%n",
                TABLE_JOINT_SYMBOL,
                TABLE_H_SPLIT_SYMBOL.repeat(totalW),
                TABLE_JOINT_SYMBOL,
                TABLE_V_SPLIT_SYMBOL,
                " ".repeat(nbSpaces),
                title,
                " ".repeat(totalW - title.length() - nbSpaces),
                TABLE_V_SPLIT_SYMBOL);
        System.out.println(this.generateTable(columnMaxWidthMapping, null).trim());
    }

    private String generateTable(
            Map<Integer, Integer> columnMaxWidthMapping, Integer overRiddenHeaderHeight) {
        StringBuilder stringBuilder = new StringBuilder();

        int rowHeight = overRiddenHeaderHeight == null ? 1 : overRiddenHeaderHeight;

        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
        stringBuilder.append(NEW_LINE);

        int index = 0;
        for (String header : this.headersList) {
            fillCell(stringBuilder, header, index, columnMaxWidthMapping);
            index++;
        }

        stringBuilder.append(NEW_LINE);

        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);

        for (List<String> row : rowsList) {

            stringBuilder.append(NEW_LINE.repeat(Math.max(0, rowHeight)));
            index = 0;
            for (String rowContent : row) {
                fillCell(stringBuilder, rowContent, index, columnMaxWidthMapping);
                index++;
            }
        }

        stringBuilder.append(NEW_LINE);
        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }

    private void fillSpace(StringBuilder stringBuilder, int length) {
        stringBuilder.append(" ".repeat(Math.max(0, length)));
    }

    private void createRowLine(
            StringBuilder stringBuilder,
            int headersListSize,
            Map<Integer, Integer> columnMaxWidthMapping) {
        for (int i = 0; i < headersListSize; i++) {
            if (i == 0) {
                stringBuilder.append(TABLE_JOINT_SYMBOL);
            }

            stringBuilder.append(
                    TABLE_H_SPLIT_SYMBOL.repeat(
                            Math.max(0, columnMaxWidthMapping.get(i) + PADDING_SIZE * 2)));
            stringBuilder.append(TABLE_JOINT_SYMBOL);
        }
    }

    private Map<Integer, Integer> getMaximumWidthOfTable() {
        Map<Integer, Integer> columnMaxWidthMapping = new HashMap<>();

        for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {
            columnMaxWidthMapping.put(columnIndex, 0);
        }
        int index = 0;
        for (String header : headersList) {

            if (header.length() > columnMaxWidthMapping.get(index)) {
                columnMaxWidthMapping.put(index, header.length());
            }
            index++;
        }
        for (List<String> row : rowsList) {
            index = 0;

            for (String rowText : row) {
                if (rowText.length() > columnMaxWidthMapping.get(index)) {
                    columnMaxWidthMapping.put(index, rowText.length());
                }
                index++;
            }
        }
        for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

            if (columnMaxWidthMapping.get(columnIndex) % 2 != 0) {
                columnMaxWidthMapping.put(columnIndex, columnMaxWidthMapping.get(columnIndex) + 1);
            }
        }
        return columnMaxWidthMapping;
    }

    private int getOptimumCellPadding(
            int cellIndex,
            int datalength,
            Map<Integer, Integer> columnMaxWidthMapping,
            int cellPaddingSize) {
        if (datalength % 2 != 0) {
            datalength++;
        }

        if (datalength < columnMaxWidthMapping.get(cellIndex)) {
            cellPaddingSize =
                    cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
        }

        return cellPaddingSize;
    }

    private void fillCell(
            StringBuilder stringBuilder,
            String cell,
            int cellIndex,
            Map<Integer, Integer> columnMaxWidthMapping) {

        int cellPaddingSize =
                getOptimumCellPadding(
                        cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);

        if (cellIndex == 0) {
            stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
        }

        fillSpace(stringBuilder, cellPaddingSize);
        stringBuilder.append(cell);
        if (cell.length() % 2 != 0) {
            stringBuilder.append(" ");
        }

        fillSpace(stringBuilder, cellPaddingSize);

        stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
    }
}
