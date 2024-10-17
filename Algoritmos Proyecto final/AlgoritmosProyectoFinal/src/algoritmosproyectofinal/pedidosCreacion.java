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
import java.lang.reflect.Array;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author eduar
 */
public class pedidosCreacion extends javax.swing.JFrame {

    /**
     * Creates new form pedidosCreacion
     */
    public pedidosCreacion() {
        initComponents();
        setLocationRelativeTo(null);
        refrescarCbombobox();
        txtFechaE.setText(obtenerFechaActual());
    }
    
    // Función para rerescar todos los datos de los combobox
    public void refrescarCbombobox(){
        cargarCombobox(cboProducto, "listaProductos", 1);
        cargarCombobox(cboProveedores, "listaProveedores", 0);
    }
    
    // Cargar Categorias del comboBox
    public void cargarCombobox(JComboBox cboIn, String fileText, int column){
        cboIn.removeAllItems();
        
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileText + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            cboIn.addItem("");
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {
                Object[] newRow = data.split("%/%");
                
                cboIn.addItem(newRow[column]);
            }
            
            // Cierre de operación de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función de limpiar tabla
    public void limpiarTabla(JTable table){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // ciclo de repetición para remover las filas una por una
        for (int i = row; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    // Función para calcular el total del pedido
    public String calcularTotal(JTable table){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        // obtener el recuento de filas
        int row = model.getRowCount()-1;
        
        // incialización de variable que almacenará la sumatoria
        float sumatoria = 0.00f;
        
        // ciclo de repetición para calcular el total
        for (int i = row; i >= 0; i--) {
            float priceColumn = Float.parseFloat(model.getValueAt(i, 3).toString());
            int cantColumn = Integer.parseInt(model.getValueAt(i, 1).toString());
            
            sumatoria = sumatoria + (priceColumn * cantColumn);
        }
        
        String total = String.valueOf(String.format("%.2f", sumatoria));
        
        return total;
    }
    
    // Función para agregar un producto a la lista
    public void agregarProducto(JTable table, JComboBox cboProd, JSpinner cant, JComboBox cboProv){
        try {
            // Creación ficticia del archivo
            File file = new File("data/listaProductos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";

            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {
                Object[] newRow = data.split("%/%");
                
                
                if (newRow[1].toString().equals(cboProd.getSelectedItem().toString())){
                    // Determinación de modelo de tabla
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    String getPrice = newRow[5].toString();
                
                    // Adición de los datos a las filas de la tabla
                    model.addRow(new Object[]{cboProd.getSelectedItem(), cant.getValue(), cboProv.getSelectedItem(), getPrice});
                }
            }
            
            // Cierre de operación de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }  
    
    
    // Función para obtener todos los datos del lista
    public void enviarLista(JTable table){
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        // obtener el recuento de filas
        int row = model.getRowCount()-1;
        
        // obtener el recuento de columnas
        int col = model.getColumnCount()-1;
        
        // Condición para determinar si la tabla esta vacía
        if (row == -1 || col == -1){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "No se ha ingresado ningún producto aún", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar el pedido?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }
        
        // Método Try obligatorio para ejecutar la escritura y/o escritura de datos.
        try {
             // Creación ficticia del archivo
            File file = new File("data/detallesCompra.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/detallesCompraCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Inicialización de contador de registros
            int noReg = 1;
            
            // Inicializaciión de variable que almacenará la lista de productos
            String listData = "";
            
            // ciclo de repetición para seleccionar datos de las filas y columnas una por una
            for (int i = 0; i <= row; i++) {
                for (int j = 0; j <= col; j++){

                    data = model.getValueAt(i, j).toString();
                    if (j <= (col -1)){
                        data = data + "%/%";
                    } else {
                        data = data + "\n";
                        
                    }
                    listData = listData + data;
                }

            }
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // Escritura de datos almacenados al archivo copia
                data = newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString() + "%/%" + newRow[4].toString();
                
                writer.write(data + "\n");
                noReg++;
                System.out.println(noReg);
            }
            // Separador de espacios
            Object[] newLine = listData.split("\n");
            
            // Ciclo para obtener cada elemento del objeto
            for (Object count : newLine) {                
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = count.toString().split("%/%");
                
                // Escritura de nueva linea de registro
                data = noReg + "%/%" + newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString();
                writer.write(data + "\n");
                System.out.println(noReg);
            }
            
            // llamado de función para registrar el pedido en nuevo archivo secuencial
            registrarPedido(noReg, table);
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
            
             // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Se ha agregado solicitado el pedido con éxito", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(gestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    // Función para registrar el pedido en la lista de pedidos
    public void registrarPedido(int noReg, JTable table){
        try {
            // Creación ficticia del archivo
            File file = new File("data/listaPedidosCompra.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            
            
            // Escritura de datos al archivo de acceso secuencial
            writer.write(noReg + "%/%" + obtenerFechaActual() + "%/%" + txtFechaE.getText() + "%/%" + "pendiente" + "%/%" + calcularTotal(table) + "\n");
            
            // Cierre de acción de escritura
            writer.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // función para obtener la fecha actual
    public String obtenerFechaActual(){
        // Obtener la fecha actual
        LocalDate dateNow = LocalDate.now();

        // Formatear la fecha
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return dateNow.format(formatDate);
    }
    
    // función para validar campos
    public int validarCampos(){
        if (cboProveedores.getSelectedItem().toString().compareTo("") == 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor", "Atención", JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        if (cboProducto.getSelectedItem().toString().compareTo("") == 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "Atención", JOptionPane.WARNING_MESSAGE);
            return 2;
        }
        if (Integer.parseInt(spnCantidad.getValue().toString()) < 1){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "La cantidad no puede ser igual a 0", "Atención", JOptionPane.WARNING_MESSAGE);
            return 3;
        }
        
        return 0;
    }
    
    // función para limpiar campos
    public void limpiarCampos(){
        cboProveedores.setSelectedIndex(0);
        cboProducto.setSelectedIndex(0);
        spnCantidad.setValue(0);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboProveedores = new javax.swing.JComboBox<>();
        cboProducto = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAgregarP = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        spnCantidad = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnCrearPedido = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtFechaE = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("Pedidos de compra");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel6.setText("Creación");

        cboProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        cboProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProveedoresActionPerformed(evt);
            }
        });

        cboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Pendiente", "En curso", "Completado" }));
        cboProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProductoActionPerformed(evt);
            }
        });

        jLabel4.setText("Producto:");

        jLabel5.setText("Cantidad");

        jLabel2.setText("proveedor:");

        btnAgregarP.setText("Agregar producto");
        btnAgregarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar lista");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Proveedor", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPedidos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaPedidosPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPedidos);

        lblTotal.setText("Total del pedido:");

        jLabel8.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Si desea modificar la cantidad de productos, haga doble clic en la columna");

        btnEliminar.setText("Eliminar selección");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboProveedores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(349, 349, 349))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarP, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(lblTotal)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(25, 25, 25)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnAgregarP))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(78, 78, 78)))
                .addComponent(lblTotal)
                .addContainerGap())
        );

        btnCrearPedido.setText("Crear pedido");
        btnCrearPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPedidoActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha de entrega estimada:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCrearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtFechaE, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFechaE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCrearPedido)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProductoActionPerformed

    private void btnAgregarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPActionPerformed
        if (validarCampos() != 0) {
            return;
        }
        agregarProducto(tablaPedidos, cboProducto, spnCantidad, cboProveedores);
        limpiarCampos();
        lblTotal.setText("Total del pedido: GTQ" + calcularTotal(tablaPedidos));
    }//GEN-LAST:event_btnAgregarPActionPerformed

    private void btnCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPedidoActionPerformed
        enviarLista(tablaPedidos);
    }//GEN-LAST:event_btnCrearPedidoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTabla(tablaPedidos);
        lblTotal.setText("Total del pedido: GTQ" + calcularTotal(tablaPedidos));
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cboProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProveedoresActionPerformed

    private void tablaPedidosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaPedidosPropertyChange
        lblTotal.setText("Total del pedido: GTQ" + calcularTotal(tablaPedidos));
    }//GEN-LAST:event_tablaPedidosPropertyChange

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        
        // Obtener la fila seleccionda de la tabla
        int row = tablaPedidos.getSelectedRow();
        
        model.removeRow(row);
        lblTotal.setText("Total del pedido: GTQ" + calcularTotal(tablaPedidos));
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(pedidosCreacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pedidosCreacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pedidosCreacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pedidosCreacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pedidosCreacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarP;
    private javax.swing.JButton btnCrearPedido;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cboProducto;
    private javax.swing.JComboBox<String> cboProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tablaPedidos;
    private javax.swing.JTextField txtFechaE;
    // End of variables declaration//GEN-END:variables
}
