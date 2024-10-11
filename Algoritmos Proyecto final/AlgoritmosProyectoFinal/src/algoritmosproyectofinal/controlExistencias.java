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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eduar
 */
public class controlExistencias extends javax.swing.JFrame {

    /**
     * Creates new form controlExistencias
     */
    public controlExistencias() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    //* Apartado de funciones *//
    //Función para buscar un producto
    public void buscarProducto(JTextField codeProd, String fileroot, JTable table){
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileroot + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                
                
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                if (newRow[0].toString().compareTo(codeProd.getText()) == 0){
                    // Determinación de modelo de tabla
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    Object[] selectData = {newRow[0], newRow[1], newRow[6]};
                
                    // Adición de los datos a las filas de la tabla
                    model.addRow(selectData);
                }
                
                
            }
            
            // Cierre de archivo de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
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
    
    // Función para modificar la cantidad
    public void modificarCantidad(JTextField codeProd, JSpinner jsp, String fileName, JTable table, boolean aumento){
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileName + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/" + fileName + "Copia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                
                
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                if (newRow[0].toString().compareTo(codeProd.getText()) == 0){
                    int valueJSpinner = Integer.parseInt(jsp.getValue().toString());
                    int value = 0;
                    
                    // condición para determinar si el valor va aser negativo o positivo
                    if (aumento == true){
                        value = (Integer.parseInt(newRow[6].toString()) + valueJSpinner);
                    } else {
                        value = (Integer.parseInt(newRow[6].toString()) - valueJSpinner);
                    }
                    // Selección de datos
                    data = newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString() + "%/%" + newRow[4].toString() + "%/%" + newRow[5].toString() + "%/%" + value;
                    
                    registrarMovimiento(newRow[0].toString(), newRow[1].toString(), valueJSpinner, aumento);
                    
                }
                writer.write(data + "\n");
                
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
            limpiarTabla(table);
            buscarProducto(codeProd, fileName, table);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función para registrar movimientos realizados
    public void registrarMovimiento(String codProd, String prod, int cantidad, boolean aumento){
        try {
            // Creación ficticia del archivo
            File file = new File("data/movimientos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/movimientosCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // condición para determinar el tipo de movimiento
            String typeTXN = "";
            
            if (aumento == true){
                typeTXN = "Entrada";
            } else {
                typeTXN = "Salida";
            }
            
            // Obtener la fecha actual
            LocalDate dateNow = LocalDate.now();

            // Obtener la hora actual
            LocalTime timeNow = LocalTime.now();

            // Formatear la fecha y hora en el formato deseado (opcional)
            DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Inicialización de contador de registros
            int noReg = 1;
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // Escritura de datos almacenados al archivo copia
                data = newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString() + "%/%" + newRow[4].toString() + "%/%" + newRow[5].toString() + "%/%" + newRow[6].toString();
                
                writer.write(data + "\n");
                noReg++;
            }
            
            // Escritura de nueva linea de registro
            data = noReg + "%/%" + codProd + "%/%" + prod + "%/%" + cantidad + "%/%" + typeTXN + "%/%" + dateNow.format(formatDate) + "%/%" + timeNow.format(formatHour);
            writer.write(data + "\n");
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función de obtenerDatos
    public void obtenerDatos(JTable table, String fileName){
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileName + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Determinación de modelo de tabla
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // Adición de los datos a las filas de la tabla
                model.addRow(newRow);
            }
            
            // Cierre de archivo de lectura
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        txtCodProdE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscarE = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEntradas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        spnE = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        btnConsumir = new javax.swing.JButton();
        spnS = new javax.swing.JSpinner();
        txtCodProdS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnBuscarS = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaSalidas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCodProdA = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnBuscarA = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaAlertas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaMovimientos = new javax.swing.JTable();
        txtCodProdV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnBuscarV = new javax.swing.JButton();
        btnVerMovimientos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("Control de existencias");

        jLabel2.setText("Código de producto:");

        btnBuscarE.setText("Buscar");
        btnBuscarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEActionPerformed(evt);
            }
        });

        tablaEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Stock inicial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEntradas);

        jLabel3.setText("Cantidad de ingreso:");

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        spnE.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodProdE)
                            .addComponent(spnE, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarE)
                            .addComponent(btnIngresar))))
                .addContainerGap(346, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodProdE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIngresar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Entradas de producto", jPanel2);

        btnConsumir.setText("Consumir");
        btnConsumir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsumirActionPerformed(evt);
            }
        });

        spnS.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel4.setText("Código de producto:");

        btnBuscarS.setText("Buscar");
        btnBuscarS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSActionPerformed(evt);
            }
        });

        tablaSalidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Stock inicial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaSalidas);

        jLabel5.setText("Cantidad de salida:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodProdS)
                            .addComponent(spnS, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarS)
                            .addComponent(btnConsumir))))
                .addContainerGap(346, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodProdS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsumir))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Salidas de producto", jPanel3);

        jLabel6.setText("Código de producto:");

        btnBuscarA.setText("Buscar");

        tablaAlertas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Stock inicial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaAlertas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodProdA, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarA)))
                .addContainerGap(346, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodProdA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarA))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Alertas de stock bajo", jPanel1);

        tablaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Código", "Producto", "Cantidad", "Tipo de movimiento", "Fecha de registro", "Hora de registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tablaMovimientos);
        if (tablaMovimientos.getColumnModel().getColumnCount() > 0) {
            tablaMovimientos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablaMovimientos.getColumnModel().getColumn(3).setPreferredWidth(15);
            tablaMovimientos.getColumnModel().getColumn(4).setPreferredWidth(90);
        }

        jLabel7.setText("Código de producto:");

        btnBuscarV.setText("Buscar");
        btnBuscarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVActionPerformed(evt);
            }
        });

        btnVerMovimientos.setText("Ver todos");
        btnVerMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerMovimientosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtCodProdV, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarV)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerMovimientos))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodProdV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarV)
                    .addComponent(btnVerMovimientos))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Visualizar movimientos", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEActionPerformed
        limpiarTabla(tablaEntradas);
        buscarProducto(txtCodProdE, "listaProductos", tablaEntradas);
    }//GEN-LAST:event_btnBuscarEActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        modificarCantidad(txtCodProdE, spnE, "listaProductos", tablaEntradas, true);
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnBuscarSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSActionPerformed
        limpiarTabla(tablaEntradas);
        buscarProducto(txtCodProdS, "listaProductos", tablaSalidas);
    }//GEN-LAST:event_btnBuscarSActionPerformed

    private void btnConsumirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsumirActionPerformed
        modificarCantidad(txtCodProdS, spnS, "listaProductos", tablaSalidas, false);
    }//GEN-LAST:event_btnConsumirActionPerformed

    private void btnBuscarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVActionPerformed
        
    }//GEN-LAST:event_btnBuscarVActionPerformed

    private void btnVerMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerMovimientosActionPerformed
        obtenerDatos(tablaMovimientos, "movimientos");
    }//GEN-LAST:event_btnVerMovimientosActionPerformed

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
            java.util.logging.Logger.getLogger(controlExistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(controlExistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(controlExistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(controlExistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new controlExistencias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarA;
    private javax.swing.JButton btnBuscarE;
    private javax.swing.JButton btnBuscarS;
    private javax.swing.JButton btnBuscarV;
    private javax.swing.JButton btnConsumir;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnVerMovimientos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnE;
    private javax.swing.JSpinner spnS;
    private javax.swing.JTable tablaAlertas;
    private javax.swing.JTable tablaEntradas;
    private javax.swing.JTable tablaMovimientos;
    private javax.swing.JTable tablaSalidas;
    private javax.swing.JTextField txtCodProdA;
    private javax.swing.JTextField txtCodProdE;
    private javax.swing.JTextField txtCodProdS;
    private javax.swing.JTextField txtCodProdV;
    // End of variables declaration//GEN-END:variables
}
