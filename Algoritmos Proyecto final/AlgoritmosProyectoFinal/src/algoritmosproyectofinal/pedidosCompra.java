/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package algoritmosproyectofinal;

import java.awt.Component;
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
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author eduar
 */
public class pedidosCompra extends javax.swing.JFrame {

    /**
     * Creates new form pedidosCompra
     */
    public pedidosCompra() {
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos("listaPedidosCompra", tablaPedidos);
    }
    
    // Verificar usuario de sesión activa
    public String verificarUsuario(){
        try {
            // Creación ficticia del archivo
            File file = new File("data/sesionActiva.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            data = reader.readLine();
            
            // Cierre de archivo de lectura
            reader.close();
            return data;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlExistencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    // Función para cargar los datos completos de productos
    public void cargarDatos(String fileroot, JTable table){
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileroot + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(newRow);

                // Creación de objeto Combobox
                String[] options = {"Pendiente", "En curso", "Completado"};
                JComboBox<String> comboBox = new JComboBox<>(options);

                // Insersión de objeto dentro de la tabla
                TableColumn optionColumn = table.getColumnModel().getColumn(3);
                optionColumn.setCellEditor(new DefaultCellEditor(comboBox));
            }
            
            // Cierre de archivo de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función para cargar los detalles de un pedido
    public void cargarDetalles(String fileroot, JTable table, String noP){
        try {
            // Creación ficticia del archivo
            File file = new File("data/" + fileroot + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // condición para filtrar por no. de pedido los detalles
                if (newRow[0].toString().compareTo(noP) == 0){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    
                    // Nueva linea
                    newRow = new Object[]{newRow[1], newRow[2], newRow[3], newRow[4], Integer.parseInt(newRow[5].toString())};
                    model.addRow(newRow);
                    table.setRowHeight(25);
                    
                    // Creación de objeto Combobox
                    JSpinner spnIngreso = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

                    // Insersión de objeto dentro de la tabla
                    TableColumn optionColumn = table.getColumnModel().getColumn(4);
                    optionColumn.setCellEditor(new SpinnerEditor(spnIngreso));

                }
                
            }
            
            // Cierre de archivo de lectura
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Clase para usar el JSpinner como editor de celdas
    class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private JSpinner spinner;

        public SpinnerEditor(JSpinner spinner) {
            // Usamos el JSpinner ya creado
            this.spinner = spinner; 
        }

        @Override
        public Object getCellEditorValue() {
            // Devuelve el valor actual del JSpinner
            return spinner.getValue();  
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Establecer el valor actual en el JSpinner
            spinner.setValue(value);
            return spinner;  // Retornar el JSpinner como componente de la celda
        }
    }
    
    // Función para eliminar detalles de compra
    public void eliminarDetalleC(String noP){
        try {
            // Creación ficticia del archivo
            File file = new File("data/detallesCompra.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/detallesCompraCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // condición para filtrar por no. de pedido los detalles
                if (newRow[0].toString().compareTo(noP) != 0){
                    // Escritura de nueva linea
                    writer.write(data + "\n");
                }
                
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función para eliminar el registro de pedido.
    public void eliminarRegP(String noP){
        try {
            // Creación ficticia del archivo
            File file = new File("data/listaPedidosCompra.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/listaPedidosCompraCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // condición para filtrar por no. de pedido los detalles
                if (newRow[0].toString().compareTo(noP) != 0){
                    // Escritura de nueva linea
                    writer.write(data + "\n");
                }
                
                
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función para eliminar el registro de pedido.
    public void actualizarEstatus(String noP, String Estatus){
        try {
            // Creación ficticia del archivo
            File file = new File("data/listaPedidosCompra.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
            
            // Creación ficticia del archivo
            File fileCopyable = new File("data/listaPedidosCompraCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Recopilación de datos
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // condición para filtrar por no. de pedido los detalles
                if (newRow[0].toString().compareTo(noP) == 0){
                    // Simplificación de variables
                    String noReg = newRow[0].toString();
                    String fechaP = newRow[1].toString();
                    String fechaE = newRow[2].toString();
                    String total = newRow[4].toString();
                    
                    // Escritura de nueva linea
                    data = noReg + "%/%" + fechaP + "%/%" + fechaE + "%/%" + Estatus + "%/%" + total;
                }
                // Escritura de nueva linea
                writer.write(data + "\n");
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Función para cancelar pedido
    public void cancelarPedidoC(String noP){
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }
        
        eliminarDetalleC(noP);
        eliminarRegP(noP);
        
        // Mensaje de aviso al usuario
        JOptionPane.showMessageDialog(null, "Se ha cancelado el pedido", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Función para modificar la cantidad
    public void modificarCantidad(String prod, String noP, int cant, String fileName, JTable table){
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
                
                if (newRow[1].toString().compareTo(prod) == 0){
                    int value = 0;
                    
                    value = (Integer.parseInt(newRow[6].toString()) + cant);
                   
                    // Selección de datos
                    data = newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString() + "%/%" + newRow[4].toString() + "%/%" + newRow[5].toString() + "%/%" + value;
                    registrarRecepcion(noP, prod, cant);
                }
                writer.write(data + "\n");
                
            }
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
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
    
    // Función para registrar recepción de productos
    public void registrarRecepcion(String reg, String prod, int value){
        try {
            // Creación ficticia del archivo
            File file = new File("data/recepcion.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = "";
                        
            // Creación ficticia del archivo
            File fileCopyable = new File("data/recepcionCopia.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileCopyable, true));
            
            // Obtener la fecha actual
            LocalDate dateNow = LocalDate.now();

            // Obtener la hora actual
            LocalTime timeNow = LocalTime.now();

            // Formatear la fecha y hora en el formato deseado
            DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Inicialización de contador de registros
            int noReg = 1;
            
            
            // Recopilación de datos del archivo original
            while ((data = reader.readLine()) != null) {   
                // Creación de objeto y asignación con separación de cadenas
                Object[] newRow = data.split("%/%");
                
                // Escritura de datos almacenados al archivo copia
                data = newRow[0].toString() + "%/%" + newRow[1].toString() + "%/%" + newRow[2].toString() + "%/%" + newRow[3].toString() + "%/%" + newRow[4].toString() + "%/%" + newRow[5].toString() + "%/%" + newRow[6].toString();
                
                writer.write(data + "\n");
                noReg = Integer.parseInt(newRow[0].toString());
                noReg++;
            }
            
            // Escritura de nueva linea de registro
            data = noReg + "%/%" + reg + "%/%" + prod + "%/%" + value + "%/%" + verificarUsuario() + "%/%" + dateNow.format(formatDate) + "%/%" + timeNow.format(formatHour);
            writer.write(data + "\n");
            
            // Cierre de acción de escritura y lectura
            reader.close();
            writer.close();
            
            // Reemplazar archivo copia con el archivo original
            Files.move(fileCopyable.toPath(), file.toPath(), REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pedidosCompra.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboProveedores = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtFechaP = new javax.swing.JTextField();
        cboProveedores1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnVerH = new javax.swing.JButton();
        btnCrearPedido = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnVerDetalles = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        btnCancelarP = new javax.swing.JButton();
        btnContabilizar = new javax.swing.JButton();
        btnActualizarE = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("Pedidos de compra");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel6.setText("Búsqueda");

        cboProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel3.setText("fecha de pedido:");

        cboProveedores1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Pendiente", "En curso", "Completado" }));
        cboProveedores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProveedores1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Estado del pedido:");

        jLabel5.setText("producto");

        jLabel2.setText("proveedor:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");

        btnVerH.setText("Ver historial ");
        btnVerH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerHActionPerformed(evt);
            }
        });

        btnCrearPedido.setText("Crear pedido");
        btnCrearPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPedidoActionPerformed(evt);
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
                        .addComponent(jLabel6)
                        .addGap(566, 566, 566))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCrearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVerH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 100, Short.MAX_VALUE))
                                    .addComponent(cboProveedores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtFechaP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboProveedores1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(34, 34, 34))))
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
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboProveedores1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnVerH)
                    .addComponent(btnCrearPedido))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Gestión");

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Pedido", "Fecha de pedido", "Fecha de entrega", "Estado del pedido", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPedidosMouseClicked(evt);
            }
        });
        tablaPedidos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaPedidosPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPedidos);
        if (tablaPedidos.getColumnModel().getColumnCount() > 0) {
            tablaPedidos.getColumnModel().getColumn(0).setHeaderValue("No. Pedido");
            tablaPedidos.getColumnModel().getColumn(1).setHeaderValue("Fecha de pedido");
            tablaPedidos.getColumnModel().getColumn(2).setHeaderValue("Fecha de entrega");
            tablaPedidos.getColumnModel().getColumn(3).setHeaderValue("Estado del pedido");
            tablaPedidos.getColumnModel().getColumn(4).setHeaderValue("Total");
        }

        jLabel8.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Haga clic en la columna de estado del pedido para actualizar");

        btnVerDetalles.setText("Ver detalles del pedido");
        btnVerDetalles.setEnabled(false);
        btnVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetallesActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Detalles");

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Proveedor", "Precio", "Ingreso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaDetalles);

        btnCancelarP.setText("Cancelar pedido");
        btnCancelarP.setEnabled(false);
        btnCancelarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPActionPerformed(evt);
            }
        });

        btnContabilizar.setText("Contabilizar");
        btnContabilizar.setEnabled(false);
        btnContabilizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContabilizarActionPerformed(evt);
            }
        });

        btnActualizarE.setText("Actualizar estatus");
        btnActualizarE.setEnabled(false);
        btnActualizarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("Haga clic en la columna de ingreso para actualizar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 103, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(115, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnCancelarP)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnVerDetalles)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnActualizarE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)))
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnContabilizar)
                                        .addGap(95, 95, 95)))
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalles)
                    .addComponent(btnCancelarP)
                    .addComponent(btnContabilizar)
                    .addComponent(btnActualizarE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedores1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProveedores1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tablaPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPedidosMouseClicked
        btnVerDetalles.setEnabled(true);
        btnCancelarP.setEnabled(true);
        btnActualizarE.setEnabled(true);
    }//GEN-LAST:event_tablaPedidosMouseClicked

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        
        // Obtener la fila seleccionda de la tabla
        int row = tablaPedidos.getSelectedRow();
        
        // Obtener el no de pedido
        String noP = model.getValueAt(row, 0).toString();
   
        limpiarTabla(tablaDetalles);
        cargarDetalles("detallesCompra", tablaDetalles, String.valueOf(noP));
        btnContabilizar.setEnabled(true);
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnCancelarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPActionPerformed
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        
        // Obtener la fila seleccionda de la tabla
        int row = tablaPedidos.getSelectedRow();
        
        // Obtener el no de pedido
        String noP = model.getValueAt(row, 0).toString();
        
        // Obtener el estatus del pedido
        String Status = model.getValueAt(row, 3).toString();
        
        // Condición para comparar el Estatus y evitar cancelar el pedido en un estado avanzado
        if (Status.compareTo("Pendiente") != 0){
            // Mensaje de aviso al usuario
            JOptionPane.showMessageDialog(null, "El pedido se encuentra en un estado avanzado por lo que ya no es posible cancelarlo", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        cancelarPedidoC(noP);
        limpiarTabla(tablaDetalles);
        limpiarTabla(tablaPedidos);
        
        cargarDatos("listaPedidosCompra", tablaPedidos);
    }//GEN-LAST:event_btnCancelarPActionPerformed

    private void btnContabilizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContabilizarActionPerformed
        // Segumiento de acción por parte del usuario
        int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de contabilizar el pedido?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) {
            return;
        }
        
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
        
        // obtener el recuento de columnas
        int row = model.getRowCount()-1;
        
        // Determinación de modelo de tabla
        DefaultTableModel modelP = (DefaultTableModel) tablaPedidos.getModel();
                    
        // Obtener la fila seleccionda de la tabla
        int rowP = tablaPedidos.getSelectedRow();
                    
        // Obtener el no de pedido
        String noP = modelP.getValueAt(rowP, 0).toString();
        
        // ciclo de repetición para remover las filas una por una
        for (int i = 0; i <= row; i++) {
            // Obtener campos de la tabla
            int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString());
            int ingreso = Integer.parseInt(model.getValueAt(i, 4).toString());
            
            String prod = model.getValueAt(row, 0).toString();    
            
            
            // Determinación de que cada uno de los productos sean iguales a la cantidad
            if (ingreso < cantidad) {
                // Mensaje de aviso al usuario
                JOptionPane.showMessageDialog(null, "Faltan productos", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
                
            } else {
                if (ingreso > cantidad){
                // Mensaje de aviso al usuario
                JOptionPane.showMessageDialog(null, "Hay un exceso de productos", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
                } else {
                    modificarCantidad(prod, noP, cantidad, "listaProductos", tablaPedidos);
                }
            }
        }
        limpiarTabla(tablaDetalles);
        actualizarEstatus(noP, "Completado");
        limpiarTabla(tablaPedidos);
        cargarDatos("listaPedidosCompra", tablaPedidos);
        
        // Mensaje de aviso al usuario
        JOptionPane.showMessageDialog(null, "Se ha contabilizado el pedido", "Información", JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_btnContabilizarActionPerformed

    private void btnActualizarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEActionPerformed
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        
        // Obtener la fila seleccionda de la tabla
        int row = tablaPedidos.getSelectedRow();
        
        // Obtener el no de pedido
        String noP = model.getValueAt(row, 0).toString();
        
        // Obtener el estatus del pedido
        String Status = model.getValueAt(row, 3).toString();
        
        actualizarEstatus(noP, Status);
        limpiarTabla(tablaPedidos);
        cargarDatos("listaPedidosCompra", tablaPedidos);
    }//GEN-LAST:event_btnActualizarEActionPerformed

    private void tablaPedidosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaPedidosPropertyChange
        // Determinación de modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        
        // Obtener la fila seleccionda de la tabla
        int row = tablaPedidos.getSelectedRow();
        
        // Condición para evitar fila no seleccionada dentro de la tabla
        if (row - 1 < 0){
            return;
        }
        
        // Obtener el no de pedido
        String noP = model.getValueAt(row, 0).toString();
        
        // Obtener el estatus del pedido
        String Status = model.getValueAt(row, 3).toString();
        
        actualizarEstatus(noP, Status);
        limpiarTabla(tablaPedidos);
        cargarDatos("listaPedidosCompra", tablaPedidos);
    }//GEN-LAST:event_tablaPedidosPropertyChange

    private void btnVerHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerHActionPerformed
        new pedidosRecepcion().setVisible(true);
    }//GEN-LAST:event_btnVerHActionPerformed

    private void btnCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPedidoActionPerformed
        new pedidosCreacion().setVisible(true);
    }//GEN-LAST:event_btnCrearPedidoActionPerformed

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
            java.util.logging.Logger.getLogger(pedidosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pedidosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pedidosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pedidosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pedidosCompra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarE;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarP;
    private javax.swing.JButton btnContabilizar;
    private javax.swing.JButton btnCrearPedido;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JButton btnVerH;
    private javax.swing.JComboBox<String> cboProveedores;
    private javax.swing.JComboBox<String> cboProveedores1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaPedidos;
    private javax.swing.JTextField txtFechaP;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
