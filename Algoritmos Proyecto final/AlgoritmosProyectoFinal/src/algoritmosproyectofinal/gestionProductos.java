/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package algoritmosproyectofinal;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eduar
 */
public class gestionProductos extends javax.swing.JFrame {

    /**
     * Creates new form gestionProductos
     */
    
    
    // Funciones
    public gestionProductos() {
        initComponents();
        setLocationRelativeTo(null);
        obtenerDatos();
        
        // Carga de datos en los comboBox
        cargarCategorias(cboCatProd, "listaCategorias");
        cargarCategorias(cboCarProd, "listaCaracteristicas");

    }
    
    // Función de obtenerDatos
    public void obtenerDatos(){
        try {
            // Creación ficticia del archivo
            File listaProductos = new File("data/listaProductos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaProductos));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Determinación de modelo de tabla
                DefaultTableModel model = (DefaultTableModel) productosTabla.getModel();
                
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // Adición de los datos a las filas de la tabla
                model.addRow(newRow);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Función de limpiar tabla
    public void limpiarTabla(){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) productosTabla.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // ciclo de repetición para remover las filas una por una
        for (int i = row; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    // Función para verificar la autenticidad de ID - Insersión de datos
    public boolean verificarID1(){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) productosTabla.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // Inicialización de variable de recuento de coincidencias
        int isEquasAT = 0;
        
        // ciclo de repetición para comparar las filas una por una
        for (int i = row; i >= 0; i--) {
            if (txtCodProd.getText().compareTo(model.getValueAt(i, 0).toString()) == 0){
                isEquasAT++;
            }
        }
        
        if (isEquasAT > 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "No se pueden duplicar códigos de producto", "Error", JOptionPane.ERROR_MESSAGE);
            
            return false;
        } else {
            return true;
        }
    }
    
    // Función para verificar la autenticidad de ID - Actualización de datos
    public boolean verificarID2(){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) productosTabla.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // Inicialización de variable de recuento de coincidencias
        int isEquasAT = 0;
        
        // ciclo de repetición para comparar las filas una por una
        for (int i = row; i >= 0; i--) {
            if (txtCodProd.getText().compareTo(model.getValueAt(i, 0).toString()) == 0){
                isEquasAT++;
            }
        }
        
        if (isEquasAT == 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "No se existe el código de producto ingresado para porder actualizar datos", "Error", JOptionPane.ERROR_MESSAGE);
            
            return false;
        } else {
            return true;
        }
    }
    
    // Función de limpiar campos
    public void limpiarCampos(){
        txtCodProd.setText("");
        txtDesProd.setText("");
        cboCatProd.setSelectedIndex(0);
    }
    
