/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

//import static boton_imprimir.NumberToLetterConverter.convertNumberToLetter;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.JobAttributes;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.Math.round;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
//import jdk.nashorn.internal.objects.NativeString;
//import static jdk.nashorn.internal.objects.NativeString.length;
//import static jdk.nashorn.internal.objects.NativeString.trim;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.util.JRLoader;
import org.json.JSONException;

/**
 *
 * @author heyner.rivas
 */
public class VENTAS extends javax.swing.JInternalFrame {
    
    
    
    

    int id;

    Calendar cal;
    String descripcion, year;
    int cantidad = 0;
    double importe;
    //String productos[] = {"galletas", "aguas", "cigarro", "chicle", "cafe", "empanada", "gomitas"};
   //double precios[] = {0.50, 2.00, 1.00, 0.20, 3.00, 2.00, 0.50};
    double precio = 0;
    DefaultTableModel modelo = new DefaultTableModel();
    ArrayList<VENTAS> listaVentas = new ArrayList<>();

    Connection conexion = null;
    PreparedStatement preparedStatement = null;

    
    
    ArrayList<String> productoss = new ArrayList<>();
ArrayList<Double> precioss = new ArrayList<>();
    /**
     * Creates new form VENTAS
     */
    public VENTAS() {
        initComponents();
        
        obtenerProductosDesdeBD(); 
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(productoss.toArray(new String[0]));
    cbo_producto.setModel(comboModel);

       // DefaultComboBoxModel comboModel = new DefaultComboBoxModel(productos);
       // cbo_producto.setModel(comboModel);
        
      // modelo.addColumn("ID");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO U");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("IMPORTE");
        
         tbl_productos.getColumnModel().getColumn(0).setMinWidth(0);
    tbl_productos.getColumnModel().getColumn(0).setMaxWidth(0);
    tbl_productos.getColumnModel().getColumn(0).setWidth(0);

        actualizarTabla();

        calcularPrecio();

        cal = Calendar.getInstance();
        year = Integer.toString(cal.get(Calendar.YEAR));
        fecha_v.setDate(cal.getTime());

    }
    
    
    
    private void obtenerProductosDesdeBD() {
    try {
       conexion = conectar.obtenerConexion();

        String consulta = "SELECT nombre_producto, precio FROM almacen_producto"; // Ajusta la consulta a tu esquema de base de datos
        preparedStatement = conexion.prepareStatement(consulta);
        
        
        Map<String, Double> productosYPrecios = new HashMap<>();

        ResultSet resultados = preparedStatement.executeQuery();

        while (resultados.next()) {
            String cbo_producto = resultados.getString("nombre_producto");
            double txt_precio = resultados.getDouble("precio");
            
             productosYPrecios.put(cbo_producto, txt_precio);

            productoss.add(cbo_producto);
            precioss.add(txt_precio);
            
            
          }
        
        
        

        // Cerrar la conexión y liberar recursos
        conexion.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }}
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public JComboBox<String> getCbox_cantidad() {
        return cbo_producto;
    }

    public void setCbox_cantidad(JComboBox<String> cbox_cantidad) {
        this.cbo_producto = cbox_cantidad;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JButton getjButton2() {
        return btnModificar;
    }

    public void setjButton2(JButton jButton2) {
        this.btnModificar = jButton2;
    }

    public JButton getjButton3() {
        return jButton3;
    }

    public void setjButton3(JButton jButton3) {
        this.jButton3 = jButton3;
    }

//    public JButton getjButton4() {
//        return jButton4;
//    }
//
//    public void setjButton4(JButton jButton4) {
//        this.jButton4 = jButton4;
//    }

    public JButton getjButton5() {
        return jButton5;
    }

    public void setjButton5(JButton jButton5) {
        this.jButton5 = jButton5;
    }

    public JButton getjButton6() {
        return jButton6;
    }

    public void setjButton6(JButton jButton6) {
        this.jButton6 = jButton6;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTable getjTable1() {
        return tbl_productos;
    }

    public void setjTable1(JTable jTable1) {
        this.tbl_productos = jTable1;
    }

//    public JTextField getTxt_fecha() {
//        return txt_fecha;
//    }
//
//    public void setTxt_fecha(JTextField txt_fecha) {
//        this.txt_fecha = txt_fecha;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        cbo_producto = new javax.swing.JComboBox<>();
        spn_cantidad = new javax.swing.JSpinner();
        txt_precio = new javax.swing.JLabel();
        txt_importe = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        lbl_igv = new javax.swing.JLabel();
        lbl_subtotal1 = new javax.swing.JLabel();
        lbl_subtotal2 = new javax.swing.JLabel();
        lbl_subtotal3 = new javax.swing.JLabel();
        fecha_v = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(212, 175, 55));

        jLabel4.setText("FECHA:");

        tbl_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_productos);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ADF.jpg"))); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 102, 0));
        jLabel1.setText("CAFETIN \" DON PABLO\"");

