package src;

import java_cup.runtime.Symbol;    //Libreria de CUP para manipular simbolos
import java.io.Reader;             //Lectura de datos desde distintos tipos de fuentes
import java.io.IOException;        //Captura de errores de JDK durante ejecucion
import java.io.FileReader;         //Lectura de datos desde archivos fuente
import java.io.FileWriter;         //Escritura de datos en archivos destino
import java.io.BufferedReader;     //Lector de flujos de entrada para leer bloques de codigo en lugar de simbolo a simbolo
import java.io.BufferedWriter;     //Escritor de flujos de codigo para escribir bloques de codigo en lugar de simbolo a simbolo

public class AnalizadorLexico {
    // Entradas: La ruta con el codigo fuente del programa para testear
    // Salidas::Escribe en el documento TOKENS.txt todos los lexemas encontrados en el codigo del archivo test.txt
    // Restricciones::la ruta del codigo fuente debe ser valida
    // Objetivo: Generar al Lexer
    //           Realizar el analisis lexico del archivoFuente.txt
    //     
    public static void analisisLexico(String archivoFuente) {
        try {
            // Crear el lector y el escritor de archivo
            Reader reader = new BufferedReader(new FileReader(archivoFuente));
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:/CompiladoresEInterpretes/Proyectos/Compi-Proye-1/Proye1_Compi/elemGenerados/TOKENS.txt"));
    
            // Crear el lexer
            Lexer lexer = new Lexer(reader);
    
            // Analizar los tokens del archivo
            int numTokens = 0;
            Symbol token;
            while ((token = lexer.next_token()).sym != 0) {
                // Imprimir token en consola
                System.out.println("ID: " + numTokens + "\tToken: " + token.sym + "\tvalor: " + lexer.yytext());
    
                // Escribir token en el archivo
                writeToken(writer, token.sym, lexer.yytext());
    
                numTokens++;
            }
    
            // Imprimir el número total de tokens encontrados
            System.out.println("Cantidad de lexemas encontrados: " + numTokens);
    
            // Cerrar el escritor de archivo
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static void writeToken(BufferedWriter writer, int tokenSymbol, String tokenValue) throws IOException {
        writer.write("Token: " + tokenSymbol + "\tvalor: " + tokenValue);
        writer.newLine();
    } 
}
