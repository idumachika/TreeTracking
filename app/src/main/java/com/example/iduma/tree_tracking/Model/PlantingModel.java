package com.example.iduma.tree_tracking.Model;

/**
 * Created by iduma on 3/28/18.
 */

public class PlantingModel {
    private String planterName, treeCoordinates,typeOfTrees, noOfTrees;

    public PlantingModel() {
    }

    public PlantingModel(String planterName, String treeCoordinates, String typeOfTrees, String noOfTrees) {
        this.planterName = planterName;
        this.treeCoordinates = treeCoordinates;
        this.typeOfTrees = typeOfTrees;
        this.noOfTrees = noOfTrees;
    }

    public String getPlanterName() {
        return planterName;
    }

    public void setPlanterName(String planterName) {
        this.planterName = planterName;
    }

    public String getTreeCoordinates() {
        return treeCoordinates;
    }

    public void setTreeCoordinates(String treeCoordinates) {
        this.treeCoordinates = treeCoordinates;
    }

    public String getTypeOfTrees() {
        return typeOfTrees;
    }

    public void setTypeOfTrees(String typeOfTrees) {
        this.typeOfTrees = typeOfTrees;
    }

    public String getNoOfTrees() {
        return noOfTrees;
    }

    public void setNoOfTrees(String noOfTrees) {
        this.noOfTrees = noOfTrees;
    }
}
