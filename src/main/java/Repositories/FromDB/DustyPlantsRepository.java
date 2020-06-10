package Repositories.FromDB;

import Objects.FromDB.DustyPlant;

import java.util.ArrayList;

public final class DustyPlantsRepository {
    private  final ArrayList<DustyPlant>dustyPlantArraylist;
    public DustyPlantsRepository(ArrayList<DustyPlant> dustyPlantArraylist) {
        this.dustyPlantArraylist = dustyPlantArraylist;
    }


    public ArrayList<DustyPlant> getDustyPlantArraylist() {
        return dustyPlantArraylist;
    }

}