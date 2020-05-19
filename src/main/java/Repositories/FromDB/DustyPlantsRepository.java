package Repositories.FromDB;

import Objects.DustyPlant;

import java.util.ArrayList;

public class DustyPlantsRepository {
    public DustyPlantsRepository(ArrayList<DustyPlant> dustyPlantArraylist) {
        this.dustyPlantArraylist = dustyPlantArraylist;
    }

    ArrayList<DustyPlant>dustyPlantArraylist=new ArrayList<>();
    public ArrayList<DustyPlant> getDustyPlantArraylist() {
        return dustyPlantArraylist;
    }

}