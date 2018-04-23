package com.example.iduma.tree_tracking.Model;

/**
 * Created by iduma on 3/28/18.
 */

public class PlantingModel {
    String treeCoordinates, noOfTrees, reporterName;

    public PlantingModel() {
    }

    public PlantingModel(String treeCoordinates, String noOfTrees, String reporterName) {
        this.treeCoordinates = treeCoordinates;
        this.noOfTrees = noOfTrees;
        this.reporterName = reporterName;
    }

    public String getTreeCoordinates() {
        return treeCoordinates;
    }

    public void setTreeCoordinates(String treeCoordinates) {
        this.treeCoordinates = treeCoordinates;
    }

    public String getNoOfTrees() {
        return noOfTrees;
    }

    public void setNoOfTrees(String noOfTrees) {
        this.noOfTrees = noOfTrees;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
