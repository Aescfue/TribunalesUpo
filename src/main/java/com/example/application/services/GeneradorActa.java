package com.example.application.services;


import com.example.application.data.Defensa;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GeneradorActa {


    public GeneradorActa() {

    }

    public File generarActa(Defensa defensa) throws IOException {
        File file = File.createTempFile("Acta", ".pdf", new File("/myfiles/"));
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file.getPath()));
        Document doc = new Document(pdfDoc);

        // Título
        Paragraph titulo = new Paragraph("Acta del Tribunal")
                .setFontSize(20f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20f);
        doc.add(titulo);

        // Datos del TFG
        Paragraph tfg = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Nombre del TFG: ").setBold())
                .add(new Text(defensa.getTribunal().getCodigoTFG().getNombre()));
        doc.add(tfg);

        // Alumno
        Paragraph alumno = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Alumno: ").setBold())
                .add(new Text(defensa.getTribunal().getCodigoTFG().getAlumno().getPersona().toString() ));
        doc.add(alumno);

        //Tutor
        Paragraph tutor = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Tutor: ").setBold())
                .add(new Text(defensa.getTribunal().getCodigoTFG().getDocente1().getPersona().toString() ));
        if(defensa.getTribunal().getCodigoTFG().getDocente2() != null){
            tutor.add(new Text(", " + defensa.getTribunal().getCodigoTFG().getDocente2().getPersona().toString() ));
        }
        doc.add(tutor);

        //convocatoria
        Paragraph convocatoria = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Convocatoria: ").setBold())
                .add(new Text(defensa.getTribunal().getConvocatoria().getId().getCurso() + " " +defensa.getTribunal().getConvocatoria().getId().getNumero()));
        doc.add(convocatoria);

        // Datos del Tribunal
        doc.add(new Paragraph("Tribunal").setBold().setUnderline().setMarginBottom(5f));

        //fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Paragraph fecha = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Fecha del tribunal: ").setBold())
                .add(new Text(defensa.getTribunal().getFecha().format(formato)));
        doc.add(fecha);


        Table tribunalTable = new Table(2);
        tribunalTable.addCell(new Cell().add(new Paragraph("Presidente:").setBold())
                .setBorder(Border.NO_BORDER));
        tribunalTable.addCell(new Cell().add(new Paragraph(defensa.getTribunal().getDocente1().getPersona().toString()))
                .setBorder(Border.NO_BORDER));
        tribunalTable.addCell(new Cell().add(new Paragraph("Vocal:").setBold())
                .setBorder(Border.NO_BORDER));
        tribunalTable.addCell(new Cell().add(new Paragraph(defensa.getTribunal().getDocente2().getPersona().toString()))
                .setBorder(Border.NO_BORDER));
        tribunalTable.addCell(new Cell().add(new Paragraph("Secretario:").setBold())
                .setBorder(Border.NO_BORDER));
        tribunalTable.addCell(new Cell().add(new Paragraph(defensa.getTribunal().getDocente3().getPersona().toString()))
                .setBorder(Border.NO_BORDER));
        doc.add(tribunalTable.setMarginBottom(20f));

        //Rubrica
        Paragraph rubrica = new Paragraph()
                .setMarginBottom(5f)
                .add(new Text("Valoración de la defensa: ").setBold())
                .add(new Text(defensa.getRubrica().toString()));
        doc.add(rubrica);
        doc.close();

        return file;
    }
}
