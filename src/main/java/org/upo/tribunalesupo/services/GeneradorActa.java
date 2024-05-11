package org.upo.tribunalesupo.services;


import org.upo.tribunalesupo.data.Defensa;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class GeneradorActa {

    public GeneradorActa() {

    }

    public File generarActa(Defensa defensa) throws IOException {
        File file = File.createTempFile("Acta", ".pdf", new File("/myfiles/"));
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file.getPath()));
        Document doc = new Document(pdfDoc);
        doc.setFontSize(10);
        // Título
        // Agregar textos al PDF
        String titulo = "ACTA DE EVALUACIÓN DEL TRIBUNAL DE TFG";
        String escuela = "ESCUELA POLITÉCNICA SUPERIOR";
        String grado = "GRADO EN INGENIERÍA INFORMÁTICA EN SISTEMAS DE INFORMACIÓN";
        String universidad = "UNIVERSIDAD PABLO DE OLAVIDE";
        String trabajoFinGrado = "TRABAJO DE FIN DE GRADO";


        // Agregar párrafos al documento
        Paragraph p1 = new Paragraph(titulo).setTextAlignment(TextAlignment.CENTER).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10f).setMarginBottom(3f).setMarginTop(0);
        Paragraph p2 = new Paragraph(trabajoFinGrado + "\n" + escuela + "\n" + grado + "\n" + universidad).setTextAlignment(TextAlignment.CENTER).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10f).setMarginBottom(3f).setMarginTop(0);

        doc.add(p1);
        doc.add(p2);

        // Alumno
        Paragraph alumno = new Paragraph()
                .setMarginBottom(3f)
                .add(new Text("NOMBRE DEL ESTUDIANTE: ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)))
                .add(new Text(defensa.getTribunal().getCodigoTFG().getAlumno().getPersona().toString() ));
        doc.add(alumno);

        //Tutor
        Paragraph tutor = new Paragraph()
                .setMarginBottom(3f)
                .add(new Text("NOMBRE DEL TUTOR/ES: ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)))
                .add(new Text(defensa.getTribunal().getCodigoTFG().getDocente1().getPersona().toString() ));
        if(defensa.getTribunal().getCodigoTFG().getDocente2() != null){
            tutor.add(new Text(", " + defensa.getTribunal().getCodigoTFG().getDocente2().getPersona().toString() ));
        }
        doc.add(tutor);

        // Datos del TFG
        Paragraph tfg = new Paragraph()
                .setMarginBottom(3f)
                .add(new Text("TITULO DEL TRABAJO: ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)))
                .add(new Text(defensa.getTribunal().getCodigoTFG().getNombre()));
        doc.add(tfg);

        Table criterios = new Table(5);
        //Titulos
        Color fondoTitulo = new DeviceRgb(217, 234, 211);
        Color fondoCriterio = new DeviceRgb(255, 242, 204);
        Color blanco = new DeviceRgb(255, 255, 255);
        criterios.addHeaderCell(new Cell().add(new Paragraph("CRITERIO")).setBackgroundColor(fondoTitulo).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.CENTER));
        criterios.addHeaderCell(new Cell().add(new Paragraph("ASPECTOS")).setBackgroundColor(fondoTitulo).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.CENTER));
        criterios.addHeaderCell(new Cell().add(new Paragraph("PUNTUACIÓN")).setBackgroundColor(fondoTitulo).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.CENTER));
        criterios.addHeaderCell(new Cell().add(new Paragraph("PONDERACIÓN (0 A 10)")).setBackgroundColor(fondoTitulo).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.CENTER));
        criterios.addHeaderCell(new Cell().add(new Paragraph("PUNTUACIÓN PONDERADA")).setBackgroundColor(fondoTitulo).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.CENTER));
        
        //Columnas auxiliares
        String[] criterio = {"Calidad técnica del proyecto desarrollado","Adquisición de las competencias de la asignatura","Presentación","Defensa"};
        String[][] aspecto = {
                {"Complejidad del trabajo desarrollado.",
                        "Aportaciones técnicas en el proyecto.",
                        "Grado de innovación.",
                        "Conocimientos de las tecnologías.",
                        "Calidad de la memoria.",
                        "Destrezas en organización y/o planificación.",
                        "Destrezas en desarrollo.",
                        "Grado de cumplimiento de objetivos."},

                {"Nivel de adquisición."},

                {"Organización de la exposición.",
                        "Claridad en la explicación.",
                        "Corrección en textos, figuras y/o tablas.",
                        "Habilidad de comunicación."},

                {"Exposición clara de argumentos.",
                        "Habilidad comunicativa en respuestas a preguntas del tribunal.",
                        "Corrección técnica de las respuestas."}
        };
        Double[] ponderaciones = {5.0,2.5,1.5,1.0};
        List[] listaAspecto = new List[aspecto.length];

        for (int i = 0; i < aspecto.length; i++) {
            int j;
            List aux = new List();
            for(j = 0; j < aspecto[i].length; j++){
                aux.add(new ListItem(aspecto[i][j]));
            }
            listaAspecto[i] = aux;
            listaAspecto[i].setListSymbol("•");
        }
        //Puntuaciones
        DecimalFormat formateador = new DecimalFormat("0.00");
        DecimalFormat formateador2 = new DecimalFormat("0.0");
        Double[] punciacion = {defensa.getCalidad(), defensa.getAdquisicion(), defensa.getPresentacion(), defensa.getDefensa()};
        Double total = 0.0;
        //Generar la tabla
        for (int i=0; i<5; i++) {
            if(i<4) {
                double puntPonderada = (punciacion[i] * ponderaciones[i]) / 10;
                total = total + puntPonderada;
                criterios.addCell(createCell(criterio[i], 1, TextAlignment.CENTER, VerticalAlignment.MIDDLE).setBackgroundColor(fondoCriterio));
                criterios.addCell(new Cell().add(listaAspecto[i]).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9).setVerticalAlignment(VerticalAlignment.MIDDLE));
                criterios.addCell(createCell(formateador.format(punciacion[i]), 1, TextAlignment.CENTER, VerticalAlignment.MIDDLE));
                criterios.addCell(createCell(formateador2.format(ponderaciones[i]), 1, TextAlignment.CENTER, VerticalAlignment.MIDDLE));
                criterios.addCell(createCell(formateador.format(puntPonderada), 1, TextAlignment.CENTER, VerticalAlignment.MIDDLE));
            }else{
                criterios.addCell(new Cell(1,3).add(new Paragraph("")).setBorder(Border.NO_BORDER));
                criterios.addCell(new Cell().add(new Paragraph("TOTAL: ")).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(fondoTitulo));
                criterios.addCell(new Cell().add(new Paragraph(formateador.format(total))).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(fondoTitulo));
            }
        }

        doc.add(criterios);

        //fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Paragraph fecha = new Paragraph().setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .add(new Text("FECHA: "))
                .add(new Text(defensa.getTribunal().getFecha().format(formato)));
        doc.add(fecha);

        //Firmas
        Paragraph firma = new Paragraph().setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .add(new Text("FIRMAS: "));
        doc.add(firma);
        Table tablaFirmas = new Table(3);
        tablaFirmas.addCell(new Cell().setWidth(170).setHeight(60)).addCell(new Cell().setWidth(170).setHeight(60)).addCell(new Cell().setWidth(170).setHeight(60));
        tablaFirmas.addCell(new Cell().add(new Paragraph("PRESIDENTE: " + defensa.getTribunal().getDocente1().getPersona().toString()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))));
        tablaFirmas.addCell(new Cell().add(new Paragraph("VOCAL: " + defensa.getTribunal().getDocente2().getPersona().toString()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))));
        tablaFirmas.addCell(new Cell().add(new Paragraph("SECRETARIO/A: " + defensa.getTribunal().getDocente3().getPersona().toString()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))));
        doc.add(tablaFirmas);

        doc.add(new Paragraph(""));

        doc.close();

        return file;
    }

    private static Cell createCell(String content, int colspan, TextAlignment alignment, VerticalAlignment verticalAlignment) throws IOException {
        Cell cell = new Cell(1, colspan).add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
        return cell;
    }

}
