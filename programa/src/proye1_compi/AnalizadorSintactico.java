package src;

import java_cup.runtime.Symbol;    //Libreria de CUP para manipular simbolos
import java.io.Reader;             //Lectura de datos desde distintos tipos de fuentes
import java.io.IOException;        //Captura de errores de JDK durante ejecucion
import java.util.Map;              //Libreria para mapeo de estructuras de datos
import java.io.FileReader;         //Lectura de datos desde archivos fuente
import java.io.FileWriter;         //Escritura de datos en archivos destino
import java.io.BufferedReader;     //Lector de flujos de entrada para leer bloques de codigo en lugar de simbolo a simbolo
import java.io.BufferedWriter;     //Escritor de flujos de codigo para escribir bloques de codigo en lugar de simbolo a simbolo

public class AnalizadorSintactico {
    // Entradas: La ruta con el codigo fuente del programa para testear
    // Salidas: Escritura de un archivo .txt que contiene las tablas de simbolos definidas durante el parseo 
    // Restricciones::la ruta del codigo fuente debe ser valida
    // Objetivo Analizar la estructura sintactica de un archivoFuente y listar las tablas de simbolos definidas en un archivoRespuesta, ademas de indicar si el archivo puede ser generado haciendo uso de la gramatica definida
    public static void analisisSintactico(String archivoFuente) {
        try {
            // Crear archivo de salida para las tablas de símbolos
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:/CompiladoresEInterpretes/Proyectos/Compi-Proye-1/Proye1_Compi/elemGenerados/tablaSimbolos.txt"));
    
            // Crear el lexer y el parser
            Reader reader = new BufferedReader(new FileReader(archivoFuente));
            Lexer lexer = new Lexer(reader);
            parser parser = new parser(lexer);
    
            // Ejecutar el análisis sintáctico
            parser.parse();
    
            // Verificar si hubo errores durante el análisis
            if (!parser.getErrores()) {
                ListaTablasSimbolos tableStack = parser.getListaTablasSimbolos();
    
                // Si no hay tablas de símbolos, imprimir mensaje
                if (tableStack.verificarVacio()) {
                    System.out.println("No fue posible generar tablas de simbolos");
                } else {
                    // Recorrer y escribir las tablas de símbolos
                    writeSymbolTables(writer, tableStack);
                }
            } else {
                System.out.println("El parser no se ha generado debido a errores sintacticos");
                writer.write("Imposible generar parser");
            }
    
            writer.close();
        } catch (Exception e) {
            System.out.println("El parser no se ha generado debido a errores de sintaxis");
        }
    }
    
    private static void writeSymbolTables(BufferedWriter writer, ListaTablasSimbolos tableStack) throws IOException {
        for (TablaSimbolos tabla : tableStack.getStack()) {
            // Imprimir en consola las tablas de símbolos
            System.out.println("\nTabla: " + tabla.getName() + " Retorno: " + tabla.getvalorRetorno());
    
            // Escribir en el archivo las tablas de símbolos
            writer.write("\n\nTabla: " + tabla.getName() + " Retorno: " + tabla.getvalorRetorno());
            writer.newLine();
    
            for (Map.Entry<String, String> entry : tabla.getidentificadores().entrySet()) {
                String valor = entry.getKey();
                String tipo = entry.getValue();
                writer.write("valor: " + valor + " tipo: " + tipo + "\n");
                System.out.println("valor: " + valor + " tipo: " + tipo);
            }
        }
    }
    
}
