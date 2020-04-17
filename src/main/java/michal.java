import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class michal {
    private String filepath="Tłumaczenie.txt";
    ArrayList<String> englishStringArraylist = new ArrayList<>();
    ArrayList<String> polnishStringArraylist = new ArrayList<>();
    public michal(){
        BufferedReader read= null;
        FileReader fread= null;
        try {
            fread = new FileReader(filepath);
        }catch (FileNotFoundException fn){
            fn.printStackTrace();
        }
        try {
            String strCurrentLine;
            read = new BufferedReader (fread);

            while ((strCurrentLine = read.readLine()) != null) {
                englishStringArraylist.add(strCurrentLine);
                strCurrentLine = read.readLine();
                polnishStringArraylist.add(strCurrentLine);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(read!=null)
                    read.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<String> getEnglishStringArraylist() {
        return englishStringArraylist;
    }
    public ArrayList<String> getPolnishStringArraylist() {
        return polnishStringArraylist;
    }
    public String transleteEnglishToPolnish(String englishstring ){
        String polnish=null;
        for(int i=0;i<englishStringArraylist.size();i++)
        {
            if(englishStringArraylist.get(i).equals(englishstring))
            {
                polnish=polnishStringArraylist.get(i);
                break;
            }
        }
        return polnish;
    }
}