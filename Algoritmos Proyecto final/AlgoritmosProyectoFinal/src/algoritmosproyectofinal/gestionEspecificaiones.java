/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package algoritmosproyectofinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eduar
 */
public class gestionEspecificaiones extends javax.swing.JFrame {

    /**
     * Creates new form gestionEspecificaiones
     */
    public gestionEspecificaiones() {
        initComponents();
        setLocationRelativeTo(null);
        obtenerDatos();
    }
    
    // Función de obtenerDatos
    public void obtenerDatos(){
        try {
            // Creación ficticia del archivo
            File listaEspecificaciones = new File("data/listaEspecificaciones.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaEspecificaciones));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {                
                DefaultTableModel model = (DefaultTableModel) EspecificaionesTabla.getModel();
                
                Object[] newRow = {data};
                
                model.addRow(newRow);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gestionCategorias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gestionCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Función de limpiar tabla
    public void limpiarTabla(){
        DefaultTableModel model = (DefaultTableModel) EspecificaionesTabla.getModel();
        int row = model.getRowCount()-1;
        for (int i = row; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    // Función de limpiar campos
    public void limpiarCampos(){
        txtEspProd.setText("");
        cboUnidadMedida.setSelectedItem("");
    }
    
    // Función de validación de campos
    public boolean validarCampos(){
        // Determinación para que existan valores en el campo ID
        if (txtEspProd.getText().toString().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: No deje vacío el campo de descripción de la especificación", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // Función para validar la autenticidad de descripción de categoria
    public boolean validarEspecificacion(){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) EspecificaionesTabla.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // Inicialización de variable de recuento de coincidencias
        int isEquasAT = 0;
        
        // ciclo de repetición para comparar las filas una por una
        for (int i = row; i >= 0; i--) {
            if (txtEspProd.getText().compareTo(model.getValueAt(i, 0).toString()) == 0){
                isEquasAT++;
            }
        }
        
        if (isEquasAT > 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "No se pueden duplicar características de productos", "Error", JOptionPane.ERROR_MESSAGE);
            
            return false;
        } else{
            return true;
        }
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
        txtEspProd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EspecificaionesTabla = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarCampos = new javax.swing.JButton();
        btnAgregarEsp = new javax.swing.JButton();
        cboUnidadMedida = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Especificaciones");
        jLabel1.setToolTipText("");

        txtEspProd.setToolTipText("");
        txtEspProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEspProdActionPerformed(evt);
            }
        });

        jLabel2.setText("Descrición de la especificación:");

        EspecificaionesTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especificación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EspecificaionesTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspecificaionesTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(EspecificaionesTabla);

        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiarCampos.setText("Limpiar");
        btnLimpiarCampos.setToolTipText("");
        btnLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCamposActionPerformed(evt);
            }
        });

        btnAgregarEsp.setText("Agregar");
        btnAgregarEsp.setToolTipText("");
        btnAgregarEsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEspActionPerformed(evt);
            }
        });

        cboUnidadMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "KG", "CM", "M", "A", "L", "ML", "CD", "PLG", "PIE" }));

        jLabel3.setText("Unidad de medida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAgregarEsp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(106, 106, 106))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(183, 183, 183)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtEspProd, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiarCampos)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEspProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarCampos)
                    .addComponent(cboUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnAgregarEsp))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEspProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEspProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEspProdActionPerformed

    private void EspecificaionesTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspecificaionesTablaMouseClicked
        // Obtener la fila seleccionada de la tabla
        int row = EspecificaionesTabla.getSelectedRow();

        // Obtener valores de la fila seleccionada por medio de la posición de columnas
        String desCat = EspecificaionesTabla.getValueAt(row, 0).toString();

        // Reflejar los datos obtenidos en las JTextField
        txtEspProd.setText(desCat);
    }//GEN-LAST:event_EspecificaionesTablaMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }

        // Validación de campos
        if (validarCampos() == false){
            return;
        }

        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaEspecificaciones = new File("data/listaEspecificaciones.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaEspecificaciones));
            String data = "";

            // Creación ficticia del archivo
            File listaEspecificacionesCopia = new File("data/listaEspecificacionesCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaEspecificacionesCopia, true));

            // Recopilación de datos
            while ((data = reader.readLine()) != null) {
                if (data.toString().compareTo(txtEspProd.getText()) != 0){
                    writer.write(data + "\n");
                }
            }

            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();

            // Reemplazar archivo copia con el archivo original
            Files.move(listaEspecificacionesCopia.toPath(), listaEspecificaciones.toPath(), REPLACE_EXISTING);

            // Métodos para refrescar el módulo
            limpiarCampos();
            limpiarTabla();
            obtenerDatos();

            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(gestionCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCamposActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarCamposActionPerformed

    private void btnAgregarEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEspActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }

        // Validación de campos
        if (validarCampos() == false){
            return;
        }

        // Condición para validar el campo de descripción de categoría
        if (validarEspecificacion()== false){
            return;
        }

        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaEspecificaciones = new File("data/listaEspecificaciones.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaEspecificaciones, true));

            // Inicialización de variable que contiene los datos de inserción
            String newReg = "";
            
            // Validación de unidad de medida - ¿Se ingresa o no?
            if (!(cboUnidadMedida.getSelectedItem().toString() == "")){
                // Si se ingresa se toma en cuenta el combobox de unidad de medida
                newReg = txtEspProd.getText() + " " + cboUnidadMedida.getSelectedItem().toString();
            } else {
                // sino el registro solo toma en cuenta el campo de descripción de la especificación
                newReg = txtEspProd.getText();
            }
            
            // Escritura de datos al archivo de acceso secuencial
                writer.write(newReg + "\n");

            // Cierre de acción de escritura
            writer.close();

            // Métodos para refrescar el módulo
            limpiarTabla();
            obtenerDatos();
            limpiarCampos();

            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Se ha agregado una nueva especificación", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            Logger.getLogger(gestionCaracteristicas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarEspActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gestionEspecificaiones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionEspecificaiones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionEspecificaiones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionEspecificaiones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionEspecificaiones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable EspecificaionesTabla;
    private javax.swing.JButton btnAgregarEsp;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarCampos;
    private javax.swing.JComboBox<String> cboUnidadMedida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtEspProd;
    // End of variables declaration//GEN-END:variables
}