    // Función de validación de campos
    public int validarCampos(){
        // Determinación para que existan valores en el campo ID
        if (txtCodProd.getText().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: No deje vacío el campo de ID", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        
        // Determinación para que existan valores en el campo descrición del producto
        if (txtDesProd.getText().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: No deje vacío el campo de descripción de producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return 2;
        }
        
        // Determinación para que existan valores en el campo categoría del producto
        if (cboCatProd.getSelectedItem().toString().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: Seleccione la categoría del producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return 3;
        }
        /*
        // Determinación para que existan valores en el campo características del producto
        if (cboCarProd.getSelectedItem().toString().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: Seleccione las características del producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return 4;
        }
        
        // Determinación para que existan valores en el campo especificaciones del producto
        if (cboEspeProd.getSelectedItem().toString().compareTo("") == 0) {
            JOptionPane.showMessageDialog(null, "Advertencia: Seleccione las especificaciones del producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return 5;
        }
        */
        return 0;
    }
    
    // Cargar Categorias del comboBox
    public void cargarCategorias(JComboBox cboIn, String fileText){
        cboIn.removeAllItems();
        
        try {
            // Creación ficticia del archivo
            File listaCategorias = new File("data/" + fileText + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaCategorias));
            String data = "";
            
            cboIn.addItem("");
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {                
                cboIn.addItem(data);
            }
            
            // Cierre de operación de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        txtDesProd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAgregarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productosTabla = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtCodProd = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarCampos = new javax.swing.JButton();
        cboCatProd = new javax.swing.JComboBox<>();
        btnVerCategorias = new javax.swing.JButton();
        btnVerCategorias1 = new javax.swing.JButton();
        btnVerCategorias2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cboCarProd = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboEspeProd = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Productos");
        jLabel1.setToolTipText("");

        jLabel2.setText("Descripción del producto:");

        txtDesProd.setToolTipText("");
        txtDesProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesProdActionPerformed(evt);
            }
        });

        jLabel3.setText("Categoría del producto:");

        btnAgregarProducto.setText("Agregar");
        btnAgregarProducto.setToolTipText("");
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });

        productosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Categoría", "Características", "Especificaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productosTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productosTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productosTabla);

        jLabel4.setText("Código");

        txtCodProd.setToolTipText("");
        txtCodProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProdActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

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

        cboCatProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCatProdActionPerformed(evt);
            }
        });

        btnVerCategorias.setText("Categorías");
        btnVerCategorias.setToolTipText("");
        btnVerCategorias.setActionCommand("");
        btnVerCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCategoriasActionPerformed(evt);
            }
        });

        btnVerCategorias1.setText("Características");
        btnVerCategorias1.setToolTipText("");
        btnVerCategorias1.setActionCommand("");
        btnVerCategorias1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCategorias1ActionPerformed(evt);
            }
        });

        btnVerCategorias2.setText("Especificaciones");
        btnVerCategorias2.setToolTipText("");
        btnVerCategorias2.setActionCommand("");
        btnVerCategorias2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCategorias2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Características del producto:");

        cboCarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCarProdActionPerformed(evt);
            }
        });

        jLabel6.setText("Especificaciones dle producto");

        cboEspeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEspeProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(btnLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(cboCarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(cboEspeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnVerCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtDesProd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(cboCatProd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnVerCategorias1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnVerCategorias2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVerCategorias)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerCategorias1)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerCategorias2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDesProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCatProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEspeProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarProducto)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiarCampos)
                    .addComponent(btnActualizar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesProdActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }

        // Condición para validar campos
        if (validarCampos() != 0) {
            return;
        }
        
        // Condición para evitar duplicacion de código primario
        if (verificarID1() == false){
            return;
        }
        
        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaProductos = new File("data/listaProductos.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaProductos, true));
            
            // Escritura de datos al archivo de acceso secuencial
            writer.write(txtCodProd.getText() + "%/%" + txtDesProd.getText() + "%/%" + cboCatProd.getSelectedItem().toString() + "\n");
            
            // Cierre de acción de escritura
            writer.close();
            
            // Métodos para refrescar el módulo
            limpiarTabla();
            obtenerDatos();
            limpiarCampos();
            
             // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo producto", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void txtCodProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }

        
        // Condición para validar campos
        if (validarCampos() != 0) {
            return;
        }
        
        // Condición para validar si existe el código de producto
        if (verificarID2() == false){
            return;
        }
        
        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaProductos = new File("data/listaProductos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaProductos));
            String data = "";
            
             // Creación ficticia del archivo
            File listaProductosCopia = new File("data/listaProductosCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaProductosCopia, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {                
                Object[] compareData = data.split("%/%");
                if (compareData[0].toString().compareTo(txtCodProd.getText()) == 0){
                    data = txtCodProd.getText() + "%/%" + txtDesProd.getText() + "%/%" + cboCatProd.getSelectedItem().toString();
                }
                writer.write(data + "\n");
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(listaProductosCopia.toPath(), listaProductos.toPath(), REPLACE_EXISTING);
            
            // Métodos para refrescar el módulo
            limpiarCampos();
            limpiarTabla();
            obtenerDatos();
            
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }
        
        // Validación de campos
        if (validarCampos() == 1){
            return;
        }

        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
            // Creación ficticia del archivo
            File listaProductos = new File("data/listaProductos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(listaProductos));
            String data = "";
            
             // Creación ficticia del archivo
            File listaProductosCopia = new File("data/listaProductosCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(listaProductosCopia, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {                
                Object[] compareData = data.split("%/%");
                if (compareData[0].toString().compareTo(txtCodProd.getText()) != 0){
                    writer.write(data + "\n");
                }
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(listaProductosCopia.toPath(), listaProductos.toPath(), REPLACE_EXISTING);
            
            // Métodos para refrescar el módulo
            limpiarCampos();
            limpiarTabla();
            obtenerDatos();
            
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCamposActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarCamposActionPerformed

    private void btnVerCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCategoriasActionPerformed
        new gestionCategorias().setVisible(true);
    }//GEN-LAST:event_btnVerCategoriasActionPerformed

    private void cboCatProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCatProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCatProdActionPerformed

    private void btnVerCategorias1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCategorias1ActionPerformed
        new gestionCaracteristicas().setVisible(true);
    }//GEN-LAST:event_btnVerCategorias1ActionPerformed

    private void btnVerCategorias2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCategorias2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerCategorias2ActionPerformed

    private void cboCarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCarProdActionPerformed

    private void cboEspeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEspeProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEspeProdActionPerformed

    private void productosTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productosTablaMouseClicked

        // Obtener la fila seleccionada de la tabla
        int row = productosTabla.getSelectedRow();

        // Obtener valores de la fila seleccionada por medio de la posición de columnas
        String id = productosTabla.getValueAt(row, 0).toString();
        String desProd = productosTabla.getValueAt(row, 1).toString();
        String catProd = productosTabla.getValueAt(row, 2).toString();

        // Reflejar los datos obtenidos en las JTextField
        txtCodProd.setText(id);
        txtDesProd.setText(desProd);
        cboCatProd.setSelectedItem(catProd);
    }//GEN-LAST:event_productosTablaMouseClicked

    
    
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
            java.util.logging.Logger.getLogger(gestionProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarCampos;
    private javax.swing.JButton btnVerCategorias;
    private javax.swing.JButton btnVerCategorias1;
    private javax.swing.JButton btnVerCategorias2;
    private javax.swing.JComboBox<String> cboCarProd;
    private javax.swing.JComboBox<String> cboCatProd;
    private javax.swing.JComboBox<String> cboEspeProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productosTabla;
    private javax.swing.JTextField txtCodProd;
    private javax.swing.JTextField txtDesProd;
    // End of variables declaration//GEN-END:variables
}
