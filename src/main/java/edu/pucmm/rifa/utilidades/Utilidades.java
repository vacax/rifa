package edu.pucmm.rifa.utilidades;

import com.google.gson.Gson;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import edu.pucmm.rifa.dominios.PoblacionRifa;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vacax on 03/07/16.
 */
public class Utilidades {

    /**
     * Agrega la serializacion por defecnto para UniRest.
     */
    public static void agregarSerializacionUniRest(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();

            public <T> T readValue(String s, Class<T> aClass) {
                //System.out.println("Clase: "+aClass.getName());
                //System.out.println("S: "+s);
                try{
                    return gson.fromJson(s, aClass);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object o) {
                try{
                    return gson.toJson(o);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     *
     * @param rutaArchivo
     * @return
     */
    public static List<PoblacionRifa> getListaDesdeArchivoExcel(String rutaArchivo) throws Exception{
        List<PoblacionRifa> lista = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();
        FileInputStream file = new FileInputStream(new File(rutaArchivo));
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);
        for(Row row : sheet){

            //Creando el objeto
            PoblacionRifa poblacionRifa = new PoblacionRifa();

            if(row.getRowNum() == 0){
                continue;
            }

            for(Cell cell :  row){
                //recorriendo el excel.
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print("["+cellRef.formatAsString()+"]");
                System.out.print(" - ");
                System.out.print(""+formatter.formatCellValue(cell));
                System.out.print(",");

                switch (cell.getColumnIndex()){
                    case 0:
                        poblacionRifa.setCedula(formatter.formatCellValue(cell));
                      break;
                    case 1:
                        poblacionRifa.setNombre(formatter.formatCellValue(cell));
                        break;
                    case 2:
                        poblacionRifa.setDepartamento(formatter.formatCellValue(cell));
                        break;
                }

            }

            //
            if(poblacionRifa.getCedula()!=null && poblacionRifa.getCedula().length() >0) {
                lista.add(poblacionRifa);
            }
            System.out.println("");
        }

        //retornando
        System.out.println("Lista cargada: "+lista.size());
        return lista;
    }
}
