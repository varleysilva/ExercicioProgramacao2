/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varleysilva
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
        
    
    public void buscar() throws FileNotFoundException, IOException{
        
        //implementa os tipos de arquivos que apareceram para escolha
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("ppm", "pgm", "PPM", "PGM");

        //instanciando o selecionador de arquivosw
        JFileChooser fc = new JFileChooser();

        //adicionando os arquivos que poderam ser selecionados
        fc.setFileFilter(fileNameExtensionFilter);
        
        //nome da tela localizadora de arquivos
        fc.setDialogTitle("Selecione a imagem para ser trabalhada");
        
        //Recebe a resposta da janela quando algum evento do localizador de arquivo seja acessado
        int resposta = fc.showOpenDialog(null);
        
        //verifica se a resposta recebida é igual a OK
        if (resposta ==  JFileChooser.APPROVE_OPTION){
            //se sim abre um buffer do arquivo e joga na tela
            File file = new File(fc.getSelectedFile().getAbsolutePath());
            FileReader fis;
            try {
                fis = new FileReader(file);
                BufferedReader bis = new BufferedReader(fis);
                while(bis.ready()){
                    System.out.println(bis.readLine()+"\n");
                }
                bis.close();
                fis.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }  
    }
    
}
