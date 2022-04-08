package com.example.giuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.example.giuaki.R;
import com.example.giuaki.adapter.ChamCongAdapter;
import com.example.giuaki.database.DBHelper;
import com.example.giuaki.model.ChamCong;
import com.example.giuaki.model.Worker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChamCongActivity extends AppCompatActivity {
    Spinner spinnerWorker;
    ArrayList<ChamCong> listChamCong;
    ArrayAdapter<ChamCong> adapterChamCong;
    String lop;
    ListView lvTimekeeping;
    ArrayList<Worker> listWorker;
    ChamCongAdapter chamCongAdapter;
    DBHelper database;
    Calendar calendar;


    // dialog add
    Dialog dialog;
    TextView titleDialogAddChamCong;
    EditText txtMaCC;
    EditText txtMaCN;
    EditText txtTenCN;
    RadioButton rdbNam;
    RadioButton rdbNu;
    EditText txtNgayCC;
    ImageView btnChooseDate;
    Button btnConfirmAddChamCong;

    // export pdf

    ImageView btnExportPDFChamCong, btnBackManageChamCong;
    FloatingActionButton btnAddChamCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        addControls();
        initData();

    }
    private void addControls() {
        spinnerWorker = findViewById(R.id.spinnerWorker);
        lvTimekeeping = findViewById(R.id.lvTimekeeping);
        btnAddChamCong = findViewById(R.id.btnAddChamCong);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_chamcong);
        titleDialogAddChamCong = dialog.findViewById(R.id.titleDialogAddChamCong);
        txtMaCC = dialog.findViewById(R.id.txtMaCC);
        txtMaCN = dialog.findViewById(R.id.txtMaCN);
        txtTenCN = dialog.findViewById(R.id.txtTen);
//        rdbNam = dialog.findViewById(R.id.rdbNam);
//        rdbNu = dialog.findViewById(R.id.rdbNu);
        txtNgayCC = dialog.findViewById(R.id.txtNgayCC);
        btnChooseDate = dialog.findViewById(R.id.btnChooseDate);
        btnConfirmAddChamCong= dialog.findViewById(R.id.btnConfirmAddChamCong);

        // export pdf
        btnExportPDFChamCong = findViewById(R.id.btnExportPDFChamCong);
        btnBackManageChamCong = findViewById(R.id.btnBackManageChamCong);


    }
    private void addEvent(){
        btnBackManageChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChamCongActivity.this, "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChamCongActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        });
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        btnExportPDFChamCong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(ChamCongActivity.this, "btnPDF click",Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
    }
    private void initData() {
        database = new DBHelper(ChamCongActivity.this);
//        listClassroom = database.getAllClassrooms();
//        adapterClassroom = new ArrayAdapter<>(ManageStudent.this, android.R.layout.simple_spinner_dropdown_item, listClassroom);
//        spinnerClass.setAdapter(adapterClassroom);


    }

    private void createPDFFile(String path) {
        if (new File(path).exists()) {
            new File(path).delete();
        }

        try {
            Document document = new Document();
            // save
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.setPageSize(PageSize.A4);
            document.setMargins(50f, 5f, 5f, 10f);
            document.addCreationDate();
            document.addAuthor("TNT_LONG");
            document.addCreator("Long Nguyen");

            // font setting
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 18);
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            // Custom font
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            Paragraph paragraph = new Paragraph("");
            //create title of document
            Font titleFont = new Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "MANAGE TIMEKEEPING", Element.ALIGN_CENTER, titleFont);
            //specify column widths
            Worker worker = (Worker) spinnerWorker.getSelectedItem();
            ChamCong chamCong = new ChamCong();
            //spendingDataToPDF(classroom.getLOP());


            addNewItem(document, "Ma CN:"  +worker.getMaCN() + "           "+ "Ma CC:"   +
                    chamCong.getMaCC(), Element.ALIGN_CENTER, bf12);
            addNewItem(document, "                              ", Element.ALIGN_CENTER, bf12);


            //specify column widths

            for(Worker worker1: listWorker){
//                addNewItem(document, "TIMEKEEPING INFORMATION", Element.ALIGN_LEFT, bfBold12);
//
//
//                addNewItem(document, String.format("%-30s %-30s ","HO:"+ worker.getHo(),
//                        "NGAY CC:"+ new SimpleDateFormat("dd/MM/yyyy").format(chamCong.getNgayCC())),
//                        Element.ALIGN_LEFT, bf12);

                createTable(document,bfBold12, bf12, worker1);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addNewItem(Document document, String text, int align, Font font) {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
    private void createTable(Document document, Font header, Font data, Worker worker){
        Paragraph paragraph = new Paragraph();
        ArrayList<Mark> markArrayList = database.getStudentMark(student.getMaHocSinh());
        DecimalFormat df = new DecimalFormat("0.00");
        float[] columnWidths = {1f, 3f, 2f, 2f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(100f);

        //insert column headings
        insertCell(table, "STT", Element.ALIGN_RIGHT, 1, header);
        insertCell(table, "HO", Element.ALIGN_LEFT, 1, header);
        insertCell(table, "TEN", Element.ALIGN_LEFT, 1, header);
        insertCell(table, "NGAY CHAM CONG", Element.ALIGN_LEFT, 1, header);
//            insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
        table.setHeaderRows(1);

        //insert an empty row
//            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
        //create section heading by cell merging
//            insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
        double orderTotal, total = 0;

        //just some random data to fill
        for (int i=0;i<markArrayList.size(); i++) {
            Mark mark = markArrayList.get(i);
            insertCell(table, "" + (i+1), Element.ALIGN_RIGHT, 1, data);
            insertCell(table, mark.getSubject().getTenMH(), Element.ALIGN_RIGHT, 1, data);
            insertCell(table, mark.getSubject().getHeSo()+"" , Element.ALIGN_LEFT, 1, data);
            insertCell(table, mark.getDiem()==null?"":mark.getDiem() , Element.ALIGN_LEFT, 1, data);

//                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
//                total = total + orderTotal;
//                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

        }
        //merge the cells to create a footer for that section
        insertCell(table, "Average subject:", Element.ALIGN_RIGHT, 3, header);
        insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, header);

        paragraph.add(table);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

}