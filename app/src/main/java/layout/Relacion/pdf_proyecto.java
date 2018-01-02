package layout.Relacion;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import principal.android.empresa.eduardo.Loading;
import principal.android.empresa.eduardo.MainActivity;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;


public class pdf_proyecto extends Fragment implements View.OnClickListener{

    private final static String NOMBRE_DIRECTORIO = "PDF Gestion de Proyectos";
    private final static String NOMBRE_DOCUMENTO = "Proyecto.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    String cod;
    Button Generar;
    int num;
    ProyectoBean objbean=null;
    ProyectoDao objdao=null;
    private static final int NOTIF_ALERTA_ID = 1;

    public pdf_proyecto() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View obj=inflater.inflate(R.layout.fragment_pdf_proyecto, container, false);

        // Generaremos el documento al hacer click sobre el botón.
        Generar=(Button)obj.findViewById(R.id.pdf_proyecto);
        Generar.setOnClickListener(this);
        cod = getArguments().getString("num");
        num=Integer.parseInt(cod);





        return obj;
    }





        @Override
        public void onClick(View v) {
            if(Generar==v){
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas crear pdf del proyecto?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        pdf_proyecto();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

            }


        }

        public void pdf_proyecto(){
            new async_generar_pdf().execute();
        }






    class async_generar_pdf extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Generando PDF", true);

        }

        @Override
        protected String doInBackground(String... params) {
            String mensaje = "";
            // Creamos el documento.
            Document documento = new Document();
            try {

                objbean=new ProyectoBean();
                objdao=new ProyectoDao();
                objbean=objdao.Listar_Proyecto_pdf(num);


                // Creamos el fichero con el nombre que deseemos.
                File f = crearFichero(NOMBRE_DOCUMENTO);

                // Creamos el flujo de datos de salida para el fichero donde
                // guardaremos el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream(
                        f.getAbsolutePath());

                // Asociamos el flujo que acabamos de crear al documento.
                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);




                documento.open();
                Paragraph par1=new Paragraph();
                Font fontitulo= FontFactory.getFont(FontFactory.HELVETICA, 16,
                        Font.BOLD);
                par1.add(new Phrase("GESTION DE PROYECTOS DE LA EMPRESA ISAGEN ",fontitulo));
                par1.setAlignment(Element.ALIGN_CENTER);
                par1.add(new Phrase(Chunk.NEWLINE));
                par1.add(new Phrase(Chunk.NEWLINE));
                documento.add(par1);




                Paragraph par2=new Paragraph();
                Font descripcion = FontFactory.getFont(FontFactory.HELVETICA, 13,
                        Font.BOLD);
                par2.add(new Phrase("INFORMACION DEL PROYECTO ",descripcion));
                par2.setAlignment(Element.ALIGN_CENTER);
                par2.add(new Phrase(Chunk.NEWLINE));
                par2.add(new Phrase(Chunk.NEWLINE));
                documento.add(par2);





                PdfPTable tabla=new PdfPTable(10);
                tabla.setWidthPercentage(100);

                PdfPCell celda1=new PdfPCell(new Paragraph("NUM. NUMERO",FontFactory.getFont("Arial",12,Font.BOLD )));
                PdfPCell celda2=new PdfPCell(new Paragraph("TITULO",FontFactory.getFont("Arial",12,Font.BOLD )));
                PdfPCell celda3=new PdfPCell(new Paragraph("DURACION",FontFactory.getFont("Arial",12,Font.BOLD )));
                PdfPCell celda4=new PdfPCell(new Paragraph("DESCRIPCION",FontFactory.getFont("Arial",12,Font.BOLD )));
                PdfPCell celda5=new PdfPCell(new Paragraph("TIPO",FontFactory.getFont("Arial",12,Font.BOLD )));
                PdfPCell celda6=new PdfPCell(new Paragraph("FASES",FontFactory.getFont("Arial",12,Font.BOLD)));
                PdfPCell celda7=new PdfPCell(new Paragraph("INICIO",FontFactory.getFont("Arial",12,Font.BOLD)));
                PdfPCell celda8=new PdfPCell(new Paragraph("FIN",FontFactory.getFont("Arial",12,Font.BOLD)));
                PdfPCell celda9=new PdfPCell(new Paragraph("GASTOS",FontFactory.getFont("Arial",12,Font.BOLD)));
                PdfPCell celda10=new PdfPCell(new Paragraph("CODIGO JEFE",FontFactory.getFont("Arial",12,Font.BOLD )));

                tabla.addCell(celda1);
                tabla.addCell(celda2);
                tabla.addCell(celda3);
                tabla.addCell(celda4);
                tabla.addCell(celda5);
                tabla.addCell(celda6);
                tabla.addCell(celda7);
                tabla.addCell(celda8);
                tabla.addCell(celda9);
                tabla.addCell(celda10);

                tabla.addCell(objbean.getNumero());
                tabla.addCell(objbean.getTitulo());
                tabla.addCell(objbean.getDuracion());
                tabla.addCell(objbean.getDescripcion());
                tabla.addCell(objbean.getTipo());
                tabla.addCell(objbean.getFases());
                tabla.addCell(objbean.getInicio());
                tabla.addCell(objbean.getFin());
                tabla.addCell(objbean.getGastos());
                tabla.addCell(objbean.getCODJEFE());

                documento.add(tabla);

                //CREAR NOTIFICACIONES
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.profile)
                                .setLargeIcon((((BitmapDrawable)getResources()
                                        .getDrawable(R.drawable.isagen)).getBitmap()))
                                .setContentTitle("Isagen")
                                .setContentText("Se genero PDF.")
                                .setContentInfo("")
                                .setTicker("Alerta!");
                //direccionar al dar click en notificacion
                Intent notIntent =
                        new Intent(getActivity(),Loading.class);

                PendingIntent contIntent =
                        PendingIntent.getActivity(
                                getActivity(), 0, notIntent, 0);

                mBuilder.setContentIntent(contIntent);

                NotificationManager mNotificationManager =(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
               //SNACBAR CON BUTTON
                Snackbar.make(getView(), "Se guardo el PDF", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                        .setAction("ABRIR PDF", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AbrirPDF();
                            }
                        }).show();
            }
            catch (DocumentException e) {
                Log.e(ETIQUETA_ERROR, e.getMessage());
                Snackbar.make(getView(), "Error al guardar PDF", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                Log.e(ETIQUETA_ERROR, e.getMessage());
                Snackbar.make(getView(), "Error al guardar PDF", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            } finally {

                // Cerramos el documento.
                documento.close();
            }





            return mensaje;
    }
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

        }

    }

    //Procedimiento para mostrar el documento PDF generado
    public void AbrirPDF() {
        //Llama a aplicacion de PDF
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+NOMBRE_DIRECTORIO+"/"+NOMBRE_DOCUMENTO;
        File targetFile = new File(path);
        Uri targetUri = Uri.fromFile(targetFile);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(targetUri, "application/pdf");
        startActivity(intent);
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }


    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStorageDirectory(),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

}