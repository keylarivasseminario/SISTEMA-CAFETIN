/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;
import static java.util.Objects.hash;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class REGISTROS extends javax.swing.JInternalFrame {

    /**
     * Creates new form REGISTROS
     */
    public REGISTROS() {
        initComponents();
    }
     private void limpiarcajastexto(){
            
            txtcodigo.setText("");
            txt_usuario.setText("");
            txtpassword.setText("");
            txtconfirmacionpass.setText("");
           
            
            }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        txt_usuario = new javax.swing.JTextField();
        txtcodigo = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        txtpassword = new javax.swing.JPasswordField();
        txtconfirmacionpass = new javax.swing.JPasswordField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(null);

        jLabel1.setBackground(new java.awt.Color(0, 51, 51));
        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("REGISTROS USUARIOS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(480, 10, 370, 60);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("NOMBRE:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(330, 110, 100, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("USUARIO:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(330, 160, 110, 22);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("CODIGO:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(330, 220, 100, 22);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("TELEFONO:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(330, 280, 110, 22);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("CONTRASEÑA:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(330, 350, 150, 22);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("CONFIRMAR CONTRASEÑA:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(330, 410, 260, 22);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 0));
        jButton1.setText("REGISTRAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(910, 530, 160, 32);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(780, 100, 155, 35);
        getContentPane().add(txt_usuario);
        txt_usuario.setBounds(780, 160, 155, 35);
        getContentPane().add(txtcodigo);
        txtcodigo.setBounds(780, 220, 155, 35);
        getContentPane().add(jTextField4);
        jTextField4.setBounds(780, 280, 155, 35);
        getContentPane().add(txtpassword);
        txtpassword.setBounds(780, 340, 155, 35);
        getContentPane().add(txtconfirmacionpass);
        txtconfirmacionpass.setBounds(780, 400, 155, 35);

        jComboBox1.setBackground(new java.awt.Color(153, 153, 0));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "administrador", "invitado" }));
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(360, 530, 240, 42);

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\heyner.rivas\\Pictures\\fi.jpg")); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(-10, -10, 1300, 630);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SqlUsuarios modSql = new SqlUsuarios();
usuarios mod = new usuarios();

String pass = new String(txtpassword.getText());
String passCon = new String(txtconfirmacionpass.getText());

// Validación para que ninguna caja de texto esté vacía
if (txtcodigo.getText().equals("") || txt_usuario.getText().equals("") || passCon.equals("") || jComboBox1.getSelectedItem() == null) {
    JOptionPane.showMessageDialog(null, "Hay Campos vacíos, debe llenar todos los campos");
} else {
    if (pass.equals(passCon)) {
        if (modSql.existeUsuario(txt_usuario.getText()) == 0) {
            String nuevopassword = hash.sha1(pass);

            mod.setId(txtcodigo.getText());
            mod.setUsuario(txt_usuario.getText());
            mod.setClave(nuevopassword);
            // Obtener el tipo seleccionado del JComboBox
            String tipoSeleccionado = jComboBox1.getSelectedItem().toString();
            mod.setTipo(tipoSeleccionado);

            if (modSql.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Usuario Registrado");
              
            } else {
                JOptionPane.showMessageDialog(null, "Error al Registrar");
               
            }
        } else {
            JOptionPane.showMessageDialog(null, "El Usuario ya Existe");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Las Contraseñas no coinciden");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField txt_usuario;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JPasswordField txtconfirmacionpass;
    private javax.swing.JPasswordField txtpassword;
    // End of variables declaration//GEN-END:variables
}
