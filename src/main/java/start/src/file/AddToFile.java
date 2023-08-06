package start.src.file;
import java.awt.*;
import java.io.*;


public class AddToFile {

    private File file;

    public AddToFile(String fileName){
       file = new File("/Users/vadim/Downloads/Прикладне програмування/LR-7FX/Test1/src/main/java/start/src/file/"+fileName+".txt");
    }

    public void writeToFile(String data, int numberOfRows){
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = numberOfRows; i>0; i--){
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openFile(){
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

}