        jLabel2.setText("PRODUCTO:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/G.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("PRECIO: S/");

        jLabel3.setText("CANTIDAD:");

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/F.jpg"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel6.setText("IMPORTE: S/");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/S.jpg"))); // NOI18N
        jButton3.setText("ELIMINAR");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/foto.png"))); // NOI18N
        jButton6.setText("CERRAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cbo_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbo_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_productoActionPerformed(evt);
            }
        });

        spn_cantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        spn_cantidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spn_cantidadStateChanged(evt);
            }
        });

        txt_precio.setText("0.00");

        txt_importe.setText("0.00");

        lbl_subtotal.setText("0.00");

        lbl_total.setText("0.00");

        lbl_igv.setText("0.00");

        lbl_subtotal1.setText("Sub total");

        lbl_subtotal2.setText("Total");

        lbl_subtotal3.setText("IGV");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_subtotal3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_subtotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_subtotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_igv, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spn_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_importe, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fecha_v, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(130, 130, 130)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4)
                        .addGap(55, 55, 55))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(cbo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_precio))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txt_importe)
                                    .addComponent(spn_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(fecha_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_subtotal)
                            .addComponent(lbl_subtotal1))
                        .addGap(41, 41, 41)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(btnModificar)
                            .addComponent(jButton1))
                        .addContainerGap(60, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_subtotal3)
                            .addComponent(lbl_igv))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_subtotal2)
                            .addComponent(lbl_total))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         int id_venta = obtenerIdVenta(); // Método para obtener el ID de la venta
    
    // Verificar que el ID de la venta sea válido
    if (id_venta != 1) {
             try {
                 generarTicketVenta(id_venta); // Llamar a la función para generar el ticket
             } catch (SQLException ex) {
                 Logger.getLogger(VENTAS.class.getName()).log(Level.SEVERE, null, ex);
             }
    } else {
        System.out.println("ID de venta iSynválido.");
    }
       
    }//GEN-LAST:event_jButton5ActionPerformed
      private int obtenerIdVenta() {
   
    return -1;
}
      // Método para generar el ticket de venta
