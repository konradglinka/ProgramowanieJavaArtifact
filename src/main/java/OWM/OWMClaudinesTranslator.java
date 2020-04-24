package OWM;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OWMClaudinesTranslator {
   // private String filepath="C:\\translateClaudiness.txt";
   ClassLoader classLoader = getClass().getClassLoader();
   private String filepath =classLoader.getResource("translateClaudiness.txt").getPath();
    ArrayList<String> englishStringArraylist = new ArrayList<>();
    ArrayList<String> polishStringArraylist = new ArrayList<>();
    public OWMClaudinesTranslator() throws IOException {
        BufferedReader read= null;
        FileReader fread= null;
        try {
            fread = new FileReader(filepath, StandardCharsets.UTF_8);
        }catch (FileNotFoundException fn){
            fn.printStackTrace();
        }
        try {
            String strCurrentLine;
            read = new BufferedReader (fread);

            while ((strCurrentLine = read.readLine()) != null) {

                englishStringArraylist.add(strCurrentLine);
                strCurrentLine = read.readLine();
                polishStringArraylist.add(strCurrentLine);
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
    public ArrayList<String> getPolishStringArraylist() {
        return polishStringArraylist;
    }
    public String translateEnglishToPolish(String englishString ){
        String polishString=null;
        for(int i=0;i<englishStringArraylist.size();i++)
        {
            if(englishStringArraylist.get(i).equals(englishString))
            {
                polishString= polishStringArraylist.get(i);
                break;
            }
        }
        return polishString;
    }
}