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
public class gestionCaracteristicas extends javax.swing.JFrame {

    /**
     * Creates new form gestionCaracteristicas
     */
    public gestionCaracteristicas() {
        initComponents();
        setLocationRelativeTo(null);
        obtenerDatos();
    }
    
    // Función de obtenerDatos
    public void obtenerDatos(){
        try {
            // Creación ficticia del archivo
            File listaCaracteristicas = new File("data/listaCaracteristicas.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaCaracteristicas));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {                
                DefaultTableModel model = (DefaultTableModel) caracteristicasTabla.getModel();
                
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
        DefaultTableModel model = (DefaultTableModel) caracteristicasTabla.getModel();
        int row = model.getRowCount()-1;
        for (int i = row; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    // Función de limpiar campos
    public void limpiarCampos(){
        txtCarProd.setText("");
    }
    
    // Función de validación de campos
    public boolean validarCampos(){
        // Determinación para que existan valores en el campo ID
        if (txtCarProd.getText().toString().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: No deje vacío el campo de descripción de la característica", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // Función para validar la autenticidad de descripción de categoria
    public boolean validarCaracteristica(){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) caracteristicasTabla.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // Inicialización de variable de recuento de coincidencias
        int isEquasAT = 0;
        
        // ciclo de repetición para comparar las filas una por una
        for (int i = row; i >= 0; i--) {
            if (txtCarProd.getText().compareTo(model.getValueAt(i, 0).toString()) == 0){
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
        txtCarProd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        caracteristicasTabla = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarCampos = new javax.swing.JButton();
        btnAgregarCar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Características");
        jLabel1.setToolTipText("");

        txtCarProd.setToolTipText("");
        txtCarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCarProdActionPerformed(evt);
            }
        });

        jLabel2.setText("Descripción de la característica:");

        caracteristicasTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoría"
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
        caracteristicasTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caracteristicasTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(caracteristicasTabla);

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

        btnAgregarCar.setText("Agregar");
        btnAgregarCar.setToolTipText("");
        btnAgregarCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarCar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarCampos))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnAgregarCar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCarProdActionPerformed

    private void caracteristicasTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caracteristicasTablaMouseClicked
        // Obtener la fila seleccionada de la tabla
        int row = caracteristicasTabla.getSelectedRow();

        // Obtener valores de la fila seleccionada por medio de la posición de columnas
        String desCat = caracteristicasTabla.getValueAt(row, 0).toString();

        // Reflejar los datos obtenidos en las JTextField
        txtCarProd.setText(desCat);
    }//GEN-LAST:event_caracteristicasTablaMouseClicked

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
            File listaCaracteristicas = new File("data/listaCaracteristicas.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaCaracteristicas));
            String data = "";

            // Creación ficticia del archivo
            File listaCaracteristicasCopia = new File("data/listaCaracteristicasCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaCaracteristicasCopia, true));

            // Recopilación de datos
            while ((data = reader.readLine()) != null) {
                if (data.toString().compareTo(txtCarProd.getText()) != 0){
                    writer.write(data + "\n");
                }
            }

            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();

            // Reemplazar archivo copia con el archivo original
            Files.move(listaCaracteristicasCopia.toPath(), listaCaracteristicas.toPath(), REPLACE_EXISTING);

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

    private void btnAgregarCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCarActionPerformed
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
        if (validarCaracteristica() == false){
            return;
        }

        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaCaracteristicas = new File("data/listaCaracteristicas.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaCaracteristicas, true));

            // Escritura de datos al archivo de acceso secuencial
            writer.write(txtCarProd.getText() + "\n");

            // Cierre de acción de escritura
            writer.close();

            // Métodos para refrescar el módulo
            limpiarTabla();
            obtenerDatos();
            limpiarCampos();

            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Se ha agregado nueva categoría", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            Logger.getLogger(gestionCaracteristicas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarCarActionPerformed

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
            java.util.logging.Logger.getLogger(gestionCaracteristicas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionCaracteristicas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionCaracteristicas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionCaracteristicas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionCaracteristicas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarCampos;
    private javax.swing.JTable caracteristicasTabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCarProd;
    // End of variables declaration//GEN-END:variables
}