private void generarTicketVenta(int idVenta) throws SQLException {
    //Connection conexion = null;
    PreparedStatement consulta = null;
    
     try {
      conexion = conectar.obtenerConexion();
      String sql = "SELECT * FROM ventas ORDER BY id_ventas DESC LIMIT 1"; // Ajusta según tu estructura de datos
      
         Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);

       // ResultSet resultado = consulta.executeQuery();

        // Mostrar el ticket de venta
        System.out.println("***** TICKET DE VENTA *****");
         System.out.println(resultado);
        
    

if (resultado.next()) {
    // Obtener los datos de la venta
    int id_ventas = 0;
    try {
        id_ventas = resultado.getInt("id_ventas");
    } catch (SQLException ex) {
        Logger.getLogger(VENTAS.class.getName()).log(Level.SEVERE, null, ex);
    }
    int id_producto = 0;
    try {
        id_producto = resultado.getInt("id_producto");
    } catch (SQLException ex) {
        Logger.getLogger(VENTAS.class.getName()).log(Level.SEVERE, null, ex);
    }
    int id_usuarios = 0;
    try {
        id_usuarios = resultado.getInt("id_usuarios");
    } catch (SQLException ex) {
        Logger.getLogger(VENTAS.class.getName()).log(Level.SEVERE, null, ex);
    }
    String nombre_producto = resultado.getString("nombre_producto");
    double precio = resultado.getDouble("precio");
    int unidad_producto = resultado.getInt("unidad_producto");
    
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date fecha_venta = resultado.getDate("fecha_venta");
    String fechaFormateada = dateFormat.format(fecha_venta);
    
    double importe_producto = resultado.getDouble("importe_producto");
    double total_venta = resultado.getDouble("total_venta");
    
    
    
    
    PrinterJob job = PrinterJob.getPrinterJob();
    StringBuilder ticket = new StringBuilder();
    
   

ticket.append("----- Ticket de Venta -----\n").append("\n");
//ticket.append("ID Venta:---------------\n ").append(id_ventas).append("\n");
//ticket.append("nombre_producto: ------ \n").append(nombre_producto).append("\n");
//ticket.append("precio:-----------------\n ").append(precio).append("\n");
//ticket.append("unidad_producto:\n ").append(unidad_producto).append("\n");
//ticket.append("Fecha:\n ").append(fechaFormateada).append("\n");
//ticket.append("importe_producto:------\n ").append(importe_producto).append("\n");
//ticket.append("total_venta: $\n").append(total_venta).append("\n");
  
//double subTotal = 0;

double subTotal = Double.parseDouble(lbl_subtotal.getText());
double igv = Double.parseDouble(lbl_igv.getText());
double total = Double.parseDouble(lbl_total.getText());
//double igv = subTotal * 0.18;
//double total = subTotal + igv;
//double subTotal=   lbl_subtotal.setText(String.valueOf(subTotal));
//        lbl_igv.setText(String.valueOf(subTotal * 0.18));
//        lbl_total.setText(String.valueOf(total + subTotal + 0.18));
        
//        ticket.append("subTotal:---------------\n ").append(subTotal).append("\n");
//        ticket.append("igv:----------------------\n ").append(igv).append("\n");
//        ticket.append("------------------total:$\n ").append("\n").append(total).append("\n");
       
        
        // Suponiendo que 'tbl_productos' es tu JTable
int rowCount = tbl_productos.getRowCount();
int colCount = tbl_productos.getColumnCount();

for (int i = 0; i < rowCount; i++) {
    ticket.append("----- Producto ").append(i + 1).append(" -----\n");
    for (int j = 0; j < colCount; j++) {
        Object value = tbl_productos.getValueAt(i, j);
        ticket.append(tbl_productos.getColumnName(j)).append(": ").append(value).append("\n");
    }
    ticket.append("----------------------\n");
   
}
 ticket.append("subTotal:\n ").append(subTotal).append("\n");
        ticket.append("igv:\n ").append(igv).append("\n");
        ticket.append("total:$\n ").append("\n").append(total).append("\n");

        
        
     




job.setPrintable(new Printable() {
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Establece la fuente y el tamaño de texto
        
        String[] lineas = ticket.toString().split("\n");
        int y = 20; // Posición vertical inicial para imprimir cada línea
        
        for (String linea : lineas) {
            g2d.drawString(linea, 50, y);
            y += 20; // Incrementa la posición vertical para la siguiente línea
        }
        
        return PAGE_EXISTS;
        
        
        
        
       
        
        
      }
        });

        try {
            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}       catch (SQLException e) {
            Logger.getLogger(VENTAS.class.getName()).log(Level.SEVERE, null, e);
        }}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            // Obtener la conexión desde la clase ConexionBD
            conexion = conectar.obtenerConexion();

            String productoSeleccionado = String.valueOf(cbo_producto.getSelectedItem());
            double precio = Double.parseDouble(txt_precio.getText()); // Convertir a double según sea necesario
            int cantidad = (int) spn_cantidad.getValue(); // Obtener valor del spinner, puedes necesitar convertirlo según el tipo que uses en tu base de datos
            double importe = Double.parseDouble(txt_importe.getText());
            String sql = "INSERT INTO ventas ( nombre_producto, precio,unidad_producto,fecha_venta,importe_producto,total_venta) VALUES ( ? , ? , ? , ? ,? ,? )";
            preparedStatement = conexion.prepareStatement(sql);
            double total = Double.parseDouble(lbl_total.getText());

            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha_v.getDate());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int mes = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String daystr;
            if (day <= 9) {
                daystr = '0' + String.valueOf(day);
            } else {
                daystr = String.valueOf(day);
            }

            String messtr;
            mes = mes + 1;
            if (mes <= 9) {
                messtr = '0' + String.valueOf(mes);
            } else {
                messtr = String.valueOf(mes);
            }

            String yearstr = String.valueOf(year);
            String fecha_venta = yearstr + "-" + messtr + "-" + daystr;

            preparedStatement.setString(1, productoSeleccionado);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setInt(3, cantidad);
            //preparedStatement.setDouble(2, precio);
            //String fecha = fecha_v.getDateFormatString();
            // preparedStatement.setString(3, spn_cantidad.getText());
            preparedStatement.setString(4, fecha_venta);
            preparedStatement.setDouble(5, importe);
            System.out.println(precio*cantidad);
            preparedStatement.setDouble(6, precio*cantidad);

            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("El registro fue agregado correctamente.");
            } else {
                System.out.println("No se pudo agregar el registro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        VENTAS venta = new VENTAS();
        venta.setDescripcion(String.valueOf(cbo_producto.getSelectedItem()));
        venta.setPrecio(precioss.get(cbo_producto.getSelectedIndex()));
        venta.setCantidad((int) spn_cantidad.getValue());
        venta.setImporte(venta.getPrecio() * venta.getCantidad());

        if (!buscarVenta(venta)) {
            listaVentas.add(venta);
        }

        // Agregar la venta a la lista de ventas
        // Actualizar la tabla con los datos de listaVentas
        actualizarTabla();

        // Limpiar los campos después de agregar la venta
        limpiarCampos();

    }

    private void limpiarCampos() {
        cbo_producto.setSelectedIndex(0);
        spn_cantidad.setValue(1);
    }

    public boolean buscarVenta(VENTAS nueva) {

        for (VENTAS v : listaVentas) {
            if (v.getDescripcion().equals(nueva.getDescripcion())) {
                int nuevaCantidad = v.getCantidad() + nueva.getCantidad();
                v.setCantidad(nuevaCantidad);
                v.setImporte(v.getPrecio() * nuevaCantidad);
                return true;
            }
        }
        return false;
    }

    public void borrarVenta() {
        precio = 0;
        cantidad = 1;
        cbo_producto.setSelectedIndex(0);
        spn_cantidad.setValue(1);
        calcularPrecio();
    }

    public void calcularPrecio() {


 if (cbo_producto.getSelectedIndex() != -1) {
        // Obtener el precio solo si hay un ítem seleccionado
        double precio = 
        precioss.set(cbo_producto.getSelectedIndex(), precioss.get(cbo_producto.getSelectedIndex()));
        int cantidad = (int) spn_cantidad.getValue();
        
        
        
//        // Asignar el precio al JTextField
        txt_precio.setText(String.valueOf(precio));
        txt_importe.setText(String.valueOf(precio * cantidad));
       
        
    } else {
        // Manejar el caso cuando no hay un ítem seleccionado
        txt_precio.setText(""); // Si no hay selección, limpia el campo de texto
    }
}

    
    
    
    public String aMoneda(double precio) {
        // Formatear el precio como moneda
        return String.format("%.2f", precio); // Mostrar dos decimales
    }

    public void actualizarTabla() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        double subTotal = 0;
        for (VENTAS v : listaVentas) {
            Object x[] = new Object[4];
            x[0] = v.getDescripcion();
            x[1] = aMoneda(v.getPrecio());
            x[2] = v.getCantidad();
            x[3] = aMoneda(v.getImporte());
            subTotal += v.getImporte();
            modelo.addRow(x);
        }
        double total = subTotal;
        lbl_subtotal.setText(String.valueOf(subTotal));
        lbl_igv.setText(String.valueOf(total * 0.18));
        lbl_total.setText(String.valueOf(total + total * 0.18));
        tbl_productos.setModel(modelo);
    }//GEN-LAST:event_jButton1ActionPerformed



    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void cbo_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_productoActionPerformed
          
       
        calcularPrecio();


    }//GEN-LAST:event_cbo_productoActionPerformed

    private void spn_cantidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spn_cantidadStateChanged
        calcularPrecio();
    }//GEN-LAST:event_spn_cantidadStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        // Obtener la conexión a la base de datos
      

try {
    conexion = conectar.obtenerConexion(); 
    // Obtener la fila que quieres eliminar de la base de datos
    String sqlSelect = "SELECT * FROM ventas ORDER BY id_ventas DESC LIMIT 1";
    Statement selectStatement = conexion.createStatement();
    ResultSet resultSet = selectStatement.executeQuery(sqlSelect);
    
    if (resultSet.next()) {
        // Obtener los valores de la fila seleccionada
        int idVentas = resultSet.getInt("id_ventas");
        // ... Otros campos que necesites obtener
        
        // Eliminar la fila de la base de datos
        String sqlDelete = "DELETE FROM ventas WHERE id_ventas = ?";
        PreparedStatement deleteStatement = conexion.prepareStatement(sqlDelete);
        deleteStatement.setInt(1, idVentas);
        deleteStatement.executeUpdate();
        
      
            modelo.removeRow(0);
        //}
        JOptionPane.showMessageDialog(null, "Se ha eliminado exitosamente el registro.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        
     //System.out.println("La fila fue eliminada exitosamente de la base de datos y de la tabla en Java.");
    } else {
       JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro. La tabla está vacía o es nula.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
// System.out.println("No se encontraron filas para eliminar.");
    }
    
    conexion.close(); // No olvides cerrar la conexión cuando hayas terminado
    
} catch (SQLException e) {
    e.printStackTrace();
}

        
        
        
        
        

    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
     try {
    conexion = conectar.obtenerConexion();  // Obtener la conexión a la base de datos
    
    String sqlSelect = "SELECT * FROM ventas ORDER BY id_ventas DESC LIMIT 1";
    Statement selectStatement = conexion.createStatement();
    ResultSet resultSet = selectStatement.executeQuery(sqlSelect);
    
    if (resultSet.next()) {
        // Obtener los valores de la última fila seleccionada
        int idVentas = resultSet.getInt("id_ventas");
       // String nombreCliente = resultSet.getString("nombre_cliente");
        // Otros campos que necesites obtener
        
        // Aquí puedes aplicar la lógica para modificar los datos obtenidos
        
        // Por ejemplo, supongamos que quieres cambiar el nombre del cliente
        //String nuevoNombreCliente = "Nuevo Nombre";
        
        String productoSeleccionado = String.valueOf(cbo_producto.getSelectedItem());
            double precio = Double.parseDouble(txt_precio.getText()); // Convertir a double según sea necesario
            int cantidad = (int) spn_cantidad.getValue(); // Obtener valor del spinner, puedes necesitar convertirlo según el tipo que uses en tu base de datos
            double importe = Double.parseDouble(txt_importe.getText());
            double total = Double.parseDouble(lbl_total.getText());
        
        // Actualizar el nombre del cliente en la base de datos
        String sqlUpdate = "UPDATE ventas SET nombre_producto = ?,precio = ?,unidad_roducto = ?,importe_producto = ?,total_venta = ? WHERE id_ventas = ?";
        PreparedStatement updateStatement = conexion.prepareStatement(sqlUpdate);
        updateStatement.setString(1, productoSeleccionado);
        updateStatement.setDouble(2, precio);
        updateStatement.setInt(3, cantidad);
        updateStatement.setDouble(4, importe);
        updateStatement.setDouble(5, total);
       
        //actualizarTabla() ;
        
        calcularPrecio();
        System.out.println("Los datos han sido modificados exitosamente.");
    } else {
        System.out.println("No se encontraron datos para modificar.");
    }
    
    conexion.close(); // No olvides cerrar la conexión cuando hayas terminado
    
} catch (SQLException e) {
    e.printStackTrace();
}

        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tbl_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_productosMouseClicked

    }//GEN-LAST:event_tbl_productosMouseClicked
    
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        //eliminarVentaPorId();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5MouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbo_producto;
    private com.toedter.calendar.JDateChooser fecha_v;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_igv;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_subtotal1;
    private javax.swing.JLabel lbl_subtotal2;
    private javax.swing.JLabel lbl_subtotal3;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JSpinner spn_cantidad;
    private javax.swing.JTable tbl_productos;
    private javax.swing.JLabel txt_importe;
    private javax.swing.JLabel txt_precio;
    // End of variables declaration//GEN-END:variables

   private void eliminarVentaPorId(int idVenta) {
    // Lógica para eliminar la venta en la base de datos utilizando el ID
    try {
        // Realiza la eliminación de la venta con el ID proporcionado
        conexion = conectar.obtenerConexion();
        String sql = "DELETE FROM ventas WHERE id_ventas = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setInt(1, idVenta);
        int filasEliminadas = preparedStatement.executeUpdate();
        if (filasEliminadas > 0) {
            System.out.println("Fila eliminada exitosamente de la tabla.");
        } else {
            System.out.println("No se encontró la fila para eliminar en la tabla.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
   }
}
